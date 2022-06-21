package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class ConfigVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String configKey;

    private String configValue;

    private String description;

    /**
     * Constructor
     *
     */
    public ConfigVO() {
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
     * Get configKey
     *
     * @return configKey
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * Set configKey
     *
     * @param configKey
     *            the configKey to set
     */
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    /**
     * Get configValue
     *
     * @return configValue
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * Set configValue
     *
     * @param configValue
     *            the configValue to set
     */
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ConfigVO [id=" + id + ", configKey=" + configKey + ", configValue=" + configValue + ", description="
                + description + "]";
    }

}