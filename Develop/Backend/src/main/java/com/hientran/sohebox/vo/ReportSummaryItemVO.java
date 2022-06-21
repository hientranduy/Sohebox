package com.hientran.sohebox.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class ReportSummaryItemVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String pair;

    private Date fromDate;

    private Date toDate;

    /**
     * Constructor
     *
     */
    public ReportSummaryItemVO() {
        super();
    }

    /**
     * Get pair
     *
     * @return pair
     */
    public String getPair() {
        return pair;
    }

    /**
     * Set pair
     *
     * @param pair
     *            the pair to set
     */
    public void setPair(String pair) {
        this.pair = pair;
    }

    /**
     * Get fromDate
     *
     * @return fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Set fromDate
     *
     * @param fromDate
     *            the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Get toDate
     *
     * @return toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * Set toDate
     *
     * @param toDate
     *            the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ReportSummaryVO [pair=" + pair + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
    }

}