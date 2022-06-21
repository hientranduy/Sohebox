package com.hientran.sohebox.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class EnglishLearnRecordVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private UserVO user;

    private EnglishVO english;

    private Long recordTimes;

    private Long learnedToday;

    private String note;

    private Date updatedDate;

    /**
     * Constructor
     *
     */
    public EnglishLearnRecordVO() {
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
     * Get english
     *
     * @return english
     */
    public EnglishVO getEnglish() {
        return english;
    }

    /**
     * Set english
     *
     * @param english
     *            the english to set
     */
    public void setEnglish(EnglishVO english) {
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

    /**
     * Get updatedDate
     *
     * @return updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set updatedDate
     *
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "EnglishLearnRecordVO [id=" + id + ", user=" + user + ", english=" + english + ", recordTimes="
                + recordTimes + ", learnedToday=" + learnedToday + ", note=" + note + ", updatedDate=" + updatedDate
                + "]";
    }

}