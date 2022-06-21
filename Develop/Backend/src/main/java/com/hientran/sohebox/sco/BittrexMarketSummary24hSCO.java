package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class BittrexMarketSummary24hSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO marketName;

    private SearchDateVO timeStamp;

    /**
     * Set default constructor
     *
     */
    public BittrexMarketSummary24hSCO() {
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexMarketSummary24hSCO [id=" + id + ", marketName=" + marketName + ", timeStamp=" + timeStamp + "]";
    }

}
