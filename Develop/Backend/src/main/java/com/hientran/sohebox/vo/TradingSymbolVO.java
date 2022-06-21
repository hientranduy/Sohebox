package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class TradingSymbolVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String symbol;

    private String name;

    private CountryVO country;

    private TypeVO symbolType;

    private TypeVO zone;

    private String description;

    /**
     * Constructor
     *
     */
    public TradingSymbolVO() {
        super();
    }

    /**
     * Get symbol
     *
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Set symbol
     *
     * @param symbol
     *            the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
     * Get country
     *
     * @return country
     */
    public CountryVO getCountry() {
        return country;
    }

    /**
     * Set country
     *
     * @param country
     *            the country to set
     */
    public void setCountry(CountryVO country) {
        this.country = country;
    }

    /**
     * Get symbolType
     *
     * @return symbolType
     */
    public TypeVO getSymbolType() {
        return symbolType;
    }

    /**
     * Set symbolType
     *
     * @param symbolType
     *            the symbolType to set
     */
    public void setSymbolType(TypeVO symbolType) {
        this.symbolType = symbolType;
    }

    /**
     * Get zone
     *
     * @return zone
     */
    public TypeVO getZone() {
        return zone;
    }

    /**
     * Set zone
     *
     * @param zone
     *            the zone to set
     */
    public void setZone(TypeVO zone) {
        this.zone = zone;
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
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TradingSymbolVO [symbol=" + symbol + ", name=" + name + ", country=" + country + ", symbolType="
                + symbolType + ", zone=" + zone + ", description=" + description + "]";
    }

}