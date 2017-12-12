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

import java.util.ArrayList;

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
                Intent intent_delete = new Intent(ManagementActivity.this,DeleteCommodityActivity.class);
                startActivity(intent_delete);
                break;
            case R.id.btn_cd_update:
                Intent intent_update = new Intent(ManagementActivity.this,UpdateCommodityActivity.class);
                startActivity(intent_update);
                break;
            case R.id.btn_cd_query:
                Intent intent_query = new Intent(ManagementActivity.this,ShowCommodityListActivity.class);
                startActivity(intent_query);
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
            commodity = new Commodity(id,name,price,num);
        }

        return commodity;
    }

    //删除商品
    public static void deleteCommodity(String queryString){
        db.delete("Commodity", "id=? or name=?", new String[]{queryString,queryString});
    }

    //更新商品(根据id)
    public static void updateCommodity(String id, ContentValues values){
        db.update("Commodity", values, "id=?",new String[]{id});
    }

    //商品显示
    public static ArrayList<Commodity> showCommodity(int mode){
        ArrayList<Commodity> commodityArrayList = new ArrayList<>();
        switch (mode){
            //入库顺序
            case 1:
                commodityArrayList.clear();
                Cursor cursor_1 = db.query("Commodity",null, null, null,
                        null, null, null);
                if(cursor_1.moveToFirst()){
                    do{
                        String id = cursor_1.getString(cursor_1.getColumnIndex("id"));
                        String name = cursor_1.getString(cursor_1.getColumnIndex("name"));
                        Float price = cursor_1.getFloat(cursor_1.getColumnIndex("price"));
                        int num = cursor_1.getInt(cursor_1.getColumnIndex("num"));
                        Commodity commodity = new Commodity(id,name,price,num);
                        commodityArrayList.add(commodity);
                    }while (cursor_1.moveToNext());
                }
                cursor_1.close();
                break;
            //数量降序
            case 2:
                commodityArrayList.clear();
                Cursor cursor_2 = db.query("Commodity",null, null, null,
                        null, null, "num desc");
                if(cursor_2.moveToFirst()){
                    do{
                        String id = cursor_2.getString(cursor_2.getColumnIndex("id"));
                        String name = cursor_2.getString(cursor_2.getColumnIndex("name"));
                        Float price = cursor_2.getFloat(cursor_2.getColumnIndex("price"));
                        int num = cursor_2.getInt(cursor_2.getColumnIndex("num"));
                        Commodity commodity = new Commodity(id,name,price,num);
                        commodityArrayList.add(commodity);
                    }while (cursor_2.moveToNext());
                }
                cursor_2.close();
                break;
            //数量升序
            case 3:
                commodityArrayList.clear();
                Cursor cursor_3 = db.query("Commodity",null, null, null,
                        null, null, "num");
                if(cursor_3.moveToFirst()){
                    do{
                        String id = cursor_3.getString(cursor_3.getColumnIndex("id"));
                        String name = cursor_3.getString(cursor_3.getColumnIndex("name"));
                        Float price = cursor_3.getFloat(cursor_3.getColumnIndex("price"));
                        int num = cursor_3.getInt(cursor_3.getColumnIndex("num"));
                        Commodity commodity = new Commodity(id,name,price,num);
                        commodityArrayList.add(commodity);
                    }while (cursor_3.moveToNext());
                }
                cursor_3.close();
                break;
            //价格降序
            case 4:
                commodityArrayList.clear();
                Cursor cursor_4 = db.query("Commodity",null, null, null,
                        null, null, "price desc");
                if(cursor_4.moveToFirst()){
                    do{
                        String id = cursor_4.getString(cursor_4.getColumnIndex("id"));
                        String name = cursor_4.getString(cursor_4.getColumnIndex("name"));
                        Float price = cursor_4.getFloat(cursor_4.getColumnIndex("price"));
                        int num = cursor_4.getInt(cursor_4.getColumnIndex("num"));
                        Commodity commodity = new Commodity(id,name,price,num);
                        commodityArrayList.add(commodity);
                    }while (cursor_4.moveToNext());
                }
                cursor_4.close();
                break;
            //价格升序
            case 5:
                commodityArrayList.clear();
                Cursor cursor_5 = db.query("Commodity",null, null, null,
                        null, null, "price");
                if(cursor_5.moveToFirst()){
                    do{
                        String id = cursor_5.getString(cursor_5.getColumnIndex("id"));
                        String name = cursor_5.getString(cursor_5.getColumnIndex("name"));
                        Float price = cursor_5.getFloat(cursor_5.getColumnIndex("price"));
                        int num = cursor_5.getInt(cursor_5.getColumnIndex("num"));
                        Commodity commodity = new Commodity(id,name,price,num);
                        commodityArrayList.add(commodity);
                    }while (cursor_5.moveToNext());
                }
                cursor_5.close();
                break;
            default:
        }
        return commodityArrayList;
    }
}
