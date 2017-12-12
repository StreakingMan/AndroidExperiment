package com.maxstudio.androidexperiment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ShowCommodityListActivity extends Activity implements SearchView.OnQueryTextListener{
    private SearchView searchView;
    private ArrayList<Commodity> cdList = new ArrayList<>();
    private ArrayList<Commodity> tempList = new ArrayList<>();
    private ListView lv_commodity;
    private CommodityListAdapter cdAdapter;
    private SharedPreferences.Editor editor;
    boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_commodity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        initList();
        editor = getSharedPreferences("Commodity",MODE_PRIVATE).edit();
        //list 点击进入详情
        lv_commodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //判断是否搜索列表
                if(isSearching){
                    editor.putString("cd_id",tempList.get(i).getId());
                    editor.putString("cd_name",tempList.get(i).getName());
                    editor.putFloat("cd_price",tempList.get(i).getPrice());
                    editor.putInt("cd_num",tempList.get(i).getNum());
                    editor.apply();
                    Intent intent = new Intent(ShowCommodityListActivity.this,CommodityDetailActivity.class);
                    startActivity(intent);
                }else {
                    editor.putString("cd_id",cdList.get(i).getId());
                    editor.putString("cd_name",cdList.get(i).getName());
                    editor.putFloat("cd_price",cdList.get(i).getPrice());
                    editor.putInt("cd_num",cdList.get(i).getNum());
                    editor.apply();
                    Intent intent = new Intent(ShowCommodityListActivity.this,CommodityDetailActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    //列表初始化
    private void initList(){
        cdList.clear();
        cdList = ManagementActivity.showCommodity(1);
        lv_commodity = findViewById(R.id.lv_cd_list);
        cdAdapter = new CommodityListAdapter(ShowCommodityListActivity.this,
                R.layout.item_commodity,cdList);
        lv_commodity.setAdapter(cdAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_back,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        View view = MenuItemCompat.getActionView(menuItem);
        if (view != null) {
            searchView = (SearchView) view;
            //设置SearchView 的查询回调接口
            searchView.setOnQueryTextListener(this);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    initList();
                    isSearching = false;
                    return false;
                }
            });
        }
        return true;
    }

    //toolbar相关操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.normal:
                notifyListView(1);
                break;
            case R.id.num_desc:
                notifyListView(2);
                break;
            case R.id.num_asc:
                notifyListView(3);
                break;
            case R.id.price_desc:
                notifyListView(4);
                break;
            case R.id.price_asc:
                notifyListView(5);
                break;
            default:
                break;
        }
        return true;
    }

    //刷新列表
    private void notifyListView(int mode){
        cdList.clear();
        cdList = ManagementActivity.showCommodity(mode);
        cdAdapter = new CommodityListAdapter(ShowCommodityListActivity.this,
                R.layout.item_commodity,cdList);
        lv_commodity.setAdapter(cdAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        isSearching = false;
        return true;
    }

    //搜索动态刷新
    @Override
    public boolean onQueryTextChange(String newText) {
        int length = newText.length();
        isSearching = true;
        tempList.clear();
        for(int i=0;i<cdList.size();i++){
            try{
                if(cdList.get(i).getId().substring(0,length).equals(newText)){
                    tempList.add(cdList.get(i));
                }else {
                    try{if(cdList.get(i).getName().substring(0,length).equals(newText)){
                        tempList.add(cdList.get(i));
                    }
                    }catch (Exception ex2){
                    }
                }
            }catch (Exception ex){
            }
        }
        cdAdapter = new CommodityListAdapter(ShowCommodityListActivity.this,
                R.layout.item_commodity,tempList);
        lv_commodity.setAdapter(cdAdapter);
        return true;
    }

}
