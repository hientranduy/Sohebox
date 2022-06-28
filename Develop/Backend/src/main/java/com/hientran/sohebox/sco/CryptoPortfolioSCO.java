package com.hientran.sohebox.sco;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hientran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoPortfolioSCO extends BaseSCO {

    private static final long serialVersionUID = -68140570073293062L;

    private SearchNumberVO id;

    private SearchNumberVO user;

    private SearchNumberVO token;

    private SearchTextVO wallet;

    private SearchTextVO starname;

}
