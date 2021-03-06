import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CryptoPortfolioSCO } from '@app/pages/crypto/_sco/cryptoPortfolioSCO';
import { environment } from '@environments/environment';
import { CryptoPortfolio } from '../_models';
import { CryptoPortfolioHistorySCO } from '../_sco';

@Injectable({ providedIn: 'root' })
export class CryptoPortfolioService {

    constructor(private http: HttpClient) {
    }

    // Add
    add(item: CryptoPortfolio) {
        return this.http.post(`${environment.soheboxUrl}/api/cryptoPortfolio`, item);
    }

    // Update
    update(item: CryptoPortfolio) {
        return this.http.put(`${environment.soheboxUrl}/api/cryptoPortfolio`, item);
    }

    // Search
    search(sco: CryptoPortfolioSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/cryptoPortfolio/search`, sco);
    }

    // Delete
    delete(id: Number) {
        return this.http.delete(`${environment.soheboxUrl}/api/cryptoPortfolio/${id}`);
    }

    // Get by ID
    getById(id: number) {
        return this.http.get(`${environment.soheboxUrl}/api/cryptoPortfolio/${id}`);
    }

    // Portfolio Summary
    getPortfolioSummary(sco: CryptoPortfolioHistorySCO) {
        return this.http.post(`${environment.soheboxUrl}/api/cryptoPortfolioHistory`, sco);
    }
}
