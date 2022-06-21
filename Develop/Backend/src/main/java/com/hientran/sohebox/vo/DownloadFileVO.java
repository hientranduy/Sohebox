package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class DownloadFileVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String url;

    private String destinationFolderPath;

    private String destinationFileName;

    /**
     * Constructor
     *
     */
    public DownloadFileVO() {
        super();
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
     * Get destinationFolderPath
     *
     * @return destinationFolderPath
     */
    public String getDestinationFolderPath() {
        return destinationFolderPath;
    }

    /**
     * Set destinationFolderPath
     *
     * @param destinationFolderPath
     *            the destinationFolderPath to set
     */
    public void setDestinationFolderPath(String destinationFolderPath) {
        this.destinationFolderPath = destinationFolderPath;
    }

    /**
     * Get destinationFileName
     *
     * @return destinationFileName
     */
    public String getDestinationFileName() {
        return destinationFileName;
    }

    /**
     * Set destinationFileName
     *
     * @param destinationFileName
     *            the destinationFileName to set
     */
    public void setDestinationFileName(String destinationFileName) {
        this.destinationFileName = destinationFileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DownloadFileVO [url=" + url + ", destinationFolderPath=" + destinationFolderPath
                + ", destinationFileName=" + destinationFileName + "]";
    }

}