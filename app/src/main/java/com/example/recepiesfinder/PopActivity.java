package com.example.recepiesfinder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;


public class PopActivity extends Activity{

    LinearLayout linearLayout;
    int num_of_theme = 1;

    SharedPreferences sharedPreferences;

    final int DIALOG_APPLY_THEME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        SharedPreferences sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
        int theme = sharedPreferences.getInt("THEME",0);
        changeTheme(theme);

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
                if(num_of_theme != 1) {
                    showDialog(DIALOG_APPLY_THEME);
                }
            }
        });

        newTheme.setLayoutParams(layoutParams1);
        newTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num_of_theme != 2){
                    showDialog(DIALOG_APPLY_THEME);
                }
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

    private void changeTheme(int num_){
        if(num_ == 1){
            num_of_theme = 1;
        }else if(num_ == 2){
            num_of_theme = 2;
        }
    }

    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_APPLY_THEME){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Подтвердите смену темы");
            adb.setMessage("Если вы хотите изменить тему, то приложение автоматически перезапустится.\nВсе введенные данные удалятся.\nВы точно хотите сменить тему?");
            adb.setPositiveButton("Сменить тему", myClickListener);
            adb.setNegativeButton("Отмена", myClickListener);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case Dialog.BUTTON_POSITIVE:
                    recreateApp();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    private void changeNumOfTheme(){

    }

    private void recreateApp(){
        sharedPreferences = getSharedPreferences("theme.num",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        num_of_theme = sharedPreferences.getInt("THEME",1);
        if(num_of_theme == 1) {
            editor.putInt("THEME", 2).apply();
        }else if(num_of_theme == 2){
            editor.putInt("THEME", 1).apply();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // Intent mStartActivity = new Intent(this, MainActivity.class);
       // int mPendingIntentId = 123456;
       // PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
       // AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
       // mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
       // System.exit(0);
    }
}
