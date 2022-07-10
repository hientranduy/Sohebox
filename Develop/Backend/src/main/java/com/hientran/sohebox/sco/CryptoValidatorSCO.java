package com.hientran.sohebox.sco;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hientran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoValidatorSCO extends BaseSCO {

    private static final long serialVersionUID = 1L;

    private SearchNumberVO id;

    private SearchTextVO validatorAddress;

}
