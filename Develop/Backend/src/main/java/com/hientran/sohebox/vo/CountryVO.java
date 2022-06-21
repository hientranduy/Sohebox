package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class CountryVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String name;

    private String url;

    private String note;

    /**
     * Constructor
     *
     */
    public CountryVO() {
        super();
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     *
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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
        return "CountryVO [name=" + name + ", url=" + url + ", note=" + note + "]";
    }
}