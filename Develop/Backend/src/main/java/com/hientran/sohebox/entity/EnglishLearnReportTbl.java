package com.hientran.sohebox.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "english_learn_report_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_english_learn_report", columnNames = { "user_id", "learned_date" }) })
public class EnglishLearnReportTbl extends GenericTbl {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishLearnReportTbl_UserTbl_user"))
	private UserTbl user;

	@Column(name = "learned_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date learnedDate;

	@Column(name = "learned_total", nullable = false)
	private Long learnedTotal;
}
