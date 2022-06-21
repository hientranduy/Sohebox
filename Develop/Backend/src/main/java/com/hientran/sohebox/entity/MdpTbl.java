package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "mdp_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_mdp", columnNames = { "mdp" }) })
@EntityListeners(AuditingEntityListener.class)
public class MdpTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "createdDate", nullable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Column(name = "mdp", nullable = false)
    private String mdp;

    @Column(name = "description")
    private String description;

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
