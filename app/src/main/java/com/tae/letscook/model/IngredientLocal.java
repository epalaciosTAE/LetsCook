package com.tae.letscook.model;

/**
 * Created by Eduardo on 15/01/2016.
 */
public class IngredientLocal {

    private String name;
    private String amount;

    public IngredientLocal(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }


}
