import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/apiReponse';
import { EnglishType } from '@app/models/englishType';
import { SearchText } from '@app/scos/core_sco/searchText';
import { Sorter } from '@app/scos/core_sco/sorter';
import { EnglishSCO } from '@app/scos/englishSCO';
import { EnglishTypeSCO } from '@app/scos/englishTypeSCO';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  templateUrl: './add-word-dialog.component.html',
})
export class AddWordDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  // Input value
  gradeValue: string;
  categoryValue: string;
  learnDayValue: string;
  keyWord: string;
  explanationEnForm: string;
  explanationVnForm: string;
  imageFile: any;
  imageExtention: String;

  // Filter grade
  filteredGrades: Observable<EnglishType[]>;

  // Filter category
  filteredCategories: Observable<EnglishType[]>;

  // Filter learn day
  filteredLearnDays: Observable<EnglishType[]>;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : grade
  gradeControl = new FormControl('', [Validators.required]);

  // Field : category
  categoryControl = new FormControl('', [Validators.required]);

  // Field : learn day
  learnDayControl = new FormControl('', [Validators.required]);

  // Field : key word
  wordFormControl = new FormControl('', [englishKeyWord, Validators.required]);

  // Field : Explanation English
  explanationEnFormControl = new FormControl('', []);

  // Field : Explanation Vietnamese
  explanationVnFormControl = new FormControl('', []);

  ngOnInit() {
    this.getFilterGrades();
    this.getFilterCategories();
    this.getFilterLearnDays();
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
        const grades: EnglishType[] = typeResponse.data.elements;
        if (typeResponse.data.elements != null) {
          this.filteredGrades = this.gradeControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              grades.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toLowerCase()),
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
   * Get list of category
   */
  getFilterCategories() {
    // Show loading
    this.spinner.show();

    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'ENGLISH_CATEGORY';
    const typeSCO = new EnglishTypeSCO();
    typeSCO.typeClass = typeClass;
    typeSCO.deleteFlag = false;

    // Get list type
    this.backendService.searchEnglishType(typeSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishType> = responseAPi;
        const categories: EnglishType[] = typeResponse.data.elements;
        if (typeResponse.data.elements != null) {
          // Prepare data for account types
          this.filteredCategories = this.categoryControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              categories.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toLowerCase()),
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
    englishTypeSCO.deleteFlag = false;

    // Show loading
    this.spinner.show();

    // Get list type
    this.backendService.searchEnglishType(englishTypeSCO).subscribe(
      (data) => {
        // Get dataa
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishType> = responseAPi;
        const learnDays: EnglishType[] = typeResponse.data.elements;
        if (typeResponse.data.elements != null) {
          // Prepare data for account types
          this.filteredLearnDays = this.learnDayControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              learnDays.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toLowerCase()),
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

  //////////////////
  // Browse image //
  //////////////////
  preview(files) {
    // Return if have no image
    if (files.length === 0) {
      return;
    }

    // Return if not an image
    const mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = null;
      this.messageError = 'Only images are supported.';
      return;
    }

    // Return if file is too big
    if (files[0].size > 300000) {
      this.message = null;
      this.messageError = 'Max image size is 300kb, please update image';
      return;
    }

    // Preview
    const reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imageFile = reader.result;

      // Set image extention
      this.imageExtention = files[0].name.split('.').pop();
    };

    // Remove error message
    this.messageError = null;
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
    if (this.isFormValid()) {
      const categoryTypeVO: EnglishType = new EnglishType();
      categoryTypeVO.typeCode = this.categoryValue;
      const gradeTypeVO: EnglishType = new EnglishType();
      gradeTypeVO.typeCode = this.gradeValue;
      const learnDayTypeVO: EnglishType = new EnglishType();
      learnDayTypeVO.typeCode = this.learnDayValue;

      // Prepare adding word form
      const addWordForm: FormGroup = this.formBuilder.group({
        category: [categoryTypeVO],
        learnDay: [learnDayTypeVO],
        keyWord: [this.keyWord],
        explanationEn: [this.explanationEnForm],
        explanationVn: [this.explanationVnForm],
        imageFile: [this.imageFile],
        imageExtention: [this.imageExtention],
        vusGrade: [gradeTypeVO],
      });

      // Show loading
      this.spinner.show();

      // Add account
      this.backendService.addEnglish(addWordForm.value).subscribe(
        (data) => {
          // Send success toast message
          this.toastr.success(
            'New Word ' + this.keyWord + ' is added successful',
          );

          // Hide loading
          this.spinner.hide();

          // Close dialog
          this.activeModal.close(true);
        },
        (error) => {
          // Hide loading
          this.spinner.hide();

          // Send error message to dialog
          this.message = null;
          this.messageError = error;
        },
      );
    } else {
      this.message = null;
      this.messageError = 'Invalid fields, please check your input';
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;

    if (this.gradeControl.status === 'INVALID') {
      result = false;
    }
    if (this.categoryControl.status === 'INVALID') {
      result = false;
    }
    if (this.learnDayControl.status === 'INVALID') {
      result = false;
    }
    if (this.wordFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.explanationEnFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.explanationVnFormControl.status === 'INVALID') {
      result = false;
    }

    if (!this.imageFile) {
      result = false;
    }
    return result;
  }
}

/**
 * Function validator english ky work
 *  - Must be not existed in database
 *
 */
function englishKeyWord(control: FormControl) {
  const keyWord = control.value;

  // Validate keyword if have input
  if (keyWord) {
    // Search keyword
    const keywordSearch = new SearchText();
    keywordSearch.eq = keyWord;
    const sco = new EnglishSCO();
    sco.keyWord = keywordSearch;

    // this.englishService.searchEnglish(sco)
    //   .subscribe(data => {
    //     const responseAPi: any = data;
    //     const typeResponse: ApiReponse<English> = responseAPi;
    //     if (typeResponse.data != null) {
    //       // Invalid because new work is existed
    //       return {
    //         englishIsExisted: {
    //           parsedUrln: keyWord
    //         }
    //       };
    //     } else {
    //       // New english is not existed
    //     }
    //   }, error => {
    //     this.toastr.info('error:' + error);
    //   });
  }
  return null;
}
