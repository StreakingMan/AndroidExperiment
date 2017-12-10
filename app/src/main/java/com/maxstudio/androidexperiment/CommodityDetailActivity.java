package com.maxstudio.androidexperiment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class CommodityDetailActivity extends Activity {
    private TextView tv_cd_name;
    private TextView tv_cd_id;
    private TextView tv_cd_price;
    private TextView tv_cd_num;
    private Bundle bundle;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        //Intent intent = this.getIntent();
        //bundle = intent.getExtras();
        pref = getSharedPreferences("Commodity",MODE_PRIVATE);
        initView();
        initData();
    }

    private void initView() {
        tv_cd_name = findViewById(R.id.tv_cd_name);
        tv_cd_id = findViewById(R.id.tv_cd_id);
        tv_cd_price = findViewById(R.id.tv_cd_price);
        tv_cd_num = findViewById(R.id.tv_cd_num);
    }

    private void initData() {
        /*tv_cd_name.setText(bundle.getString("cd_name","name"));
        tv_cd_id.setText("编号："+bundle.getString("cd_id","id"));
        tv_cd_price.setText("价格："+Float.toString(bundle.getFloat("cd_price",0)));
        tv_cd_num.setText("数量："+Integer.toString(bundle.getInt("cd_num",0)));*/

        tv_cd_name.setText(pref.getString("cd_name","name"));
        tv_cd_id.setText("编号："+pref.getString("cd_id","id"));
        tv_cd_price.setText("价格："+Float.toString(pref.getFloat("cd_price",0)));
        tv_cd_num.setText("数量："+Integer.toString(pref.getInt("cd_num",0)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_back,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
