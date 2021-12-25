package com.example.myproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class main_activity extends Activity {
    public String morning = "清晨的阳光，一如初升的希望";         //早上的问候语
    public String noon = "是不是忙碌了一上午了呢？\n要不奖励一下自己？";         //中午的问候语
    public String night = "今天的黄昏，是否和昨日一样美丽呢？";         //晚上的问候语
    public String midnight = "半夜还不睡觉啊，这是在期待着什么呢？";      //半夜的问候语
    public String[] random_welcome = {"且听清风吟，且觅佳人音","冲冲冲！！！","你要学会忍受孤独，才能走得更远"};
    Button dairyWriteButton,dairyReadButton;
    Calendar calendar = Calendar.getInstance();
    Date date_now;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        dairyWriteButton = findViewById(R.id.btn_toDairyWrite);
        dairyWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_activity.this,dairy.class);
                startActivity(intent);
            }
        });

        dairyReadButton = findViewById(R.id.btn_toDairyRead);
        dairyReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_activity.this,dairyRead.class);
                startActivity(intent);
            }
        });


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //getWindow().setStatusBarColor(Color.BLACK);                                             //设置状态栏颜色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        TextView textView = findViewById(R.id.textView);                                            //设置字体
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font/lishuziti.ttf");
        textView.setTypeface(typeface);

        date_now=new Date();
        calendar.setTime(date_now);
        show_welcome(calendar.get(calendar.HOUR_OF_DAY));
    }
    private void show_welcome(int time){
        switch (time)
        {
            case 23:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:Toast.makeText(main_activity.this,midnight,Toast.LENGTH_LONG).show();break;
            case 6:
            case 7:
            case 8:Toast.makeText(main_activity.this,morning,Toast.LENGTH_LONG).show();break;
            case 11:
            case 12:
            case 13:Toast.makeText(main_activity.this,noon,Toast.LENGTH_LONG).show();break;
            case 17:
            case 18:
            case 19:Toast.makeText(main_activity.this,night,Toast.LENGTH_LONG).show();break;
            default:
                Random random = new Random();
                Toast.makeText(main_activity.this,random_welcome[random.nextInt(random_welcome.length)],Toast.LENGTH_LONG).show();
        }
    }
}
