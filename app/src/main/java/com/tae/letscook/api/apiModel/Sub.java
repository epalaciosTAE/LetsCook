
package com.tae.letscook.api.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sub {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("schemaOrgTag")
    @Expose
    private String schemaOrgTag;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("hasRDI")
    @Expose
    private Boolean hasRDI;
    @SerializedName("daily")
    @Expose
    private Integer daily;
    @SerializedName("unit")
    @Expose
    private String unit;

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
     *     The tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * 
     * @param tag
     *     The tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 
     * @return
     *     The schemaOrgTag
     */
    public String getSchemaOrgTag() {
        return schemaOrgTag;
    }

    /**
     * 
     * @param schemaOrgTag
     *     The schemaOrgTag
     */
    public void setSchemaOrgTag(String schemaOrgTag) {
        this.schemaOrgTag = schemaOrgTag;
    }

    /**
     * 
     * @return
     *     The total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The hasRDI
     */
    public Boolean getHasRDI() {
        return hasRDI;
    }

    /**
     * 
     * @param hasRDI
     *     The hasRDI
     */
    public void setHasRDI(Boolean hasRDI) {
        this.hasRDI = hasRDI;
    }

    /**
     * 
     * @return
     *     The daily
     */
    public Integer getDaily() {
        return daily;
    }

    /**
     * 
     * @param daily
     *     The daily
     */
    public void setDaily(Integer daily) {
        this.daily = daily;
    }

    /**
     * 
     * @return
     *     The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit
     *     The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

}
