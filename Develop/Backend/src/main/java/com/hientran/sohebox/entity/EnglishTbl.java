package com.hientran.sohebox.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "english_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_english", columnNames = { "key_word" }) })
@EntityListeners(AuditingEntityListener.class)
public class EnglishTbl extends GenericTbl {
	@Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "updated_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedDate;

	@Column(name = "key_word")
	private String keyWord;

	@ManyToOne
	@JoinColumn(name = "word_level_id", foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_wordLevel"))
	private EnglishTypeTbl wordLevel;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_category"))
	private EnglishTypeTbl category;

	@ManyToOne
	@JoinColumn(name = "learn_day_id", foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_learnDay"))
	private EnglishTypeTbl learnDay;

	@Column(name = "image_name", nullable = false)
	private String imageName;

	@Column(name = "explanation_en")
	private String explanationEn;

	@Column(name = "explanation_vn")
	private String explanationVn;

	@Column(name = "voice_uk_file")
	private String voiceUkFile;

	@Column(name = "voice_us_file")
	private String voiceUsFile;

	@ManyToOne
	@JoinColumn(name = "vus_grade_id", foreignKey = @ForeignKey(name = "FK_EnglishTbl_EnglishTypeTbl_vusGrade"))
	private EnglishTypeTbl vusGrade;

	// Other field
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String imageFile;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String imageExtention;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Long recordTimes;
}
