package com.example.myproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dairy extends Activity {


    Button button_out,button_confirm;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dairy_layout);

        editText = findViewById(R.id.dairy_editText);
        //AlertDialog.Builder builder;


        try{
            getStoragePermission(this);                                                   //尝试获取读写权限
        }
        catch (Exception e){
            e.printStackTrace();
        }


        File root_folder = getExternalFilesDir("");                                       //获取对应包下的files目录
        File dairy = new File(root_folder+"/dairy.txt");
        if(!dairy.exists()){                                                                   //不存在dairy文件，新建
            try{
                dairy.createNewFile();
            }
            catch (Exception e){
                Toast.makeText(dairy.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){                    //按下回车自动输入四个空格的监听事件
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && v.getText()!=null&& event.getAction() == KeyEvent.ACTION_DOWN){
                    editText.getText().insert(editText.getSelectionStart(),"\n    ");
                }
                return true;
            }
        });


        button_out = findViewById(R.id.dairy_out);                                                     //关闭按钮按键监听事件
        button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        button_confirm = findViewById(R.id.dairy_confirm);                                            //确认按钮按键监听事件
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(dairy.this);
                    builder.setTitle("日记将被记录").setMessage("一天尽量保存一次哦。").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try{
                                FileWriter fileWriter = new FileWriter(dairy,true);
                                fileWriter.write(simpleDateFormat.format(date)+'\n'+editText.getText()+'\n');
                                fileWriter.close();
                                Toast.makeText(dairy.this,"今日份日记记录成功^v^~",Toast.LENGTH_LONG).show();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton("取消",null).show();
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
