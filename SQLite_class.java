import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class SQLite_class {
    private static Connection connection;
    private  ArrayList<String> all_ingredients;

    public void open_create_SQL() throws SQLException{
        final String database = "all_recipes.db";
        connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS 'categories'"
                + " ('category_id'       INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " 'category_name'       TEXT NOT NULL"
                + ");");



        statement.executeUpdate("CREATE TABLE  IF NOT EXISTS 'dishes' ("
                + " 'dish_id'         INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " 'dish_name'       TEXT NOT NULL,"
                + " 'dish_steps' TEXT NOT NULL,"
                + " 'category_id'    INTEGER"
                + ");");

        statement.executeUpdate("CREATE TABLE  IF NOT EXISTS 'ingredients' ("
                + " 'ingredient_id'       INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " 'ingredient_name'       TEXT NOT NULL,"
                + " 'ingredient_prior'      INTEGER"
                + ");");

        statement.executeUpdate("CREATE TABLE  IF NOT EXISTS 'ingredients_matching' ("
                + " 'ingredient_id'       INTEGER,"
                + " 'dish_id'       INTEGER"
                + ");");
    }

    public void closeSQLite(){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {}
        }
    }

    public void addCategories(List<parsing.Recipes_category> list_of_recipes) throws SQLException{
        PreparedStatement insertStmt = connection.prepareStatement(
                "INSERT INTO categories(category_name) VALUES(?)");
        for (int i = 0; i < list_of_recipes.size(); i++){
            String name = list_of_recipes.get(i).name;
            insertStmt.setString(1, name);
            insertStmt.executeUpdate();
        }
    }


    public void addDishes(List<parsing.Recipes_category> list_of_recipes) throws SQLException{
        PreparedStatement insertStmt = connection.prepareStatement(
                "INSERT INTO dishes(dish_name, dish_steps, category_id) VALUES(?, ?, ?)");
        int quan_of_categories = list_of_recipes.size();
        for (int i = 0; i < quan_of_categories; i++){
            int category_id = i;
            int quan_of_recipes = list_of_recipes.get(i).recipes_name.size();
            for (int j = 0; j < quan_of_recipes; j++){
                String dish_name = list_of_recipes.get(i).recipes_name.get(j).name;
                String dish_steps = "";
                int quan_of_steps = list_of_recipes.get(i).recipes_name.get(j).steps_of_cooking.size();
                for (int k = 0; k < quan_of_steps; k++){
                    String dish_add = list_of_recipes.get(i).recipes_name.get(j).steps_of_cooking.get(k).name;
                    dish_steps += dish_add;
                }
                insertStmt.setString(1, dish_name);
                insertStmt.setString(2, dish_steps);
                insertStmt.setInt(3, category_id);
                insertStmt.executeUpdate();
            }
        }
    }

    public void addIngredients(List<parsing.Recipes_category> list_of_recipes) throws SQLException{
        PreparedStatement insertStmt = connection.prepareStatement(
                "INSERT INTO ingredients(ingredient_name, ingredient_prior) VALUES(?, ?)");
        int quan_of_categories = list_of_recipes.size();
        all_ingredients = new ArrayList<String>();
        for (int i = 0; i < quan_of_categories; i++){
            int quan_of_recipes = list_of_recipes.get(i).recipes_name.size();
            for (int j= 0; j < quan_of_recipes; j++) {
                int quan_of_ingr = list_of_recipes.get(i).recipes_name.get(j).indredients.size();
                for (int k = 0; k < quan_of_ingr; k++) {
                    String ingr = list_of_recipes.get(i).recipes_name.get(j).indredients.get(k).name;
                    if (!all_ingredients.contains(ingr)){
                        all_ingredients.add(ingr);
                    }
                }
            }
        }
        for (int i = 0; i < all_ingredients.size(); i++){
            insertStmt.setString(1, all_ingredients.get(i));
            insertStmt.setInt(2, 1);
            insertStmt.executeUpdate();
        }
    }

    public void addIngredientsMatching(List<parsing.Recipes_category> list_of_recipes) throws SQLException{
        PreparedStatement insertStmt = connection.prepareStatement(
                "INSERT INTO ingredients_matching(ingredient_id, dish_id) VALUES(?, ?)");
        int quan_of_categories = list_of_recipes.size();
        int previous_quan = 0;
        for (int i = 0; i < quan_of_categories; i++) {
            int quan_of_recipes = list_of_recipes.get(i).recipes_name.size();
            for (int j = 0; j < quan_of_recipes; j++) {
                int dish_id = j + previous_quan;
                int quan_of_ingr = list_of_recipes.get(i).recipes_name.get(j).indredients.size();
                for (int k = 0; k < quan_of_ingr; k++) {
                    String ingr = list_of_recipes.get(i).recipes_name.get(j).indredients.get(k).name;
                    int ingr_id = all_ingredients.indexOf(ingr);
                    insertStmt.setInt(1, ingr_id);
                    insertStmt.setInt(2, dish_id);
                    insertStmt.executeUpdate();
                }
            }
            previous_quan += quan_of_recipes;
        }
    }
}
