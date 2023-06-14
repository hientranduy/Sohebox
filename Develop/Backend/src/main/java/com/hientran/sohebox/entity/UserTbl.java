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
@Table(name = "user_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_user", columnNames = { "userName" }) })
public class UserTbl extends BaseTbl {
	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "username", nullable = false)
	private String username;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_MdpTbl_mdp"))
	private MdpTbl mdp;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_MdpTbl_privateKey"))
	private MdpTbl privateKey;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_RoleTbl_role"))
	private RoleTbl role;

	@Column(name = "avatarUrl")
	private String avatarUrl;
}
