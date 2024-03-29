import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
import { CryptoPortfolio } from '@app/models/cryptoPortfolio';
import { CryptoTokenConfig } from '@app/models/cryptoTokenConfig';
import { CryptoTokenConfigSCO } from '@app/scos/cryptoTokenConfigSCO';
import { BackendService } from '@app/services/backend.service';
import { RequireMatchForm } from '@app/services/requireMatchForm';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  templateUrl: 'edit-crypto-portfolio-dialog.component.html',
})
export class EditCryptoPortfolioDialogComponent implements OnInit {
  constructor(
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
  @Input() cryptoPortfolio: CryptoPortfolio;

  // Field token
  tokenValue: CryptoTokenConfig;
  filteredTokens: Observable<CryptoTokenConfig[]>;
  tokenFormControl = new FormControl('', [
    Validators.required,
    RequireMatchForm,
  ]);

  // Field wallet
  walletValue: string;
  walletFormControl = new FormControl('', [Validators.required]);

  // Field starname
  starnameValue: string;
  starnameFormControl = new FormControl('', []);

  ngOnInit() {
    // Set current value
    this.tokenValue = this.cryptoPortfolio.token;
    this.walletValue = this.cryptoPortfolio.wallet;
    this.starnameValue = this.cryptoPortfolio.starname;

    this.getTokenList();
  }

  public displayToken(item: CryptoTokenConfig) {
    if (item) {
      return item.tokenCode + ' (' + item.tokenName + ')';
    }
    return null;
  }

  /**
   * Get list
   */
  getTokenList() {
    // Prepare search condition
    const sco = new CryptoTokenConfigSCO();

    // Show loading
    this.spinner.show();

    // Get list type
    this.backendService.searchCryptoTokenConfig(sco).subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<CryptoTokenConfig> = responseAPi;
        if (typeResponse.data != null) {
          const items: CryptoTokenConfig[] = typeResponse.data.elements;
          this.filteredTokens = this.tokenFormControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              items.filter((valueFilter) =>
                valueFilter.tokenCode
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
        this.messageError = 'Skip update because the value is not changed';
        break;

      // Case data is invalid
      case !this.isFormValid():
        this.message = null;
        this.messageError = 'Invalid fields, please check your input';
        break;

      // Case ok
      default:
        // Prepare update
        const updateItem: CryptoPortfolio = this.cryptoPortfolio;
        updateItem.token = this.tokenValue;
        updateItem.wallet = this.walletValue;
        updateItem.starname = this.starnameValue;

        // Show loading
        this.spinner.show();

        // Edit account
        this.backendService.updateCryptoPortfolio(updateItem).subscribe({
          next: async (res) => {
            // Send success toast message
            this.toastr.success(
              'Wallet ' +
                this.walletValue +
                '<' +
                this.tokenValue.tokenCode +
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
    if (this.tokenFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.walletFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.starnameFormControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }

  /**
   * Have any update infos
   */
  public isHaveUpdateValue() {
    let result = false;
    if (this.tokenValue !== this.cryptoPortfolio.token) {
      result = true;
    }
    if (this.walletValue !== this.cryptoPortfolio.wallet) {
      result = true;
    }
    if (this.starnameValue !== this.cryptoPortfolio.starname) {
      result = true;
    }
    return result;
  }
}
