package com.hientran.sohebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "english_learn_record_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_english_learn_record", columnNames = { "user_id", "english_id" }) })
public class EnglishLearnRecordTbl extends BaseTbl {
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
}
