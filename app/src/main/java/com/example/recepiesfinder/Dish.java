package com.example.recepiesfinder;

import java.io.Serializable;

public class Dish implements Serializable {
    private int id;
    private String name;
    private String ingredients;
    private String picture; //TODO: fix picture
    private String category;


    private String[] steps;
    private int count_steps;

    public Dish(int id_, String name_, String ingredients_,
                String picture_, String category_, String steps_) {
        id = id_;
        name = name_;
        ingredients = ingredients_;
        picture = picture_;
        category = category_;

        steps = steps_.split("\n");
        count_steps = steps.length;
    }

    public Dish(String name_, String ingredients_,
                String picture_, String category_, String steps_) {
        this(0, name_, ingredients_, picture_, category_, steps_);
    }

    public Dish(int id_, String name_, String ingredients_,
                String picture_, String category_, String[] steps_) {
        id = id_;
        name = name_;
        ingredients = ingredients_;
        picture = picture_;
        category = category_;

        steps = steps_.clone();
        count_steps = steps.length;
    }

    public Dish(String name_, String ingredients_,
                String picture_, String category_, String[] steps_) {
        this(0, name_, ingredients_, picture_, category_, steps_);
    }

    public int getId() { return id; }

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
