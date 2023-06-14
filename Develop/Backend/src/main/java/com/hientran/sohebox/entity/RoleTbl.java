package com.hientran.sohebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "role_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_role", columnNames = { "roleName" }) })
public class RoleTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "roleName", nullable = false)
    private String roleName;

    @Column(name = "description")
    private String description;

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
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
