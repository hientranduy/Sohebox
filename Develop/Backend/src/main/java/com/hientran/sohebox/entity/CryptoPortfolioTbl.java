package com.hientran.sohebox.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "crypto_portfolio_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_crypto_portfolio", columnNames = { "user_id", "token_id" }) })
public class CryptoPortfolioTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioTbl_UserTbl_user"))
    private UserTbl user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_CryptoPorfolioTbl_CryptoTokenConfigTbl_tokenCode"))
    private CryptoTokenConfigTbl token;

    /**
     * Get user
     *
     * @return user
     */
    public UserTbl getUser() {
        return user;
    }

    /**
     * Set user
     *
     * @param user
     *            the user to set
     */
    public void setUser(UserTbl user) {
        this.user = user;
    }

    /**
     * Get token
     *
     * @return token
     */
    public CryptoTokenConfigTbl getToken() {
        return token;
    }

    /**
     * Set token
     *
     * @param token
     *            the token to set
     */
    public void setToken(CryptoTokenConfigTbl token) {
        this.token = token;
    }

    /**
     * Explanation of processing
     *
     */
    public CryptoPortfolioTbl() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Explanation of processing
     *
     * @param user
     * @param token
     */
    public CryptoPortfolioTbl(UserTbl user, CryptoTokenConfigTbl token) {
        super();
        this.user = user;
        this.token = token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CryptoPortfolioTbl [user=" + user + ", token=" + token + "]";
    }

}
