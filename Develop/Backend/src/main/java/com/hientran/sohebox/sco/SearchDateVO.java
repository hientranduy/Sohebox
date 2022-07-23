package com.hientran.sohebox.sco;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 
 * Search day
 *
 * @author hientran
 */
public class SearchDateVO implements Serializable {

    private static final long serialVersionUID = 295043855195423880L;

    private Date lt;

    private Date gt;

    private Date le;

    private Date ge;

    private Date eq;

    private Date[] in;

    /**
     * Explanation of processing
     *
     */
    public SearchDateVO() {
        super();
    }

    public SearchDateVO(Date eq) {
        super();
        this.eq = eq;
    }

    /**
     * Get lt
     *
     * @return lt
     */
    public Date getLt() {
        return lt;
    }

    /**
     * Set lt
     *
     * @param lt
     *            the lt to set
     */
    public void setLt(Date lt) {
        this.lt = lt;
    }

    /**
     * Get gt
     *
     * @return gt
     */
    public Date getGt() {
        return gt;
    }

    /**
     * Set gt
     *
     * @param gt
     *            the gt to set
     */
    public void setGt(Date gt) {
        this.gt = gt;
    }

    /**
     * Get le
     *
     * @return le
     */
    public Date getLe() {
        return le;
    }

    /**
     * Set le
     *
     * @param le
     *            the le to set
     */
    public void setLe(Date le) {
        this.le = le;
    }

    /**
     * Get ge
     *
     * @return ge
     */
    public Date getGe() {
        return ge;
    }

    /**
     * Set ge
     *
     * @param ge
     *            the ge to set
     */
    public void setGe(Date ge) {
        this.ge = ge;
    }

    /**
     * Get eq
     *
     * @return eq
     */
    public Date getEq() {
        return eq;
    }

    /**
     * Set eq
     *
     * @param eq
     *            the eq to set
     */
    public void setEq(Date eq) {
        this.eq = eq;
    }

    /**
     * Get in
     *
     * @return in
     */
    public Date[] getIn() {
        return in;
    }

    /**
     * Set in
     *
     * @param in
     *            the in to set
     */
    public void setIn(Date[] in) {
        this.in = in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SearchDateVO [lt=" + lt + ", gt=" + gt + ", le=" + le + ", ge=" + ge + ", eq=" + eq + ", in="
                + Arrays.toString(in) + "]";
    }
}
