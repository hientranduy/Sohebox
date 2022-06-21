package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class EnglishUserGradeSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchNumberVO userId;

    private SearchNumberVO vusGradeId;

    private SearchNumberVO learnDayId;

    public EnglishUserGradeSCO() {
        super();
    }

    public SearchNumberVO getId() {
        return id;
    }

    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    public SearchNumberVO getUserId() {
        return userId;
    }

    public void setUserId(SearchNumberVO userId) {
        this.userId = userId;
    }

    public SearchNumberVO getVusGradeId() {
        return vusGradeId;
    }

    public void setVusGradeId(SearchNumberVO vusGradeId) {
        this.vusGradeId = vusGradeId;
    }

    public SearchNumberVO getLearnDayId() {
        return learnDayId;
    }

    public void setLearnDayId(SearchNumberVO learnDayId) {
        this.learnDayId = learnDayId;
    }

    @Override
    public String toString() {
        return "EnglishUserGradeSCO [id=" + id + ", userId=" + userId + ", vusGradeId=" + vusGradeId + ", learnDayId="
                + learnDayId + "]";
    }

}
