package com.hientran.sohebox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hientran
 */
@Entity
@Table(name = "crypto_token_config_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_crypto_token_config", columnNames = { "tokenCode" }) })
@Getter
@Setter
public class CryptoTokenConfigTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "tokenCode", nullable = false)
    private String tokenCode;

    @Column(name = "tokenName")
    private String tokenName;

    @Column(name = "iconUrl")
    private String iconUrl;

    @Column(name = "nodeUrl")
    private String nodeUrl;

    @Column(name = "rpcUrl")
    private String rpcUrl;

    @Column(name = "denom")
    private String denom;

    @Column(name = "decimalExponent")
    private Long decimalExponent;

    @Column(name = "addressPrefix")
    private String addressPrefix;

    @Column(name = "mintscanPrefix")
    private String mintscanPrefix;

    @Column(name = "deligateUrl")
    private String deligateUrl;
}