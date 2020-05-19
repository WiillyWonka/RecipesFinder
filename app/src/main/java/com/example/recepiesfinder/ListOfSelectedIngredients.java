package com.example.recepiesfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListOfSelectedIngredients extends AppCompatActivity {
    protected ArrayList<String> ingredients_;
    protected ArrayAdapter<String> mAdapter;
    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_indredients);
        Toast toast = Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.Hint), Toast.LENGTH_LONG);
        toast.show();
        ListView lv = (ListView) findViewById(R.id.listView);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            ingredients_ = getIntent().getStringArrayListExtra("list");
        }
        mAdapter = new ArrayAdapter<>(this, R.layout.item_ingredient, ingredients_);
        lv.setAdapter(mAdapter);
        lv.setOnCreateContextMenuListener(this);
        Button b = findViewById(R.id.FindButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ingredients_.isEmpty()) {
                    /*Intent intent = new Intent();
                    intent.putExtra("class_name", FindIngredient.class.getName());
                    intent.putStringArrayListExtra("list", ingredients_);
                    setResult(RESULT_OK, intent);*/


                    Intent i = new Intent(v.getContext(), DisplayListOfDishesActivity.class);
                    i.putExtra("class_name", FindIngredient.class.getName());
                    i.putStringArrayListExtra("list", ingredients_);
                    startActivity(i);
                }
                else{
                    CharSequence s = "Выберите ингредиенты!";
                    Toast.makeText(v.getContext(), s, s.length()).show();
                }
            }
        });
        Button MenuBt = (Button) findViewById(R.id.Menu);
        MenuBt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(ListOfSelectedIngredients.this);
                        a_builder.setMessage("Вы действительно хотите сделать сброс ингрединетов и выйти в Меню?")
                                .setCancelable(false)
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ListOfSelectedIngredients.this.finish();
                                        Intent intent = new Intent(ListOfSelectedIngredients.this, MainActivity.class);
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
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteItem:
                selectedItem = mAdapter.getItem(info.position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ListOfSelectedIngredients.this);
                //builder.setTitle(title);
                builder.setMessage("Удалить " + selectedItem + "?");
                builder.setNegativeButton(R.string.Cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {}
                        });
                builder.setPositiveButton(R.string.DeleteIngr,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                mAdapter.remove(selectedItem);
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),
                                        selectedItem + " удалён.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("class_name", FindIngredient.class.getName());
                                intent.putStringArrayListExtra("list", ingredients_);
                                setResult(RESULT_OK, intent);
                                if (ingredients_.isEmpty()) {
                                    ListOfSelectedIngredients.this.finish();
                                }
                            }
                        });
                builder.show();
                return true;
            default:
                return super.onContextItemSelected(item);
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
}

