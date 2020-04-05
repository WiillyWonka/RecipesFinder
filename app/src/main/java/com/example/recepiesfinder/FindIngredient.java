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
import java.util.Collections;
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
    final char[] AlphabetChar = new char[]{ 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И' , 'Й',
            'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш',
            'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
    protected void loadData(String [] array1, String [] array2, String [] array3, String [] array4,
                            String [] array5, String [] array6, String [] array7, String [] array8,
                            String [] array9, String [] array10, String [] array11, String [] array12,
                            String [] array13, String [] array14, String [] array15, String [] array16,
                            String [] array17, String [] array18, String [] array19, String [] array20,
                            String [] array21, String [] array22, String [] array23, String [] array24,
                            String [] array25, String [] array26, String [] array27, String [] array28,
                            String [] array29, String [] array30, String [] array31, String [] array32,
                            String [] array33){
        expListDetail = new HashMap<>();
        char[] arr = AlphabetChar;
        for (char c : arr) {
            int resId = 0;
            String[] help = new String[0];
            if (array1.length != 0) {
                if (c == array1[0].charAt(0)) {
                    resId = array1.length;
                    help = array1;
                }
            }
            if (array2.length != 0) {
                if (c == array2[0].charAt(0)) {
                    resId = array2.length;
                    help = array2;
                }
            }
            if (array3.length != 0) {
                if (c == array3[0].charAt(0)) {
                    resId = array3.length;
                    help = array3;
                }
            }
            if (array4.length != 0) {
                if (c == array4[0].charAt(0)) {
                    resId = array4.length;
                    help = array4;
                }
            }
            if (array5.length != 0) {
                if (c == array5[0].charAt(0)) {
                    resId = array5.length;
                    help = array5;
                }
            }
            if (array6.length != 0) {
                if (c == array6[0].charAt(0)) {
                    resId = array6.length;
                    help = array6;
                }
            }
            if (array7.length != 0) {
                if (c == array7[0].charAt(0)) {
                    resId = array7.length;
                    help = array7;
                }
            }
            if (array8.length != 0) {
                if (c == array8[0].charAt(0)) {
                    resId = array8.length;
                    help = array8;
                }
            }
            if (array9.length != 0) {
                if (c == array9[0].charAt(0)) {
                    resId = array9.length;
                    help = array9;
                }
            }
            if (array10.length != 0) {
                if (c == array10[0].charAt(0)) {
                    resId = array10.length;
                    help = array10;
                }
            }
            if (array11.length != 0) {
                if (c == array11[0].charAt(0)) {
                    resId = array11.length;
                    help = array11;
                }
            }
            if (array12.length != 0) {
                if (c == array12[0].charAt(0)) {
                    resId = array12.length;
                    help = array12;
                }
            }
            if (array13.length != 0) {
                if (c == array13[0].charAt(0)) {
                    resId = array13.length;
                    help = array13;
                }
            }
            if (array14.length != 0) {
                if (c == array14[0].charAt(0)) {
                    resId = array14.length;
                    help = array14;
                }
            }
            if (array15.length != 0) {
                if (c == array15[0].charAt(0)) {
                    resId = array15.length;
                    help = array15;
                }
            }
            if (array16.length != 0) {
                if (c == array16[0].charAt(0)) {
                    resId = array16.length;
                    help = array16;
                }
            }
            if (array17.length != 0) {
                if (c == array17[0].charAt(0)) {
                    resId = array17.length;
                    help = array17;
                }
            }
            if (array18.length != 0) {
                if (c == array18[0].charAt(0)) {
                    resId = array18.length;
                    help = array18;
                }
            }
            if (array19.length != 0) {
                if (c == array19[0].charAt(0)) {
                    resId = array19.length;
                    help = array19;
                }
            }
            if (array20.length != 0) {
                if (c == array20[0].charAt(0)) {
                    resId = array20.length;
                    help = array20;
                }
            }
            if (array21.length != 0) {
                if (c == array21[0].charAt(0)) {
                    resId = array21.length;
                    help = array21;
                }
            }
            if (array22.length != 0) {
                if (c == array22[0].charAt(0)) {
                    resId = array22.length;
                    help = array22;
                }
            }
            if (array23.length != 0) {
                if (c == array23[0].charAt(0)) {
                    resId = array23.length;
                    help = array23;
                }
            }
            if (array24.length != 0) {
                if (c == array24[0].charAt(0)) {
                    resId = array24.length;
                    help = array24;
                }
            }
            if (array25.length != 0) {
                if (c == array25[0].charAt(0)) {
                    resId = array25.length;
                    help = array25;
                }
            }
            if (array26.length != 0) {
                if (c == array26[0].charAt(0)) {
                    resId = array26.length;
                    help = array26;
                }
            }
            if (array27.length != 0) {
                if (c == array27[0].charAt(0)) {
                    resId = array27.length;
                    help = array27;
                }
            }
            if (array28.length != 0) {
                if (c == array28[0].charAt(0)) {
                    resId = array28.length;
                    help = array28;
                }
            }
            if (array29.length != 0) {
                if (c == array29[0].charAt(0)) {
                    resId = array29.length;
                    help = array29;
                }
            }
            if (array30.length != 0) {
                if (c == array30[0].charAt(0)) {
                    resId = array30.length;
                    help = array30;
                }
            }
            if (array31.length != 0) {
                if (c == array31[0].charAt(0)) {
                    resId = array31.length;
                    help = array31;
                }
            }
            if (array32.length != 0) {
                if (c == array32[0].charAt(0)) {
                    resId = array32.length;
                    help = array32;
                }
            }
            if (array33.length != 0) {
                if (c == array33[0].charAt(0)) {
                    resId = array33.length;
                    help = array33;
                }
            }
            if (resId == 0)
                continue;
            List<String> arrHelp = new ArrayList<String>();
            Collections.addAll(arrHelp, help);
            if (arrHelp.size() != 0)
                expListDetail.put(arrHelp.get(0).substring(0, 1), arrHelp);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ingridien);
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        DataBase db = DataBase.getDataBase(this);
        String[] ingredients = db.getIngredientsList();
        int[] str = new int[33];
        int count = 0;
        int num = 0;
        for (String ingredient : ingredients) {
            char ch = ingredient.charAt(0);
            char c = AlphabetChar[num];
            if (c == ch) {
                count++;
            } else {
                str[num] = count;
                count = 0;
                num++;
                c = AlphabetChar[num];
                while (c != ch) {
                    num++;
                    c = AlphabetChar[num];
                }
                count++;
            }
        }
        str[num] = count;
        String[] array1 = new String[str[0]];
        String[] array2 = new String[str[1]];
        String[] array3 = new String[str[2]];
        String[] array4 = new String[str[3]];
        String[] array5 = new String[str[4]];
        String[] array6 = new String[str[5]];
        String[] array7 = new String[str[6]];
        String[] array8 = new String[str[7]];
        String[] array9 = new String[str[8]];
        String[] array10 = new String[str[9]];
        String[] array11 = new String[str[10]];
        String[] array12 = new String[str[11]];
        String[] array13 = new String[str[12]];
        String[] array14 = new String[str[13]];
        String[] array15 = new String[str[14]];
        String[] array16 = new String[str[15]];
        String[] array17 = new String[str[16]];
        String[] array18 = new String[str[17]];
        String[] array19 = new String[str[18]];
        String[] array20 = new String[str[19]];
        String[] array21 = new String[str[20]];
        String[] array22 = new String[str[21]];
        String[] array23 = new String[str[22]];
        String[] array24 = new String[str[23]];
        String[] array25 = new String[str[24]];
        String[] array26 = new String[str[25]];
        String[] array27 = new String[str[26]];
        String[] array28 = new String[str[27]];
        String[] array29 = new String[str[28]];
        String[] array30 = new String[str[29]];
        String[] array31 = new String[str[30]];
        String[] array32 = new String[str[31]];
        String[] array33 = new String[str[32]];
        num = 0;
        count = 0;
        int i = 0;
        int len = str[num];
        for (i = 0; i < len; i++) {
            array1[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array2[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array3[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array4[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array5[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array6[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array7[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array8[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array9[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array10[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array11[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array12[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array13[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array14[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array15[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array16[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array17[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array18[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array19[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array20[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array21[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array22[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array23[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array24[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array25[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array26[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array27[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array28[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array29[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array30[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array31[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array32[count] = ingredients[i];
            count++;
        }
        count = 0;
        num++;
        len += str[num];
        for (; i < len; i++) {
            array33[count] = ingredients[i];
            count++;
        }

        loadData(array1,  array2,  array3,  array4, array5, array6, array7, array8,
                 array9, array10, array11, array12, array13, array14, array15, array16,
                 array17, array18, array19, array20, array21, array22, array23, array24,
                 array25, array26, array27, array28, array29, array30, array31, array32, array33);

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
