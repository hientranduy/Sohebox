package com.hientran.sohebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "food_type_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_food_type", columnNames = { "typeClass", "typeCode" }) })
public class FoodTypeTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "typeClass", nullable = false)
    private String typeClass;

    @Column(name = "typeCode", nullable = false)
    private String typeCode;

    @Column(name = "typeName")
    private String typeName;

    @Column(name = "description")
    private String description;

    @Column(name = "iconUrl")
    private String iconUrl;

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

}