package com.hientran.sohebox.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class ReportSummaryVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Boolean sendMailFalg;

    private List<ReportSummaryItemVO> reportItems;

    /**
     * Constructor
     *
     */
    public ReportSummaryVO() {
        super();
    }

    /**
     * Get sendMailFalg
     *
     * @return sendMailFalg
     */
    public Boolean getSendMailFalg() {
        return sendMailFalg;
    }

    /**
     * Set sendMailFalg
     *
     * @param sendMailFalg
     *            the sendMailFalg to set
     */
    public void setSendMailFalg(Boolean sendMailFalg) {
        this.sendMailFalg = sendMailFalg;
    }

    /**
     * Get reportItems
     *
     * @return reportItems
     */
    public List<ReportSummaryItemVO> getReportItems() {
        return reportItems;
    }

    /**
     * Set reportItems
     *
     * @param reportItems
     *            the reportItems to set
     */
    public void setReportItems(List<ReportSummaryItemVO> reportItems) {
        this.reportItems = reportItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ReportSummaryVO [sendMailFalg=" + sendMailFalg + ", reportItems=" + reportItems + "]";
    }

}