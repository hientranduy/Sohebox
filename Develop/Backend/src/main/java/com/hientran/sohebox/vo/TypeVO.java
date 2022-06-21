
package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class TypeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String typeClass;

    private String typeCode;

    private String typeName;

    private String description;

    private String iconUrl;

    private String url;

    private boolean deleteFlag;

    /**
     * Constructor
     *
     */
    public TypeVO() {
        super();
    }

    /**
     * Constructor
     *
     * @param typeClass
     * @param typeCode
     */
    public TypeVO(String typeClass, String typeCode) {
        super();
        this.typeClass = typeClass;
        this.typeCode = typeCode;
    }

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get typeClass
     *
     * @return typeClass
     */
    public String getTypeClass() {
        return typeClass;
    }

    /**
     * Set typeClass
     *
     * @param typeClass
     *            the typeClass to set
     */
    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    /**
     * Get typeCode
     *
     * @return typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * Set typeCode
     *
     * @param typeCode
     *            the typeCode to set
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * Get typeName
     *
     * @return typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Set typeName
     *
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get iconUrl
     *
     * @return iconUrl
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Set iconUrl
     *
     * @param iconUrl
     *            the iconUrl to set
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
     * Get deleteFlag
     *
     * @return deleteFlag
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Set deleteFlag
     *
     * @param deleteFlag
     *            the deleteFlag to set
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TypeVO [id=" + id + ", typeClass=" + typeClass + ", typeCode=" + typeCode + ", typeName=" + typeName
                + ", description=" + description + ", iconUrl=" + iconUrl + ", url=" + url + ", deleteFlag="
                + deleteFlag + "]";
    }

}