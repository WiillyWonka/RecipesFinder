package com.example.recepiesfinder;

import java.io.Serializable;

public class Dish implements Serializable {
    private String name;
    private String ingredients;
    private String picture; //TODO: fix picture
    private String category;


    private String[] steps;
    private int count_steps;

    public Dish(String name_, String ingredients_,
                String picture_, String category_, String steps_) {
        name = name_;
        ingredients = ingredients_;
        picture = picture_;
        category = category_;

        steps = steps_.split("\n");
        count_steps = steps.length;
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
    public String[] getSteps() {
        return steps;
    }

    public int getCount_steps(){
        return count_steps;
    }
}
