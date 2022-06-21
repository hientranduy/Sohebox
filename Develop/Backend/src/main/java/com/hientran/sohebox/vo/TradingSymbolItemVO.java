package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class TradingSymbolItemVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("Ticker")
    private String ticker;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Date")
    private String date;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("state")
    private String state;

    @JsonProperty("Last")
    private Double last;

    @JsonProperty("DailyChange")
    private Double dailyChange;

    @JsonProperty("DailyPercentualChange")
    private Double dailyPercentualChange;

    @JsonProperty("WeeklyChange")
    private Double weeklyChange;

    @JsonProperty("WeeklyPercentualChange")
    private Double weeklyPercentualChange;

    @JsonProperty("MonthlyChange")
    private Double monthlyChange;

    @JsonProperty("MonthlyPercentualChange")
    private Double monthlyPercentualChange;

    @JsonProperty("YearlyChange")
    private Double yearlyChange;

    @JsonProperty("YearlyPercentualChange")
    private Double yearlyPercentualChange;

    @JsonProperty("LastUpdate")
    private String lastUpdate;

    // Other field
    private CountryVO country;

    /**
     * Constructor
     *
     */
    public TradingSymbolItemVO() {
        super();
    }

    /**
     * Get symbol
     *
     * @return symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Set symbol
     *
     * @param symbol
     *            the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Get ticker
     *
     * @return ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Set ticker
     *
     * @param ticker
     *            the ticker to set
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set date
     *
     * @param date
     *            the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get type
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set type
     *
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get state
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Set state
     *
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Get last
     *
     * @return last
     */
    public Double getLast() {
        return last;
    }

    /**
     * Set last
     *
     * @param last
     *            the last to set
     */
    public void setLast(Double last) {
        this.last = last;
    }

    /**
     * Get dailyChange
     *
     * @return dailyChange
     */
    public Double getDailyChange() {
        return dailyChange;
    }

    /**
     * Set dailyChange
     *
     * @param dailyChange
     *            the dailyChange to set
     */
    public void setDailyChange(Double dailyChange) {
        this.dailyChange = dailyChange;
    }

    /**
     * Get dailyPercentualChange
     *
     * @return dailyPercentualChange
     */
    public Double getDailyPercentualChange() {
        return dailyPercentualChange;
    }

    /**
     * Set dailyPercentualChange
     *
     * @param dailyPercentualChange
     *            the dailyPercentualChange to set
     */
    public void setDailyPercentualChange(Double dailyPercentualChange) {
        this.dailyPercentualChange = dailyPercentualChange;
    }

    /**
     * Get weeklyChange
     *
     * @return weeklyChange
     */
    public Double getWeeklyChange() {
        return weeklyChange;
    }

    /**
     * Set weeklyChange
     *
     * @param weeklyChange
     *            the weeklyChange to set
     */
    public void setWeeklyChange(Double weeklyChange) {
        this.weeklyChange = weeklyChange;
    }

    /**
     * Get weeklyPercentualChange
     *
     * @return weeklyPercentualChange
     */
    public Double getWeeklyPercentualChange() {
        return weeklyPercentualChange;
    }

    /**
     * Set weeklyPercentualChange
     *
     * @param weeklyPercentualChange
     *            the weeklyPercentualChange to set
     */
    public void setWeeklyPercentualChange(Double weeklyPercentualChange) {
        this.weeklyPercentualChange = weeklyPercentualChange;
    }

    /**
     * Get monthlyChange
     *
     * @return monthlyChange
     */
    public Double getMonthlyChange() {
        return monthlyChange;
    }

    /**
     * Set monthlyChange
     *
     * @param monthlyChange
     *            the monthlyChange to set
     */
    public void setMonthlyChange(Double monthlyChange) {
        this.monthlyChange = monthlyChange;
    }

    /**
     * Get monthlyPercentualChange
     *
     * @return monthlyPercentualChange
     */
    public Double getMonthlyPercentualChange() {
        return monthlyPercentualChange;
    }

    /**
     * Set monthlyPercentualChange
     *
     * @param monthlyPercentualChange
     *            the monthlyPercentualChange to set
     */
    public void setMonthlyPercentualChange(Double monthlyPercentualChange) {
        this.monthlyPercentualChange = monthlyPercentualChange;
    }

    /**
     * Get yearlyChange
     *
     * @return yearlyChange
     */
    public Double getYearlyChange() {
        return yearlyChange;
    }

    /**
     * Set yearlyChange
     *
     * @param yearlyChange
     *            the yearlyChange to set
     */
    public void setYearlyChange(Double yearlyChange) {
        this.yearlyChange = yearlyChange;
    }

    /**
     * Get yearlyPercentualChange
     *
     * @return yearlyPercentualChange
     */
    public Double getYearlyPercentualChange() {
        return yearlyPercentualChange;
    }

    /**
     * Set yearlyPercentualChange
     *
     * @param yearlyPercentualChange
     *            the yearlyPercentualChange to set
     */
    public void setYearlyPercentualChange(Double yearlyPercentualChange) {
        this.yearlyPercentualChange = yearlyPercentualChange;
    }

    /**
     * Get lastUpdate
     *
     * @return lastUpdate
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set lastUpdate
     *
     * @param lastUpdate
     *            the lastUpdate to set
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Get country
     *
     * @return country
     */
    public CountryVO getCountry() {
        return country;
    }

    /**
     * Set country
     *
     * @param country
     *            the country to set
     */
    public void setCountry(CountryVO country) {
        this.country = country;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TradingSymbolItemVO [symbol=" + symbol + ", ticker=" + ticker + ", name=" + name + ", date=" + date
                + ", type=" + type + ", state=" + state + ", last=" + last + ", dailyChange=" + dailyChange
                + ", dailyPercentualChange=" + dailyPercentualChange + ", weeklyChange=" + weeklyChange
                + ", weeklyPercentualChange=" + weeklyPercentualChange + ", monthlyChange=" + monthlyChange
                + ", monthlyPercentualChange=" + monthlyPercentualChange + ", yearlyChange=" + yearlyChange
                + ", yearlyPercentualChange=" + yearlyPercentualChange + ", lastUpdate=" + lastUpdate + ", country="
                + country + "]";
    }

}