package com.example.recepiesfinder;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;

public class PopActivity extends Activity{

    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        linearLayout = (LinearLayout) findViewById(R.id.llmain);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.5));

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 0;
        layoutParams.y = -20;

        getWindow().setAttributes(layoutParams);

        ViewGroup.LayoutParams layoutParams1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams((int)(width*.6), ViewGroup.LayoutParams.WRAP_CONTENT);

        linearLayout.getLayoutParams().width = (int)(width*.6);


        Button defaultTheme = new Button(this);
        Button newTheme = new Button(this);

        defaultTheme.setLayoutParams(layoutParams1);
        defaultTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                editor.putInt("THEME",1).apply();
                setResult(RESULT_OK);
                finish();
            }
        });

        newTheme.setLayoutParams(layoutParams1);
        newTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                editor.putInt("THEME",2).apply();
                setResult(RESULT_OK);
                finish();
            }
        });

        defaultTheme.setText("Светлая Тема");
        defaultTheme.setTextColor(getResources().getColor(R.color.my_textColorPrimary));

        newTheme.setText("Темная тема");
        newTheme.setTextColor(getResources().getColor(R.color.my_textColorPrimary));

        defaultTheme.setBackgroundResource(R.drawable.button_border1);
        newTheme.setBackgroundResource(R.drawable.button_border3);
        defaultTheme.setGravity(Gravity.CENTER);
        newTheme.setGravity(Gravity.CENTER);

        Space space = new Space(this);

        space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20));

        linearLayout.addView(defaultTheme);
        linearLayout.addView(space);
        linearLayout.addView(newTheme);
    }



}
