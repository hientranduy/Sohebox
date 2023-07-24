import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { AlertService } from '@app/commons/alert/alert.service';
import { Account } from '@app/models/account';
import { ApiReponse } from '@app/models/apiReponse';
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
  styleUrls: ['add-account-dialog.component.css'],
  templateUrl: 'add-account-dialog.component.html',
})
export class AddAccountDialogComponent implements OnInit {
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
    if (this.isFormValid()) {
      // Show loading
      this.spinner.show();

      // Add account
      const account: Account = new Account();
      account.accountType = this.accountTypeValue;
      account.accountName = this.accountNameValue;
      account.mdp = this.passwordValue;
      account.note = this.noteValue;

      this.backendService.createAccount(account).subscribe(
        (data) => {
          // Send success toast message
          this.toastr.success(
            'Account ' +
              this.accountTypeValue.typeCode +
              '<' +
              this.accountNameValue +
              '>' +
              ' is added successful',
          );

          // Hide loading
          this.spinner.hide();

          // Close dialog
          this.activeModal.close(true);
        },
        (error) => {
          // Hide loading
          this.spinner.hide();

          // Send error toast message
          this.message = null;
          this.messageError = error;
        },
      );
    } else {
      this.message = null;
      this.messageError = 'Invalid fields, please check your input';
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
}
