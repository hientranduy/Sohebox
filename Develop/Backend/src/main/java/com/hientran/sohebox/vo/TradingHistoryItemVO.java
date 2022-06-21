package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class TradingHistoryItemVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("Date")
    private String date;

    @JsonProperty("Open")
    private Double open;

    @JsonProperty("High")
    private Double high;

    @JsonProperty("Low")
    private Double low;

    @JsonProperty("Close")
    private Double close;

    /**
     * Constructor
     *
     */
    public TradingHistoryItemVO() {
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
     * Get date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set date
     *
     * @param date
     *            the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get open
     *
     * @return open
     */
    public Double getOpen() {
        return open;
    }

    /**
     * Set open
     *
     * @param open
     *            the open to set
     */
    public void setOpen(Double open) {
        this.open = open;
    }

    /**
     * Get high
     *
     * @return high
     */
    public Double getHigh() {
        return high;
    }

    /**
     * Set high
     *
     * @param high
     *            the high to set
     */
    public void setHigh(Double high) {
        this.high = high;
    }

    /**
     * Get low
     *
     * @return low
     */
    public Double getLow() {
        return low;
    }

    /**
     * Set low
     *
     * @param low
     *            the low to set
     */
    public void setLow(Double low) {
        this.low = low;
    }

    /**
     * Get close
     *
     * @return close
     */
    public Double getClose() {
        return close;
    }

    /**
     * Set close
     *
     * @param close
     *            the close to set
     */
    public void setClose(Double close) {
        this.close = close;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TradingHistoryItemVO [symbol=" + symbol + ", date=" + date + ", open=" + open + ", high=" + high
                + ", low=" + low + ", close=" + close + "]";
    }

}
