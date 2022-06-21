package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class FoodSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO name;

    private SearchNumberVO typeId;

    private SearchNumberVO categoryId;

    private Boolean isFastFood;

    /**
     * Set default constructor
     *
     */
    public FoodSCO() {
        super();
    }

    public SearchNumberVO getId() {
        return id;
    }

    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    public SearchTextVO getName() {
        return name;
    }

    public void setName(SearchTextVO name) {
        this.name = name;
    }

    public SearchNumberVO getTypeId() {
        return typeId;
    }

    public void setTypeId(SearchNumberVO typeId) {
        this.typeId = typeId;
    }

    public SearchNumberVO getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(SearchNumberVO categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getIsFastFood() {
        return isFastFood;
    }

    public void setIsFastFood(Boolean isFastFood) {
        this.isFastFood = isFastFood;
    }

    @Override
    public String toString() {
        return "FoodSCO [id=" + id + ", name=" + name + ", typeId=" + typeId + ", categoryId=" + categoryId
                + ", isFastFood=" + isFastFood + "]";
    }

}
