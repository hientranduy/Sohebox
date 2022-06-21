package com.hientran.sohebox.vo;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class QuandlDatasetVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private Object[][] data;

    /**
     * Constructor
     *
     */
    public QuandlDatasetVO() {
        super();
    }

    /**
     * Get startDate
     *
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Set startDate
     *
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Get endDate
     *
     * @return endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Set endDate
     *
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Get data
     *
     * @return data
     */
    public Object[][] getData() {
        return data;
    }

    /**
     * Set data
     *
     * @param data
     *            the data to set
     */
    public void setData(Object[][] data) {
        this.data = data;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "QuandlReponseVO [startDate=" + startDate + ", endDate=" + endDate + ", data=" + Arrays.toString(data)
                + "]";
    }

}