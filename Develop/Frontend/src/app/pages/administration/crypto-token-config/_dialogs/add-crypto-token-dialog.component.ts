import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { CryptoTokenService } from '@app/pages/crypto/_services';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse, CryptoToken } from '@app/_common/_models';
import { CryptoTokenSCO } from '@app/_common/_sco';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-crypto-token-dialog',
  templateUrl: './add-crypto-token-dialog.component.html',
  styleUrls: ['./add-crypto-token-dialog.component.css']
})
export class AddCryptoTokenDialogComponent implements OnInit {

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
    validTokenCode,
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

      // Prepare form
      const addForm: FormGroup = this.formBuilder.group({
        tokenCode: [this.tokenCodeValue],
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
      this.cryptoTokenService.add(addForm.value)
        .subscribe(
          data => {
            // Send success toast message
            this.toastr.success('New token ' + this.tokenCodeValue + ' is added successful');

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


/**
* Check inexistence
*
*/
function validTokenCode(control: FormControl) {
  const tokenCode = control.value;

  // Validate exixtence
  if (tokenCode) {
    const tokenCodeSearch = new SearchText();
    tokenCodeSearch.eq = tokenCode;
    const sco = new CryptoTokenSCO();
    sco.tokenCode = tokenCodeSearch;

    this.cryptoTokenService.search(sco)
      .subscribe(data => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<CryptoToken> = responseAPi;
        if (typeResponse.data != null) {
          return {
            tokenCOdeIsExisted: {
              parsedUrln: tokenCode
            }
          };
        }
      }, error => {
        this.toastr.info('error:' + error);
      });
  }
  return null;
}
