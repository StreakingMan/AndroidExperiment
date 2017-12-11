package com.maxstudio.androidexperiment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    private Button btn_shiyan4;
    private Button btn_shiyan6;
    private Button btn_shiyan10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        btn_shiyan4 = findViewById(R.id.btn_shiyan4);
        btn_shiyan6 = findViewById(R.id.btn_shiyan6);
        btn_shiyan10 = findViewById(R.id.btn_shiyan10);
        btn_shiyan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HelloWorldActivity.class);
                startActivityForResult(intent,4);
            }
        });
        btn_shiyan6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddCommodityActivity.class);
                startActivityForResult(intent,6);
            }
        });
        btn_shiyan10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ManagementActivity.class);
                startActivityForResult(intent,10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 4:
                if(resultCode==RESULT_OK){
                    btn_shiyan4.setTextColor(0XffFFCDD2);
                    btn_shiyan4.setText("实验四（已读）");
                }
                break;
            case 6:
                if(resultCode==RESULT_OK){
                    btn_shiyan6.setTextColor(0XffFFCDD2);
                    btn_shiyan6.setText("实验六（已读）");
                }
                break;
            case 10:
                if(resultCode==RESULT_OK){
                    btn_shiyan10.setTextColor(0XffFFCDD2);
                    btn_shiyan10.setText("实验十（已读）");
                }
                break;
            default:
        }
    }


}
