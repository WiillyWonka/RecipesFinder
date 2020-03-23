package com.recipesfinder;

public class Dish {
    private String name;
    private String ingredients;
    private String picture; //TODO: fix picture
    private String category;
    private String steps;

    public Dish(String name_, String ingredients_,
                String picture_, String category_, String steps_) {
        name = name_;
        ingredients = ingredients_;
        picture = picture_;
        category = category_;
        steps = steps_;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPicture() {
        return picture;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }
}
