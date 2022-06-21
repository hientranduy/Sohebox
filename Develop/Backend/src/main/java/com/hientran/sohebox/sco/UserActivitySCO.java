package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class UserActivitySCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchDateVO createdDate;

    private SearchTextVO userName;

    private SearchNumberVO userId;

    private SearchTextVO activity;

    /**
     * Set default constructor
     *
     */
    public UserActivitySCO() {
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
     * Get createdDate
     *
     * @return createdDate
     */
    public SearchDateVO getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     *
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(SearchDateVO createdDate) {
        this.createdDate = createdDate;
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
     * Get userId
     *
     * @return userId
     */
    public SearchNumberVO getUserId() {
        return userId;
    }

    /**
     * Set userId
     *
     * @param userId
     *            the userId to set
     */
    public void setUserId(SearchNumberVO userId) {
        this.userId = userId;
    }

    /**
     * Get activity
     *
     * @return activity
     */
    public SearchTextVO getActivity() {
        return activity;
    }

    /**
     * Set activity
     *
     * @param activity
     *            the activity to set
     */
    public void setActivity(SearchTextVO activity) {
        this.activity = activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserActivitySCO [id=" + id + ", createdDate=" + createdDate + ", userName=" + userName + ", userId="
                + userId + ", activity=" + activity + "]";
    }

}
