package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class BittrexCryptoSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private Boolean active;

    private Boolean restricted;

    private SearchTextVO notice;

    private SearchTextVO currency;

    private SearchTextVO currencyLong;

    private SearchNumberVO minConfirmation;

    private SearchNumberVO txFee;

    private SearchTextVO coinType;

    private SearchTextVO baseAddress;

    /**
     * Set default constructor
     *
     */
    public BittrexCryptoSCO() {
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
     * Get currency
     *
     * @return currency
     */
    public SearchTextVO getCurrency() {
        return currency;
    }

    /**
     * Set currency
     *
     * @param currency
     *            the currency to set
     */
    public void setCurrency(SearchTextVO currency) {
        this.currency = currency;
    }

    /**
     * Get currencyLong
     *
     * @return currencyLong
     */
    public SearchTextVO getCurrencyLong() {
        return currencyLong;
    }

    /**
     * Set currencyLong
     *
     * @param currencyLong
     *            the currencyLong to set
     */
    public void setCurrencyLong(SearchTextVO currencyLong) {
        this.currencyLong = currencyLong;
    }

    /**
     * Get minConfirmation
     *
     * @return minConfirmation
     */
    public SearchNumberVO getMinConfirmation() {
        return minConfirmation;
    }

    /**
     * Set minConfirmation
     *
     * @param minConfirmation
     *            the minConfirmation to set
     */
    public void setMinConfirmation(SearchNumberVO minConfirmation) {
        this.minConfirmation = minConfirmation;
    }

    /**
     * Get txFee
     *
     * @return txFee
     */
    public SearchNumberVO getTxFee() {
        return txFee;
    }

    /**
     * Set txFee
     *
     * @param txFee
     *            the txFee to set
     */
    public void setTxFee(SearchNumberVO txFee) {
        this.txFee = txFee;
    }

    /**
     * Get coinType
     *
     * @return coinType
     */
    public SearchTextVO getCoinType() {
        return coinType;
    }

    /**
     * Set coinType
     *
     * @param coinType
     *            the coinType to set
     */
    public void setCoinType(SearchTextVO coinType) {
        this.coinType = coinType;
    }

    /**
     * Get baseAddress
     *
     * @return baseAddress
     */
    public SearchTextVO getBaseAddress() {
        return baseAddress;
    }

    /**
     * Set baseAddress
     *
     * @param baseAddress
     *            the baseAddress to set
     */
    public void setBaseAddress(SearchTextVO baseAddress) {
        this.baseAddress = baseAddress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexCryptoSCO [id=" + id + ", active=" + active + ", restricted=" + restricted + ", notice=" + notice
                + ", currency=" + currency + ", currencyLong=" + currencyLong + ", minConfirmation=" + minConfirmation
                + ", txFee=" + txFee + ", coinType=" + coinType + ", baseAddress=" + baseAddress + "]";
    }

}
