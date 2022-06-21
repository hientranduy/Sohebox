package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class UserStatusVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String roleName;

    private String avatarUrl;

    private String status;

    private String durationTime;

    private long durationSeconds;

    /**
     * Constructor
     *
     */
    public UserStatusVO() {
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
     * Get firstName
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set firstName
     *
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get lastName
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set lastName
     *
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get roleName
     *
     * @return roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set roleName
     *
     * @param roleName
     *            the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Get avatarUrl
     *
     * @return avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Set avatarUrl
     *
     * @param avatarUrl
     *            the avatarUrl to set
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * Get status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     *
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get durationTime
     *
     * @return durationTime
     */
    public String getDurationTime() {
        return durationTime;
    }

    /**
     * Set durationTime
     *
     * @param durationTime
     *            the durationTime to set
     */
    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public long getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(long durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "UserStatusVO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
                + username + ", roleName=" + roleName + ", avatarUrl=" + avatarUrl + ", status=" + status
                + ", durationTime=" + durationTime + ", durationSeconds=" + durationSeconds + "]";
    }

}