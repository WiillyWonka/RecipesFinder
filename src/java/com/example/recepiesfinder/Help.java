package com.example.recepiesfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Help extends AppCompatActivity implements View.OnClickListener {

    Button MenuBt;
    Button SettingBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        MenuBt = (Button)findViewById(R.id.Menu);
        SettingBt = (Button) findViewById(R.id.Settings);
        MenuBt.setOnClickListener(this);
        SettingBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Menu:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.Settings:
                Intent intent2 = new Intent(this,Settings.class);
                startActivity(intent2);
                break;
        }
    }
}

