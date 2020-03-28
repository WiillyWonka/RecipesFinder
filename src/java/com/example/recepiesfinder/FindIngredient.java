package com.example.recepiesfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindIngredient extends AppCompatActivity {
    protected ExpandableListView expListView;
    protected ExpandableListAdapter expListAdapter;
    protected List<String> expListTitle;
    protected HashMap<String, List<String>> expListDetail;
    protected CheckBox mCheck;
    protected TextView mTextView;
    protected List<String> ActiveList = new ArrayList<>();
    protected void loadData(){
        expListDetail = new HashMap<>();
        TypedArray arr = getResources().obtainTypedArray(R.array.ingredients);
        for (int i = 0; i < arr.length(); ++i){
            int resId = arr.getResourceId(i, -1);
            if (resId < 0)
                continue;
            TypedArray help = getResources().obtainTypedArray(resId);
            List<String> arrHelp = new ArrayList<String>();
            for (int j = 0; j < help.length(); j++)
                arrHelp.add(help.getString(j));
            if (arrHelp.size() != 0)
                expListDetail.put(arrHelp.get(0).substring(0,1), arrHelp);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ingridien);
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        loadData();

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

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*((CheckBox) v.findViewById(R.id.checkIng)).setChecked(!((CheckBox) v.findViewById(R.id.checkIng)).isChecked());
                if (((CheckBox) v.findViewById(R.id.checkIng)).isChecked())
                {
                    ActiveList.add(expListDetail.get(expListTitle.get(groupPosition))
                            .get(childPosition));
                    ((TextView) v.findViewById(R.id.expandedListItem)).setBackgroundColor(R.color.colorPrimary);
                    ((TextView) v.findViewById(R.id.expandedListItem)).setTextColor(R.color.colorWhite);
                }
                else
                    if (ActiveList.contains(expListDetail.get(expListTitle.get(groupPosition))
                            .get(childPosition))){
                        ActiveList.remove(expListDetail.get(expListTitle.get(groupPosition))
                                .get(childPosition));
                        ((TextView) v.findViewById(R.id.expandedListItem)).setBackgroundColor(R.color.colorWhite);
                        ((TextView) v.findViewById(R.id.expandedListItem)).setTextColor(R.color.colorBlack);
                    }
                /*Toast.makeText(getApplicationContext(),
                        expListTitle.get(groupPosition)
                                + " : " + expListDetail.get(expListTitle.get(groupPosition))
                                .get(childPosition), Toast.LENGTH_SHORT).show();*/
                /*expListView.invalidate();*/
                return false;
            }
        });
    }
}
