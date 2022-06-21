package com.hientran.sohebox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "trading_symbol_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_trading_symbol", columnNames = { "symbol" }) })
@EntityListeners(AuditingEntityListener.class)
public class TradingSymbolTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TradingSymbolTbl_TypeTbl_symbolType"))
    private TypeTbl symbolType;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TradingSymbolTbl_TypeTbl_zone"))
    private TypeTbl zone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TradingSymbolTbl_CountryTbl_country"))
    private CountryTbl country;

    @Column(name = "description")
    private String description;

    /**
     * Constructor
     *
     */
    public TradingSymbolTbl() {
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
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get symbolType
     *
     * @return symbolType
     */
    public TypeTbl getSymbolType() {
        return symbolType;
    }

    /**
     * Set symbolType
     *
     * @param symbolType
     *            the symbolType to set
     */
    public void setSymbolType(TypeTbl symbolType) {
        this.symbolType = symbolType;
    }

    /**
     * Get zone
     *
     * @return zone
     */
    public TypeTbl getZone() {
        return zone;
    }

    /**
     * Set zone
     *
     * @param zone
     *            the zone to set
     */
    public void setZone(TypeTbl zone) {
        this.zone = zone;
    }

    /**
     * Get country
     *
     * @return country
     */
    public CountryTbl getCountry() {
        return country;
    }

    /**
     * Set country
     *
     * @param country
     *            the country to set
     */
    public void setCountry(CountryTbl country) {
        this.country = country;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
