package com.example.myproject;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.Calendar;
import java.util.Date;

public class dairyRead extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dairy_read);


        try {
            getStoragePermission(this);                                                   //尝试获取读写权限
        } catch (Exception e) {
            e.printStackTrace();
        }


        File root_folder = getExternalFilesDir("");                                       //获取对应包下的files目录
        File dairy = new File(root_folder + "/dairy.txt");
        if (!dairy.exists()) {                                                                   //不存在dairy文件，新建
            try {
                dairy.createNewFile();
            } catch (Exception e) {
                Toast.makeText(dairyRead.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);;
        EditText dairy_readDate = findViewById(R.id.dairy_read_date);
        dairy_readDate.setText(calendar.get(Calendar.YEAR)+"-0"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));

        Button dairy_read_confirmDate = findViewById(R.id.btn_dairy_read_confirmDate);
        dairy_read_confirmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetDate = dairy_readDate.getText().toString();
                String tempString = null,goalString = null;
                try{
                    TextView textView = findViewById(R.id.dairy_readTextview);
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(dairy));
                    while((tempString = bufferedReader.readLine()) != null)
                    {
                        if(tempString.length() < 10)continue;
                        if(tempString.substring(0,10).equals(targetDate))break;
                    }

                    if(tempString == null){
                        textView.setText("找不到对应的日记。");
                        return;
                    }

                    goalString = tempString;
                    tempString = bufferedReader.readLine();
                    while(tempString != null && !(tempString.substring(0,4).equals(targetDate.substring(0,4)))){
                        goalString = goalString.concat('\n'+tempString);
                        tempString = bufferedReader.readLine();
                    }

                    textView.setText(goalString);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public void getStoragePermission(Activity activity){                                            //获取存储权限
        int permission = ActivityCompat.checkSelfPermission(activity,"android.permission.WRITE_EXTERNAL_STORAGE");
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{ "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" }, 1);
            }
        }
}
