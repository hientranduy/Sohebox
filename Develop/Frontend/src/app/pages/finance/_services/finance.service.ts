import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@environments/environment";

@Injectable({ providedIn: "root" })
export class FinanceService {
  constructor(private http: HttpClient) {}

  // Get currency VCB
  getCurrencyVcbRate() {
    return this.http.get(
      `${environment.soheboxUrl}/api/finance/vietcombankRate`,
    );
  }

  // Get gold price SJC
  getGoldSjcPrice() {
    return this.http.get(`${environment.soheboxUrl}/api/finance/goldSjc`);
  }
}
