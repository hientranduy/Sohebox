package com.hientran.sohebox.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hientran.sohebox.cache.TypeCache;
import com.hientran.sohebox.constants.DBConstants;
import com.hientran.sohebox.constants.MessageConstants;
import com.hientran.sohebox.constants.enums.UserActivityTblEnum;
import com.hientran.sohebox.entity.UserActivityTbl;
import com.hientran.sohebox.entity.UserTbl;
import com.hientran.sohebox.exception.APIResponse;
import com.hientran.sohebox.repository.UserActivityRepository;
import com.hientran.sohebox.repository.UserRepository;
import com.hientran.sohebox.transformer.TypeTransformer;
import com.hientran.sohebox.transformer.UserActivityTransformer;
import com.hientran.sohebox.vo.TypeVO;
import com.hientran.sohebox.vo.UserActivityVO;
import com.hientran.sohebox.vo.UserVO;

/**
 * @author hientran
 */
@Service
@Transactional(readOnly = true)
public class UserActivityService extends BaseService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private UserActivityTransformer userActivityTransformer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TypeCache typeCache;

    @Autowired
    private TypeTransformer typeTransformer;

    /**
     * 
     * Create
     * 
     * @param vo
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public APIResponse<Long> create(UserActivityVO vo) {
        // Declare result
        APIResponse<Long> result = new APIResponse<Long>();

        // Validate input
        if (result.getStatus() == null) {
            List<String> errors = new ArrayList<>();

            // User must not null
            if (vo.getUser() == null && vo.getUserTbl() == null) {
                errors.add(
                        buildMessage(MessageConstants.FILED_EMPTY, new String[] { UserActivityTblEnum.user.name() }));
            }

            // Activity must not null
            if (vo.getActivity() == null) {
                errors.add(buildMessage(MessageConstants.FILED_EMPTY,
                        new String[] { UserActivityTblEnum.activity.name() }));
            }

            // Record error
            if (CollectionUtils.isNotEmpty(errors)) {
                result = new APIResponse<Long>(HttpStatus.BAD_REQUEST, errors);
            }
        }

        // Create activity
        if (result.getStatus() == null) {
            // Transform
            UserActivityTbl tbl = userActivityTransformer.convertToTbl(vo);

            // Set user
            if (vo.getUserTbl() != null) {
                tbl.setUser(vo.getUserTbl());
            } else {
                tbl.setUser(userRepository.findById(vo.getUser().getId()).get());
            }

            // Set activity
            TypeVO typeVO = typeCache.getType(vo.getActivity().getTypeClass(), vo.getActivity().getTypeCode());
            tbl.setActivity(typeTransformer.convertToTypeTbl(typeVO));

            // Create User
            tbl = userActivityRepository.save(tbl);

            // Set id return
            result.setData(tbl.getId());
        }

        // Return
        return result;
    }

    /**
     * Write user activity
     *
     * @param userTbl
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void recordUserActivity(UserTbl userTbl, String activity) {
        UserActivityTbl tbl = new UserActivityTbl();

        // Set user
        tbl.setUser(userTbl);

        // Set activity
        TypeVO typeVO = typeCache.getType(DBConstants.TYPE_CLASS_USER_ACTIVITY, activity);
        tbl.setActivity(typeTransformer.convertToTypeTbl(typeVO));

        // Create User
        tbl = userActivityRepository.save(tbl);
    }

    /**
     * Write user activity
     *
     * @param userVO
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void recordUserActivity(UserVO userVO, String activity) {
        UserActivityTbl tbl = new UserActivityTbl();

        // Set user
        tbl.setUser(userRepository.findById(userVO.getId()).get());

        // Set activity
        TypeVO typeVO = typeCache.getType(DBConstants.TYPE_CLASS_USER_ACTIVITY, activity);
        tbl.setActivity(typeTransformer.convertToTypeTbl(typeVO));

        // Create User
        tbl = userActivityRepository.save(tbl);
    }
}
