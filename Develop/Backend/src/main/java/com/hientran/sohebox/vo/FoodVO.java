package com.hientran.sohebox.vo;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hientran.sohebox.entity.FoodTypeTbl;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class FoodVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String imageName;

    private String description;

    private String locationNote;

    private FoodTypeTbl type;

    private FoodTypeTbl category;

    private Boolean isFastFood;

    private byte[] recipe;

    private String recipeString;

    private String urlReference;

    // Extra fields

    private String imageFile;

    private String imageExtention;

    public FoodVO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get imageName
     *
     * @return imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Set imageName
     *
     * @param imageName
     *            the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get locationNote
     *
     * @return locationNote
     */
    public String getLocationNote() {
        return locationNote;
    }

    /**
     * Set locationNote
     *
     * @param locationNote
     *            the locationNote to set
     */
    public void setLocationNote(String locationNote) {
        this.locationNote = locationNote;
    }

    /**
     * Get type
     *
     * @return type
     */
    public FoodTypeTbl getType() {
        return type;
    }

    /**
     * Set type
     *
     * @param type
     *            the type to set
     */
    public void setType(FoodTypeTbl type) {
        this.type = type;
    }

    /**
     * Get category
     *
     * @return category
     */
    public FoodTypeTbl getCategory() {
        return category;
    }

    /**
     * Set category
     *
     * @param category
     *            the category to set
     */
    public void setCategory(FoodTypeTbl category) {
        this.category = category;
    }

    /**
     * Get isFastFood
     *
     * @return isFastFood
     */
    public Boolean getIsFastFood() {
        return isFastFood;
    }

    /**
     * Set isFastFood
     *
     * @param isFastFood
     *            the isFastFood to set
     */
    public void setIsFastFood(Boolean isFastFood) {
        this.isFastFood = isFastFood;
    }

    /**
     * Get recipe
     *
     * @return recipe
     */
    public byte[] getRecipe() {
        return recipe;
    }

    /**
     * Set recipe
     *
     * @param recipe
     *            the recipe to set
     */
    public void setRecipe(byte[] recipe) {
        this.recipe = recipe;
    }

    /**
     * Get recipeString
     *
     * @return recipeString
     */
    public String getRecipeString() {
        return recipeString;
    }

    /**
     * Set recipeString
     *
     * @param recipeString
     *            the recipeString to set
     */
    public void setRecipeString(String recipeString) {
        this.recipeString = recipeString;
    }

    /**
     * Get urlReference
     *
     * @return urlReference
     */
    public String getUrlReference() {
        return urlReference;
    }

    /**
     * Set urlReference
     *
     * @param urlReference
     *            the urlReference to set
     */
    public void setUrlReference(String urlReference) {
        this.urlReference = urlReference;
    }

    /**
     * Get imageFile
     *
     * @return imageFile
     */
    public String getImageFile() {
        return imageFile;
    }

    /**
     * Set imageFile
     *
     * @param imageFile
     *            the imageFile to set
     */
    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    /**
     * Get imageExtention
     *
     * @return imageExtention
     */
    public String getImageExtention() {
        return imageExtention;
    }

    /**
     * Set imageExtention
     *
     * @param imageExtention
     *            the imageExtention to set
     */
    public void setImageExtention(String imageExtention) {
        this.imageExtention = imageExtention;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FoodVO [id=" + id + ", name=" + name + ", imageName=" + imageName + ", description=" + description
                + ", locationNote=" + locationNote + ", type=" + type + ", category=" + category + ", isFastFood="
                + isFastFood + ", recipe=" + Arrays.toString(recipe) + ", recipeString=" + recipeString
                + ", urlReference=" + urlReference + ", imageFile=" + imageFile + ", imageExtention=" + imageExtention
                + "]";
    }

}