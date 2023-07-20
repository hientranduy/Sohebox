import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EnglishType } from '@app/_common/_models';
import { EnglishTypeSCO } from '@app/_common/_sco';
import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class EnglishTypeService {
  constructor(private http: HttpClient) {}

  // Search
  search(sco: EnglishTypeSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/englishTypes/search`,
      sco,
    );
  }

  // Add new
  create(item: EnglishType) {
    return this.http.post(`${environment.soheboxUrl}/api/englishTypes`, item);
  }

  // Delete
  delete(id: Number) {
    return this.http.delete(`${environment.soheboxUrl}/api/englishTypes/${id}`);
  }

  // Update
  update(item: EnglishType) {
    return this.http.put(`${environment.soheboxUrl}/api/englishTypes`, item);
  }

  // Get
  get(id: number) {
    return this.http.get(`${environment.soheboxUrl}/api/englishTypes/${id}`);
  }
}
