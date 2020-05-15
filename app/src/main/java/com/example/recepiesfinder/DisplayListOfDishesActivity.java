package com.example.recepiesfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class DisplayListOfDishesActivity extends AppCompatActivity implements  View.OnClickListener {
    private Dish[] list_of_dishes_;
    private String[] names_;
    private String[] urls_;
    Button MenuBt;
    Button AddRecipeBt;

    public DisplayListOfDishesActivity(){}

    public DisplayListOfDishesActivity(Dish[] dishes){
        list_of_dishes_ = dishes;
    }

    public void ConvertToStrings(){
        int size = list_of_dishes_.length;
        names_ = new String[size];
        for (int i = 0; i < size; i++){
            if(list_of_dishes_[i]!=null)
                names_[i] = list_of_dishes_[i].getName();
        }
    }

    public void DownloadURLS(){
        int size = list_of_dishes_.length;
        urls_ = new String[size];
        for (int i = 0; i < size; i++){
            if(list_of_dishes_[i]!=null)
                urls_[i] = list_of_dishes_[i].getPicture();
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


    private DishAdapter mAdapter;
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            String name_ = arguments.getString("class_name");
            if(name_.equals("my_recipes")){
                setContentView(R.layout.activity_my_recipes);
                AddRecipeBt = (Button)findViewById(R.id.add_recipe);
                AddRecipeBt.setOnClickListener(this);
                MenuBt = (Button) findViewById(R.id.Menu);
                MenuBt.setOnClickListener(this);

                DataBase db = DataBase.getDataBase(this);
                sharedPreferences = getSharedPreferences("user.recipes.id", Context.MODE_PRIVATE);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.clear().apply();
                Map<String, ?> map = sharedPreferences.getAll();
                Set<String> str_of_fav = map.keySet();
                list_of_dishes_ = new Dish[str_of_fav.size()];

                if(list_of_dishes_.length == 0){
                    TextView text = (TextView)findViewById(R.id.TextRec);
                    text.setText("Ничего нет, добавьте свой рецепт!");
                    text.setTextSize(20);
                }else{
                    int j = 0;
                    for (String key : str_of_fav){
                        list_of_dishes_[j] = db.getDishById((int)(long) Objects.requireNonNull(map.get(key)));
                        j++;
                    }
                    ConvertToStrings();
                    DownloadURLS();
                    mAdapter = new DishAdapter(this);
                    ListView listView = (ListView)findViewById(R.id.lvMain);
                    listView.setAdapter(mAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selection = mAdapter.getString(position);
                            //Toast.makeText(view.getContext(), selection, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(view.getContext(), Recipe.class);
                            intent.putExtra("user", list_of_dishes_[position]);
                            startActivityForResult(intent, 1);
                        }
                    });
                }

            }
            if (name_.equals("favourites")){
                setContentView(R.layout.activity_list_of_dishes);
                MenuBt = (Button) findViewById(R.id.Menu);
                MenuBt.setOnClickListener(this);
                DataBase db = DataBase.getDataBase(this);
                sharedPreferences = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
                Map<String, ?> map = sharedPreferences.getAll();
                Set<String> str_of_fav = map.keySet();
                list_of_dishes_ = new Dish[str_of_fav.size()];
                int j = 0;
                for (String key : str_of_fav){
                    list_of_dishes_[j] = db.getDishById(Objects.requireNonNull((Integer)(map.get(key))));
                    j++;
                }
                ConvertToStrings();
                DownloadURLS();
                mAdapter = new DishAdapter(this);
                ListView listView = (ListView)findViewById(R.id.lvMain);
                listView.setAdapter(mAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selection = mAdapter.getString(position);
                        //Toast.makeText(view.getContext(), selection, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), Recipe.class);
                        intent.putExtra("favourites", list_of_dishes_[position]);
                        startActivityForResult(intent, 1);
                    }
                });
            }
            if (name_.equals(MainActivity.class.getName())) {
                setContentView(R.layout.activity_list_of_dishes);
                MenuBt = (Button) findViewById(R.id.Menu);
                MenuBt.setOnClickListener(this);
                DataBase db = DataBase.getDataBase(this);
                list_of_dishes_ = db.getAllDishList();
                ConvertToStrings();
                DownloadURLS();
                mAdapter = new DishAdapter(this);
                ListView listView = (ListView)findViewById(R.id.lvMain);
                listView.setAdapter(mAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selection = mAdapter.getString(position);
                        //Toast.makeText(view.getContext(), selection, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), Recipe.class);
                        intent.putExtra(Dish.class.getSimpleName(), list_of_dishes_[position]);
                        startActivity(intent);
                    }
                });
            }
            if (name_.equals(FindIngredient.class.getName())){
                setContentView(R.layout.activity_list_of_dishes);
                MenuBt = (Button) findViewById(R.id.Menu);
                MenuBt.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(DisplayListOfDishesActivity.this);
                                a_builder.setMessage("Вы действительно хотите сделать сброс ингрединетов и выйти в Меню?")
                                        .setCancelable(false)
                                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DisplayListOfDishesActivity.this.finish();
                                                Intent intent = new Intent(DisplayListOfDishesActivity.this, MainActivity.class);
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
                ArrayList<String> dishes_ = getIntent().getStringArrayListExtra("list");
                if (dishes_ != null) {
                    String[] ingredients = dishes_.toArray(new String[dishes_.size()]);
                    DataBase db = DataBase.getDataBase(this);
                    list_of_dishes_ = db.getDishListByIngredients(ingredients);
                    ConvertToStrings();
                    DownloadURLS();
                    mAdapter = new DishAdapter(this);
                    ListView listView = (ListView)findViewById(R.id.lvMain);
                    listView.setAdapter(mAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String selection = mAdapter.getString(position);
                                //Toast.makeText(view.getContext(), selection, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(view.getContext(), Recipe.class);
                                intent.putExtra(Dish.class.getSimpleName(), list_of_dishes_[position]);
                                startActivity(intent);
                            }

                    });
                }
            }
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        String selection = mAdapter.getString(position);
        Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
        Intent intent  = new Intent(this, Recipe.class);
        intent.putExtra(Dish.class.getSimpleName(), list_of_dishes_[position]);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            DisplayListOfDishesActivity.this.recreate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Menu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.add_recipe:
                Intent intent1 = new Intent(this,UserRecipe.class);
                startActivityForResult(intent1,1);
                break;
        }
    }


    class DishAdapter extends BaseAdapter{
        LayoutInflater mLayoutInflater;
        DishAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list_of_dishes_.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item, null);

            ImageView imgView = (ImageView)convertView.findViewById(R.id.imageViewIcon);


            Glide.with(DisplayListOfDishesActivity.this)
                    .load(urls_[position])
                    .placeholder(R.drawable.ic_logo1)
                    .error(R.drawable.ic_logo1)
                    .apply(new RequestOptions().override(100, 80))
                    .into(imgView);



            TextView signTextView = (TextView) convertView.findViewById(R.id.textViewRecipe);
            signTextView.setText(names_[position]);

            return convertView;
        }
        String getString(int position) {
            return names_[position];
        }

    }

}
