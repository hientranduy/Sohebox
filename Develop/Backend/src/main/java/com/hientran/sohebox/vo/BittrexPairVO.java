package com.hientran.sohebox.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class BittrexPairVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("IsActive")
    private Boolean active;

    @JsonProperty("IsRestricted")
    private Boolean restricted;

    @JsonProperty("Notice")
    private String notice;

    @JsonProperty("MarketCurrency")
    private String marketCurrency;

    @JsonProperty("BaseCurrency")
    private String baseCurrency;

    @JsonProperty("MarketCurrencyLong")
    private String marketCurrencyLong;

    @JsonProperty("BaseCurrencyLong")
    private String baseCurrencyLong;

    @JsonProperty("MinTradeSize")
    private BigDecimal minTradeSize;

    @JsonProperty("MarketName")
    private String marketName;

    @JsonProperty("Created")
    private Date created;

    @JsonProperty("IsSponsored")
    private Boolean sponsored;

    @JsonProperty("LogoUrl")
    private String logoUrl;

    /**
     * Constructor
     *
     */
    public BittrexPairVO() {
        super();
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
    public String getNotice() {
        return notice;
    }

    /**
     * Set notice
     *
     * @param notice
     *            the notice to set
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }

    /**
     * Get marketCurrency
     *
     * @return marketCurrency
     */
    public String getMarketCurrency() {
        return marketCurrency;
    }

    /**
     * Set marketCurrency
     *
     * @param marketCurrency
     *            the marketCurrency to set
     */
    public void setMarketCurrency(String marketCurrency) {
        this.marketCurrency = marketCurrency;
    }

    /**
     * Get baseCurrency
     *
     * @return baseCurrency
     */
    public String getBaseCurrency() {
        return baseCurrency;
    }

    /**
     * Set baseCurrency
     *
     * @param baseCurrency
     *            the baseCurrency to set
     */
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     * Get marketCurrencyLong
     *
     * @return marketCurrencyLong
     */
    public String getMarketCurrencyLong() {
        return marketCurrencyLong;
    }

    /**
     * Set marketCurrencyLong
     *
     * @param marketCurrencyLong
     *            the marketCurrencyLong to set
     */
    public void setMarketCurrencyLong(String marketCurrencyLong) {
        this.marketCurrencyLong = marketCurrencyLong;
    }

    /**
     * Get baseCurrencyLong
     *
     * @return baseCurrencyLong
     */
    public String getBaseCurrencyLong() {
        return baseCurrencyLong;
    }

    /**
     * Set baseCurrencyLong
     *
     * @param baseCurrencyLong
     *            the baseCurrencyLong to set
     */
    public void setBaseCurrencyLong(String baseCurrencyLong) {
        this.baseCurrencyLong = baseCurrencyLong;
    }

    /**
     * Get minTradeSize
     *
     * @return minTradeSize
     */
    public BigDecimal getMinTradeSize() {
        return minTradeSize;
    }

    /**
     * Set minTradeSize
     *
     * @param minTradeSize
     *            the minTradeSize to set
     */
    public void setMinTradeSize(BigDecimal minTradeSize) {
        this.minTradeSize = minTradeSize;
    }

    /**
     * Get marketName
     *
     * @return marketName
     */
    public String getMarketName() {
        return marketName;
    }

    /**
     * Set marketName
     *
     * @param marketName
     *            the marketName to set
     */
    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    /**
     * Get created
     *
     * @return created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Set created
     *
     * @param created
     *            the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
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
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Set logoUrl
     *
     * @param logoUrl
     *            the logoUrl to set
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexPairVO [active=" + active + ", restricted=" + restricted + ", notice=" + notice
                + ", marketCurrency=" + marketCurrency + ", baseCurrency=" + baseCurrency + ", marketCurrencyLong="
                + marketCurrencyLong + ", baseCurrencyLong=" + baseCurrencyLong + ", minTradeSize=" + minTradeSize
                + ", marketName=" + marketName + ", created=" + created + ", sponsored=" + sponsored + ", logoUrl="
                + logoUrl + "]";
    }
}