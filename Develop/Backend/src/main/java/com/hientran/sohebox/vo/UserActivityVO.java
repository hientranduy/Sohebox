package com.hientran.sohebox.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hientran.sohebox.entity.UserTbl;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class UserActivityVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    private Date createdDate;

    private UserVO user;

    private UserTbl userTbl;

    private TypeVO activity;

    /**
     * Constructor
     *
     */
    public UserActivityVO() {
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
     * Get createdDate
     *
     * @return createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set createdDate
     *
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
     * Get userTbl
     *
     * @return userTbl
     */
    public UserTbl getUserTbl() {
        return userTbl;
    }

    /**
     * Set userTbl
     *
     * @param userTbl
     *            the userTbl to set
     */
    public void setUserTbl(UserTbl userTbl) {
        this.userTbl = userTbl;
    }

    /**
     * Get activity
     *
     * @return activity
     */
    public TypeVO getActivity() {
        return activity;
    }

    /**
     * Set activity
     *
     * @param activity
     *            the activity to set
     */
    public void setActivity(TypeVO activity) {
        this.activity = activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserActivityVO [id=" + id + ", createdDate=" + createdDate + ", user=" + user + ", userTbl=" + userTbl
                + ", activity=" + activity + "]";
    }

}