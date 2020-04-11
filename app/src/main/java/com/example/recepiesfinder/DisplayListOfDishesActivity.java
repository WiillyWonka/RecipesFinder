package com.example.recepiesfinder;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class DisplayListOfDishesActivity extends AppCompatActivity implements  View.OnClickListener {
    private Dish[] list_of_dishes_;
    private String[] names_;
    private String[] urls_;
    Button MenuBt;

    public DisplayListOfDishesActivity(){}

    public DisplayListOfDishesActivity(Dish[] dishes){
        list_of_dishes_ = dishes;
    }

    public void ConvertToStrings(){
        int size = list_of_dishes_.length;
        names_ = new String[size];
        for (int i = 0; i < size; i++){
            names_[i] = list_of_dishes_[i].getName();
        }
    }

    public void DownloadURLS(){
        int size = list_of_dishes_.length;
        urls_ = new String[size];
        for (int i = 0; i < size; i++){
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        setContentView(R.layout.activity_list_of_dishes);
        MenuBt = (Button) findViewById(R.id.Menu);
        MenuBt.setOnClickListener(this);
        if (arguments != null){
            String name_ = arguments.getString("class_name");
            if (name_.equals(MainActivity.class.getName())) {
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
                        Toast.makeText(view.getContext(), selection, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), Recipe.class);
                        intent.putExtra(Dish.class.getSimpleName(), list_of_dishes_[position]);
                        startActivity(intent);
                    }
                });
            }
            if (name_.equals(FindIngredient.class.getName())){
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
                                Toast.makeText(view.getContext(), selection, Toast.LENGTH_LONG).show();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Menu:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
