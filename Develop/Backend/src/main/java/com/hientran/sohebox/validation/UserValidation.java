package com.hientran.sohebox.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * User validation
 *
 * @author hientran
 */
@Component
public class UserValidation extends BaseValidation {

    private static final long serialVersionUID = 1L;

    public Boolean isInvalidPassword(String input) {
        Boolean result = false;
        if (StringUtils.isBlank(input)) {
            result = true;
        } else {
            if (input.length() < 6) {
                result = true;
            }
        }
        return result;
    }
}
