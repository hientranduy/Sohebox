import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FoodSCO } from '@app/_common/_sco/foodSCO';
import { environment } from '@environments/environment';
import { Food } from '@app/_common/_models';

@Injectable({ providedIn: 'root' })
export class FoodService {
  constructor(private http: HttpClient) {}

  // Add
  addFood(food: Food) {
    return this.http.post(`${environment.soheboxUrl}/api/food`, food);
  }

  // Update
  editFood(food: Food) {
    return this.http.put(`${environment.soheboxUrl}/api/food`, food);
  }

  // Search
  searchFood(sco: FoodSCO) {
    return this.http.post(`${environment.soheboxUrl}/api/food/search`, sco);
  }

  // Get by id
  getFood(id: number) {
    return this.http.get(`${environment.soheboxUrl}/api/food/${id}`);
  }
}
