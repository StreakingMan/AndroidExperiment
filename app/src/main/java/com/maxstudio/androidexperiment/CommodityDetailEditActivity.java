package com.maxstudio.androidexperiment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class CommodityDetailEditActivity extends Activity {
    private EditText edt_id;
    private EditText edt_name;
    private EditText edt_price;
    private EditText edt_num;
    private Bundle bundle;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean isReadSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_detail_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        //Intent intent = this.getIntent();
        //bundle = intent.getExtras();
        pref = getSharedPreferences("Commodity",MODE_PRIVATE);
        editor = getSharedPreferences("Commodity",MODE_PRIVATE).edit();
        initView();
        initData();
    }

    private void initView() {
        edt_id = findViewById(R.id.edt_cd_id);
        edt_name = findViewById(R.id.edt_cd_name);
        edt_price = findViewById(R.id.edt_cd_price);
        edt_num = findViewById(R.id.edt_cd_num);
    }

    private void initData() {
        edt_name.setText(pref.getString("cd_name","name"));
        edt_id.setText(pref.getString("cd_id","id"));
        edt_price.setText(Float.toString(pref.getFloat("cd_price",0)));
        edt_num.setText(Integer.toString(pref.getInt("cd_num",0)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_save_back,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                getEditTextData();
                if(isReadSuccess){
                    ContentValues values = new ContentValues();
                    values.put("id", edt_id.getText().toString());
                    values.put("name", edt_name.getText().toString());
                    values.put("price", Float.parseFloat(edt_price.getText().toString()));
                    values.put("num", Integer.parseInt(edt_num.getText().toString()) );
                    ManagementActivity.updateCommodity(pref.getString("cd_id","id"),values);
                    //修改后保存此页面临时id
                    editor.putString("cd_id",edt_id.getText().toString());
                    values.clear();
                    Toast.makeText(CommodityDetailEditActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    //非空判断
    private void getEditTextData(){
        if(edt_id.length()!=0){
            if(edt_name.length()!=0){
                if(edt_price.length()!=0){
                    if(Float.parseFloat(edt_price.getText().toString())>0){
                        if(edt_num.length()!=0){
                            if(Integer.parseInt(edt_num.getText().toString())>0){
                                isReadSuccess = true;
                            }else {
                                Toast.makeText(CommodityDetailEditActivity.this,"数量必须大于0！",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(CommodityDetailEditActivity.this,"数量不能为空！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CommodityDetailEditActivity.this,"价格必须大于0！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CommodityDetailEditActivity.this,"价格不能为空！",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(CommodityDetailEditActivity.this,"名称不能为空！",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(CommodityDetailEditActivity.this,"编号不能为空！",Toast.LENGTH_SHORT).show();
        }
    }

}
