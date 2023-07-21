import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ApiReponse, Type } from '@app/_common/_models';
import { TypeSCO } from '@app/_common/_sco';
import { SearchText } from '@app/_common/_sco/core_sco';
import {
  BackendService,
  RequireMatchForm,
  SpinnerService,
} from '@app/_common/_services';
import { AlertService } from '@app/_common/alert/alert.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Account } from '@app/_common/_models';
import { AccountService } from '../_services';

@Component({
  styleUrls: ['edit-account-dialog.component.css'],
  templateUrl: 'edit-account-dialog.component.html',
})
export class EditAccountDialogComponent implements OnInit {
  constructor(
    private activeModal: NgbActiveModal,
    private accountService: AccountService,
    private alertService: AlertService,
    private backendService: BackendService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() account: Account;

  // Field account type
  accountTypeValue: Type;
  filteredAccountTypes: Observable<Type[]>;
  accountTypeFormControl = new FormControl('', [
    Validators.required,
    RequireMatchForm,
  ]);

  // Field account name
  accountNameValue: string;
  accountNameFormControl = new FormControl('', [Validators.required]);

  // Field password
  passwordValue: string;
  passwordFormControl = new FormControl('', []);

  // Field note
  noteValue: string;
  noteFormControl = new FormControl('', []);

  ngOnInit() {
    // Set current value
    this.accountTypeValue = this.account.accountType;
    this.accountNameValue = this.account.accountName;
    this.passwordValue = this.account.mdp;
    this.noteValue = this.account.note;

    this.getAccountTypeList();
  }
  public displayAccountType(type: Type) {
    if (type) {
      return type.typeName;
    }
    return null;
  }

  /**
   * Get list account type
   */
  getAccountTypeList() {
    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'ACCOUNT';
    const typeSCO = new TypeSCO();
    typeSCO.typeClass = typeClass;

    // Show loading
    this.spinner.show();

    // Get list type
    this.backendService.searchType(typeSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<Type> = responseAPi;
        if (typeResponse.data != null) {
          const accountTypes: Type[] = typeResponse.data.elements;
          this.filteredAccountTypes =
            this.accountTypeFormControl.valueChanges.pipe(
              startWith(''),
              map((value) =>
                accountTypes.filter((valueFilter) =>
                  valueFilter.typeCode
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

        // Show alert message
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
      // Case data is unchanged
      case !this.isHaveUpdateValue():
        this.message = null;
        this.messageError =
          'Skip update account because the value is not changed';
        break;

      // Case data is invalid
      case !this.isFormValid():
        this.message = null;
        this.messageError = 'Invalid fields, please check your input';
        break;

      // Case ok
      default:
        // Prepare update account
        const updateAccount: Account = this.account;
        updateAccount.accountType = this.accountTypeValue;
        updateAccount.accountName = this.accountNameValue;
        if (this.passwordValue !== this.account.mdp) {
          updateAccount.mdp = this.passwordValue;
        }
        if (this.noteValue !== this.account.note) {
          updateAccount.note = this.noteValue;
        }

        // Show loading
        this.spinner.show();

        // Edit account
        this.accountService.updateAccount(updateAccount).subscribe(
          (data) => {
            // Send success toast message
            this.toastr.success(
              'Account ' +
                this.accountTypeValue.typeCode +
                '<' +
                this.accountNameValue +
                '>' +
                ' is updated successful',
            );

            // Hide loading
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);
          },
          (error) => {
            // Hide loading
            this.spinner.hide();

            // Send error
            this.message = null;
            this.messageError = error;
          },
        );
    }
  }

  /**
   * Validate all fields
   */
  public isFormValid() {
    let result = true;
    if (this.accountTypeFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.accountNameFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.passwordFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.noteFormControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }

  /**
   * Have any update infos
   */
  public isHaveUpdateValue() {
    let result = false;
    if (this.accountTypeValue !== this.account.accountType) {
      result = true;
    }
    if (this.accountNameValue !== this.account.accountName) {
      result = true;
    }
    if (this.passwordValue !== this.account.mdp) {
      result = true;
    }
    if (this.noteValue !== this.account.note) {
      result = true;
    }
    return result;
  }
}
