package com.example.recepiesfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    Button MenuBt;
    Button SwitchThemeBt;
    ImageView imageView;
    int num_of_theme = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME",0);
        changeTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        imageView = (ImageView)findViewById(R.id.imageView);

        if(num_of_theme == 2){
            imageView.setImageResource(R.drawable.logo_for_dark_theme);
        }

        MenuBt = (Button) findViewById(R.id.act_main_findRecipes);
        SwitchThemeBt = (Button) findViewById(R.id.switch_theme);

        MenuBt.setOnClickListener(this);
        SwitchThemeBt.setOnClickListener(this);

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Settings.this.recreate();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            setResult(RESULT_OK);
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_main_findRecipes:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.switch_theme:
                Intent intent1 = new Intent(this,PopActivity.class);
                startActivityForResult(intent1,1);
                break;
        }
    }
}
