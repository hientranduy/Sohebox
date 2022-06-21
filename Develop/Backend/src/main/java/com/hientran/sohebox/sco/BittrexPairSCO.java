package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class BittrexPairSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO marketCurrency;

    private SearchTextVO baseCurrency;

    private SearchTextVO marketCurrencyLong;

    private SearchTextVO baseCurrencyLong;

    private SearchNumberVO minTradeSize;

    private SearchTextVO marketName;

    private Boolean active;

    private Boolean restricted;

    private SearchDateVO created;

    private SearchTextVO notice;

    private Boolean sponsored;

    private SearchTextVO logoUrl;

    private Boolean extractDataFlag;

    /**
     * Set default constructor
     *
     */
    public BittrexPairSCO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public SearchNumberVO getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    /**
     * Get marketCurrency
     *
     * @return marketCurrency
     */
    public SearchTextVO getMarketCurrency() {
        return marketCurrency;
    }

    /**
     * Set marketCurrency
     *
     * @param marketCurrency
     *            the marketCurrency to set
     */
    public void setMarketCurrency(SearchTextVO marketCurrency) {
        this.marketCurrency = marketCurrency;
    }

    /**
     * Get baseCurrency
     *
     * @return baseCurrency
     */
    public SearchTextVO getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * Set baseCurrency
     *
     * @param baseCurrency
     *            the baseCurrency to set
     */
    public void setBaseCurrency(SearchTextVO baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     * Get marketCurrencyLong
     *
     * @return marketCurrencyLong
     */
    public SearchTextVO getMarketCurrencyLong() {
        return marketCurrencyLong;
    }

    /**
     * Set marketCurrencyLong
     *
     * @param marketCurrencyLong
     *            the marketCurrencyLong to set
     */
    public void setMarketCurrencyLong(SearchTextVO marketCurrencyLong) {
        this.marketCurrencyLong = marketCurrencyLong;
    }

    /**
     * Get baseCurrencyLong
     *
     * @return baseCurrencyLong
     */
    public SearchTextVO getBaseCurrencyLong() {
        return baseCurrencyLong;
    }

    /**
     * Set baseCurrencyLong
     *
     * @param baseCurrencyLong
     *            the baseCurrencyLong to set
     */
    public void setBaseCurrencyLong(SearchTextVO baseCurrencyLong) {
        this.baseCurrencyLong = baseCurrencyLong;
    }

    /**
     * Get minTradeSize
     *
     * @return minTradeSize
     */
    public SearchNumberVO getMinTradeSize() {
        return minTradeSize;
    }

    /**
     * Set minTradeSize
     *
     * @param minTradeSize
     *            the minTradeSize to set
     */
    public void setMinTradeSize(SearchNumberVO minTradeSize) {
        this.minTradeSize = minTradeSize;
    }

    /**
     * Get marketName
     *
     * @return marketName
     */
    public SearchTextVO getMarketName() {
        return marketName;
    }

    /**
     * Set marketName
     *
     * @param marketName
     *            the marketName to set
     */
    public void setMarketName(SearchTextVO marketName) {
        this.marketName = marketName;
    }

    /**
     * Get active
     *
     * @return active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Set active
     *
     * @param active
     *            the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Get restricted
     *
     * @return restricted
     */
    public Boolean getRestricted() {
        return restricted;
    }

    /**
     * Set restricted
     *
     * @param restricted
     *            the restricted to set
     */
    public void setRestricted(Boolean restricted) {
        this.restricted = restricted;
    }

    /**
     * Get created
     *
     * @return created
     */
    public SearchDateVO getCreated() {
        return created;
    }

    /**
     * Set created
     *
     * @param created
     *            the created to set
     */
    public void setCreated(SearchDateVO created) {
        this.created = created;
    }

    /**
     * Get notice
     *
     * @return notice
     */
    public SearchTextVO getNotice() {
        return notice;
    }

    /**
     * Set notice
     *
     * @param notice
     *            the notice to set
     */
    public void setNotice(SearchTextVO notice) {
        this.notice = notice;
    }

    /**
     * Get sponsored
     *
     * @return sponsored
     */
    public Boolean getSponsored() {
        return sponsored;
    }

    /**
     * Set sponsored
     *
     * @param sponsored
     *            the sponsored to set
     */
    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    /**
     * Get logoUrl
     *
     * @return logoUrl
     */
    public SearchTextVO getLogoUrl() {
        return logoUrl;
    }

    /**
     * Set logoUrl
     *
     * @param logoUrl
     *            the logoUrl to set
     */
    public void setLogoUrl(SearchTextVO logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * Get extractDataFlag
     *
     * @return extractDataFlag
     */
    public Boolean getExtractDataFlag() {
        return extractDataFlag;
    }

    /**
     * Set extractDataFlag
     *
     * @param extractDataFlag
     *            the extractDataFlag to set
     */
    public void setExtractDataFlag(Boolean extractDataFlag) {
        this.extractDataFlag = extractDataFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexPairSCO [id=" + id + ", marketCurrency=" + marketCurrency + ", baseCurrency=" + baseCurrency
                + ", marketCurrencyLong=" + marketCurrencyLong + ", baseCurrencyLong=" + baseCurrencyLong
                + ", minTradeSize=" + minTradeSize + ", marketName=" + marketName + ", active=" + active
                + ", restricted=" + restricted + ", created=" + created + ", notice=" + notice + ", sponsored="
                + sponsored + ", logoUrl=" + logoUrl + ", extractDataFlag=" + extractDataFlag + "]";
    }

}
