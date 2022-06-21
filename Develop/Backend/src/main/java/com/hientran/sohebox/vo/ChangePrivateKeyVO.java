package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class ChangePrivateKeyVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String oldPrivateKey;

    private String newPrivateKey;

    /**
     * Constructor
     *
     */
    public ChangePrivateKeyVO() {
        super();
    }

    /**
     * Get oldPrivateKey
     *
     * @return oldPrivateKey
     */
    public String getOldPrivateKey() {
        return oldPrivateKey;
    }

    /**
     * Set oldPrivateKey
     *
     * @param oldPrivateKey
     *            the oldPrivateKey to set
     */
    public void setOldPrivateKey(String oldPrivateKey) {
        this.oldPrivateKey = oldPrivateKey;
    }

    /**
     * Get newPrivateKey
     *
     * @return newPrivateKey
     */
    public String getNewPrivateKey() {
        return newPrivateKey;
    }

    /**
     * Set newPrivateKey
     *
     * @param newPrivateKey
     *            the newPrivateKey to set
     */
    public void setNewPrivateKey(String newPrivateKey) {
        this.newPrivateKey = newPrivateKey;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ChangePrivateKeyVO [oldPrivateKey=" + oldPrivateKey + ", newPrivateKey=" + newPrivateKey + "]";
    }

}