package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class CountrySCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchTextVO name;

    private SearchTextVO note;

    /**
     * Set default constructor
     *
     */
    public CountrySCO() {
        super();
    }

    /**
     * Get name
     *
     * @return name
     */
    public SearchTextVO getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(SearchTextVO name) {
        this.name = name;
    }

    /**
     * Get note
     *
     * @return note
     */
    public SearchTextVO getNote() {
        return note;
    }

    /**
     * Set note
     *
     * @param note
     *            the note to set
     */
    public void setNote(SearchTextVO note) {
        this.note = note;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CountrySCO [name=" + name + ", note=" + note + "]";
    }

}
