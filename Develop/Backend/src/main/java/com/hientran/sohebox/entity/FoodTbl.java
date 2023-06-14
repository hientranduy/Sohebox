package com.hientran.sohebox.entity;

import java.util.Date;

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
import jakarta.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hientran
 */
@Entity
@Table(name = "food_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_food", columnNames = { "name" }) })
@EntityListeners(AuditingEntityListener.class)
public class FoodTbl extends GenericTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "createdDate", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Column(name = "updatedDate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedDate;

    @Column(name = "name")
    private String name;

    @Column(name = "imageName", nullable = false)
    private String imageName;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "locationNote")
    private String locationNote;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_FoodTbl_FoodTypeTbl_type"))
    private FoodTypeTbl type;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_FoodTbl_FoodTypeTbl_category"))
    private FoodTypeTbl category;

    @Column(name = "isFastFood")
    private Boolean isFastFood;

    @Lob
    @Column(name = "recipe", length = 2000)
    private byte[] recipe;

    @Column(name = "urlReference")
    private String urlReference;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationNote() {
        return locationNote;
    }

    public void setLocationNote(String locationNote) {
        this.locationNote = locationNote;
    }

    public FoodTypeTbl getType() {
        return type;
    }

    public void setType(FoodTypeTbl type) {
        this.type = type;
    }

    public FoodTypeTbl getCategory() {
        return category;
    }

    public void setCategory(FoodTypeTbl category) {
        this.category = category;
    }

    public Boolean getIsFastFood() {
        return isFastFood;
    }

    public void setIsFastFood(Boolean isFastFood) {
        this.isFastFood = isFastFood;
    }

    public byte[] getRecipe() {
        return recipe;
    }

    public void setRecipe(byte[] recipe) {
        this.recipe = recipe;
    }

    public String getUrlReference() {
        return urlReference;
    }

    public void setUrlReference(String urlReference) {
        this.urlReference = urlReference;
    }

}
