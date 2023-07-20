import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "@environments/environment";
import { Type } from "../_models";
import { TypeSCO } from "../_sco";

@Injectable({ providedIn: "root" })
export class TypeService {
  constructor(private http: HttpClient) {}

  // Add new type
  create(type: Type) {
    return this.http.post(`${environment.soheboxUrl}/api/types`, type);
  }

  // Update type
  update(type: Type) {
    return this.http.put(`${environment.soheboxUrl}/api/types`, type);
  }

  // Search type
  search(sco: TypeSCO) {
    return this.http.post(`${environment.soheboxUrl}/api/types/search`, sco);
  }
}
