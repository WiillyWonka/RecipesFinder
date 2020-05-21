package com.example.recepiesfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindIngredient extends AppCompatActivity {
    protected ExpandableListView expListView;
    protected ListAdapter expListAdapter;
    protected List<String> expListTitle;
    protected HashMap<String, List<String>> expListDetail = new HashMap<>();
    protected ArrayList<String> FindList = new ArrayList<>();
    protected SearchView sV;

    int num_of_theme = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME",0);
        changeTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ingridien);
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        List<String> ActiveList= new ArrayList<>();

        DataBase db = DataBase.getDataBase(this);
        String[] ingredients = db.getIngredientsList();
        String last = "";
        for (String str : ingredients){
            String nowChar = str.substring(0, 1);
            if (!nowChar.equals(last)){
                if (ActiveList.size() != 0)
                    expListDetail.put(ActiveList.get(0).substring(0, 1), ActiveList);
                ActiveList = new ArrayList<>();
                last = str.substring(0 , 1);
            }
            ActiveList.add(str);
        }
        expListDetail.put(ActiveList.get(0).substring(0, 1), ActiveList);

        expListTitle = new ArrayList<>(expListDetail.keySet());
        expListAdapter = new ListAdapter(this, expListTitle, expListDetail);

        expListView.setAdapter(expListAdapter);


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String name = expListDetail.get(expListTitle.get(groupPosition))
                        .get(childPosition);
                expListAdapter.setMyCheck(name, !expListAdapter.getMyCheck(name));
                CheckBox cb = (CheckBox) v.findViewById(R.id.checkIng);
                cb.setChecked(expListAdapter.getMyCheck(name));
                if (expListAdapter.getMyCheck(name)){
                    FindList.add(expListDetail.get(expListTitle.get(groupPosition))
                            .get(childPosition));
                }
                else{
                    if (FindList.contains(name))
                        FindList.remove(name);
                }

                return false;
            }
        });
        Button b = findViewById(R.id.FindButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FindList.isEmpty()) {
                    Intent i = new Intent(v.getContext(), ListOfSelectedIngredients.class);
                    i.putExtra("class_name", FindIngredient.class.getName());
                    i.putStringArrayListExtra("list", FindList);
                    startActivityForResult(i, 1);
                }
                else{
                    CharSequence s = "Выберите ингредиенты!";
                    Toast.makeText(v.getContext(), s, s.length()).show();
                }
            }
        });
        sV = findViewById(R.id.Seek);
        if(num_of_theme == 2){
            sV.setBackgroundColor(getResources().getColor(R.color.secondstuff));
        }
        sV.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        sV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sV.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                expListAdapter.filter(newText);

                if (!newText.isEmpty()) {
                    int g = expListTitle.indexOf(newText.substring(0, 1).toUpperCase());
                    if (g != -1) {
                        if (!expListView.isGroupExpanded(g)) {
                            expListView.expandGroup(g, true);
                        }
                    }
                } else {
                    for (int i = 0; i < expListTitle.size(); i++) {
                        expListView.collapseGroup(i);
                    }
                    this.onQueryTextSubmit("");
                }
                return false;
            }
        });

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

    //changes HERE!!!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sV.clearFocus();
        if (data == null) {
            return;
        }
        sV.setQuery("", false);
        ArrayList<String> renew_FindList = (ArrayList<String>)data.getStringArrayListExtra("list");
        if (renew_FindList != null && resultCode == RESULT_OK) {
            for (String name : FindList) {
                if (!renew_FindList.contains(name)) {
                    expListAdapter.setMyCheck(name, !expListAdapter.getMyCheck(name));
                    expListAdapter.notifyDataSetChanged();
                }
            }
            FindList.clear();
            FindList = (ArrayList<String>) renew_FindList.clone();

            //FindIngredient.this.onRestart();
        }
    }

}