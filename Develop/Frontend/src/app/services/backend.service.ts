import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '@app/models/account';
import { CryptoPortfolio } from '@app/models/cryptoPortfolio';
import { CryptoTokenConfig } from '@app/models/cryptoTokenConfig';
import { DownloadFile } from '@app/models/downloadFile';
import { English } from '@app/models/english';
import { EnglishLearnRecord } from '@app/models/englishLearnRecord';
import { EnglishType } from '@app/models/englishType';
import { EnglishUserGrade } from '@app/models/englishUserGrade';
import { Type } from '@app/models/type';
import { User } from '@app/models/user';
import { AccountSCO } from '@app/scos/accountSCO';
import { CryptoPortfolioHistorySCO } from '@app/scos/cryptoPortfolioHistorySCO';
import { CryptoPortfolioSCO } from '@app/scos/cryptoPortfolioSCO';
import { CryptoTokenConfigSCO } from '@app/scos/cryptoTokenConfigSCO';
import { EnglishLearnRecordSCO } from '@app/scos/englishLearnRecordSCO';
import { EnglishLearnReportSCO } from '@app/scos/englishLearnReportSCO';
import { EnglishSCO } from '@app/scos/englishSCO';
import { EnglishTypeSCO } from '@app/scos/englishTypeSCO';
import { EnglishUserGradeSCO } from '@app/scos/englishUserGradeSCO';
import { TypeSCO } from '@app/scos/typeSCO';
import { environment } from '@environments/environment';

@Injectable({ providedIn: 'root' })
export class BackendService {
  constructor(private http: HttpClient) { }

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

  //////////
  // USER //
  //////////
  // Register user
  register(user: User) {
    return this.http.post(`${environment.soheboxUrl}/users`, user);
  }

  // Update user
  updateUser(user: User) {
    return this.http.put(`${environment.soheboxUrl}/users`, user);
  }

  // Logout
  logout() {
    return this.http.post(`${environment.soheboxUrl}/users/logout`, null);
  }

  // Change password
  changePasswordByLoggedUser(user: User) {
    return this.http.put(
      `${environment.soheboxUrl}/users/changePasswordLoggedUser`,
      user,
    );
  }

  // Delete user
  deleteLoggedUser() {
    return this.http.delete(`${environment.soheboxUrl}/users`);
  }

  // Change private key
  changePrivatekey(user: User) {
    return this.http.put(
      `${environment.soheboxUrl}/users/changePrivateKey`,
      user,
    );
  }

  /////////////
  // ACCOUNT //
  /////////////
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

  // Get clear password
  showPassword(account: Account) {
    return this.http.post(
      `${environment.soheboxUrl}/api/accounts/showPassword`,
      account,
    );
  }

  //////////////////////
  // CRYPTO PORTFOLIO //
  //////////////////////
  // Add
  addCryptoPortfolio(item: CryptoPortfolio) {
    return this.http.post(
      `${environment.soheboxUrl}/api/cryptoPortfolio`,
      item,
    );
  }

  // Update
  updateCryptoPortfolio(item: CryptoPortfolio) {
    return this.http.put(`${environment.soheboxUrl}/api/cryptoPortfolio`, item);
  }

  // Search
  searchCryptoPortfolio(sco: CryptoPortfolioSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/cryptoPortfolio/search`,
      sco,
    );
  }

  // Delete
  deleteCryptoPortfolio(id: Number) {
    return this.http.delete(
      `${environment.soheboxUrl}/api/cryptoPortfolio/${id}`,
    );
  }

  // Portfolio Summary
  getPortfolioSummary(sco: CryptoPortfolioHistorySCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/cryptoPortfolioHistory`,
      sco,
    );
  }

  //////////////////////
  // CRYPTO TOKEN CONFIG //
  //////////////////////
  // Add
  addCryptoTokenConfig(item: CryptoTokenConfig) {
    return this.http.post(
      `${environment.soheboxUrl}/api/cryptoTokenConfig`,
      item,
    );
  }

  // Update
  updateCryptoTokenConfig(item: CryptoTokenConfig) {
    return this.http.put(
      `${environment.soheboxUrl}/api/cryptoTokenConfig`,
      item,
    );
  }

  // Search
  searchCryptoTokenConfig(sco: CryptoTokenConfigSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/cryptoTokenConfig/search`,
      sco,
    );
  }

  /////////////
  // ENGLISH //
  /////////////
  // Add english
  addEnglish(english: English) {
    return this.http.post(`${environment.soheboxUrl}/api/english`, english);
  }

  // Update english
  updateEnglish(english: English) {
    return this.http.put(`${environment.soheboxUrl}/api/english`, english);
  }

  // Search engish
  searchEnglish(sco: EnglishSCO) {
    return this.http.post(`${environment.soheboxUrl}/api/english/search`, sco);
  }

  // Search engish
  searchLowLearnEnglish(sco: EnglishSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/english/searchLowLearn`,
      sco,
    );
  }

  // Search engish
  searchTopLearn(sco: EnglishLearnReportSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/english/englishTopLearn`,
      sco,
    );
  }

  // Add learn record
  addLearnRecord(englishLearnRecord: EnglishLearnRecord) {
    return this.http.put(
      `${environment.soheboxUrl}/api/english/englishLearnRecord/add`,
      englishLearnRecord,
    );
  }

  // Search learn
  searchLearnRecord(sco: EnglishLearnRecordSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/english/englishLearnRecord/search`,
      sco,
    );
  }

  // Search learn report
  searchLearnReport(sco: EnglishLearnReportSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/english/englishLearnReport/search`,
      sco,
    );
  }

  // Download mp3 file
  downloadFileMp3(vo: DownloadFile) {
    return this.http.post(
      `${environment.soheboxUrl}/api/english/downloadFileMp3`,
      vo,
    );
  }

  // Update user english level
  updateEnglishLevel(vo: EnglishUserGrade) {
    return this.http.put(
      `${environment.soheboxUrl}/api/english/englishUserGrade/set`,
      vo,
    );
  }

  // Search user english level
  searchEnglishLevel(sco: EnglishUserGradeSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/english/englishUserGrade/search`,
      sco,
    );
  }

  //////////////////
  // ENGLISH TYPE //
  //////////////////
  // Search
  searchEnglishType(sco: EnglishTypeSCO) {
    return this.http.post(
      `${environment.soheboxUrl}/api/englishTypes/search`,
      sco,
    );
  }

  // Update
  updateEnglishType(item: EnglishType) {
    return this.http.put(`${environment.soheboxUrl}/api/englishTypes`, item);
  }
}
