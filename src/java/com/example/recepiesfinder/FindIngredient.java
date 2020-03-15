package com.example.recepiesfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class FindIngredient extends ListActivity {
    final String[] Alphabet = new String[]{ "А","Б","В","Г","Д","Е","Ё","Ж","З","И","Й","К","Л",
            "М","Н","О","П","Р","С","Т","У","Ф","Х","Ц","Ч","Ш",
            "Щ","Ъ","Ы","Ь","Э","Ю","Я"};

    private ArrayList<String> alphabetList = new ArrayList<>(Arrays.asList(Alphabet));
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,alphabetList);
        setListAdapter(mAdapter);
        setContentView(R.layout.activity_find_ingridien);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getApplicationContext(),
                "Вы выбрали " + l.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }
}
