import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { CryptoTokenService } from '@app/pages/crypto/_services';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { CryptoToken } from '@app/_common/_models';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-edit-crypto-token-dialog',
  templateUrl: './edit-crypto-token-dialog.component.html',
  styleUrls: ['./edit-crypto-token-dialog.component.css']
})
export class EditCryptoTokenDialogComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private cryptoTokenService: CryptoTokenService,
    private toastr: ToastrService,
    private spinner: SpinnerService
  ) {
  }

  isLoading: Boolean;
  isLoadingContent: Boolean;

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() cryptoToken: CryptoToken;

  // Input value
  tokenCodeValue: string;
  tokenNameValue: string;
  iconUrlValue: string;
  nodeUrlValue: string;
  denomValue: string;
  addressPrefixValue: string;


  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // tokenCode
  tokenCodeFormControl = new FormControl('', [
    Validators.required,
  ]);

  // tokenName
  tokenNameFormControl = new FormControl('', [
    Validators.required,
  ]);

  // iconUrl
  iconUrlFormControl = new FormControl('', [
  ]);

  // nodeUrl
  nodeUrlFormControl = new FormControl('', [
  ]);

  // denom
  denomFormControl = new FormControl('', [
  ]);

  // addressPrefix
  addressPrefixFormControl = new FormControl('', [
  ]);

  ngOnInit() {
    // Set old data
    this.tokenCodeValue = this.cryptoToken.tokenCode;
    this.tokenNameValue = this.cryptoToken.tokenName;
    this.iconUrlValue = this.cryptoToken.iconUrl;
    this.nodeUrlValue = this.cryptoToken.nodeUrl;
    this.denomValue = this.cryptoToken.denom;
    this.addressPrefixValue = this.cryptoToken.addressPrefix;
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

      // Prepare adding word form
      const editForm: FormGroup = this.formBuilder.group({
        tokenName: [this.tokenNameValue],
        iconUrl: [this.iconUrlValue],
        nodeUrl: [this.nodeUrlValue],
        denom: [this.denomValue],
        addressPrefix: [this.addressPrefixValue]
      });

      // Show loading
      this.isLoading = true;
      this.spinner.show();

      // Add
      this.cryptoTokenService.update(editForm.value)
        .subscribe(
          data => {
            // Send success toast message
            this.toastr.success('Token ' + this.tokenCodeValue + ' is updated successful');

            // Hide loading
            this.isLoading = false;
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);

          },
          error => {
            // Hide loading
            this.isLoading = false;
            this.spinner.hide();

            // Send error message to dialog
            this.message = null;
            this.messageError = error;
          });
    } else {
      this.message = null;
      this.messageError = 'Invalid fields, please check your input';
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;

    if (this.tokenCodeFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.tokenNameFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.iconUrlFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.nodeUrlFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.denomFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.addressPrefixFormControl.status === 'INVALID') {
      result = false;
    }

    return result;
  }
}
