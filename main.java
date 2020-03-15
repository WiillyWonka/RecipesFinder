import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class main {
    public static void main(String [] args) {
        parsing p = new parsing();
        SQLite_class sqLite_class = new SQLite_class();
        try {
            List<parsing.Recipes_category> myMap = p.parse_json();

            sqLite_class.open_create_SQL();
            sqLite_class.addCategories(myMap);
            sqLite_class.addDishes(myMap);
            sqLite_class.addIngredients(myMap);
            sqLite_class.addIngredientsMatching(myMap);
            sqLite_class.closeSQLite();
        }catch(IOException ex){
            System.out.println("Error!");
        }
        catch(SQLException ex){
            System.out.println("Error in SQL!");
        }
    }
}
