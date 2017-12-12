package com.maxstudio.androidexperiment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;

public class AddCommodityActivity extends Activity implements View.OnClickListener{
    private EditText edt_id;
    private EditText edt_name;
    private EditText edt_price;
    private EditText edt_num;
    private String cd_id;
    private String cd_name;
    private Float cd_price;
    private int cd_num;
    private Bundle bundle = new Bundle();
    private SharedPreferences.Editor editor;
    boolean isReadSuccess = false;
    private MyDatabaseHelper dbHelper;
    private File dataBaseFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commodity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        initView();
        initEvent();
        dataBaseFile = new File("/data/data/com.maxstudio.androidexperiment/databases/ShopStore.db");
        editor = getSharedPreferences("Commodity",MODE_PRIVATE).edit();
        dbHelper = new MyDatabaseHelper(this, "ShopStore.db", null, 1);
    }

    private void initView() {
        edt_id = findViewById(R.id.edt_cd_id);
        edt_name = findViewById(R.id.edt_cd_name);
        edt_price = findViewById(R.id.edt_cd_price);
        edt_num = findViewById(R.id.edt_cd_num);
    }

    private void initEvent() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.btn_cd_add:
                getEditTextData();
                Intent intent_cd_detail = new Intent(AddCommodityActivity.this,CommodityDetailActivity.class);
                //intent_cd_detail.putExtras(bundle);
                if(isReadSuccess){
                    startActivity(intent_cd_detail);
                }
                break;*/
            /*case R.id.btn_read:
                setResult(RESULT_OK,new Intent());
                finish();*/
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add_eye_back,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                getEditTextData();
                Intent intent_cd_detail = new Intent(AddCommodityActivity.this,CommodityDetailActivity.class);
                intent_cd_detail.putExtra("fromAdd",true);
                if(isReadSuccess){
                    //查重
                    if(!ManagementActivity.isExist(edt_id.getText().toString())){
                        if(!ManagementActivity.isExist(edt_name.getText().toString())){
                            dataPackage();
                            Toast.makeText(AddCommodityActivity.this,"商品添加成功",Toast.LENGTH_SHORT).show();
                            startActivity(intent_cd_detail);
                        }else {
                            Toast.makeText(AddCommodityActivity.this,"商品名称已存在",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddCommodityActivity.this,"商品id已存在",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.eye:
                setResult(RESULT_OK,new Intent());
                Toast.makeText(AddCommodityActivity.this,"已查看实验",Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private void getEditTextData(){
        if(edt_id.length()!=0){
            //bundle.putString("cd_id",edt_id.getText().toString());
            editor.putString("cd_id",edt_id.getText().toString());
            if(edt_name.length()!=0){
                //bundle.putString("cd_name",edt_name.getText().toString());
                editor.putString("cd_name",edt_name.getText().toString());
                if(edt_price.length()!=0){
                    if(Float.parseFloat(edt_price.getText().toString())>0){
                        //bundle.putFloat("cd_price",Float.parseFloat(edt_price.getText().toString()));
                        editor.putFloat("cd_price",Float.parseFloat(edt_price.getText().toString()));
                        if(edt_num.length()!=0){
                            if(Integer.parseInt(edt_num.getText().toString())>0){
                                //bundle.putInt("cd_num",Integer.parseInt(edt_num.getText().toString()));
                                editor.putInt("cd_num",Integer.parseInt(edt_num.getText().toString()));
                                editor.apply();
                                isReadSuccess = true;
                            }else {
                                Toast.makeText(AddCommodityActivity.this,"数量必须大于0！",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(AddCommodityActivity.this,"数量不能为空！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddCommodityActivity.this,"价格必须大于0！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddCommodityActivity.this,"价格不能为空！",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(AddCommodityActivity.this,"名称不能为空！",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(AddCommodityActivity.this,"编号不能为空！",Toast.LENGTH_SHORT).show();
        }
    }

    //数据打包储存
    private void dataPackage(){
        cd_id = edt_id.getText().toString();
        cd_name = edt_name.getText().toString();
        cd_price = Float.parseFloat(edt_price.getText().toString());
        cd_num = Integer.parseInt(edt_num.getText().toString());
        ContentValues values = new ContentValues();
        values.put("id", cd_id);
        values.put("name", cd_name);
        values.put("price", cd_price);
        values.put("num", cd_num);
        ManagementActivity.addCommodity(values);
        values.clear();
    }
}
