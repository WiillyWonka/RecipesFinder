package com.example.recepiesfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

    final int DELETE_RECIPE = 1;
    private boolean come_from_favorites = false;
    private boolean come_from_user_recipe = false;

    static SharedPreferences sharedPreferences;

    int num_of_theme = 1;


    private void SetButtons(){
        CheckBt = (ImageButton)findViewById(R.id.ImgBt);
        CheckBt.setOnClickListener(this);

        if(num_of_theme == 2){
            CheckBt.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_border3));
        }

        MenuBt = (Button)findViewById(R.id.Menu);
        MenuBt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Recipe.this);
                        a_builder.setMessage("Вы действительно хотите выйти в Меню?")
                                .setCancelable(false)
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Recipe.this.finish();
                                        Intent intent = new Intent(Recipe.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.show();
                    }
                }
        );

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
            if(num_of_theme == 2){
                textView.setTextColor(getResources().getColor(R.color.my_textColorPrimary));
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

        SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME",0);
        changeTheme(theme);

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

        if(come_from_user_recipe) {
            if(num_of_theme == 1) {
                CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.brush));
            }else{
                CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.brush1));
            }
        }

        PrintIngredients();
        SetSteps(dish.getCount_steps(),come_from_user_recipe);

        sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        if(sharedPreferences.contains(String.valueOf(dish.getId())) && !come_from_user_recipe){
            CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.star_86960));
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DELETE_RECIPE){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Удалить рецепт");
            adb.setMessage("Вы точно хотите удалить созданный вами рецепт?");
            adb.setPositiveButton("Удалить", myClickListener);
            adb.setNegativeButton("Отмена", myClickListener);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    private void changeTheme(int num_){
        if(num_ == 1){
            setTheme(R.style.MyStyle);
            num_of_theme = 1;
        }else if(num_ == 2){
            num_of_theme = 2;
            setTheme(R.style.MyStyle2);
        }
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    DeleteRecipe();
                    bringDataBack(true);
                    finish();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    private void DeleteRecipe(){
        sharedPreferences = getSharedPreferences("user.recipes.id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(String.valueOf(dish.getId())).apply();

        DataBase db = DataBase.getDataBase(this);
        db.deleteDish(dish.getId());
        dish = null;
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
            Recipe.this.recreate();
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
                if(!come_from_user_recipe) {
                    sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
                    if (!sharedPreferences.contains(String.valueOf(dish.getId()))) {
                        CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.star_86960));
                        addrec(this);
                        if (come_from_favorites) {
                            bringDataBack(false);
                        }
                    } else {
                        CheckBt.setImageDrawable(getResources().getDrawable(R.drawable.star_47680));
                        remove(this);
                        if (come_from_favorites) {
                            bringDataBack(true);
                        }
                    }
                }else{
                    showDialog(DELETE_RECIPE);
                }
                break;
        }
    }
}
