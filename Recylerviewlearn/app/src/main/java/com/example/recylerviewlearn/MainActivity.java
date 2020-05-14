package com.example.recylerviewlearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recylerviewlearn.demo1.Activity1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    各个按钮跳转页面
    public void jump(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_1:
                intent.setClass(this, Activity1.class);
                break;
        }
        startActivity(intent);
    }
}
