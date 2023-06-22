package com.hientran.sohebox.service;

import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.entity.RoleTbl;
import com.hientran.sohebox.repository.RoleRepository;
import com.hientran.sohebox.sco.RoleSCO;
import com.hientran.sohebox.sco.SearchTextVO;
import com.hientran.sohebox.utils.LogUtils;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class RoleService  {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Prepare role
     *
     * @param roleName
     * @return
     */
    public RoleTbl getRole(String roleName) {
        // Declare result
        RoleTbl result = null;

        // Search role
        result = getByRoleName(roleName);

        // Create new if empty
        if (result == null) {
            result = new RoleTbl();
            result.setRoleName(roleName);
            Long roleId = create(result);

            result = getById(roleId);
        }

        // Return
        return result;
    }

    /**
     * Get role by id
     *
     * @param id
     * @return
     */
    public RoleTbl getById(Long id) {
        // Declare result
        RoleTbl result = null;

        try {
            // Get Data
            Optional<RoleTbl> tbl = roleRepository.findById(id);

            // Transformer
            if (tbl.isPresent()) {
                result = tbl.get();
            }

        } catch (Exception e) {
            LogUtils.writeLogError(e);
            throw e;
        }

        // Return
        return result;
    }

    /**
     * Get role by id
     *
     * @param roleId
     * @return
     */
    public RoleTbl getByRoleName(String roleName) {
        // Declare result
        RoleTbl result = null;

        try {
            // Get Data
            SearchTextVO roleSearch = new SearchTextVO();
            roleSearch.setEq(roleName);
            RoleSCO sco = new RoleSCO();
            sco.setRoleName(roleSearch);
            Page<RoleTbl> page = roleRepository.findAll(sco);

            // Transformer
            if (CollectionUtils.isNotEmpty(page.getContent())) {
                result = page.getContent().get(0);
            }

        } catch (Exception e) {
            LogUtils.writeLogError(e);
            throw e;
        }

        // Return
        return result;
    }

    /**
     * Add a role
     *
     * @param role
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Long create(RoleTbl tbl) {
        // Declare result
        Long result = null;

        try {
            // Add role
            tbl = roleRepository.save(tbl);
            result = tbl.getId();

        } catch (Exception e) {
            LogUtils.writeLogError(e);
            throw e;
        }

        // Return
        return result;
    }

    /**
     * Update a role
     *
     * @param role
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean update(RoleTbl tbl) {
        // Declare result
        boolean result = false;

        try {
            // Get old record
            Optional<RoleTbl> roleTbl = roleRepository.findById(tbl.getId());

            // Update if found, else return not found exception
            if (roleTbl.isPresent()) {
                RoleTbl updateTbl = roleTbl.get();
                updateTbl.setRoleName(tbl.getRoleName());
                updateTbl.setDescription(tbl.getDescription());
                roleRepository.save(updateTbl);
                result = true;
            }
        } catch (Exception e) {
            LogUtils.writeLogError(e);
            throw e;
        }

        // Return
        return result;
    }

    /**
     * Delete a role
     *
     * @param role
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean delete(Long id) {
        // Declare result
        boolean result = false;

        try {
            // Check existed
            Optional<RoleTbl> roleTbl = roleRepository.findById(id);

            // Delete if found, else return not found exception
            if (roleTbl.isPresent()) {
                roleRepository.delete(roleTbl.get());
                result = true;
            }
        } catch (Exception e) {
            LogUtils.writeLogError(e);
            throw e;
        }

        // Return
        return result;
    }

}
