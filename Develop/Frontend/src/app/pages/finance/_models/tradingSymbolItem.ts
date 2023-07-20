import { Country } from '@app/_common/_models';

export class TradingSymbolItem {
  Symbol: String;
  Ticker: String;
  Name: String;
  Date: String;
  Type: String;
  state: String;
  Last: number;
  DailyChange: number;
  DailyPercentualChange: number;
  WeeklyChange: number;
  WeeklyPercentualChange: number;
  MonthlyChange: number;
  MonthlyPercentualChange: number;
  YearlyChange: number;
  YearlyPercentualChange: number;
  LastUpdate: String;
  country: Country;
}
