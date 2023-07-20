import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {
  EnglishLearnRecordSCO,
  EnglishLearnReportSCO,
  EnglishSCO,
  EnglishUserGradeSCO,
} from "@app/_common/_sco";
import { environment } from "@environments/environment";
import {
  DownloadFile,
  English,
  EnglishLearnRecord,
  EnglishUserGrade,
} from "../_model";

@Injectable({ providedIn: "root" })
export class EnglishService {
  constructor(private http: HttpClient) {}

  // Add english
  addWord(english: English) {
    return this.http.post(`${environment.soheboxUrl}/api/english`, english);
  }

  // Update english
  updateWord(english: English) {
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

  // Get engish by id
  getEnglish(id: number) {
    return this.http.get(`${environment.soheboxUrl}/api/english/${id}`);
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
}
