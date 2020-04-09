package com.example.recipes_finder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FindIngredient extends AppCompatActivity {
    protected ExpandableListView expListView;
    protected ListAdapter expListAdapter;
    protected List<String> expListTitle;
    protected HashMap<String, List<String>> expListDetail = new HashMap<>();
    protected CheckBox mCheck;
    protected TextView mTextView;
    protected ArrayList<String> FindList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition) + " Список раскрыт.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition) + " Список скрыт.",
                        Toast.LENGTH_SHORT).show();*/
                expListAdapter.notifyDataSetChanged();
            }
        });

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
                    Intent i = new Intent(v.getContext(), DisplayListOfDishesActivity.class);
                    i.putExtra("class_name", FindIngredient.class.getName());
                    i.putStringArrayListExtra("list", FindList);
                    startActivity(i);
                }
                else{
                    CharSequence s = "Выберите ингредиенты!";
                    Toast.makeText(v.getContext(), s, s.length()).show();
                }
            }
        });
    }
}
