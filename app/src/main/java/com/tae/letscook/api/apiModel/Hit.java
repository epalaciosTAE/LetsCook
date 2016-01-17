
package com.tae.letscook.api.apiModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tae.letscook.model.*;

public class Hit {

    @SerializedName("recipe")
    @Expose
    private Recipe recipe;
    @SerializedName("bookmarked")
    @Expose
    private Boolean bookmarked;
    @SerializedName("bought")
    @Expose
    private Boolean bought;

    /**
     * 
     * @return
     *     The recipe
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * 
     * @param recipe
     *     The recipe
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * 
     * @return
     *     The bookmarked
     */
    public Boolean getBookmarked() {
        return bookmarked;
    }

    /**
     * 
     * @param bookmarked
     *     The bookmarked
     */
    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    /**
     * 
     * @return
     *     The bought
     */
    public Boolean getBought() {
        return bought;
    }

    /**
     * 
     * @param bought
     *     The bought
     */
    public void setBought(Boolean bought) {
        this.bought = bought;
    }

}
