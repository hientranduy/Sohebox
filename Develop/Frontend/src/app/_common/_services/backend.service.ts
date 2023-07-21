import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Config, Type, User } from '../_models';
import { ConfigSCO, TypeSCO, UserSCO } from '../_sco';

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

  //////////
  // USER //
  //////////
  // Register user
  register(user: User) {
    return this.http.post(`${environment.soheboxUrl}/users`, user);
  }

  // Logout
  logout() {
    return this.http.post(`${environment.soheboxUrl}/users/logout`, null);
  }

  // Change password by logged user
  changePasswordByLoggedUser(user: User) {
    return this.http.put(
      `${environment.soheboxUrl}/users/changePasswordLoggedUser`,
      user,
    );
  }

  // Delete logged user
  deleteLoggedUser() {
    return this.http.delete(`${environment.soheboxUrl}/users`);
  }

  // Update user
  updateUser(user: User) {
    return this.http.put(`${environment.soheboxUrl}/users`, user);
  }

  // Search user
  searchUserStatus(sco: UserSCO) {
    return this.http.post(`${environment.soheboxUrl}/users/status`, sco);
  }

  // Search user
  searchActiveUser(sco: UserSCO) {
    return this.http.post(`${environment.soheboxUrl}/users/activeUser`, sco);
  }

  // Change private key
  changePrivatekey(user: User) {
    return this.http.put(
      `${environment.soheboxUrl}/users/changePrivateKey`,
      user,
    );
  }
}
