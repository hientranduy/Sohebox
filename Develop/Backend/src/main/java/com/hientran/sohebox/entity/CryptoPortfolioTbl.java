package com.hientran.sohebox.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "crypto_portfolio_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_crypto_portfolio", columnNames = { "user_id", "token_id", "wallet" }) })
public class CryptoPortfolioTbl extends BaseTbl {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioTbl_UserTbl_user"))
	private UserTbl user;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioTbl_CryptoTokenConfigTbl_tokenCode"))
	private CryptoTokenConfigTbl token;

	@Column(name = "wallet", nullable = false)
	private String wallet;

	@Column(name = "starname")
	private String starname;

	@Column(name = "amtAvailable")
	private Double amtAvailable;

	@Column(name = "amtTotalDelegated")
	private Double amtTotalDelegated;

	@Column(name = "amtTotalReward")
	private Double amtTotalReward;

	@Column(name = "amtTotalUnbonding")
	private Double amtTotalUnbonding;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioTbl_CryptoValidatorTbl_validator"))
	private CryptoValidatorTbl validator;

	@Column(name = "syncDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date syncDate;
}
