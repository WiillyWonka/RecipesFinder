package com.recipesfinder;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DataBase {
    private static final int DISH_NAME = 0;
    private static final int STEPS = 1;
    private static final int CATEGORY = 2;
    private static final int INGREDIENTS = 3;
    private static final String DISH_TABLE_NAME = "Dishes";
    private static final String CATEGORIES_TABLE_NAME = "Categories";
    private static final String MATCHING_TABLE_NAME = "Ingredients_matching";
    private static final String INGREDIENTS_TABLE_NAME = "Ingredients";

    private static SQLiteDatabase mDb = null;
    private static DataBase db = null;

    private DataBase(Context context) {
        DatabaseHelper mDBHelper;

        mDBHelper = new DatabaseHelper(context);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public static DataBase getDataBase(Context context) {
        if (db == null) {
            db = new DataBase(context);
        }
        return db;
    }

    public Dish[] getDishListByIngredients(@NotNull String[] ingredients) {
        String query = "";
        Integer count = 1;
        for (String ingredient : ingredients) {
            query +=" join ingredients_matching mtch" + count.toString() +
                    " on dsh.dish_id = mtch" + count.toString() + ".dish_id" +
                    " join ingredients ing" + count.toString() +
                    " on ing"+ count.toString() + ".ingredient_id = mtch" +
                    count.toString() + ".ingredient_id" +
                    " AND ing" + count.toString() + ".ingredient_name = \"" + ingredient + "\"";
            count += 1;
        }

        query = "select dish_name, dish_steps, category_name, group_concat(ing.ingredient_name)" +
                " from dishes dsh"+
                query +
                " join ingredients_matching mtch " +
                " on dsh.dish_id = mtch.dish_id" +
                " join ingredients ing" +
                " on ing.ingredient_id = mtch.ingredient_id" +
                " join categories cat" +
                " on cat.category_id = dsh.category_id" +
                " GROUP by (dsh.dish_id)" +
                " Order by COUNT(ing.ingredient_name)";

        Cursor cursor = mDb.rawQuery(query, null);

        Dish[] dish = new Dish[cursor.getCount()];

        cursor.moveToFirst();
        for (int i = 0; i < dish.length; i++) {
            dish[i] = new Dish(cursor.getString(DISH_NAME),
                    cursor.getString(INGREDIENTS),
                    null,
                    cursor.getString(CATEGORY),
                    cursor.getString(STEPS));
            cursor.moveToNext();
        }

        cursor.close();

        return dish;
        /*cursor.moveToFirst();
        String product = "";
        while (!cursor.isAfterLast()) {
            product += cursor.getString(0) + " | ";
            cursor.moveToNext();
        }
        cursor.close();
        return product;*/
    }
}
