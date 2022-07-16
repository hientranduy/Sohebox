package com.hientran.sohebox.sco;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Search text
 * 
 * @author hientran
 */
public class SearchTextVO implements Serializable {

    private static final long serialVersionUID = -5375270910520186346L;

    private String eq;

    private String like;

    private int likeMode = 0;

    private String[] in;

    /**
     * Constructor
     *
     */
    public SearchTextVO() {
        super();
    }

    public SearchTextVO(String eq) {
        super();
        this.eq = eq;
    }

    /**
     * Get eq
     *
     * @return eq
     */
    public String getEq() {
        return eq;
    }

    /**
     * Set eq
     *
     * @param eq
     *            the eq to set
     */
    public void setEq(String eq) {
        this.eq = eq;
    }

    /**
     * Get like
     *
     * @return like
     */
    public String getLike() {
        return like;
    }

    /**
     * Set like
     *
     * @param like
     *            the like to set
     */
    public void setLike(String like) {
        this.like = like;
    }

    /**
     * Get likeMode
     *
     * @return likeMode
     */
    public int getLikeMode() {
        return likeMode;
    }

    /**
     * Set likeMode
     *
     * @param likeMode
     *            the likeMode to set
     */
    public void setLikeMode(int likeMode) {
        this.likeMode = likeMode;
    }

    /**
     * Get in
     *
     * @return in
     */
    public String[] getIn() {
        return in;
    }

    /**
     * Set in
     *
     * @param in
     *            the in to set
     */
    public void setIn(String[] in) {
        this.in = in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SearchTextVO [eq=" + eq + ", like=" + like + ", likeMode=" + likeMode + ", in=" + Arrays.toString(in)
                + "]";
    }

}
