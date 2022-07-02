import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { CryptoTokenConfig } from '../_models';
import { CryptoTokenSCO } from '../_sco/cryptoTokenSCO';

@Injectable({ providedIn: 'root' })
export class CryptoTokenConfigService {

    constructor(private http: HttpClient) {
    }

    // Add
    add(item: CryptoTokenConfig) {
        return this.http.post(`${environment.soheboxUrl}/api/cryptoTokenConfig`, item);
    }

    // Update
    update(item: CryptoTokenConfig) {
        return this.http.put(`${environment.soheboxUrl}/api/cryptoTokenConfig`, item);
    }

    // Search
    search(sco: CryptoTokenSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/cryptoTokenConfig/search`, sco);
    }

    // Get by id
    getById(id: number) {
        return this.http.get(`${environment.soheboxUrl}/api/cryptoTokenConfig/${id}`);
    }
}
