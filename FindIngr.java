package com.example.recepiesfinder;

import android.app.ListActivity;
import android.os.Bundle;

public class FindIngr extends ListActivity {
    final char[] Alphabet = new char[]{ 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И' , 'Й',
            'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш',
            'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBase db = DataBase.getDataBase(this);
        String[] ingredients = db.getIngredientsList();
        int[] str = new int[33];
        int count = 0;
        int num = 0;
        for (String ingredient : ingredients) {
            char ch = ingredient.charAt(0);
            char c = Alphabet[num];
            if (c == ch) {
                count++;
            } else {
                str[num] = count;
                count = 0;
                num++;
                c = Alphabet[num];
                while (c != ch) {
                    num++;
                    c = Alphabet[num];
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
    }
}
