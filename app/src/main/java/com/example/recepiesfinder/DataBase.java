package com.example.recepiesfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DataBase {
    private static final String DISH_TABLE_NAME = "Dishes";
    private static final String CATEGORIES_TABLE_NAME = "Categories";
    private static final String MATCHING_TABLE_NAME = "Ingredients_matching";
    private static final String INGREDIENTS_TABLE_NAME = "Ingredients";

    private static final String DISH_ID_COLUMN_NAME = "dish_id";
    private static final String DISH_PHOTO_COLUMN_NAME = "dish_photo";
    private static final String DISH_NAME_COLUMN_NAME = "dish_name";
    private static final String DISH_STEPS_COLUMN_NAME = "dish_steps";
    private static final String DISH_CATEGORY_COLUMN_NAME = "category_id";

    private static final String INGREDIENTS_ID_COLUMN_NAME = "ingredient_id";
    private static final String INGREDIENTS_PRIORITY_COLUMN_NAME = "ingredient_prior";
    private static final String INGREDIENTS_NAME_COLUMN_NAME = "ingredient_name";

    private static final String INGREDIENTS_MATCHING_INGREDIENT_ID_COLUMN_NAME = "ingredient_id";
    private static final String INGREDIENTS_MATCHING_DISH_ID_COLUMN_NAME = "dish_id";

    private static final String CATEGORIES_CATEGORY_ID_COLUMN_NAME = "Category_id";
    private static final String CATEGORIES_CATEGORY_NAME_COLUMN_NAME = "Category_name";

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

    public long saveDish(@NotNull Dish dish) {
        Cursor cursor;

        cursor = mDb.query(
                CATEGORIES_TABLE_NAME, new String[]{CATEGORIES_CATEGORY_ID_COLUMN_NAME},  CATEGORIES_CATEGORY_NAME_COLUMN_NAME + " = \"" + dish.getCategory() +"\"",
                null, null, null, null, null);

        cursor.moveToFirst();
        long categoryID = cursor.getLong(0);

        cursor.close();

        ContentValues cv = new ContentValues();

        cv.put(DISH_PHOTO_COLUMN_NAME, "-");
        cv.put(DISH_NAME_COLUMN_NAME, dish.getName());
        String steps = "";
        for (String step : dish.getSteps()) steps += step + "\n";
        cv.put(DISH_STEPS_COLUMN_NAME, steps);
        cv.put(DISH_CATEGORY_COLUMN_NAME, categoryID);

        long dishID = mDb.insert(DISH_TABLE_NAME, null, cv);

        cursor.close();

        long ingredientID;
        for (String ingredient : dish.getIngredients().split(",")) {
            cursor = mDb.rawQuery("SELECT * FROM ingredients " +
                    "WHERE ingredients.ingredient_name = \'" + ingredient + "\'" , null);


            if (cursor.moveToFirst()) {
                ingredientID = cursor.getLong(0);
            } else {
                cv = new ContentValues();
                cv.put(INGREDIENTS_NAME_COLUMN_NAME, ingredient);
                cv.put(INGREDIENTS_PRIORITY_COLUMN_NAME, 1);

                ingredientID = mDb.insert(INGREDIENTS_TABLE_NAME, null, cv);
            }

            cv = new ContentValues();
            cv.put(INGREDIENTS_MATCHING_DISH_ID_COLUMN_NAME, dishID);
            cv.put(INGREDIENTS_MATCHING_INGREDIENT_ID_COLUMN_NAME, ingredientID);

            long k = mDb.insert(MATCHING_TABLE_NAME, null, cv);

            cursor.close();
        }

        return dishID;
    }

    public void deleteDish(Integer dishID) {
        Cursor cursor = mDb.query(
                MATCHING_TABLE_NAME, new String[] {INGREDIENTS_MATCHING_INGREDIENT_ID_COLUMN_NAME}, INGREDIENTS_MATCHING_DISH_ID_COLUMN_NAME + " = " + dishID.toString(),
                null, null, null, null, null);

        Long[] arrayIngredientsID = new Long[cursor.getCount()];

        cursor.moveToFirst();
        for (int i = 0; i < arrayIngredientsID.length; i++) {
            arrayIngredientsID[i] = cursor.getLong(0);
            cursor.moveToNext();
        }
        cursor.close();

        mDb.delete(DISH_TABLE_NAME, DISH_ID_COLUMN_NAME + " = " + dishID.toString(), null);
        mDb.delete(MATCHING_TABLE_NAME, INGREDIENTS_MATCHING_DISH_ID_COLUMN_NAME + " = " + dishID.toString(), null);

        for (int i = 0; i < arrayIngredientsID.length; i++) {
            cursor = mDb.query(
                    MATCHING_TABLE_NAME, null,
                    INGREDIENTS_MATCHING_INGREDIENT_ID_COLUMN_NAME + " = " + arrayIngredientsID[i].toString(),
                    null, null, null, null, null);

            if (cursor.getCount() == 0) {
                mDb.delete(INGREDIENTS_TABLE_NAME, INGREDIENTS_ID_COLUMN_NAME + " = " + arrayIngredientsID[i].toString(), null);
            }
        }

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
        final int DISH_NAME = 0;
        final int DISH_PHOTO = 1;
        final int STEPS = 2;
        final int CATEGORY = 3;
        final int INGREDIENTS = 4;
        final int ID = 5;

        Cursor cursor = mDb.rawQuery(query, null);

        Dish[] dish = null;

        if (cursor.moveToFirst() && cursor.getInt(ID) > 0) {
            dish = new Dish[cursor.getCount()];
            for (int i = 0; i < dish.length; i++) {
                int k = cursor.getInt(ID);
                String n = cursor.getString(DISH_NAME);
                n = cursor.getString(INGREDIENTS);
                n = cursor.getString(DISH_PHOTO);
                n = cursor.getString(CATEGORY);
                n = cursor.getString(STEPS);
                dish[i] = new Dish(
                        cursor.getInt(ID),
                        cursor.getString(DISH_NAME),
                        cursor.getString(INGREDIENTS),
                        cursor.getString(DISH_PHOTO),
                        cursor.getString(CATEGORY),
                        cursor.getString(STEPS)
                );
                cursor.moveToNext();
            }
        }

        cursor.close();

        return dish;
    }

    public boolean getMatching(String ingredient) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " +
                "ingredients_matching " +
                "join ingredients " +
                "on ingredients.ingredient_id = ingredients_matching.ingredient_id " +
                "WHERE ingredient_name = \"" + ingredient + "\"", null);

        return cursor.getCount() > 0;
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
        String query = "SELECT dishes.dish_name, dishes.dish_photo, dishes.dish_steps, category_name, group_concat(ingredients.ingredient_name), dishes.dish_id " +
                "from dishes " +
                "join categories " +
                "on dishes.category_id = categories.category_id " +
                "join ingredients_matching " +
                "on dishes.dish_id = ingredients_matching.dish_id " +
                "join ingredients "  +
                "on ingredients.ingredient_id = ingredients_matching.ingredient_id " +
                "where dishes.dish_id = " + id.toString();

        Dish[] dishes = getDishByQuery(query);

        return dishes == null ? null : dishes[0];
    }
}
