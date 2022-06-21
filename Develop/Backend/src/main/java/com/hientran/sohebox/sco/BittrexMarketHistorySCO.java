package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class BittrexMarketHistorySCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO marketName;

    private SearchDateVO timeStamp;

    private SearchTextVO orderType;

    /**
     * Set default constructor
     *
     */
    public BittrexMarketHistorySCO() {
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
     * Get timeStamp
     *
     * @return timeStamp
     */
    public SearchDateVO getTimeStamp() {
        return timeStamp;
    }

    /**
     * Set timeStamp
     *
     * @param timeStamp
     *            the timeStamp to set
     */
    public void setTimeStamp(SearchDateVO timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Get orderType
     *
     * @return orderType
     */
    public SearchTextVO getOrderType() {
        return orderType;
    }

    /**
     * Set orderType
     *
     * @param orderType
     *            the orderType to set
     */
    public void setOrderType(SearchTextVO orderType) {
        this.orderType = orderType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexMarketHistorySCO [id=" + id + ", marketName=" + marketName + ", timeStamp=" + timeStamp
                + ", orderType=" + orderType + "]";
    }

}
