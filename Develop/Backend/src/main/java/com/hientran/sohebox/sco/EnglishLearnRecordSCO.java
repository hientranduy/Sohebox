package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class EnglishLearnRecordSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchNumberVO userId;

    private SearchNumberVO englishId;

    private SearchNumberVO recordTimes;

    private SearchDateVO updatedDate;

    /**
     * Set default constructor
     *
     */
    public EnglishLearnRecordSCO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public SearchNumberVO getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    /**
     * Get userId
     *
     * @return userId
     */
    public SearchNumberVO getUserId() {
        return userId;
    }

    /**
     * Set userId
     *
     * @param userId
     *            the userId to set
     */
    public void setUserId(SearchNumberVO userId) {
        this.userId = userId;
    }

    /**
     * Get englishId
     *
     * @return englishId
     */
    public SearchNumberVO getEnglishId() {
        return englishId;
    }

    /**
     * Set englishId
     *
     * @param englishId
     *            the englishId to set
     */
    public void setEnglishId(SearchNumberVO englishId) {
        this.englishId = englishId;
    }

    /**
     * Get recordTimes
     *
     * @return recordTimes
     */
    public SearchNumberVO getRecordTimes() {
        return recordTimes;
    }

    /**
     * Set recordTimes
     *
     * @param recordTimes
     *            the recordTimes to set
     */
    public void setRecordTimes(SearchNumberVO recordTimes) {
        this.recordTimes = recordTimes;
    }

    /**
     * Get updatedDate
     *
     * @return updatedDate
     */
    public SearchDateVO getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set updatedDate
     *
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(SearchDateVO updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "EnglishLearnRecordSCO [id=" + id + ", userId=" + userId + ", englishId=" + englishId + ", recordTimes="
                + recordTimes + ", updatedDate=" + updatedDate + "]";
    }

}
