import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse, EnglishType } from '@app/_common/_models';
import { EnglishTypeSCO } from '@app/_common/_sco';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { English } from '../_model';
import { EnglishService, EnglishTypeService } from '../_services';

@Component({
  selector: 'app-edit-word-dialog',
  templateUrl: './edit-word-dialog.component.html',
  styleUrls: ['./edit-word-dialog.component.css'],
})
export class EditWordDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private englishService: EnglishService,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private englishTypeService: EnglishTypeService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() english: English;

  // Input value
  categoryValue: string;
  learnDayValue: string;
  keyWord: string;
  explanationEnForm: string;
  explanationVnForm: string;
  imageFile: any;
  imageExtention: String;
  gradeValue: string;

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
  wordFormControl = new FormControl('', [Validators.required]);

  // Field : Explanation English
  explanationEnFormControl = new FormControl('', []);

  // Field : Explanation Vietnamese
  explanationVnFormControl = new FormControl('', []);

  ngOnInit() {
    // Set old data
    if (this.english.category != null) {
      this.categoryValue = this.english.category.typeCode;
    }
    if (this.english.learnDay != null) {
      this.learnDayValue = this.english.learnDay.typeCode;
    }
    this.keyWord = this.english.keyWord;
    this.explanationEnForm = this.english.explanationEn;
    this.explanationVnForm = this.english.explanationVn;
    if (this.english.vusGrade != null) {
      this.gradeValue = this.english.vusGrade.typeCode;
    }

    // Get all grade/category/learn day
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
    this.englishTypeService.search(typeSCO).subscribe(
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
    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'ENGLISH_CATEGORY';
    const typeSCO = new EnglishTypeSCO();
    typeSCO.typeClass = typeClass;
    typeSCO.deleteFlag = false;

    // Show loading
    this.spinner.show();

    // Get list type
    this.englishTypeService.search(typeSCO).subscribe(
      (data) => {
        // Get dataa
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
    this.englishTypeService.search(englishTypeSCO).subscribe(
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
    if (files[0].size > 250000) {
      this.message = null;
      this.messageError = 'Max image size is 200kb, please update image';
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
        // Prepare adding english form
        const categoryTypeVO: EnglishType = new EnglishType();
        categoryTypeVO.typeCode = this.categoryValue;
        const gradeTypeVO: EnglishType = new EnglishType();
        gradeTypeVO.typeCode = this.gradeValue;
        const learnDayTypeVO: EnglishType = new EnglishType();
        learnDayTypeVO.typeCode = this.learnDayValue;

        // Prepare adding word form
        let editWordForm: FormGroup;
        if (this.imageFile != null) {
          editWordForm = this.formBuilder.group({
            category: [categoryTypeVO],
            learnDay: [learnDayTypeVO],
            keyWord: [this.keyWord],
            explanationEn: [this.explanationEnForm],
            explanationVn: [this.explanationVnForm],
            imageFile: [this.imageFile],
            imageExtention: [this.imageExtention],
            vusGrade: [gradeTypeVO],
          });
        } else {
          editWordForm = this.formBuilder.group({
            category: [categoryTypeVO],
            learnDay: [learnDayTypeVO],
            keyWord: [this.keyWord],
            explanationEn: [this.explanationEnForm],
            explanationVn: [this.explanationVnForm],
            vusGrade: [gradeTypeVO],
          });
        }

        // Show loading
        this.spinner.show();

        // Edit word
        this.englishService.updateWord(editWordForm.value).subscribe(
          (data) => {
            // Send success toast message
            this.toastr.success(
              'The Word ' + this.keyWord + ' is updated successful',
            );

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
    return result;
  }

  // Have any update infos
  public isHaveUpdateValue() {
    let result = false;
    if (this.english.vusGrade != null) {
      if (this.gradeValue !== this.english.vusGrade.typeCode) {
        result = true;
      }
    } else {
      if (this.gradeValue != null) {
        result = true;
      }
    }

    if (this.english.category != null) {
      if (this.categoryValue !== this.english.category.typeCode) {
        result = true;
      }
    } else {
      if (this.categoryValue != null) {
        result = true;
      }
    }

    if (this.english.learnDay != null) {
      if (this.learnDayValue !== this.english.learnDay.typeCode) {
        result = true;
      }
    } else {
      if (this.learnDayValue != null) {
        result = true;
      }
    }

    if (this.keyWord !== this.english.keyWord) {
      result = true;
    }
    if (this.explanationEnForm !== this.english.explanationEn) {
      result = true;
    }
    if (this.explanationVnForm !== this.english.explanationVn) {
      result = true;
    }
    if (this.imageFile != null) {
      result = true;
    }
    return result;
  }
}
