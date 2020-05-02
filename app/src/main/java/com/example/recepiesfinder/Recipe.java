package com.example.recepiesfinder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class Recipe extends AppCompatActivity implements View.OnClickListener{
    private Button MenuBt;
    private ImageButton CheckBt;
    private ViewGroup.LayoutParams lp;
    private ViewGroup.LayoutParams lp1;
    private LinearLayout linearLayout;
    private LinearLayout layout;
    private String[] ingredients;
    private String[] text;
    private static Dish dish;

    private boolean come_from_favorites = false;
    private boolean come_from_user_recipe = false;

    static SharedPreferences sharedPreferences;


    private void SetButtons(){
        CheckBt = (ImageButton)findViewById(R.id.ImgBt);
        CheckBt.setOnClickListener(this);

        MenuBt = (Button)findViewById(R.id.Menu);
        MenuBt.setOnClickListener(this);

        Button name_rec = new Button(this);
        name_rec.setText(dish.getName());
        name_rec.setBackgroundResource(R.drawable.button_border);
        name_rec.setTextColor(getResources().getColor(R.color.my_textColorPrimary));
        name_rec.setLayoutParams(lp1);

        layout.addView(name_rec);
    }

    private void SetLayoutParams(){
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
    }

    private void SetSteps(int count, boolean user){
        text = dish.getSteps();
        if(!user){
            for(int i = 1; i<count-1;i++) {
                if (i < count - 1) {
                    Button bt = new Button(this);
                    bt.setBackgroundResource(R.drawable.button_border);
                    bt.setTextColor(getResources().getColor(R.color.my_textColorPrimary));
                    bt.setGravity(1);
                    bt.setTextSize(14);
                    bt.setText("Шаг " + i);
                    bt.setLayoutParams(lp1);
                    linearLayout.addView(bt);
                }

                TextView tv = new TextView(this);
                tv.setText("\n" + text[i] + "\n");
                tv.setLayoutParams(lp);
                tv.setTextSize(17);
                linearLayout.addView(tv);
            }
        }else{
            for(int i = 0; i<count;i++) {

                String test = text[i];
                if(!test.equals("")) {
                    Button bt = new Button(this);
                    bt.setBackgroundResource(R.drawable.button_border);
                    bt.setTextColor(getResources().getColor(R.color.my_textColorPrimary));
                    bt.setGravity(1);
                    bt.setTextSize(14);
                    bt.setText("Шаг " + (i + 1));
                    bt.setLayoutParams(lp1);
                    linearLayout.addView(bt);
                    TextView tv = new TextView(this);
                    tv.setText("\n" + text[i] + "\n");
                    tv.setLayoutParams(lp);
                    tv.setTextSize(17);
                    linearLayout.addView(tv);
                }
            }
        }
    }

    private void PrintIngredients(){
        for(int i = 0; i < ingredients.length; i++){
            TextView textView = new TextView(this);
            textView.setLayoutParams(lp);
            textView.setTextSize(17);
            if(i == 0){
                textView.setText(ingredients[i]);
            }else{
                textView.setText("\t\t\t\t" + "•" + ingredients[i]);
            }
            linearLayout.addView(textView);
        }
    }

    private static void addrec(Context context){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(String.valueOf(dish.getId()),dish.getId()).apply();
    }

    private static void remove(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(String.valueOf(dish.getId())).apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle argum = getIntent().getExtras();
        dish = null;
        if(argum!=null){
            if(argum.containsKey("user")){
                come_from_user_recipe = true;
                dish = (Dish) argum.getSerializable("user");
            }else {

                if (argum.containsKey("favourites")) {
                    come_from_favorites = true;
                    dish = (Dish) argum.getSerializable("favourites");
                } else {
                    dish = (Dish) argum.getSerializable(Dish.class.getSimpleName());
                }
            }
        }

        String[] test = dish.getIngredients().split(",");
        ingredients = new String[test.length + 1];
        ingredients[0] = "Для этого блюда нам понадобится: ";
        System.arraycopy(test,0,ingredients,1,test.length);

        SetLayoutParams();

        layout = (LinearLayout)findViewById(R.id.lll);
        linearLayout = findViewById(R.id.MainL);

        SetButtons();
        
        ImageView imageView = findViewById(R.id.Recipe_image);

        Glide.with(Recipe.this)
                .load(dish.getPicture())
                .placeholder(R.drawable.ic_logo1)
                .error(R.drawable.ic_logo1)
                .into(imageView);

        PrintIngredients();
        SetSteps(dish.getCount_steps(),come_from_user_recipe);

        sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        if(sharedPreferences.contains(String.valueOf(dish.getId()))){
            CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.star_86960));
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

    private void bringDataBack(boolean flag){
        if(flag){
            setResult(RESULT_OK);
        }else{
            setResult(RESULT_CANCELED);
        }
       // finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Menu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.ImgBt:
                sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
                    if(!sharedPreferences.contains(String.valueOf(dish.getId()))){
                        CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.star_86960));
                        addrec(this);
                        if(come_from_favorites){
                            bringDataBack(false);
                        }
                    }else{
                        CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.star_47680));
                        remove(this);
                        if(come_from_favorites){
                            bringDataBack(true);
                        }
                    }
                break;
        }
    }
}
