package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class EnglishTypeSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchTextVO typeClass;

    private SearchTextVO typeCode;

    private SearchTextVO typeName;

    private SearchTextVO description;

    /**
     * Set default constructor
     *
     */
    public EnglishTypeSCO() {
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

    public SearchTextVO getTypeName() {
        return typeName;
    }

    public void setTypeName(SearchTextVO typeName) {
        this.typeName = typeName;
    }

    public SearchTextVO getDescription() {
        return description;
    }

    public void setDescription(SearchTextVO description) {
        this.description = description;
    }

}
