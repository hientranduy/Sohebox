package com.hientran.sohebox.cache;

import java.io.Serializable;

public class TypeCacheKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String typeClass;

    private String typeCode;

    /**
     * Constructor
     *
     */
    public TypeCacheKey() {
        super();
    }

    /**
     * Constructor
     *
     * @param typeClass
     * @param typeCode
     */
    public TypeCacheKey(String typeClass, String typeCode) {
        super();
        this.typeClass = typeClass;
        this.typeCode = typeCode;
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TypeCacheKey [typeClass=" + typeClass + ", typeCode=" + typeCode + "]";
    }

}
