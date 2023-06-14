package com.hientran.sohebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "config_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_config", columnNames = { "configKey" }) })
public class ConfigTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "configKey", nullable = false)
    private String configKey;

    @Column(name = "configValue")
    private String configValue;

    @Column(name = "description")
    private String description;

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

}