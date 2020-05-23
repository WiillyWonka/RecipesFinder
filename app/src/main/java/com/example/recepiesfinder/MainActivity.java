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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton AddIngrBt;
    ImageButton AllRecBt;
    ImageButton FavoritesBt;
    ImageButton MeRecBt;

    ImageView imageView;

    Dish dish;
    DataBase db;

    int num_of_theme = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME",1);
        changeTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddIngrBt = (ImageButton) findViewById(R.id.add_ingr);
        AllRecBt = (ImageButton) findViewById(R.id.all_recipes);
        FavoritesBt = (ImageButton) findViewById(R.id.favorites);
        MeRecBt = (ImageButton) findViewById(R.id.my_recipes);

        db = DataBase.getDataBase(this);
        dish = db.getDishById(1);

        imageView = (ImageView)findViewById(R.id.imageView);



        if(num_of_theme == 2){
            imageView.setImageResource(R.drawable.logo_for_dark_theme);

            AllRecBt.setImageDrawable(getResources().getDrawable(R.drawable.book5));
            AddIngrBt.setImageDrawable(getResources().getDrawable(R.drawable.carrot2));
            MeRecBt.setImageDrawable(getResources().getDrawable(R.drawable.pencil2));
            FavoritesBt.setImageDrawable(getResources().getDrawable(R.drawable.star3));

            AddIngrBt.setBackgroundColor(getResources().getColor(R.color.for_menu_buttons));
            AllRecBt.setBackgroundColor(getResources().getColor(R.color.for_menu_buttons));
            FavoritesBt.setBackgroundColor(getResources().getColor(R.color.for_menu_buttons));
            MeRecBt.setBackgroundColor(getResources().getColor(R.color.for_menu_buttons));

        }

        AddIngrBt.setOnClickListener(this);
        AllRecBt.setOnClickListener(this);
        FavoritesBt.setOnClickListener(this);
        MeRecBt.setOnClickListener(this);
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
            MainActivity.this.recreate();
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
                Intent intent4 = new Intent(this, DisplayListOfDishesActivity.class);
                intent4.putExtra("class_name", "my_recipes");
                startActivity(intent4);
                break;
        }
    }
}
