 package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class launch extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);        //全屏显示
        getSupportActionBar().hide();                                            //隐藏标题栏
        setContentView(R.layout.launch);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);                                        //程序休眠
                    Intent intent = new Intent(launch.this, main_activity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}

