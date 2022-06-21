package com.hientran.sohebox.sco;

/**
 * 
 * Search condition
 *
 * @author hientran
 */
public class ConfigSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO configKey;

    private SearchTextVO configValue;

    private SearchTextVO description;

    /**
     * Set default constructor
     *
     */
    public ConfigSCO() {
        super();
    }

    /**
     * Get id
     *
     * @return id
     */
    public SearchNumberVO getId() {
        return id;
    }

    /**
     * Set id
     *
     * @param id
     *            the id to set
     */
    public void setId(SearchNumberVO id) {
        this.id = id;
    }

    /**
     * Get configKey
     *
     * @return configKey
     */
    public SearchTextVO getConfigKey() {
        return configKey;
    }

    /**
     * Set configKey
     *
     * @param configKey
     *            the configKey to set
     */
    public void setConfigKey(SearchTextVO configKey) {
        this.configKey = configKey;
    }

    /**
     * Get configValue
     *
     * @return configValue
     */
    public SearchTextVO getConfigValue() {
        return configValue;
    }

    /**
     * Set configValue
     *
     * @param configValue
     *            the configValue to set
     */
    public void setConfigValue(SearchTextVO configValue) {
        this.configValue = configValue;
    }

    public SearchTextVO getDescription() {
        return description;
    }

    public void setDescription(SearchTextVO description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ConfigSCO [id=" + id + ", configKey=" + configKey + ", configValue=" + configValue + ", description="
                + description + "]";
    }

}
