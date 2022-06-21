package com.hientran.sohebox.sco;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Base search condition object
 *
 * @author hientran
 */
public class BaseSCO implements Serializable {

    private static final long serialVersionUID = -68140570073293062L;

    private Boolean deleteFlag;

    private Integer maxRecordPerPage;

    private Integer pageToGet;

    private Boolean reportFlag;

    private Boolean searchOr;

    private List<Sorter> sorters;

    public BaseSCO() {
        super();
    }

    /**
     * Get deleteFlag
     *
     * @return deleteFlag
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Set deleteFlag
     *
     * @param deleteFlag
     *            the deleteFlag to set
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * Get maxRecordPerPage
     *
     * @return maxRecordPerPage
     */
    public Integer getMaxRecordPerPage() {
        return maxRecordPerPage;
    }

    /**
     * Set maxRecordPerPage
     *
     * @param maxRecordPerPage
     *            the maxRecordPerPage to set
     */
    public void setMaxRecordPerPage(Integer maxRecordPerPage) {
        this.maxRecordPerPage = maxRecordPerPage;
    }

    /**
     * Get pageToGet
     *
     * @return pageToGet
     */
    public Integer getPageToGet() {
        return pageToGet;
    }

    /**
     * Set pageToGet
     *
     * @param pageToGet
     *            the pageToGet to set
     */
    public void setPageToGet(Integer pageToGet) {
        this.pageToGet = pageToGet;
    }

    public Boolean getSearchOr() {
        return searchOr;
    }

    public void setSearchOr(Boolean searchOr) {
        this.searchOr = searchOr;
    }

    /**
     * Get sorters
     *
     * @return sorters
     */
    public List<Sorter> getSorters() {
        return sorters;
    }

    /**
     * Set sorters
     *
     * @param sorters
     *            the sorters to set
     */
    public void setSorters(List<Sorter> sorters) {
        this.sorters = sorters;
    }

    /**
     * Get reportFlag
     *
     * @return reportFlag
     */
    public Boolean getReportFlag() {
        return reportFlag;
    }

    /**
     * Set reportFlag
     *
     * @param reportFlag
     *            the reportFlag to set
     */
    public void setReportFlag(Boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BaseSCO [deleteFlag=" + deleteFlag + ", maxRecordPerPage=" + maxRecordPerPage + ", pageToGet="
                + pageToGet + ", reportFlag=" + reportFlag + ", sorters=" + sorters + "]";
    }

}
