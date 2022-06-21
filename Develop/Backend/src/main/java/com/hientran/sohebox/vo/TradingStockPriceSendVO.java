package com.hientran.sohebox.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class TradingStockPriceSendVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private List<TradingSymbolItemVO> america;

    private List<TradingSymbolItemVO> europe;

    private List<TradingSymbolItemVO> asia;

    private List<TradingSymbolItemVO> africa;

    /**
     * Constructor
     *
     */
    public TradingStockPriceSendVO() {
        super();
    }

    /**
     * Get america
     *
     * @return america
     */
    public List<TradingSymbolItemVO> getAmerica() {
        return america;
    }

    /**
     * Set america
     *
     * @param america
     *            the america to set
     */
    public void setAmerica(List<TradingSymbolItemVO> america) {
        this.america = america;
    }

    /**
     * Get europe
     *
     * @return europe
     */
    public List<TradingSymbolItemVO> getEurope() {
        return europe;
    }

    /**
     * Set europe
     *
     * @param europe
     *            the europe to set
     */
    public void setEurope(List<TradingSymbolItemVO> europe) {
        this.europe = europe;
    }

    /**
     * Get asia
     *
     * @return asia
     */
    public List<TradingSymbolItemVO> getAsia() {
        return asia;
    }

    /**
     * Set asia
     *
     * @param asia
     *            the asia to set
     */
    public void setAsia(List<TradingSymbolItemVO> asia) {
        this.asia = asia;
    }

    /**
     * Get africa
     *
     * @return africa
     */
    public List<TradingSymbolItemVO> getAfrica() {
        return africa;
    }

    /**
     * Set africa
     *
     * @param africa
     *            the africa to set
     */
    public void setAfrica(List<TradingSymbolItemVO> africa) {
        this.africa = africa;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TradingStockPriceSendVO [america=" + america + ", europe=" + europe + ", asia=" + asia + ", africa="
                + africa + "]";
    }

}