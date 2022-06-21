package com.hientran.sohebox.entity;

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
@Table(name = "english_user_grade_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_english_user_grade", columnNames = { "user_id" }) })
public class EnglishUserGradeTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishUserGradeTbl_UserTbl_user"))
    private UserTbl user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishUserGradeTbl_EnglishTypeTbl_vusGrade"))
    private EnglishTypeTbl vusGrade;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishUserGradeTbl_EnglishTypeTbl_learnDay"))
    private EnglishTypeTbl learnDay;

    public UserTbl getUser() {
        return user;
    }

    public void setUser(UserTbl user) {
        this.user = user;
    }

    public EnglishTypeTbl getVusGrade() {
        return vusGrade;
    }

    public void setVusGrade(EnglishTypeTbl vusGrade) {
        this.vusGrade = vusGrade;
    }

    public EnglishTypeTbl getLearnDay() {
        return learnDay;
    }

    public void setLearnDay(EnglishTypeTbl learnDay) {
        this.learnDay = learnDay;
    }

}
