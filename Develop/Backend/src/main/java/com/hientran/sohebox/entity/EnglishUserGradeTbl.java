package com.hientran.sohebox.entity;

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
@Table(name = "english_user_grade_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_english_user_grade", columnNames = { "user_id" }) })
public class EnglishUserGradeTbl extends BaseTbl {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishUserGradeTbl_UserTbl_user"))
	private UserTbl user;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishUserGradeTbl_EnglishTypeTbl_vusGrade"))
	private EnglishTypeTbl vusGrade;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_EnglishUserGradeTbl_EnglishTypeTbl_learnDay"))
	private EnglishTypeTbl learnDay;
}
