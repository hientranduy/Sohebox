package com.hientran.sohebox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
}
