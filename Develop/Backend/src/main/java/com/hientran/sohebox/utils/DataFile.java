package com.hientran.sohebox.utils;

import java.io.Serializable;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

@XmlType
public class DataFile implements Serializable {
    private static final long serialVersionUID = 7766352768745307143L;

    private static final String EXT_SEPARATOR = ".";

    private String fileName;

    private String extension;

    private transient DataHandler file;

    /**
     * Get extension
     * 
     * @return extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Set extension
     * 
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension) {
        String ext = StringUtils.trimToEmpty(extension);
        if (StringUtils.isNotBlank(ext) && !ext.startsWith(EXT_SEPARATOR)) {
            ext = EXT_SEPARATOR + ext;
        }
        this.extension = ext;
    }

    /**
     * DataFile constructor
     * 
     */
    public DataFile() {
        super();
    }

    /**
     * DataFile constructor
     * 
     * @param file
     *            DataHandler
     */
    public DataFile(DataHandler file) {
        super();
        this.file = file;
    }

    /**
     * DataFile constructor
     * 
     * @param fileName
     *            file name included file extension
     * @param file
     *            DataHandler
     */
    public DataFile(String fileName, DataHandler file) {
        super();
        this.fileName = fileName;
        this.file = file;
    }

    /**
     * Get fileName
     * 
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set fileName
     * 
     * @param fileName
     *            The fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get imageData
     * 
     * @return imageData
     */
    @XmlMimeType("application/octet-stream")
    public DataHandler getFile() {
        return file;
    }

    /**
     * Set imageData
     * 
     * @param imageData
     *            The imageData to set
     */
    public void setFile(DataHandler imageData) {
        this.file = imageData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DataFile [fileName=" + fileName + ", extension=" + extension + "]";
    }

}
