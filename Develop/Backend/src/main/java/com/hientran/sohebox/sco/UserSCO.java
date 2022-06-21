package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class UserSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO userName;

    private SearchTextVO firstName;

    private SearchTextVO lastName;

    /**
     * Set default constructor
     *
     */
    public UserSCO() {
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
     * Get firstName
     *
     * @return firstName
     */
    public SearchTextVO getFirstName() {
        return firstName;
    }

    /**
     * Set firstName
     *
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(SearchTextVO firstName) {
        this.firstName = firstName;
    }

    /**
     * Get lastName
     *
     * @return lastName
     */
    public SearchTextVO getLastName() {
        return lastName;
    }

    /**
     * Set lastName
     *
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(SearchTextVO lastName) {
        this.lastName = lastName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserSCO [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
                + "]";
    }

}
