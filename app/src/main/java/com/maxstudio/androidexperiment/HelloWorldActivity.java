package com.maxstudio.androidexperiment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

public class HelloWorldActivity extends Activity {
    private Button btn_read;
    private boolean isRead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        btn_read = findViewById(R.id.btn_read);
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK,new Intent());
                finish();
            }
        });
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
                Toast.makeText(HelloWorldActivity.this,"已查看实验",Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
