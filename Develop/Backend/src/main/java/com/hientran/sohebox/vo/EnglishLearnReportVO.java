package com.hientran.sohebox.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class EnglishLearnReportVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private UserVO user;

    private Date learnedDate;

    private Long learnedTotal;

    /**
     * Constructor
     *
     */
    public EnglishLearnReportVO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
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

    @Override
    public String toString() {
        return "EnglishLearnReportVO [id=" + id + ", user=" + user + ", learnedDate=" + learnedDate + ", learnedTotal="
                + learnedTotal + "]";
    }

}