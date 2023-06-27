package com.hientran.sohebox.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "trading_symbol_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_trading_symbol", columnNames = { "symbol" }) })
@EntityListeners(AuditingEntityListener.class)
public class TradingSymbolTbl extends BaseTbl {
	@Column(name = "symbol")
	private String symbol;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "symbol_type_id", foreignKey = @ForeignKey(name = "FK_TradingSymbolTbl_TypeTbl_symbolType"))
	private TypeTbl symbolType;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_TradingSymbolTbl_TypeTbl_zone"))
	private TypeTbl zone;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_TradingSymbolTbl_CountryTbl_country"))
	private CountryTbl country;

	@Column(name = "description")
	private String description;
}
