package com.example.recipes_finder;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class DisplayListOfDishesActivity extends ListActivity {
    private Dish[] list_of_dishes_;
    private String[] names_;
    private String[] urls_;

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


    private DishAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            String name_ = arguments.getString("class_name");
            if (name_.equals(MainActivity.class.getName())) {
                DataBase db = DataBase.getDataBase(this);
                list_of_dishes_ = db.getAllDishList();
                ConvertToStrings();
                DownloadURLS();
                mAdapter = new DishAdapter(this);
                setListAdapter(mAdapter);
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
                    setListAdapter(mAdapter);
                }
            }
        }
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String selection = mAdapter.getString(position);
        Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
        Intent intent  = new Intent(this, Recipe.class);
        intent.putExtra(Dish.class.getSimpleName(), list_of_dishes_[position]);
        startActivity(intent);
    }


    class DishAdapter extends BaseAdapter {
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
                convertView = mLayoutInflater.inflate(R.layout.activity_list_of_dishes, null);

            ImageView imgView = (ImageView)convertView.findViewById(R.id.imageViewIcon);



            Glide.with(DisplayListOfDishesActivity.this)
                    .load(urls_[position])
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .apply(new RequestOptions().override(100, 80))
                    .into(imgView);



            TextView signTextView = (TextView)convertView.findViewById(R.id.textViewRecipe);
            signTextView.setText(names_[position]);

            return convertView;
        }

        String getString(int position) {
            return names_[position];
        }
    }

}
