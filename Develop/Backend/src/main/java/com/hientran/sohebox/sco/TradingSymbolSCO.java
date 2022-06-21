package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class TradingSymbolSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchTextVO symbol;

    private SearchTextVO name;

    private SearchNumberVO country;

    private SearchNumberVO symbolType;

    private SearchNumberVO zone;

    private SearchTextVO description;

    /**
     * Set default constructor
     *
     */
    public TradingSymbolSCO() {
        super();
    }

    /**
     * Get symbol
     *
     * @return symbol
     */
    public SearchTextVO getSymbol() {
        return symbol;
    }

    /**
     * Set symbol
     *
     * @param symbol
     *            the symbol to set
     */
    public void setSymbol(SearchTextVO symbol) {
        this.symbol = symbol;
    }

    /**
     * Get name
     *
     * @return name
     */
    public SearchTextVO getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(SearchTextVO name) {
        this.name = name;
    }

    /**
     * Get country
     *
     * @return country
     */
    public SearchNumberVO getCountry() {
        return country;
    }

    /**
     * Set country
     *
     * @param country
     *            the country to set
     */
    public void setCountry(SearchNumberVO country) {
        this.country = country;
    }

    /**
     * Get symbolType
     *
     * @return symbolType
     */
    public SearchNumberVO getSymbolType() {
        return symbolType;
    }

    /**
     * Set symbolType
     *
     * @param symbolType
     *            the symbolType to set
     */
    public void setSymbolType(SearchNumberVO symbolType) {
        this.symbolType = symbolType;
    }

    /**
     * Get zone
     *
     * @return zone
     */
    public SearchNumberVO getZone() {
        return zone;
    }

    /**
     * Set zone
     *
     * @param zone
     *            the zone to set
     */
    public void setZone(SearchNumberVO zone) {
        this.zone = zone;
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
        return "TradingSymbolSCO [symbol=" + symbol + ", name=" + name + ", country=" + country + ", symbolType="
                + symbolType + ", zone=" + zone + ", description=" + description + "]";
    }

}
