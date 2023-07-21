import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Config, Type } from '../_models';
import { ConfigSCO, TypeSCO } from '../_sco';

@Injectable({ providedIn: 'root' })
export class BackendService {
  constructor(private http: HttpClient) {}

  //////////
  // TYPE //
  //////////
  // Add
  createType(type: Type) {
    return this.http.post(`${environment.soheboxUrl}/api/types`, type);
  }

  // Update
  updateType(type: Type) {
    return this.http.put(`${environment.soheboxUrl}/api/types`, type);
  }

  // Search
  searchType(sco: TypeSCO) {
    return this.http.post(`${environment.soheboxUrl}/api/types/search`, sco);
  }

  ////////////
  // CONFIG //
  ////////////
  // Add
  createConfig(item: Config) {
    return this.http.post(`${environment.soheboxUrl}/api/configs`, item);
  }

  // Update
  updateConfig(item: Config) {
    return this.http.put(`${environment.soheboxUrl}/api/configs`, item);
  }
  // Search
  searchConfig(sco: ConfigSCO) {
    return this.http.post(`${environment.soheboxUrl}/api/configs/search`, sco);
  }
}
