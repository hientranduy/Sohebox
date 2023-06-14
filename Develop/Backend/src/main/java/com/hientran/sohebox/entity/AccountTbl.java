package com.hientran.sohebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "account_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_account", columnNames = { "user_id", "type_id", "accountName" }) })
public class AccountTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_AccountTbl_UserTbl_user"))
    private UserTbl user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_AccountTbl_TypeTbl_accountType"))
    private TypeTbl type;

    @Column(name = "accountName", nullable = false)
    private String accountName;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_AccountTbl_MdpTbl_mdp"))
    private MdpTbl mdp;

    @Column(name = "note")
    private String note;

    public UserTbl getUser() {
        return user;
    }

    public void setUser(UserTbl user) {
        this.user = user;
    }

    public TypeTbl getType() {
        return type;
    }

    public void setType(TypeTbl type) {
        this.type = type;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public MdpTbl getMdp() {
        return mdp;
    }

    public void setMdp(MdpTbl mdp) {
        this.mdp = mdp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "AccountTbl [user=" + user + ", type=" + type + ", accountName=" + accountName + ", mdp=" + mdp
                + ", note=" + note + "]";
    }

    public AccountTbl(UserTbl user, TypeTbl type, String accountName, MdpTbl mdp, String note) {
        super();
        this.user = user;
        this.type = type;
        this.accountName = accountName;
        this.mdp = mdp;
        this.note = note;
    }

    public AccountTbl() {
        super();
        // TODO Auto-generated constructor stub
    }

}
