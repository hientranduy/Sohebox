import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class TradingService {
  constructor(private http: HttpClient) {}

  // Search oil price (WTI and Brent)
  getOilPrice() {
    return this.http.get(
      `${environment.soheboxUrl}/api/tradingeconomics/oilprice`,
    );
  }

  // Search stock price
  getStockPrice() {
    return this.http.get(
      `${environment.soheboxUrl}/api/tradingeconomics/stockprice`,
    );
  }
}
