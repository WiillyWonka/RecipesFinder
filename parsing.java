import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class parsing {
    static class Recipes_category{
        public String name;
        public List<Recipe> recipes_name;
    }
    static class Recipe{
        public String name;
        public URL url;
        public List<Ingredients> indredients;
        public List<Step_of_cooking> steps_of_cooking;
    }
    static class Ingredients{
        public String name;
        public URL url;
    }
    static class Step_of_cooking{
        public String name;
    }
    public List<Recipes_category> parse_json() throws IOException
    {
        Gson g = new Gson();
        Type type = new TypeToken<List<Recipes_category>>(){}.getType();
        String text = new String(Files.readAllBytes(Paths.get("recipes_from_site.json")), StandardCharsets.UTF_8);
        List<Recipes_category> myMap = g.fromJson(text, type);
        return myMap;
    }

}


