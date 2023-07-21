import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Type } from '@app/_common/_models';
import { RequireMatchForm } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { Account } from '../_models';

@Component({
  styleUrls: ['view-account-dialog.component.css'],
  templateUrl: 'view-account-dialog.component.html',
})
export class ViewAccountDialogComponent implements OnInit {
  constructor(private activeModal: NgbActiveModal) {}

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
  }
  public displayAccountType(type: Type) {
    if (type) {
      return type.typeName;
    }
    return null;
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
}
