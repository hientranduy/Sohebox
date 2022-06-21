package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

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
