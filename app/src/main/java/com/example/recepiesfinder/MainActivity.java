package com.example.recipes_finder;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button AddIngrBt;
    Button AllRecBt;
    /*Button FindBt;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddIngrBt = (Button) findViewById(R.id.AddIngr);
        AllRecBt = (Button) findViewById(R.id.Allrec);
        /*FindBt = (Button) findViewById(R.id.Find);*/

        ;

        AddIngrBt.setOnClickListener(this);
        AllRecBt.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu1,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*switch (id){
            case R.id.action_settings:
                Intent intent2 = new Intent(this,Settings.class);
                startActivity(intent2);
                break;
            case R.id.action_help:
                Intent intent = new Intent(this,Help.class);
                startActivity(intent);
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //case R.id.Help:
             //   Toast toastHelp = Toast.makeText(MainActivity.this, "Нажата кнопка : помощь", Toast.LENGTH_LONG);
               // toastHelp.show();
                //Intent intent = new Intent(this,Help.class);
                //startActivity(intent);
                //break;
            case R.id.AddIngr:
                Intent intent1 = new Intent(this, FindIngredient.class);
                startActivity(intent1);
                break;
            case R.id.Allrec:
                Intent intent3 = new Intent(this, DisplayListOfDishesActivity.class);
                intent3.putExtra("class_name", MainActivity.class.getName());
                startActivity(intent3);
                break;
        }
    }
}
