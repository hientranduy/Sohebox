package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class EnglishLearnReportSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchNumberVO userId;

    private SearchDateVO learnedDate;

    /**
     * Set default constructor
     *
     */
    public EnglishLearnReportSCO() {
        super();
    }

    public SearchNumberVO getId() {
        return id;
    }

    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    public SearchNumberVO getUserId() {
        return userId;
    }

    public void setUserId(SearchNumberVO userId) {
        this.userId = userId;
    }

    public SearchDateVO getLearnedDate() {
        return learnedDate;
    }

    public void setLearnedDate(SearchDateVO learnedDate) {
        this.learnedDate = learnedDate;
    }

    @Override
    public String toString() {
        return "EnglishLearnReportSCO [id=" + id + ", userId=" + userId + ", learnedDate=" + learnedDate + "]";
    }

}
