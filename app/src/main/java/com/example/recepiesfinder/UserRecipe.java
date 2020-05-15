package com.example.recepiesfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRecipe extends AppCompatActivity implements View.OnClickListener {

    Dish dish;

    int count_of_dishes;

    boolean save_and_exit = false;

    DataBase db;

    String[] ingredients_list_from_db;

    final int DIALOG_EXIT = 1;
    final int DIALOG_EMPTY_INGREDIENT_LIST = 2;
    final int DIALOG_EMPTY_NAME = 3;
    final int DIALOG_SAVE = 4;
    final int DIALOG_EMPTY_CATEGORY = 5;

    int count_of_ingredients_ = 0;
    int count_of_steps_ = 0;

    String item = "";

    String[] categories_ = {"Выберите категорию из списка","Первые блюда","Вторые блюда","Закуски","Салаты","Десерты","Выпечка","Торты","Блюда в мультиварке","Блины и оладьи"};

    HashMap<Integer,LinearLayout> layouts_with_ingredients = new HashMap<Integer, LinearLayout>();
    HashMap<Integer, LinearLayout> layouts_with_steps= new HashMap<Integer, LinearLayout>();

    HashMap<Integer,AutoCompleteTextView> list_with_names_ingredients = new HashMap<Integer,AutoCompleteTextView>();
    HashMap<Integer,EditText> list_with_names_steps = new HashMap<Integer,EditText>();

    LinearLayout main_ingredients_layout_;
    LinearLayout main_steps_layout_;

    LinearLayout.LayoutParams lp_for_nested_layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams lp_for_nested_view_components1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,3f);
    LinearLayout.LayoutParams lp_for_nested_view_components2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.5f);

    Button AddIngredientBt_;
    Button AddStepBt_;
    Button SaveBt;
    Button MenuBt;

    private void Create_Buttons(){
        AddIngredientBt_ = (Button)findViewById(R.id.addingredient);
        AddStepBt_ = (Button)findViewById(R.id.addstep);
        MenuBt = (Button)findViewById(R.id.Menu);
        SaveBt = (Button)findViewById(R.id.SaveBt);

        MenuBt.setOnClickListener(this);
        AddIngredientBt_.setOnClickListener(this);
        AddStepBt_.setOnClickListener(this);
        SaveBt.setOnClickListener(this);
    }

    private void Create_Layouts(){
        main_ingredients_layout_ = (LinearLayout)findViewById(R.id.Ingredients);
        main_steps_layout_ = (LinearLayout)findViewById(R.id.Steps);
    }

    private void Create_field_for_ingredient(){
        ImageButton DefaultBrushBt = new ImageButton(this);
        DefaultBrushBt.setImageResource(R.drawable.brush);
        DefaultBrushBt.setBackgroundColor(Color.parseColor("#ffffff"));
        DefaultBrushBt.setLayoutParams(lp_for_nested_view_components2);
        DefaultBrushBt.setId(count_of_ingredients_);
        DefaultBrushBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_ingredients_layout_.removeView(layouts_with_ingredients.get(v.getId()));
                layouts_with_ingredients.remove(v.getId());
                list_with_names_ingredients.remove(v.getId());
            }
        });

        AutoCompleteTextView defIngr = new AutoCompleteTextView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingredients_list_from_db);
        defIngr.setAdapter(adapter);
        defIngr.setLayoutParams(lp_for_nested_view_components1);
        defIngr.setId(count_of_ingredients_);
        defIngr.setHint("Введите название ингредиента");
        list_with_names_ingredients.put(defIngr.getId(),defIngr);

        LinearLayout nested_for_ingredients = new LinearLayout(this);
        nested_for_ingredients.setLayoutParams(lp_for_nested_layout);
        nested_for_ingredients.setOrientation(LinearLayout.HORIZONTAL);
        nested_for_ingredients.setId(count_of_ingredients_);
        nested_for_ingredients.addView(defIngr);
        nested_for_ingredients.addView(DefaultBrushBt);

        main_ingredients_layout_.addView(nested_for_ingredients);
        count_of_ingredients_++;

        layouts_with_ingredients.put(nested_for_ingredients.getId(),nested_for_ingredients);
    }

    private void Create_field_for_step(){

        ImageButton DefaultBrushBt = new ImageButton(this);
        DefaultBrushBt.setImageResource(R.drawable.brush);
        DefaultBrushBt.setBackgroundColor(Color.parseColor("#ffffff"));
        DefaultBrushBt.setLayoutParams(lp_for_nested_view_components2);
        DefaultBrushBt.setId(count_of_steps_);
        DefaultBrushBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_steps_layout_.removeView(layouts_with_steps.get(v.getId()));
                layouts_with_steps.remove(v.getId());
                list_with_names_steps.remove(v.getId());
            }
        });

        EditText defStep = new EditText(this);
        defStep.setLayoutParams(lp_for_nested_view_components1);
        defStep.setId(count_of_steps_);
        defStep.setHint("Введите текст шага рецепта");
        list_with_names_steps.put(defStep.getId(),defStep);

        LinearLayout nested_for_steps = new LinearLayout(this);
        nested_for_steps.setLayoutParams(lp_for_nested_layout);
        nested_for_steps.setOrientation(LinearLayout.HORIZONTAL);
        nested_for_steps.setId(count_of_steps_);
        nested_for_steps.addView(defStep);
        nested_for_steps.addView(DefaultBrushBt);

        main_steps_layout_.addView(nested_for_steps);
        count_of_steps_++;

        layouts_with_steps.put(nested_for_steps.getId(),nested_for_steps);
    }

    private String get_ingredients_list(){
        String ingredients = "";
        int count = list_with_names_ingredients.size();
        int empty = 0;
        if(count > 1) {
            for (int key : list_with_names_ingredients.keySet()) {
                count--;
                if (!list_with_names_ingredients.get(key).getText().toString().equals("")) {
                    char[] c = list_with_names_ingredients.get(key).getText().toString().toCharArray();
                    c[0] = Character.toUpperCase(c[0]);
                    ingredients += new String(c);
                    if (count != 0) {
                        ingredients += ',';
                    }
                }else{
                    empty++;
                }
            }
        }else{
            if(count == 1){
                for (int key : list_with_names_ingredients.keySet()) {
                    if (!list_with_names_ingredients.get(key).getText().toString().equals("")){
                        count--;
                        char[] c = list_with_names_ingredients.get(key).getText().toString().toCharArray();
                        c[0] = Character.toUpperCase(c[0]);
                        ingredients += new String(c);
                        if (count != 0) {
                            ingredients += ',';
                        }
                    }else{
                        showDialog(DIALOG_EMPTY_INGREDIENT_LIST);
                        return null;
                    }
                }
            }
        }
        if(empty == list_with_names_ingredients.size()){
            showDialog(DIALOG_EMPTY_INGREDIENT_LIST);
            return null;
        }
        return ingredients;
    }

    private String get_steps_list(){
        String steps = "";
        int count = list_with_names_steps.size();
        for(int key : list_with_names_steps.keySet()){
            count--;
            if(!list_with_names_steps.get(key).getText().toString().equals("")) {
                steps += list_with_names_steps.get(key).getText().toString();
                if(count != 0){
                    steps +='\n';
                }
            }
        }
        return steps;
    }

    private Dish Save_User_Recipe(){
        EditText Name_ = (EditText)findViewById(R.id.NameRecipe);

        if(item.equals(categories_[0])){
            item = "";
            showDialog(DIALOG_EMPTY_CATEGORY);
            return  null;
        }

        if(Name_.getText().toString().equals("")){
            showDialog(DIALOG_EMPTY_NAME);
            return null;
        }

        String ingredients = get_ingredients_list();
        if(ingredients == null){
            return null;
        }
        int size = ingredients.length();
        char[] str = ingredients.toCharArray();
        if(size!=0){
            if(str[size-1] == ','){
                char[] c = new char[size-1];
                System.arraycopy(str,0,c,0,size-1);
                ingredients = new String(c);
            }
        }

        String steps = get_steps_list();
        int size1 = steps.length();
        char[] str1 = steps.toCharArray();
        if(size1!=0) {
            if (str1[size1 - 1] == '\n') {
                char[] c = new char[size1 - 1];
                System.arraycopy(str1, 0, c, 0, size1 - 1);
                steps = new String(c);
            }
        }
        return new Dish(count_of_dishes, Name_.getText().toString(), ingredients, null, item, steps);
    }


    AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            item = (String)parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_params_user_recipe);


        db = DataBase.getDataBase(this);

        ingredients_list_from_db = db.getIngredientsList();



        Create_Buttons();
        Create_Layouts();
        Create_field_for_ingredient();
        Create_field_for_step();



        Spinner spinner = (Spinner)findViewById(R.id.CategoryName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories_);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_EXIT){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Подтвердите выход в меню");
            adb.setMessage("Если вы выйдете без сохранения, то потеряете введенные данные.\nВы точно хотите выйти?");
            adb.setPositiveButton("Выйти", myClickListener);
            adb.setNegativeButton("Отмена", myClickListener);
            adb.setNeutralButton("Сохранить и выйти", myClickListener);
            return adb.create();
        }
        if(id == DIALOG_EMPTY_INGREDIENT_LIST){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Вы должны ввести хотя бы 1 ингредиент");
            adb.setMessage("Чтобы продолжить, введите название ингредиента в пустое поле");
            adb.setNegativeButton("Ок",myClickListener);
            return adb.create();
        }
        if(id == DIALOG_EMPTY_CATEGORY){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Вы должны выбрать категорию рецепта");
            adb.setMessage("Чтобы продолжить, выберите категорию рецепта из предложенного списка");
            adb.setNegativeButton("Ок",myClickListener);
            return adb.create();
        }
        if(id == DIALOG_EMPTY_NAME){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Вы должны ввести название рецепта");
            adb.setMessage("Чтобы продолжить, введите название рецепта в пустое поле");
            adb.setNegativeButton("Ок",myClickListener);
            return adb.create();
        }
        if(id == DIALOG_SAVE){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Вы точно хотите сохранить рецепт?");
            adb.setMessage("Вы можете сохранить рецепт или остаться и проверить/отредактировать введенные данные");
            adb.setPositiveButton("Сохранить",myClickListener2);
            adb.setNegativeButton("Редактировать", myClickListener2);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case Dialog.BUTTON_POSITIVE:
                    Go_to_menu();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    EditText Name_ = (EditText)findViewById(R.id.NameRecipe);
                    if(Name_.getText().toString().equals("")){
                        showDialog(DIALOG_EMPTY_NAME);
                        break;
                    }
                    dish = Save_User_Recipe();
                    if(dish!=null){
                        SharedPreferences sharedPreferences = getSharedPreferences("user.recipes.id", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(String.valueOf(dish.getId()),dish.getId()).apply();
                        db.saveDish(dish);
                        bring_data_back();
                        Go_to_menu();
                        break;
                    }else {
                        showDialog(DIALOG_EMPTY_INGREDIENT_LIST);
                        break;
                    }
            }
        }
    };

    DialogInterface.OnClickListener myClickListener2 = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    dish = Save_User_Recipe();
                    if(dish!=null){
                        long c = db.saveDish(dish);
                        SharedPreferences sharedPreferences = getSharedPreferences("user.recipes.id", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong(String.valueOf(c), c).apply();
                        bring_data_back();
                    }
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };


    private void Go_to_menu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void bring_data_back(){
        setResult(RESULT_OK);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Menu:
                showDialog(DIALOG_EXIT);
                break;
            case R.id.addingredient:
                Create_field_for_ingredient();
                break;
            case R.id.addstep:
                Create_field_for_step();
                break;
            case R.id.SaveBt:
                showDialog(DIALOG_SAVE);
                break;
        }
    }
}
