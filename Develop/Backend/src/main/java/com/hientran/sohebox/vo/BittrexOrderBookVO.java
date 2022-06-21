package com.hientran.sohebox.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class BittrexOrderBookVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("buy")
    private List<BittrexOrderBookItemVO> buy;

    @JsonProperty("sell")
    private List<BittrexOrderBookItemVO> sell;

    /**
     * Constructor
     *
     */
    public BittrexOrderBookVO() {
        super();
    }

    /**
     * Get buy
     *
     * @return buy
     */
    public List<BittrexOrderBookItemVO> getBuy() {
        return buy;
    }

    /**
     * Set buy
     *
     * @param buy
     *            the buy to set
     */
    public void setBuy(List<BittrexOrderBookItemVO> buy) {
        this.buy = buy;
    }

    /**
     * Get sell
     *
     * @return sell
     */
    public List<BittrexOrderBookItemVO> getSell() {
        return sell;
    }

    /**
     * Set sell
     *
     * @param sell
     *            the sell to set
     */
    public void setSell(List<BittrexOrderBookItemVO> sell) {
        this.sell = sell;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BittrexOrderBookVO [buy=" + buy + ", sell=" + sell + "]";
    }
}