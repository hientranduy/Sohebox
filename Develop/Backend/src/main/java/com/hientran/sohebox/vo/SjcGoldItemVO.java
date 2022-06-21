package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class SjcGoldItemVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String buy;

    private String sell;

    private String type;

    /**
     * Constructor
     *
     */
    public SjcGoldItemVO() {
        super();
    }

    /**
     * Get buy
     *
     * @return buy
     */
    public String getBuy() {
        return buy;
    }

    /**
     * Set buy
     *
     * @param buy
     *            the buy to set
     */
    public void setBuy(String buy) {
        this.buy = buy;
    }

    /**
     * Get sell
     *
     * @return sell
     */
    public String getSell() {
        return sell;
    }

    /**
     * Set sell
     *
     * @param sell
     *            the sell to set
     */
    public void setSell(String sell) {
        this.sell = sell;
    }

    /**
     * Get type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     *
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SjcGoldItemVO [buy=" + buy + ", sell=" + sell + ", type=" + type + "]";
    }

}