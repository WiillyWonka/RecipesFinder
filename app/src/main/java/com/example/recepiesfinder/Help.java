package com.example.recepiesfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Help extends AppCompatActivity implements View.OnClickListener {

    Button MenuBt;
    ImageView imageView;

    int num_of_theme= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME",0);
        changeTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        MenuBt = (Button)findViewById(R.id.Menu);
        MenuBt.setOnClickListener(this);
    }

    private void changeTheme(int num_){
        if(num_ == 1){
            setTheme(R.style.MyStyle);
            num_of_theme = 1;
        }else if(num_ == 2){
            setTheme(R.style.MyStyle2);
            num_of_theme = 2;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu1,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            setResult(RESULT_OK);
            Help.this.recreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Intent intent2 = new Intent(this,Settings.class);
                startActivityForResult(intent2,1);
                break;
            case R.id.action_help:
                Intent intent = new Intent(this,Help.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Menu:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}

