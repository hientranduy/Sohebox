package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class BittrexReponseVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String message;

    private Object result;

    /**
     * Constructor
     *
     */
    public BittrexReponseVO() {
        super();
    }

    /**
     * Get success
     *
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Set success
     *
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Get message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message
     *
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get result
     *
     * @return result
     */
    public Object getResult() {
        return result;
    }

    /**
     * Set result
     *
     * @param result
     *            the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexReponseVO [success=" + success + ", message=" + message + ", result=" + result + "]";
    }
}