package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class YoutubeResponseVideoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String kind;

    private String etag;

    private String id;

    private YoutubeVideoSnippetVO snippet;

    /**
     * Constructor
     *
     */
    public YoutubeResponseVideoVO() {
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
     * Get id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get snippet
     *
     * @return snippet
     */
    public YoutubeVideoSnippetVO getSnippet() {
        return snippet;
    }

    /**
     * Set snippet
     *
     * @param snippet
     *            the snippet to set
     */
    public void setSnippet(YoutubeVideoSnippetVO snippet) {
        this.snippet = snippet;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "YoutubeResponseVideoVO [kind=" + kind + ", etag=" + etag + ", id=" + id + ", snippet=" + snippet + "]";
    }

}