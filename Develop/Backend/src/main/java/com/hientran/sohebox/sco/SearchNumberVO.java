package com.hientran.sohebox.sco;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * Search number
 *
 * @author hientran
 */
public class SearchNumberVO implements Serializable {

    private static final long serialVersionUID = -5082579604100959125L;

    private Double le;

    private Double ge;

    private Double eq;

    private Double notEq;

    private Double lt;

    private Double gt;

    private Double[] in;

    /**
     * Explanation of processing
     *
     */
    public SearchNumberVO() {
        super();
    }

    public SearchNumberVO(Double eq) {
        super();
        this.eq = eq;
    }

    /**
     * Get le
     *
     * @return le
     */
    public Double getLe() {
        return le;
    }

    /**
     * Set le
     *
     * @param le
     *            the le to set
     */
    public void setLe(Double le) {
        this.le = le;
    }

    /**
     * Get ge
     *
     * @return ge
     */
    public Double getGe() {
        return ge;
    }

    /**
     * Set ge
     *
     * @param ge
     *            the ge to set
     */
    public void setGe(Double ge) {
        this.ge = ge;
    }

    /**
     * Get eq
     *
     * @return eq
     */
    public Double getEq() {
        return eq;
    }

    /**
     * Set eq
     *
     * @param eq
     *            the eq to set
     */
    public void setEq(Double eq) {
        this.eq = eq;
    }

    /**
     * Get notEq
     *
     * @return notEq
     */
    public Double getNotEq() {
        return notEq;
    }

    /**
     * Set notEq
     *
     * @param notEq
     *            the notEq to set
     */
    public void setNotEq(Double notEq) {
        this.notEq = notEq;
    }

    /**
     * Get lt
     *
     * @return lt
     */
    public Double getLt() {
        return lt;
    }

    /**
     * Set lt
     *
     * @param lt
     *            the lt to set
     */
    public void setLt(Double lt) {
        this.lt = lt;
    }

    /**
     * Get gt
     *
     * @return gt
     */
    public Double getGt() {
        return gt;
    }

    /**
     * Set gt
     *
     * @param gt
     *            the gt to set
     */
    public void setGt(Double gt) {
        this.gt = gt;
    }

    /**
     * Get in
     *
     * @return in
     */
    public Double[] getIn() {
        return in;
    }

    /**
     * Set in
     *
     * @param in
     *            the in to set
     */
    public void setIn(Double[] in) {
        this.in = in;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SearchNumberVO [le=" + le + ", ge=" + ge + ", eq=" + eq + ", notEq=" + notEq + ", lt=" + lt + ", gt="
                + gt + ", in=" + Arrays.toString(in) + "]";
    }

}
