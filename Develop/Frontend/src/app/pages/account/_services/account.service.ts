import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AccountSCO } from '@app/_common/_sco';
import { environment } from '@environments/environment';
import { Account } from '../_models';

@Injectable({ providedIn: 'root' })
export class AccountService {
  constructor(private http: HttpClient) {}

  // Register account
  createAccount(account: Account) {
    return this.http.post(`${environment.soheboxUrl}/api/accounts`, account);
  }

  // Search account
  searchAccount(sco: AccountSCO) {
    return this.http.post(`${environment.soheboxUrl}/api/accounts/search`, sco);
  }

  // Delete account
  deleteAccount(id: Number) {
    return this.http.delete(`${environment.soheboxUrl}/api/accounts/${id}`);
  }

  // Update account
  updateAccount(account: Account) {
    return this.http.put(`${environment.soheboxUrl}/api/accounts`, account);
  }

  // Get account
  getAccount(id: number) {
    return this.http.get(`${environment.soheboxUrl}/api/accounts/${id}`);
  }

  // Get clear password
  showPassword(account: Account) {
    return this.http.post(
      `${environment.soheboxUrl}/api/accounts/showPassword`,
      account,
    );
  }
}
