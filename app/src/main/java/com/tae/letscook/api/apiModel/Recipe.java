
package com.tae.letscook.api.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Recipe {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sourceIcon")
    @Expose
    private String sourceIcon;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("shareAs")
    @Expose
    private String shareAs;
    @SerializedName("yield")
    @Expose
    private Integer yield;
    @SerializedName("dietLabels")
    @Expose
    private List<String> dietLabels = new ArrayList<String>();
    @SerializedName("healthLabels")
    @Expose
    private List<String> healthLabels = new ArrayList<String>();
    @SerializedName("cautions")
    @Expose
    private List<Object> cautions = new ArrayList<Object>();
    @SerializedName("ingredientLines")
    @Expose
    private List<String> ingredientLines = new ArrayList<String>();
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    @SerializedName("calories")
    @Expose
    private Double calories;
    @SerializedName("totalWeight")
    @Expose
    private Double totalWeight;
    @SerializedName("totalNutrients")
    @Expose
    private TotalNutrients totalNutrients;
    @SerializedName("totalDaily")
    @Expose
    private TotalDaily totalDaily;
    @SerializedName("digest")
    @Expose
    private List<Digest> digest = new ArrayList<Digest>();

    /**
     * 
     * @return
     *     The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *     The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     * 
     * @param source
     *     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * @return
     *     The sourceIcon
     */
    public String getSourceIcon() {
        return sourceIcon;
    }

    /**
     * 
     * @param sourceIcon
     *     The sourceIcon
     */
    public void setSourceIcon(String sourceIcon) {
        this.sourceIcon = sourceIcon;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The shareAs
     */
    public String getShareAs() {
        return shareAs;
    }

    /**
     * 
     * @param shareAs
     *     The shareAs
     */
    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    /**
     * 
     * @return
     *     The yield
     */
    public Integer getYield() {
        return yield;
    }

    /**
     * 
     * @param yield
     *     The yield
     */
    public void setYield(Integer yield) {
        this.yield = yield;
    }

    /**
     * 
     * @return
     *     The dietLabels
     */
    public List<String> getDietLabels() {
        return dietLabels;
    }

    /**
     * 
     * @param dietLabels
     *     The dietLabels
     */
    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    /**
     * 
     * @return
     *     The healthLabels
     */
    public List<String> getHealthLabels() {
        return healthLabels;
    }

    /**
     * 
     * @param healthLabels
     *     The healthLabels
     */
    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    /**
     * 
     * @return
     *     The cautions
     */
    public List<Object> getCautions() {
        return cautions;
    }

    /**
     * 
     * @param cautions
     *     The cautions
     */
    public void setCautions(List<Object> cautions) {
        this.cautions = cautions;
    }

    /**
     * 
     * @return
     *     The ingredientLines
     */
    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    /**
     * 
     * @param ingredientLines
     *     The ingredientLines
     */
    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    /**
     * 
     * @return
     *     The ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * 
     * @param ingredients
     *     The ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * 
     * @return
     *     The calories
     */
    public Double getCalories() {
        return calories;
    }

    /**
     * 
     * @param calories
     *     The calories
     */
    public void setCalories(Double calories) {
        this.calories = calories;
    }

    /**
     * 
     * @return
     *     The totalWeight
     */
    public Double getTotalWeight() {
        return totalWeight;
    }

    /**
     * 
     * @param totalWeight
     *     The totalWeight
     */
    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    /**
     * 
     * @return
     *     The totalNutrients
     */
    public TotalNutrients getTotalNutrients() {
        return totalNutrients;
    }

    /**
     * 
     * @param totalNutrients
     *     The totalNutrients
     */
    public void setTotalNutrients(TotalNutrients totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    /**
     * 
     * @return
     *     The totalDaily
     */
    public TotalDaily getTotalDaily() {
        return totalDaily;
    }

    /**
     * 
     * @param totalDaily
     *     The totalDaily
     */
    public void setTotalDaily(TotalDaily totalDaily) {
        this.totalDaily = totalDaily;
    }

    /**
     * 
     * @return
     *     The digest
     */
    public List<Digest> getDigest() {
        return digest;
    }

    /**
     * 
     * @param digest
     *     The digest
     */
    public void setDigest(List<Digest> digest) {
        this.digest = digest;
    }

}
