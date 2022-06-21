package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "english_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_english", columnNames = { "keyWord" }) })
@EntityListeners(AuditingEntityListener.class)
public class EnglishTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "createdDate", nullable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Column(name = "updatedDate", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedDate;

    @Column(name = "keyWord")
    private String keyWord;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_wordLevel"))
    private EnglishTypeTbl wordLevel;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_category"))
    private EnglishTypeTbl category;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_learnDay"))
    private EnglishTypeTbl learnDay;

    @Column(name = "imageName", nullable = false)
    private String imageName;

    @Column(name = "explanationEn")
    private String explanationEn;

    @Column(name = "explanationVn")
    private String explanationVn;

    @Column(name = "voiceUkFile")
    private String voiceUkFile;

    @Column(name = "voiceUsFile")
    private String voiceUsFile;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_vusGrade"))
    private EnglishTypeTbl vusGrade;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public EnglishTypeTbl getWordLevel() {
        return wordLevel;
    }

    public void setWordLevel(EnglishTypeTbl wordLevel) {
        this.wordLevel = wordLevel;
    }

    public EnglishTypeTbl getCategory() {
        return category;
    }

    public void setCategory(EnglishTypeTbl category) {
        this.category = category;
    }

    public EnglishTypeTbl getLearnDay() {
        return learnDay;
    }

    public void setLearnDay(EnglishTypeTbl learnDay) {
        this.learnDay = learnDay;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public EnglishTypeTbl getVusGrade() {
        return vusGrade;
    }

    public void setVusGrade(EnglishTypeTbl vusGrade) {
        this.vusGrade = vusGrade;
    }

}
