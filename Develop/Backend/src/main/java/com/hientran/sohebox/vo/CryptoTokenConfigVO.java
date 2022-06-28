package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoTokenConfigVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String tokenCode;

    private String tokenName;

    private String iconUrl;

    private String nodeUrl;

    private String denom;

    private String addressPrefix;
}