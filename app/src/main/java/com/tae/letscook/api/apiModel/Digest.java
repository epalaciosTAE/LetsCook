
package com.tae.letscook.api.apiModel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Digest {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("schemaOrgTag")
    @Expose
    private Object schemaOrgTag;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("hasRDI")
    @Expose
    private Boolean hasRDI;
    @SerializedName("daily")
    @Expose
    private Double daily;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("sub")
    @Expose
    private List<Sub> sub = new ArrayList<Sub>();

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
    public Object getSchemaOrgTag() {
        return schemaOrgTag;
    }

    /**
     * 
     * @param schemaOrgTag
     *     The schemaOrgTag
     */
    public void setSchemaOrgTag(Object schemaOrgTag) {
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
    public Double getDaily() {
        return daily;
    }

    /**
     * 
     * @param daily
     *     The daily
     */
    public void setDaily(Double daily) {
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

    /**
     * 
     * @return
     *     The sub
     */
    public List<Sub> getSub() {
        return sub;
    }

    /**
     * 
     * @param sub
     *     The sub
     */
    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }

}
