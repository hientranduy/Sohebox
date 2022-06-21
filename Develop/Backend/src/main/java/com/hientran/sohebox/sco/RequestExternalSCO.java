package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class RequestExternalSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchDateVO createdDate;

    private SearchNumberVO requestTypeId;

    private SearchTextVO requestUrl;

    private SearchTextVO note;

    /**
     * Set default constructor
     *
     */
    public RequestExternalSCO() {
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
     * Get createdDate
     *
     * @return createdDate
     */
    public SearchDateVO getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     *
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(SearchDateVO createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get requestTypeId
     *
     * @return requestTypeId
     */
    public SearchNumberVO getRequestTypeId() {
        return requestTypeId;
    }

    /**
     * Set requestTypeId
     *
     * @param requestTypeId
     *            the requestTypeId to set
     */
    public void setRequestTypeId(SearchNumberVO requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

    /**
     * Get requestUrl
     *
     * @return requestUrl
     */
    public SearchTextVO getRequestUrl() {
        return requestUrl;
    }

    /**
     * Set requestUrl
     *
     * @param requestUrl
     *            the requestUrl to set
     */
    public void setRequestUrl(SearchTextVO requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * Get note
     *
     * @return note
     */
    public SearchTextVO getNote() {
        return note;
    }

    /**
     * Set note
     *
     * @param note
     *            the note to set
     */
    public void setNote(SearchTextVO note) {
        this.note = note;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RequestExternalSCO [id=" + id + ", createdDate=" + createdDate + ", requestTypeId=" + requestTypeId
                + ", requestUrl=" + requestUrl + ", note=" + note + "]";
    }

}
