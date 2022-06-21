package com.hientran.sohebox.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class SjcGoldCityVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String cityName;

    private List<SjcGoldItemVO> items;

    /**
     * Constructor
     *
     */
    public SjcGoldCityVO() {
        super();
    }

    /**
     * Get cityName
     *
     * @return cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Set cityName
     *
     * @param cityName
     *            the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Get items
     *
     * @return items
     */
    public List<SjcGoldItemVO> getItems() {
        return items;
    }

    /**
     * Set items
     *
     * @param items
     *            the items to set
     */
    public void setItems(List<SjcGoldItemVO> items) {
        this.items = items;
    }

}