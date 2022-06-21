package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class MdpSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchTextVO mdp;

    /**
     * Set default constructor
     *
     */
    public MdpSCO() {
        super();
    }

    /**
     * Get mdp
     *
     * @return mdp
     */
    public SearchTextVO getMdp() {
        return mdp;
    }

    /**
     * Set mdp
     *
     * @param mdp
     *            the mdp to set
     */
    public void setMdp(SearchTextVO mdp) {
        this.mdp = mdp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MdpSCO [mdp=" + mdp + "]";
    }

}
