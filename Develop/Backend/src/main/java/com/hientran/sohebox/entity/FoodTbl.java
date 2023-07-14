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
import jakarta.persistence.Lob;
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
@Table(name = "food_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_food", columnNames = { "name" }) })
@EntityListeners(AuditingEntityListener.class)
public class FoodTbl extends GenericTbl {
	@Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@Column(name = "updated_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedDate;

	@Column(name = "name")
	private String name;

	@Column(name = "image_name", nullable = false)
	private String imageName;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "location_note")
	private String locationNote;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_FoodTbl_FoodTypeTbl_type"))
	private FoodTypeTbl type;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_FoodTbl_FoodTypeTbl_category"))
	private FoodTypeTbl category;

	@Column(name = "is_fast_food")
	private Boolean isFastFood;

	@Lob
	@Column(name = "recipe", length = 2000)
	private byte[] recipe;

	@Column(name = "url_reference")
	private String urlReference;

	// Other field
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String recipeString;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String imageFile;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String imageExtention;
}
