package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class MediaTypeSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchTextVO typeClass;

    private SearchTextVO typeCode;

    private SearchTextVO typeName;

    private SearchTextVO description;

    /**
     * Set default constructor
     *
     */
    public MediaTypeSCO() {
        super();
    }

    /**
     * Get typeClass
     *
     * @return typeClass
     */
    public SearchTextVO getTypeClass() {
        return typeClass;
    }

    /**
     * Set typeClass
     *
     * @param typeClass
     *            the typeClass to set
     */
    public void setTypeClass(SearchTextVO typeClass) {
        this.typeClass = typeClass;
    }

    /**
     * Get typeCode
     *
     * @return typeCode
     */
    public SearchTextVO getTypeCode() {
        return typeCode;
    }

    /**
     * Set typeCode
     *
     * @param typeCode
     *            the typeCode to set
     */
    public void setTypeCode(SearchTextVO typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * Get typeName
     *
     * @return typeName
     */
    public SearchTextVO getTypeName() {
        return typeName;
    }

    /**
     * Set typeName
     *
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(SearchTextVO typeName) {
        this.typeName = typeName;
    }

    /**
     * Get description
     *
     * @return description
     */
    public SearchTextVO getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     *            the description to set
     */
    public void setDescription(SearchTextVO description) {
        this.description = description;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MediaTypeSCO [typeClass=" + typeClass + ", typeCode=" + typeCode + ", typeName=" + typeName
                + ", description=" + description + "]";
    }

}
