package com.hientran.sohebox.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "english_learn_report_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_english_learn_report", columnNames = { "user_id", "learnedDate" }) })
public class EnglishLearnReportTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishLearnReportTbl_UserTbl_user"))
    private UserTbl user;

    @Column(name = "learnedDate", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date learnedDate;

    @Column(name = "learnedTotal", nullable = false)
    private Long learnedTotal;

    public UserTbl getUser() {
        return user;
    }

    public void setUser(UserTbl user) {
        this.user = user;
    }

    public Date getLearnedDate() {
        return learnedDate;
    }

    public void setLearnedDate(Date learnedDate) {
        this.learnedDate = learnedDate;
    }

    public Long getLearnedTotal() {
        return learnedTotal;
    }

    public void setLearnedTotal(Long learnedTotal) {
        this.learnedTotal = learnedTotal;
    }

}
