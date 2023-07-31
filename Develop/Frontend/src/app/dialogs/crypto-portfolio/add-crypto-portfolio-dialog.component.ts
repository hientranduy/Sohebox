import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/apiReponse';
import { CryptoPortfolio } from '@app/models/cryptoPortfolio';
import { CryptoTokenConfig } from '@app/models/cryptoTokenConfig';
import { SearchText } from '@app/scos/core_sco/searchText';
import { CryptoTokenConfigSCO } from '@app/scos/cryptoTokenConfigSCO';
import { BackendService } from '@app/services/backend.service';
import { RequireMatchForm } from '@app/services/requireMatchForm';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  templateUrl: 'add-crypto-portfolio-dialog.component.html',
})
export class AddCryptoFortfolioDialogComponent implements OnInit {
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
    this.getTokenList();
  }

  public displayToken(item: CryptoTokenConfig) {
    if (item) {
      return item.tokenCode;
    }
    return null;
  }

  /**
   * Get list
   */
  getTokenList() {
    // Prepare search condition
    const sco = new CryptoTokenConfigSCO();

    // Only display if have node URL
    const nodeUrl = new SearchText();
    nodeUrl.like = 'http';
    sco.nodeUrl = nodeUrl;

    // Show loading
    this.spinner.show();

    // Get list type
    this.backendService.searchCryptoTokenConfig(sco).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
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

      // Add
      const item: CryptoPortfolio = new CryptoPortfolio();
      item.token = this.tokenValue;
      item.wallet = this.walletValue;
      item.starname = this.starnameValue;

      this.backendService.addCryptoPortfolio(item).subscribe(
        (data) => {
          // Send success toast message
          this.toastr.success(
            'Wallet ' +
              this.walletValue +
              '<' +
              this.tokenValue.tokenCode +
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
}
