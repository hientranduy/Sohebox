package com.hientran.sohebox.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_user", columnNames = { "username" }) })
public class UserTbl extends BaseTbl {
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "username", nullable = false)
	private String username;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_MdpTbl_mdp"))
	private MdpTbl mdp;

	@ManyToOne
	@JoinColumn(name = "private_key_id", foreignKey = @ForeignKey(name = "FK_UserTbl_MdpTbl_privateKey"))
	private MdpTbl privateKey;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_UserTbl_RoleTbl_role"))
	private RoleTbl role;

	@Column(name = "avatar_url")
	private String avatarUrl;

	// Other field
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String password;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String token;
}
