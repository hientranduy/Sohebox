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
@Table(name = "crypto_portfolio_history_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_crypto_portfolio_history", columnNames = { "user_id", "token_id", "timeStamp" }) })
public class CryptoPortfolioHistoryTbl extends GenericTbl {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timeStamp")
	private Date timeStamp;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioHistoryTbl_UserTbl_user"))
	private UserTbl user;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioHistoryTbl_CryptoTokenConfigTbl_tokenCode"))
	private CryptoTokenConfigTbl token;

	@Column(name = "totalAvailable")
	private Double totalAvailable;

	@Column(name = "totalDelegated")
	private Double totalDelegated;

	@Column(name = "totalReward")
	private Double totalReward;

	@Column(name = "totalUnbonding")
	private Double totalUnbonding;

	@Column(name = "totalIncrease")
	private Double totalIncrease;

	@Column(name = "lastSyncDate")
	private Date lastSyncDate;
}
