package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeReponseVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String kind;

    private String etag;

    private String nextPageToken;

    private String regionCode;

    private Object pageInfo;

    private Object items;

    /**
     * Constructor
     *
     */
    public YoutubeReponseVO() {
        super();
    }

    /**
     * Get kind
     *
     * @return kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * Set kind
     *
     * @param kind
     *            the kind to set
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * Get etag
     *
     * @return etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * Set etag
     *
     * @param etag
     *            the etag to set
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * Get nextPageToken
     *
     * @return nextPageToken
     */
    public String getNextPageToken() {
        return nextPageToken;
    }

    /**
     * Set nextPageToken
     *
     * @param nextPageToken
     *            the nextPageToken to set
     */
    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    /**
     * Get regionCode
     *
     * @return regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * Set regionCode
     *
     * @param regionCode
     *            the regionCode to set
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * Get pageInfo
     *
     * @return pageInfo
     */
    public Object getPageInfo() {
        return pageInfo;
    }

    /**
     * Set pageInfo
     *
     * @param pageInfo
     *            the pageInfo to set
     */
    public void setPageInfo(Object pageInfo) {
        this.pageInfo = pageInfo;
    }

    /**
     * Get items
     *
     * @return items
     */
    public Object getItems() {
        return items;
    }

    /**
     * Set items
     *
     * @param items
     *            the items to set
     */
    public void setItems(Object items) {
        this.items = items;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeVideoReponseVO [kind=" + kind + ", etag=" + etag + ", nextPageToken=" + nextPageToken
                + ", regionCode=" + regionCode + ", pageInfo=" + pageInfo + ", items=" + items + "]";
    }

}