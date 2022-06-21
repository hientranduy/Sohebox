package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class ChangePasswordVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String username;

    private String oldPassword;

    private String newPassword;

    /**
     * Constructor
     *
     */
    public ChangePasswordVO() {
        super();
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
     * Get oldPassword
     *
     * @return oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Set oldPassword
     *
     * @param oldPassword
     *            the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Get newPassword
     *
     * @return newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set newPassword
     *
     * @param newPassword
     *            the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ChangePasswordVO [username=" + username + ", oldPassword=" + oldPassword + ", newPassword="
                + newPassword + "]";
    }

}