package com.example.recepiesfinder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class Recipe extends AppCompatActivity implements View.OnClickListener{
    Button MenuBt;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        MenuBt = (Button)findViewById(R.id.Menu);
        MenuBt.setOnClickListener(this);
        Bundle argum = getIntent().getExtras();
        Dish dish = null;
        if(argum!=null){
            dish = (Dish) argum.getSerializable(Dish.class.getSimpleName());
        }

        String[] text = dish.getSteps();
        int count = dish.getCount_steps();

        Button name_rec = findViewById(R.id.NameRecipe);
        name_rec.setText(dish.getName());

        ScrollView scrollView = findViewById(R.id.main_scroll);
        LinearLayout linearLayout = findViewById(R.id.MainL);
        ImageView imageView = findViewById(R.id.Recipe_image);

        System.out.println(dish.getPicture());

        Glide.with(Recipe.this)
                .load(dish.getPicture())
                .placeholder(R.drawable.ic_logo1)
                .error(R.drawable.ic_logo1)
                .into(imageView);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams lp1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);

        for(int i = 0; i<count;i++) {
            TextView tv = new TextView(this);
            tv.setText("\n" + text[i] + "\n");
            tv.setLayoutParams(lp);
            tv.setTextSize(17);
            linearLayout.addView(tv);
            if (i != count-1) {
                Button bt = new Button(this);
                bt.setBackgroundResource(R.drawable.button_border);
                bt.setTextColor(getResources().getColor(R.color.my_textColorPrimary));
                bt.setGravity(1);
                bt.setTextSize(14);
                int c = i + 1;
                bt.setText("Шаг " + c);
                bt.setLayoutParams(lp1);
                linearLayout.addView(bt);
            }
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
        switch (v.getId()) {
            case R.id.Menu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
