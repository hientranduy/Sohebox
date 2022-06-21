package com.hientran.sohebox.constants;

import java.io.Serializable;

/**
 * 
 * Bittrex Constants
 *
 * @author hientran
 */
public class BittrexConstants implements Serializable {

    private static final long serialVersionUID = 1L;

    /////////////////
    // SERVICE URL //
    /////////////////

    public static final String BITTREX_URL = "https://bittrex.com";

    //////////////////////////////////////////////////////////////////////////////////////////
    // Follow https://bittrex.zendesk.com/hc/en-us/articles/115003723911-Developer-s-Guide-API
    //////////////////////////////////////////////////////////////////////////////////////////

    ////////////////
    // PUBLIC API //
    ////////////////

    public static final String BITTREX_PARAM_TYPE_BUY = "buy";

    public static final String BITTREX_PARAM_TYPE_SELL = "sell";

    public static final String BITTREX_PARAM_TYPE_BOTH = "both";

    public static final String BITTREX_ORDER_TYPE_BUY = "BUY";

    public static final String BITTREX_ORDER_TYPE_SELL = "SELL";

    public static final String BITTREX_FILL_TYPE_COMPLETE = "FILL";

    public static final String BITTREX_FILL_TYPE_PARTIAL = "PARTIAL_FILL";

    // Used to get the open and available trading markets.
    // Parameters: none
    public static final String BITTREX_API_GET_PAIR = "/api/v1.1/public/getmarkets";

    // Used to get all supported currencies
    // Parameters: none
    public static final String BITTREX_API_GET_CRYPTO = "/api/v1.1/public/getcurrencies";

    // Used to get the current tick values for a market.
    // Parameters: market (BTC-ETH)
    public static final String BITTREX_API_GET_TICKER = "/api/v1.1/public/getticker";

    // Used to get the last 24 hour summary of all active markets
    // Parameters: none
    public static final String BITTREX_API_GET_MARKET_SUMMARIES = "/api/v1.1/public/getmarketsummaries";

    // Used to get the last 24 hour summary of a specific market.
    // Parameters: market (BTC-ETH)
    public static final String BITTREX_API_GET_MARKET_SUMMARY = "/api/v1.1/public/getmarketsummary";

    // Used to get retrieve the order book for a given market.
    // Parameters: market (BTC-ETH)
    // Parameters: type (buy/sell/both)
    public static final String BITTREX_API_GET_ORDER_BOOK = "/api/v1.1/public/getorderbook";

    // Used to retrieve the latest trades that have occurred for a specific market
    // Parameters: market (BTC-ETH)
    public static final String BITTREX_API_GET_MARKET_HISTORY = "/api/v1.1/public/getmarkethistory";

    ///////////////
    // TRADE API //
    ///////////////

    // Place buy order
    // Parameters: apikey (abc)
    // Parameters: market (BTC-ETH)
    // Parameters: quantity (1.2)
    // Parameters: rate (1.3)
    public static final String BITTREX_API_GET_MARKET_BUY_LIMIT = "/api/v1.1/market/buylimit";

    // Place sell order
    // Parameters: apikey (abc)
    // Parameters: market (BTC-ETH)
    // Parameters: quantity (1.2)
    // Parameters: rate (1.3)
    public static final String BITTREX_API_GET_MARKET_SELL_LIMIT = "/api/v1.1/market/selllimit";

    // Cancel an order
    // Parameters: apikey (abc)
    // Parameters: uuid (123)
    public static final String BITTREX_API_GET_MARKET_CANCEL = "/api/v1.1/market/cancel";

    // Get open order
    // Parameters: apikey (abc)
    // Parameters: market (BTC-ETH)
    public static final String BITTREX_API_GET_OPEN_ORDER = "/api/v1.1/market/getopenorders";

    /////////////////
    // ACCOUNT API //
    /////////////////

    // Get all balance
    // Parameters: apikey (abc)
    public static final String BITTREX_API_GET_ACCOUNT_BALANCES = "/api/v1.1/account/getbalances";

    // Get balance a crypto
    // Parameters: apikey (abc)
    // Parameters: currency (BTC)
    public static final String BITTREX_API_GET_ACCOUNT_BALANCE = "/api/v1.1/account/getbalance";

    // Get wallet of crypto
    // Parameters: apikey (abc)
    // Parameters: currency (BTC)
    public static final String BITTREX_API_GET_WALLET_ADDRESS = "/api/v1.1/account/getdepositaddress";

    // Fund withdraw
    // Parameters: apikey (abc)
    // Parameters: currency (BTC)
    // Parameters: quantity (20.40)
    // Parameters: address (abc)
    // Parameters: paymentid (used for CryptoNotes/BitShareX/Nxt/XRP and any other coin that has a
    // memo/message/tag/paymentid
    // option)
    public static final String BITTREX_API_WITHDRAW = "/api/v1.1/account/withdraw";

    // Get order by uuid
    // Parameters: apikey (abc)
    // Parameters: uuid (0cb4c4e4-bdc7-4e13-8c13-430e587d2cc1)
    public static final String BITTREX_API_GET_ORDER = "/api/v1.1/account/getorder";

    // Get order history
    // Parameters: apikey (abc)
    // Parameters: market (BTC-ETH)
    public static final String BITTREX_API_GET_ORDER_HISTORY = "/api/v1.1/account/getorderhistory";

    // Fund withdraw history
    // Parameters: apikey (abc)
    // Parameters: currency (BTC)
    public static final String BITTREX_API_GET_WITHDRAW_HISORY = "/api/v1.1/account/getwithdrawalhistory";

    // Fund deposit history
    // Parameters: apikey (abc)
    // Parameters: currency (BTC)
    public static final String BITTREX_API_GET_DEPOSIT_HISORY = "/api/v1.1/account/getdeposithistory";
}
