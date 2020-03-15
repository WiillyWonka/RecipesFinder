package com.example.recepiesfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    Button MenuBt;
    Button HelpBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        MenuBt = (Button) findViewById(R.id.act_main_findRecipes);
        HelpBt = (Button) findViewById(R.id.act_main_help);

        MenuBt.setOnClickListener(this);
        HelpBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_main_findRecipes:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.act_main_help:
                Intent intent1 = new Intent(this, Help.class);
                startActivity(intent1);
                break;
        }
    }
}
