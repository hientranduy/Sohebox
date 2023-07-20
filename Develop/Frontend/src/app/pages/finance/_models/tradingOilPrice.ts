import { TradingHistoryItem } from "./tradingHistoryItem";
import { TradingSymbolItem } from "./tradingSymbolItem";

export class TradingOilPrice {
  symbolCL1: TradingSymbolItem;
  symbolCO1: TradingSymbolItem;
  historyCL1: Array<TradingHistoryItem>;
  historyCO1: Array<TradingHistoryItem>;
}
