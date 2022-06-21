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
@Table(name = "english_learn_record_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_english_learn_record", columnNames = { "user_id", "english_id" }) })
public class EnglishLearnRecordTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishLearnRecordTbl_UserTbl_user"))
    private UserTbl user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishLearnRecordTbl_EnglishTbl_english"))
    private EnglishTbl english;

    @Column(name = "recordTimes", nullable = false)
    private Long recordTimes;

    @Column(name = "learnedToday", nullable = false)
    private Long learnedToday;

    @Column(name = "note")
    private String note;

    /**
     * Get user
     *
     * @return user
     */
    public UserTbl getUser() {
        return user;
    }

    /**
     * Set user
     *
     * @param user
     *            the user to set
     */
    public void setUser(UserTbl user) {
        this.user = user;
    }

    /**
     * Get english
     *
     * @return english
     */
    public EnglishTbl getEnglish() {
        return english;
    }

    /**
     * Set english
     *
     * @param english
     *            the english to set
     */
    public void setEnglish(EnglishTbl english) {
        this.english = english;
    }

    /**
     * Get recordTimes
     *
     * @return recordTimes
     */
    public Long getRecordTimes() {
        return recordTimes;
    }

    /**
     * Set recordTimes
     *
     * @param recordTimes
     *            the recordTimes to set
     */
    public void setRecordTimes(Long recordTimes) {
        this.recordTimes = recordTimes;
    }

    /**
     * Get learnedToday
     *
     * @return learnedToday
     */
    public Long getLearnedToday() {
        return learnedToday;
    }

    /**
     * Set learnedToday
     *
     * @param learnedToday
     *            the learnedToday to set
     */
    public void setLearnedToday(Long learnedToday) {
        this.learnedToday = learnedToday;
    }

    /**
     * Get note
     *
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * Set note
     *
     * @param note
     *            the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

}
