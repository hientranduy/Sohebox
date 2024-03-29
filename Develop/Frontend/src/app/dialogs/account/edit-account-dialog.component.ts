import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { AlertService } from '@app/commons/alert/alert.service';
import { Account } from '@app/models/account';
import { ApiReponse } from '@app/models/response/apiReponse';
import { Type } from '@app/models/type';
import { SearchText } from '@app/scos/core_sco/searchText';
import { TypeSCO } from '@app/scos/typeSCO';
import { BackendService } from '@app/services/backend.service';
import { RequireMatchForm } from '@app/services/requireMatchForm';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  templateUrl: 'edit-account-dialog.component.html',
})
export class EditAccountDialogComponent implements OnInit {
  constructor(
    private activeModal: NgbActiveModal,
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
    this.accountTypeValue = this.account.type;
    this.accountNameValue = this.account.accountName;
    this.passwordValue = this.account.mdpPlain;
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
    this.backendService.searchType(typeSCO).subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
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
      error: (err) => {
        // Hide loading
        this.spinner.hide();

        // Show alert message
        this.alertService.error(err);
      },
    });
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
        updateAccount.type = this.accountTypeValue;
        updateAccount.accountName = this.accountNameValue;
        if (this.passwordValue !== this.account.mdpPlain) {
          updateAccount.mdpPlain = this.passwordValue;
        } else {
          updateAccount.mdpPlain = '';
        }
        if (this.noteValue !== this.account.note) {
          updateAccount.note = this.noteValue;
        }

        // Show loading
        this.spinner.show();

        // Edit account
        this.backendService.updateAccount(updateAccount).subscribe({
          next: async (res) => {
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
          error: (err) => {
            // Hide loading
            this.spinner.hide();

            // Send error
            this.message = null;
            this.messageError = err;
          },
        });
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
    if (this.accountTypeValue !== this.account.type) {
      result = true;
    }
    if (this.accountNameValue !== this.account.accountName) {
      result = true;
    }
    if (this.passwordValue !== this.account.mdpPlain) {
      result = true;
    }
    if (this.noteValue !== this.account.note) {
      result = true;
    }
    return result;
  }
}
