package com.hientran.sohebox.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class SjcGoldVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String title;

    private String url;

    private String updated;

    private String unit;

    private List<SjcGoldCityVO> city;

    /**
     * Constructor
     *
     */
    public SjcGoldVO() {
        super();
    }

    /**
     * Get title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     *
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     *
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get updated
     *
     * @return updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * Set updated
     *
     * @param updated
     *            the updated to set
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * Get unit
     *
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set unit
     *
     * @param unit
     *            the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Get city
     *
     * @return city
     */
    public List<SjcGoldCityVO> getCity() {
        return city;
    }

    /**
     * Set city
     *
     * @param city
     *            the city to set
     */
    public void setCity(List<SjcGoldCityVO> city) {
        this.city = city;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "VcbCurrencyVO [title=" + title + ", url=" + url + ", updated=" + updated + ", unit=" + unit + ", city="
                + city + "]";
    }

}