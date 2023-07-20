import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class QuandlService {
  constructor(private http: HttpClient) {}

  // Search WTI oil price
  getWTIOilPrices() {
    return this.http.get(`${environment.soheboxUrl}/api/quandl/OPECORB`);
  }
}
