package com.hientran.sohebox.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class RequestExternalVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date createdDate;

    private TypeVO requestType;

    private String requestUrl;

    private String note;

    /**
     * Constructor
     *
     */
    public RequestExternalVO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get createdDate
     *
     * @return createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     *
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get requestType
     *
     * @return requestType
     */
    public TypeVO getRequestType() {
        return requestType;
    }

    /**
     * Set requestType
     *
     * @param requestType
     *            the requestType to set
     */
    public void setRequestType(TypeVO requestType) {
        this.requestType = requestType;
    }

    /**
     * Get requestUrl
     *
     * @return requestUrl
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * Set requestUrl
     *
     * @param requestUrl
     *            the requestUrl to set
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * Get note
     *
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     *
     * @param note
     *            the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RequestExternalVO [id=" + id + ", createdDate=" + createdDate + ", requestType=" + requestType
                + ", requestUrl=" + requestUrl + ", note=" + note + "]";
    }

}