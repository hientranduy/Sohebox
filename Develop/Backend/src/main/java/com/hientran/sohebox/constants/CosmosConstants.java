package com.hientran.sohebox.constants;

import java.io.Serializable;

/**
 * 
 * Bittrex Constants
 *
 * @author hientran
 */
public class CosmosConstants implements Serializable {

    private static final long serialVersionUID = 1L;

    ////////////////
    // PUBLIC API //
    ////////////////

    public static final String COSMOS_DELEGATIONS = "/delegations";

    public static final String COSMOS_REWARDS = "/rewards";

    public static final String COSMOS_BANK_BALANCES = "/bank/balances";
    
    public static final String COSMOS_BANK_V1BETA1_BALANCES = "/cosmos/bank/v1beta1/balances";

    public static final String COSMOS_STAKING_DELEGATORS = "/staking/delegators";
    
    public static final String COSMOS_STAKING_V1BETA1_DELEGATION = "/cosmos/staking/v1beta1/delegations";

    public static final String COSMOS_DISTRIBUTION_DELEGATORS = "/distribution/delegators";
    
    public static final String COSMOS_DISTRIBUTION_V1BETA1_DELEGATORS = "/cosmos/distribution/v1beta1/delegators";

    public static final String COSMOS_STAKING_VALIDATORS = "/staking/validators";
    
    public static final String COSMOS_STAKING_V1BETA1_VALIDATORS = "/cosmos/staking/v1beta1/validators";

}
