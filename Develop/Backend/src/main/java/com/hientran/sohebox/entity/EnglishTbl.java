package com.hientran.sohebox.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "english_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_english", columnNames = { "keyWord" }) })
@EntityListeners(AuditingEntityListener.class)
public class EnglishTbl extends GenericTbl {
	@Column(name = "createdDate", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "updatedDate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
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
}
