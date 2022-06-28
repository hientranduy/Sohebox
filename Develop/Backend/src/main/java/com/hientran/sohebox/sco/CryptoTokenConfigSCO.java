package com.hientran.sohebox.sco;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hientran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoTokenConfigSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchTextVO tokenCode;

    private SearchTextVO tokenName;

    private SearchTextVO iconUrl;

    private SearchTextVO nodeUrl;

    private SearchTextVO denom;

    private SearchTextVO addressPrefix;
}
