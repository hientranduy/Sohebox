import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
import { EnglishType } from '@app/models/englishType';
import { EnglishUserGrade } from '@app/models/englishUserGrade';
import { User } from '@app/models/user';
import { SearchNumber } from '@app/scos/core_sco/searchNumber';
import { SearchText } from '@app/scos/core_sco/searchText';
import { Sorter } from '@app/scos/core_sco/sorter';
import { EnglishTypeSCO } from '@app/scos/englishTypeSCO';
import { EnglishUserGradeSCO } from '@app/scos/englishUserGradeSCO';
import { AuthenticationService } from '@app/services/authentication.service';
import { BackendService } from '@app/services/backend.service';
import { RequireMatchForm } from '@app/services/requireMatchForm';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-update-english-level-dialog',
  templateUrl: './update-english-level-dialog.component.html',
})
export class UpdateEnglishLevelDialogComponent implements OnInit {
  constructor(
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );
  }

  currentUser: User;
  englishUserGrade: EnglishUserGrade;

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  // Field grade
  gradeSelect: EnglishType;
  filteredGrades: Observable<EnglishType[]>;
  gradeControl = new FormControl('', [Validators.required, RequireMatchForm]);

  // Field learn day
  learnDaySelect: EnglishType;
  filteredLearnDays: Observable<EnglishType[]>;
  learnDayControl = new FormControl('', [
    Validators.required,
    RequireMatchForm,
  ]);

  ngOnInit() {
    // Get all grade/learn day
    this.getFilterGrades();
    this.getFilterLearnDays();

    // Get user english level
    this.getCurrentUserlevel();
  }
  public displayGrade(grade: EnglishType) {
    if (grade) {
      return grade.typeName;
    }
    return null;
  }
  public displayLearnDay(learnDay: EnglishType) {
    if (learnDay) {
      return learnDay.typeName;
    }
    return null;
  }

  /**
   * Get list of grade
   */
  getFilterGrades() {
    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'ENGLISH_VUS_GRADE';
    const sorters: Array<Sorter> = [];
    sorters.push(new Sorter('id', 'ASC'));

    const typeSCO = new EnglishTypeSCO();
    typeSCO.typeClass = typeClass;
    typeSCO.deleteFlag = false;
    typeSCO.sorters = sorters;

    // Show loading
    this.spinner.show();

    // Get list type
    this.backendService.searchEnglishType(typeSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishType> = responseAPi;
        if (typeResponse.data != null) {
          const grades: EnglishType[] = typeResponse.data.elements;
          this.filteredGrades = this.gradeControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              grades.filter((valueFilter) =>
                valueFilter.typeName
                  .toLowerCase()
                  .includes(value.toString().toLowerCase()),
              ),
            ),
          );
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  /**
   * Get list of learn day
   */
  getFilterLearnDays() {
    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'ENGLISH_LEARN_DAY';
    const englishTypeSCO = new EnglishTypeSCO();
    englishTypeSCO.typeClass = typeClass;

    // Show loading
    this.spinner.show();

    // Get list type
    this.backendService.searchEnglishType(englishTypeSCO).subscribe(
      (data) => {
        // Get dataa
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishType> = responseAPi;
        if (typeResponse.data != null) {
          const learnDays: EnglishType[] = typeResponse.data.elements;
          this.filteredLearnDays = this.learnDayControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              learnDays.filter((valueFilter) =>
                valueFilter.typeName
                  .toLowerCase()
                  .includes(value.toString().toLowerCase()),
              ),
            ),
          );
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  /**
   * Get list of grade
   */
  getCurrentUserlevel() {
    // Prepare search condition
    const userId = new SearchNumber();
    userId.eq = this.currentUser.id;

    const englishUserGradeSCO = new EnglishUserGradeSCO();
    englishUserGradeSCO.userId = userId;

    this.spinner.show();

    // Search
    this.backendService.searchEnglishLevel(englishUserGradeSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishUserGrade> = responseAPi;
        const englishLevel: EnglishUserGrade[] = typeResponse.data.elements;
        if (typeResponse.data.elements != null) {
          this.englishUserGrade = englishLevel[0];
          this.gradeSelect = this.englishUserGrade.vusGrade;
          this.learnDaySelect = this.englishUserGrade.learnDay;
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  /////////////////////////////////////
  // FORM BUTTON CONTROL             //
  /////////////////////////////////////
  /**
   * Click decline button
   */
  public decline() {
    this.activeModal.close(false);
  }

  /**
   * Click dismiss button
   */
  public dismiss() {
    this.activeModal.dismiss();
  }

  /**
   * Click accept button
   */
  public accept() {
    switch (true) {
      // Case data is invalid
      case !this.isFormValid():
        this.message = null;
        this.messageError = 'Invalid fields, please check your input';
        break;

      // Case data is unchanged
      case !this.isHaveUpdateValue():
        // Send warning toast message
        this.toastr.warning(
          'Skip update account because the value is not changed',
        );

        // Close dialog as cancel
        this.activeModal.close(false);
        break;

      // Case ok
      default:
        // Prepare
        const gradeTypeVO: EnglishType = new EnglishType();
        gradeTypeVO.typeCode = this.gradeSelect.typeCode;
        const learnDayTypeVO: EnglishType = new EnglishType();
        learnDayTypeVO.typeCode = this.learnDaySelect.typeCode;

        // Prepare vo
        const englishUserGrade: EnglishUserGrade = new EnglishUserGrade();
        englishUserGrade.user = this.currentUser;
        englishUserGrade.vusGrade = gradeTypeVO;
        englishUserGrade.learnDay = learnDayTypeVO;

        // Show loading
        this.spinner.show();

        // Edit word
        this.backendService.updateEnglishLevel(englishUserGrade).subscribe(
          (data) => {
            // Send success toast message
            this.toastr.success('Your new level is updated successful');

            // Hide loading
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);
          },
          (error) => {
            // Send error toast message
            this.toastr.error(error);

            // Hide loading
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(false);
          },
        );
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;
    if (this.gradeControl.status === 'INVALID') {
      result = false;
    }
    if (this.learnDayControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }

  // Have any update infos
  public isHaveUpdateValue() {
    let result = false;
    if (
      this.englishUserGrade != null &&
      this.englishUserGrade.vusGrade != null
    ) {
      if (
        this.gradeSelect.typeCode !== this.englishUserGrade.vusGrade.typeCode
      ) {
        result = true;
      }
    } else {
      if (this.gradeSelect.typeCode != null) {
        result = true;
      }
    }

    if (
      this.englishUserGrade != null &&
      this.englishUserGrade.learnDay != null
    ) {
      if (
        this.learnDaySelect.typeCode !== this.englishUserGrade.learnDay.typeCode
      ) {
        result = true;
      }
    } else {
      if (this.learnDaySelect.typeCode != null) {
        result = true;
      }
    }

    return result;
  }
}
