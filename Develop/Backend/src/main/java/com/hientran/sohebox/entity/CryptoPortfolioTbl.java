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
@Table(name = "crypto_portfolio_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_crypto_portfolio", columnNames = { "user_id", "token_id", "wallet" }) })
@Getter
@Setter
public class CryptoPortfolioTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

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
