package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class EnglishSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO keyWord;

    private SearchNumberVO wordLevelId;

    private SearchNumberVO categoryId;

    private SearchNumberVO learnDayId;

    private SearchNumberVO userId;

    private SearchNumberVO vusGradeId;

    /**
     * Set default constructor
     *
     */
    public EnglishSCO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public SearchNumberVO getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    /**
     * Get keyWord
     *
     * @return keyWord
     */
    public SearchTextVO getKeyWord() {
        return keyWord;
    }

    /**
     * Set keyWord
     *
     * @param keyWord
     *            the keyWord to set
     */
    public void setKeyWord(SearchTextVO keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * Get wordLevelId
     *
     * @return wordLevelId
     */
    public SearchNumberVO getWordLevelId() {
        return wordLevelId;
    }

    /**
     * Set wordLevelId
     *
     * @param wordLevelId
     *            the wordLevelId to set
     */
    public void setWordLevelId(SearchNumberVO wordLevelId) {
        this.wordLevelId = wordLevelId;
    }

    /**
     * Get categoryId
     *
     * @return categoryId
     */
    public SearchNumberVO getCategoryId() {
        return categoryId;
    }

    /**
     * Set categoryId
     *
     * @param categoryId
     *            the categoryId to set
     */
    public void setCategoryId(SearchNumberVO categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get learnDayId
     *
     * @return learnDayId
     */
    public SearchNumberVO getLearnDayId() {
        return learnDayId;
    }

    /**
     * Set learnDayId
     *
     * @param learnDayId
     *            the learnDayId to set
     */
    public void setLearnDayId(SearchNumberVO learnDayId) {
        this.learnDayId = learnDayId;
    }

    /**
     * Get userId
     *
     * @return userId
     */
    public SearchNumberVO getUserId() {
        return userId;
    }

    /**
     * Set userId
     *
     * @param userId
     *            the userId to set
     */
    public void setUserId(SearchNumberVO userId) {
        this.userId = userId;
    }

    /**
     * Get vusGradeId
     *
     * @return vusGradeId
     */
    public SearchNumberVO getVusGradeId() {
        return vusGradeId;
    }

    /**
     * Set vusGradeId
     *
     * @param vusGradeId
     *            the vusGradeId to set
     */
    public void setVusGradeId(SearchNumberVO vusGradeId) {
        this.vusGradeId = vusGradeId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "EnglishSCO [id=" + id + ", keyWord=" + keyWord + ", wordLevelId=" + wordLevelId + ", categoryId="
                + categoryId + ", learnDayId=" + learnDayId + ", userId=" + userId + ", vusGradeId=" + vusGradeId + "]";
    }

}
