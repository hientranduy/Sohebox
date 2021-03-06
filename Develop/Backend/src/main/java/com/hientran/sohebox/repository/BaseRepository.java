package com.hientran.sohebox.repository;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hientran.sohebox.configuration.SoheboxProperties;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.sco.Sorter;

/**
 * @author hientran
 */
public interface BaseRepository {

    /**
     * Entity Manager Factory
     */
    public default EntityManagerFactory emFactory() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect",
                SoheboxProperties.getPropertyApplication("spring.jpa.properties.hibernate.dialect"));
        props.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");

        props.setProperty("javax.persistence.jdbc.url",
                SoheboxProperties.getPropertyApplication("spring.datasource.url.database"));
        props.setProperty("javax.persistence.jdbc.useSSL", "false");
        props.setProperty("javax.persistence.jdbc.user",
                SoheboxProperties.getPropertyApplication("spring.datasource.username"));
        props.setProperty("javax.persistence.jdbc.password",
                SoheboxProperties.getPropertyApplication("spring.datasource.password"));
        return Persistence.createEntityManagerFactory("com.hientran.sohebox", props);
    }

    /**
     * Get Entity Manager
     */
    public default EntityManager getEntityManager() {
        return emFactory().createEntityManager();
    }

    public default void close() {
        emFactory().close();
    }

    /**
     * 
     * Create Page able
     *
     * @param pageToGet
     * @param maxRecordPerPage
     * @param sorters
     * @return
     */
    public default Pageable createPageable(Integer pageToGet, Integer maxRecordPerPage, List<Sorter> sorters,
            Boolean reportFlag) {
        // Declare return
        Pageable result;

        // Prepare Sort
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sorters)) {
            for (Sorter sorter : sorters) {
                if (sorter.getProperty() != null) {
                    // Create sort
                    Sort checkSort = null;
                    if (StringUtils.equals(sorter.getDirection().toUpperCase(), DBConstants.DIRECTION_DECCENT)) {
                        checkSort = Sort.by(Sort.Direction.DESC, sorter.getProperty());
                    } else {
                        checkSort = Sort.by(Sort.Direction.ASC, sorter.getProperty());
                    }

                    // Add sort
                    if (sort == null) {
                        sort = checkSort;
                    } else {
                        sort = sort.and(checkSort);
                    }
                }
            }
        }

        // Get default configuration
        int dataReturnMaxRecordPerPage = Integer
                .valueOf(SoheboxProperties.getProperty("data.return.max.record.per.page"));
        int dataReturnFirstPage = Integer.valueOf(SoheboxProperties.getProperty("data.return.first.page"));

        // Calculate max record per page
        if (maxRecordPerPage == null || maxRecordPerPage > dataReturnMaxRecordPerPage) {
            if (reportFlag == null || !reportFlag) {
                maxRecordPerPage = dataReturnMaxRecordPerPage;
            }
        }

        // Calculate pageToGet; index from 0
        if (pageToGet == null || pageToGet <= 0) {
            pageToGet = dataReturnFirstPage;
        }

        // Prepare page able
        Pageable pageable = null;
        if (sort != null) {
            pageable = PageRequest.of(pageToGet, maxRecordPerPage, sort);
        } else {
            pageable = PageRequest.of(pageToGet, maxRecordPerPage);
        }

        // Return
        result = pageable;
        return result;
    }
}
