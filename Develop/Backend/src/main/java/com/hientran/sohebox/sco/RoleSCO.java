package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class RoleSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO roleName;

    private SearchDateVO createdDate;

    /**
     * Set default constructor
     *
     */
    public RoleSCO() {
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
     * Get roleName
     *
     * @return roleName
     */
    public SearchTextVO getRoleName() {
        return roleName;
    }

    /**
     * Set roleName
     *
     * @param roleName
     *            the roleName to set
     */
    public void setRoleName(SearchTextVO roleName) {
        this.roleName = roleName;
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "RoleSCO [id=" + id + ", role=" + roleName + ", createdDate=" + createdDate + "]";
    }

}
