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
@Table(name = "account_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_account", columnNames = { "user_id", "type_id", "account_name" }) })
public class AccountTbl extends BaseTbl {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_AccountTbl_UserTbl_user"))
	private UserTbl user;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_AccountTbl_TypeTbl_accountType"))
	private TypeTbl type;

	@Column(name = "account_name", nullable = false)
	private String accountName;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_AccountTbl_MdpTbl_mdp"))
	private MdpTbl mdp;

	@Column(name = "note")
	private String note;
}
