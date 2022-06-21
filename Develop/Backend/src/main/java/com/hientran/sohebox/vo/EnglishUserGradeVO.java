package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class EnglishUserGradeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private UserVO user;

    private EnglishTypeVO vusGrade;

    private EnglishTypeVO learnDay;

    public EnglishUserGradeVO() {
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

    public EnglishTypeVO getVusGrade() {
        return vusGrade;
    }

    public void setVusGrade(EnglishTypeVO vusGrade) {
        this.vusGrade = vusGrade;
    }

    public EnglishTypeVO getLearnDay() {
        return learnDay;
    }

    public void setLearnDay(EnglishTypeVO learnDay) {
        this.learnDay = learnDay;
    }

    @Override
    public String toString() {
        return "EnglishUserGradeVO [id=" + id + ", user=" + user + ", vusGrade=" + vusGrade + ", learnDay=" + learnDay
                + "]";
    }

}