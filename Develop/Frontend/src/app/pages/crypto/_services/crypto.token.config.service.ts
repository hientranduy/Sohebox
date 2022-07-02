import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CryptoTokenSCO } from '@app/_common/_sco';
import { environment } from '@environments/environment';
import { CryptoTokenConfig } from '../_models';

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
