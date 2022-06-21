package com.hientran.sohebox.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class TradingOilPriceSendVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private TradingSymbolItemVO symbolCL1;

    private TradingSymbolItemVO symbolCO1;

    private List<TradingHistoryItemVO> historyCL1;

    private List<TradingHistoryItemVO> historyCO1;

    /**
     * Constructor
     *
     */
    public TradingOilPriceSendVO() {
        super();
    }

    /**
     * Get symbolCL1
     *
     * @return symbolCL1
     */
    public TradingSymbolItemVO getSymbolCL1() {
        return symbolCL1;
    }

    /**
     * Set symbolCL1
     *
     * @param symbolCL1
     *            the symbolCL1 to set
     */
    public void setSymbolCL1(TradingSymbolItemVO symbolCL1) {
        this.symbolCL1 = symbolCL1;
    }

    /**
     * Get symbolCO1
     *
     * @return symbolCO1
     */
    public TradingSymbolItemVO getSymbolCO1() {
        return symbolCO1;
    }

    /**
     * Set symbolCO1
     *
     * @param symbolCO1
     *            the symbolCO1 to set
     */
    public void setSymbolCO1(TradingSymbolItemVO symbolCO1) {
        this.symbolCO1 = symbolCO1;
    }

    /**
     * Get historyCL1
     *
     * @return historyCL1
     */
    public List<TradingHistoryItemVO> getHistoryCL1() {
        return historyCL1;
    }

    /**
     * Set historyCL1
     *
     * @param historyCL1
     *            the historyCL1 to set
     */
    public void setHistoryCL1(List<TradingHistoryItemVO> historyCL1) {
        this.historyCL1 = historyCL1;
    }

    /**
     * Get historyCO1
     *
     * @return historyCO1
     */
    public List<TradingHistoryItemVO> getHistoryCO1() {
        return historyCO1;
    }

    /**
     * Set historyCO1
     *
     * @param historyCO1
     *            the historyCO1 to set
     */
    public void setHistoryCO1(List<TradingHistoryItemVO> historyCO1) {
        this.historyCO1 = historyCO1;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TradingOilPriceSendVO [symbolCL1=" + symbolCL1 + ", symbolCO1=" + symbolCO1 + ", historyCL1="
                + historyCL1 + ", historyCO1=" + historyCO1 + "]";
    }

}