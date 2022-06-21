package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class EnglishVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String keyWord;

    private EnglishTypeVO wordLevel;

    private EnglishTypeVO category;

    private EnglishTypeVO learnDay;

    private String imageName;

    private String imageFile;

    private String explanationEn;

    private String explanationVn;

    private String voiceUkFile;

    private String voiceUsFile;

    private EnglishTypeVO vusGrade;

    // Other fields//

    private String imageExtention;

    private Long recordTimes;

    /**
     * Constructor
     *
     */
    public EnglishVO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public EnglishTypeVO getWordLevel() {
        return wordLevel;
    }

    public void setWordLevel(EnglishTypeVO wordLevel) {
        this.wordLevel = wordLevel;
    }

    public EnglishTypeVO getCategory() {
        return category;
    }

    public void setCategory(EnglishTypeVO category) {
        this.category = category;
    }

    public EnglishTypeVO getLearnDay() {
        return learnDay;
    }

    public void setLearnDay(EnglishTypeVO learnDay) {
        this.learnDay = learnDay;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getExplanationEn() {
        return explanationEn;
    }

    public void setExplanationEn(String explanationEn) {
        this.explanationEn = explanationEn;
    }

    public String getExplanationVn() {
        return explanationVn;
    }

    public void setExplanationVn(String explanationVn) {
        this.explanationVn = explanationVn;
    }

    public String getVoiceUkFile() {
        return voiceUkFile;
    }

    public void setVoiceUkFile(String voiceUkFile) {
        this.voiceUkFile = voiceUkFile;
    }

    public String getVoiceUsFile() {
        return voiceUsFile;
    }

    public void setVoiceUsFile(String voiceUsFile) {
        this.voiceUsFile = voiceUsFile;
    }

    public EnglishTypeVO getVusGrade() {
        return vusGrade;
    }

    public void setVusGrade(EnglishTypeVO vusGrade) {
        this.vusGrade = vusGrade;
    }

    public String getImageExtention() {
        return imageExtention;
    }

    public void setImageExtention(String imageExtention) {
        this.imageExtention = imageExtention;
    }

    public Long getRecordTimes() {
        return recordTimes;
    }

    public void setRecordTimes(Long recordTimes) {
        this.recordTimes = recordTimes;
    }

    @Override
    public String toString() {
        return "EnglishVO [id=" + id + ", keyWord=" + keyWord + ", wordLevel=" + wordLevel + ", category=" + category
                + ", learnDay=" + learnDay + ", imageName=" + imageName + ", imageFile=" + imageFile
                + ", explanationEn=" + explanationEn + ", explanationVn=" + explanationVn + ", voiceUkFile="
                + voiceUkFile + ", voiceUsFile=" + voiceUsFile + ", vusGrade=" + vusGrade + ", imageExtention="
                + imageExtention + ", recordTimes=" + recordTimes + "]";
    }

}