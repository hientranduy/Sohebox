package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class AccountVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private UserVO user;

    private TypeVO accountType;

    private String accountName;

    private String mdp;

    private String note;

    ///////////////
    // SUB FIELD //
    ///////////////
    private String privateKey;

    /**
     * Constructor
     *
     */
    public AccountVO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get user
     *
     * @return user
     */
    public UserVO getUser() {
        return user;
    }

    /**
     * Set user
     *
     * @param user
     *            the user to set
     */
    public void setUser(UserVO user) {
        this.user = user;
    }

    /**
     * Get accountType
     *
     * @return accountType
     */
    public TypeVO getAccountType() {
        return accountType;
    }

    /**
     * Set accountType
     *
     * @param accountType
     *            the accountType to set
     */
    public void setAccountType(TypeVO accountType) {
        this.accountType = accountType;
    }

    /**
     * Get accountName
     *
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Set accountName
     *
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Get mdp
     *
     * @return mdp
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Set mdp
     *
     * @param mdp
     *            the mdp to set
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Get note
     *
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     *
     * @param note
     *            the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Get privateKey
     *
     * @return privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Set privateKey
     *
     * @param privateKey
     *            the privateKey to set
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AccountVO [id=" + id + ", user=" + user + ", accountType=" + accountType + ", accountName="
                + accountName + ", mdp=" + mdp + ", note=" + note + ", privateKey=" + privateKey + "]";
    }

}