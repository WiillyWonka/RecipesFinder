package com.example.recepiesfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button HelpBt;
    Button AddIngrBt;
    Button SettingsBt;
    /*Button FindBt;
    Button AllRecBt;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HelpBt = (Button) findViewById(R.id.Help);
        AddIngrBt = (Button) findViewById(R.id.AddIngr);
        SettingsBt = (Button) findViewById(R.id.Settings);
        /*FindBt = (Button) findViewById(R.id.Find);
        AllRecBt = (Button) findViewById(R.id.Allrec);*/

        ;

        HelpBt.setOnClickListener(this);
        AddIngrBt.setOnClickListener(this);
        SettingsBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Help:
                Toast toastHelp = Toast.makeText(MainActivity.this, "Нажата кнопка : помощь", Toast.LENGTH_LONG);
                toastHelp.show();
                Intent intent = new Intent(this,Help.class);
                startActivity(intent);

                break;
            case R.id.AddIngr:
                Intent intent1 = new Intent(this, FindIngredient.class);
                startActivity(intent1);
                break;
            case R.id.Settings:
                Intent intent2 = new Intent(this,Settings.class);
                startActivity(intent2);
                break;
        }
    }
}

