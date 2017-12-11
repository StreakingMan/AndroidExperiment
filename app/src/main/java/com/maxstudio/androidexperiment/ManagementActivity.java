package com.maxstudio.androidexperiment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class ManagementActivity extends Activity implements View.OnClickListener{
    private static MyDatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    private Button btn_cd_add;
    private Button btn_cd_delete;
    private Button btn_cd_update;
    private Button btn_cd_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        //创建数据库
        dbHelper = new MyDatabaseHelper(this, "ShopStore.db", null, 1);
        db = dbHelper.getWritableDatabase();

        btn_cd_add = findViewById(R.id.btn_cd_add);
        btn_cd_delete = findViewById(R.id.btn_cd_delete);
        btn_cd_update = findViewById(R.id.btn_cd_update);
        btn_cd_query = findViewById(R.id.btn_cd_query);

        btn_cd_add.setOnClickListener(this);
        btn_cd_delete.setOnClickListener(this);
        btn_cd_update.setOnClickListener(this);
        btn_cd_query.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_eye_back,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.eye:
                setResult(RESULT_OK,new Intent());
                Toast.makeText(ManagementActivity.this,"已查看实验",Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cd_add:
                Intent intent_add = new Intent(ManagementActivity.this,AddCommodityActivity.class);
                startActivity(intent_add);
                break;
            case R.id.btn_cd_delete:
                break;
            case R.id.btn_cd_update:
                break;
            case R.id.btn_cd_query:
                break;
            default:
                break;
        }
    }

    //添加商品
    public static void addCommodity(ContentValues values){
        db.insert("Commodity", null, values);
    }

    //商品查重
    public static boolean isExist(String queryString){
        boolean isHave = false;
        Cursor cursor = db.query("Commodity", null, "id=? or name=?",
                new String[]{queryString,queryString}, null, null, null);
        while (cursor.moveToNext()){
            isHave = true;
        }
        return isHave;
    }

    //查询某个商品所有信息
    public static Commodity queryCommodity(String queryString){
        Commodity commodity = new Commodity();
        Cursor cursor = db.query("Commodity", null, "id=? or name=?",
                new String[]{queryString,queryString}, null, null, null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            Float price = cursor.getFloat(2);
            int num = cursor.getInt(3);

        }

        return commodity;
    }

    //删除商品
}
