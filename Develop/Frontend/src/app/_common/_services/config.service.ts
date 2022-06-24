import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Config } from '../_models';
import { ConfigSCO } from '../_sco';

@Injectable({ providedIn: 'root' })
export class ConfigService {

    constructor(private http: HttpClient) {
    }

    // Search
    search(sco: ConfigSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/configs/search`, sco);
    }

    // Add
    create(item: Config) {
        return this.http.post(`${environment.soheboxUrl}/api/configs`, item);
    }

    // Delete
    delete(id: Number) {
        return this.http.delete(`${environment.soheboxUrl}/api/configs/${id}`);
    }

    // Update
    update(item: Config) {
        return this.http.put(`${environment.soheboxUrl}/api/configs`, item);
    }

    // Get
    get(id: number) {
        return this.http.get(`${environment.soheboxUrl}/api/configs/${id}`);
    }
}
