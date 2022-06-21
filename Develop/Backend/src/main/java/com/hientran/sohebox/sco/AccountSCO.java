package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class AccountSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchNumberVO user;

    private SearchNumberVO accountType;

    private SearchTextVO accountName;

    private SearchTextVO note;

    // Sub Field //
    private SearchTextVO userName;

    private SearchTextVO accountTypeName;

    /**
     * Set default constructor
     *
     */
    public AccountSCO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public SearchNumberVO getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    /**
     * Get user
     *
     * @return user
     */
    public SearchNumberVO getUser() {
        return user;
    }

    /**
     * Set user
     *
     * @param user
     *            the user to set
     */
    public void setUser(SearchNumberVO user) {
        this.user = user;
    }

    /**
     * Get accountType
     *
     * @return accountType
     */
    public SearchNumberVO getAccountType() {
        return accountType;
    }

    /**
     * Set accountType
     *
     * @param accountType
     *            the accountType to set
     */
    public void setAccountType(SearchNumberVO accountType) {
        this.accountType = accountType;
    }

    /**
     * Get accountName
     *
     * @return accountName
     */
    public SearchTextVO getAccountName() {
        return accountName;
    }

    /**
     * Set accountName
     *
     * @param accountName
     *            the accountName to set
     */
    public void setAccountName(SearchTextVO accountName) {
        this.accountName = accountName;
    }

    /**
     * Get note
     *
     * @return note
     */
    public SearchTextVO getNote() {
        return note;
    }

    /**
     * Set note
     *
     * @param note
     *            the note to set
     */
    public void setNote(SearchTextVO note) {
        this.note = note;
    }

    /**
     * Get userName
     *
     * @return userName
     */
    public SearchTextVO getUserName() {
        return userName;
    }

    /**
     * Set userName
     *
     * @param userName
     *            the userName to set
     */
    public void setUserName(SearchTextVO userName) {
        this.userName = userName;
    }

    /**
     * Get accountTypeName
     *
     * @return accountTypeName
     */
    public SearchTextVO getAccountTypeName() {
        return accountTypeName;
    }

    /**
     * Set accountTypeName
     *
     * @param accountTypeName
     *            the accountTypeName to set
     */
    public void setAccountTypeName(SearchTextVO accountTypeName) {
        this.accountTypeName = accountTypeName;
    }
}
