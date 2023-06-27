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
		@UniqueConstraint(name = "UQ_crypto_portfolio_history", columnNames = { "user_id", "token_id",
				"time_stamp" }) })
public class CryptoPortfolioHistoryTbl extends GenericTbl {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time_stamp")
	private Date timeStamp;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioHistoryTbl_UserTbl_user"))
	private UserTbl user;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioHistoryTbl_CryptoTokenConfigTbl_tokenCode"))
	private CryptoTokenConfigTbl token;

	@Column(name = "total_available")
	private Double totalAvailable;

	@Column(name = "total_delegated")
	private Double totalDelegated;

	@Column(name = "total_reward")
	private Double totalReward;

	@Column(name = "total_unbonding")
	private Double totalUnbonding;

	@Column(name = "total_increase")
	private Double totalIncrease;

	@Column(name = "last_sync_date")
	private Date lastSyncDate;
}
