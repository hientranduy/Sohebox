package com.hientran.sohebox.entity;

import javax.persistence.Column;
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
@Table(name = "user_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_user", columnNames = { "userName" }) })
public class UserTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_MdpTbl_mdp"))
    private MdpTbl mdp;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_MdpTbl_privateKey"))
    private MdpTbl privateKey;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_RoleTbl_role"))
    private RoleTbl role;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    /**
     * Constructor
     *
     */
    public UserTbl() {
        super();
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
     * Get mdp
     *
     * @return mdp
     */
    public MdpTbl getMdp() {
        return mdp;
    }

    /**
     * Set mdp
     *
     * @param mdp
     *            the mdp to set
     */
    public void setMdp(MdpTbl mdp) {
        this.mdp = mdp;
    }

    /**
     * Get privateKey
     *
     * @return privateKey
     */
    public MdpTbl getPrivateKey() {
        return privateKey;
    }

    /**
     * Set privateKey
     *
     * @param privateKey
     *            the privateKey to set
     */
    public void setPrivateKey(MdpTbl privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Get role
     *
     * @return role
     */
    public RoleTbl getRole() {
        return role;
    }

    /**
     * Set role
     *
     * @param role
     *            the role to set
     */
    public void setRole(RoleTbl role) {
        this.role = role;
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

}
