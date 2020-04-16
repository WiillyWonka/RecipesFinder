package com.example.recepiesfinder;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton AddIngrBt;
    ImageButton AllRecBt;
    ImageButton FavoritesBt;
    ImageButton MeRecBt;
    Dish dish;
    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddIngrBt = (ImageButton) findViewById(R.id.add_ingr);
        AllRecBt = (ImageButton) findViewById(R.id.all_recipes);
        FavoritesBt = (ImageButton) findViewById(R.id.favorites);
        MeRecBt = (ImageButton) findViewById(R.id.my_recipes);

        db = DataBase.getDataBase(this);
        dish = db.getDishById(1);

        AddIngrBt.setOnClickListener(this);
        AllRecBt.setOnClickListener(this);
        FavoritesBt.setOnClickListener(this);
        MeRecBt.setOnClickListener(this);
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
                startActivity(intent2);
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
            case R.id.add_ingr:
                Intent intent2 = new Intent(this, FindIngredient.class);
                startActivity(intent2);
                break;
            case R.id.all_recipes:
                Intent intent1 = new Intent(this, DisplayListOfDishesActivity.class);
                intent1.putExtra("class_name", MainActivity.class.getName());
                startActivity(intent1);
                break;
            case R.id.favorites:
                Intent intent3 = new Intent(this, DisplayListOfDishesActivity.class);
                intent3.putExtra("class_name", "favourites");
                startActivity(intent3);
                break;
            case R.id.my_recipes:
                break;
        }
    }
}
