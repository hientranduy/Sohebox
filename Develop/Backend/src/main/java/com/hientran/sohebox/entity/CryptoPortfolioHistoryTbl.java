package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hientran
 */
@Entity
@Table(name = "crypto_portfolio_history_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_crypto_portfolio_history", columnNames = { "user_id", "token_id", "timeStamp" }) })
@Getter
@Setter
public class CryptoPortfolioHistoryTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

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
}
