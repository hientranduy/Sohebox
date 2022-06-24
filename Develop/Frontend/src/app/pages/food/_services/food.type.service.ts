import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FoodType } from '@app/_common/_models/foodType';
import { FoodTypeSCO } from '@app/_common/_sco/foodTypeSCO';
import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class FoodTypeService {

    constructor(private http: HttpClient) {
    }

    // Search
    search(sco: FoodTypeSCO) {
        return this.http.post(`${environment.soheboxUrl}/api/foodTypes/search`, sco);
    }

    // Add
    create(item: FoodType) {
        return this.http.post(`${environment.soheboxUrl}/api/foodTypes`, item);
    }

    // Delete
    delete(id: Number) {
        return this.http.delete(`${environment.soheboxUrl}/api/foodTypes/${id}`);
    }

    // Update
    update(item: FoodType) {
        return this.http.put(`${environment.soheboxUrl}/api/foodTypes`, item);
    }

    // Get
    get(id: number) {
        return this.http.get(`${environment.soheboxUrl}/api/foodTypes/${id}`);
    }
}
