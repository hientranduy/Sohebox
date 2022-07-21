package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

/**
 * @author hientran
 */
@Entity
@Table(name = "request_external_tbl")
public class RequestExternalTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "createdDate", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_RequestExternalTbl_TypeTbl_requestType"))
    private TypeTbl requestType;

    @Column(name = "requestUrl", nullable = false, length = 500)
    private String requestUrl;

    @Column(name = "note")
    private String note;

    /**
     * Explanation of processing
     *
     */
    public RequestExternalTbl() {
        super();
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
    public TypeTbl getRequestType() {
        return requestType;
    }

    /**
     * Set requestType
     *
     * @param requestType
     *            the requestType to set
     */
    public void setRequestType(TypeTbl requestType) {
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

}
