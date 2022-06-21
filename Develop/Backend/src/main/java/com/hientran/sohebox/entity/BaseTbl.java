package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author hientran
 */
@MappedSuperclass
@JsonIgnoreProperties(value = { "createdDate", "updatedDate" }, allowGetters = true)
public abstract class BaseTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "createdDate", nullable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Column(name = "updatedDate", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedDate;

    @Column(name = "deleteFlag", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean deleteFlag;

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
     * Get updatedDate
     *
     * @return updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set updatedDate
     *
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get deleteFlag
     *
     * @return deleteFlag
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Set deleteFlag
     *
     * @param deleteFlag
     *            the deleteFlag to set
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
