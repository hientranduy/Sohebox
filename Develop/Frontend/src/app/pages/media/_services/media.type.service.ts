import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MediaType } from '@app/_common/_models/mediaType';
import { MediaTypeSCO } from '@app/_common/_sco/mediaTypeSCO';
import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class MediaTypeService {

    constructor(private http: HttpClient) {
    }

    // Search
    search(sco: MediaTypeSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/mediaTypes/search`, sco);
    }

    // Add
    create(item: MediaType) {
        return this.http.post(`${environment.soheboxUrl}/api/mediaTypes`, item);
    }

    // Delete
    delete(id: Number) {
        return this.http.delete(`${environment.soheboxUrl}/api/mediaTypes/${id}`);
    }

    // Update
    update(item: MediaType) {
        return this.http.put(`${environment.soheboxUrl}/api/mediaTypes`, item);
    }

    // Get
    get(id: number) {
        return this.http.get(`${environment.soheboxUrl}/api/mediaTypes/${id}`);
    }
}
