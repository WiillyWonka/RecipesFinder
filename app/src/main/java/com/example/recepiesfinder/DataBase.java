package com.example.recepiesfinder;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DataBase {
    private static final int DISH_NAME = 0;
    private static final int DISH_PHOTO = 1;
    private static final int STEPS = 2;
    private static final int CATEGORY = 3;
    private static final int INGREDIENTS = 4;
    private static final int ID = 5;
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

    public String[] getIngredientsList() {
        Cursor cursor = mDb.rawQuery("SELECT ingredient_name " +
                "FROM ingredients " +
                "ORDER by ingredient_name;", null);

        String[] ingredients = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = cursor.getString(0);
            cursor.moveToNext();
        }

        cursor.close();

        return ingredients;
    }

    private Dish[] getDishByQuery(String query) {
        Cursor cursor = mDb.rawQuery(query, null);

        Dish[] dish = new Dish[cursor.getCount()];

        cursor.moveToFirst();
        for (int i = 0; i < dish.length; i++) {
            dish[i] = new Dish(
                    cursor.getString(ID),
                    cursor.getString(DISH_NAME),
                    cursor.getString(INGREDIENTS),
                    cursor.getString(DISH_PHOTO),
                    cursor.getString(CATEGORY),
                    cursor.getString(STEPS)
            );
            cursor.moveToNext();
        }

        cursor.close();

        return dish;
    }

    public Dish[] getAllDishList() {
        String query = "SELECT dish_name, dish_photo, dish_steps, category_name, group_concat(ingredients.ingredient_name), dishes.dish_id " +
                "from dishes " +
                "join categories " +
                "on dishes.category_id = categories.category_id " +
                "join ingredients_matching " +
                "on dishes.dish_id = ingredients_matching.dish_id " +
                "join ingredients "  +
                "on ingredients.ingredient_id = ingredients_matching.ingredient_id " +
                "GROUP BY (dish_name)";

        return getDishByQuery(query);
    }

    public Dish[] getDishListByIngredients(@NotNull String[] ingredients) {
        String query = "(";
        for (String ingredient : ingredients) {
            query +="\'" + ingredient + "\'" + ", ";
        }

        query = query.substring(0, query.length() - 2);

        query += ")";

        query = "select dish_name, dish_photo, dish_steps, category_name, group_concat(ingredients.ingredient_name), tb.dish_id " +
                "from ( " +
                "  select count(dish_name) as number, dishes.* " +
                "from ingredients ing " +
                "join ingredients_matching " +
                "on ing.ingredient_id = ingredients_matching.ingredient_id " +
                "JOIN dishes " +
                "on dishes.dish_id = ingredients_matching.dish_id " +
                "WHERE ing.ingredient_name in " + query +
                " AND ing.ingredient_prior = 1 " +
                "GROUP BY (dish_name) " +
                "ORDER by count(dish_name) DESC " +
                "  ) tb " +
                "join categories " +
                "on tb.category_id = categories.category_id " +
                "join ingredients_matching " +
                "on tb.dish_id = ingredients_matching.dish_id " +
                "join ingredients " +
                "on ingredients.ingredient_id = ingredients_matching.ingredient_id " +
                "GROUP BY (tb.dish_name) " +
                "ORDER by number DESC ";

        return getDishByQuery(query);
    }

    public Dish getDishById(Integer id) {
        String query = "SELECT dish_name, dish_photo, dish_steps, category_name, group_concat(ingredients.ingredient_name), dishes.dish_id " +
                "from dishes " +
                "join categories " +
                "on dishes.category_id = categories.category_id " +
                "join ingredients_matching " +
                "on dishes.dish_id = ingredients_matching.dish_id " +
                "join ingredients "  +
                "on ingredients.ingredient_id = ingredients_matching.ingredient_id " +
                " where id = " + id.toString();

        return getDishByQuery(query)[0];
    }
}
