import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ApiReponse } from '@app/_common/_models';
import { SearchText } from '@app/_common/_sco/core_sco';
import { RequireMatchForm, SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { CryptoPortfolio, CryptoTokenConfig } from '../_models';
import { CryptoTokenConfigSCO } from '../_sco';
import { CryptoPortfolioService, CryptoTokenConfigService } from '../_services';
import { AlertService } from '@app/_common/alert/alert.service';

@Component({
  styleUrls: ['add-crypto-portfolio-dialog.component.css'],
  templateUrl: 'add-crypto-portfolio-dialog.component.html',
})
export class AddCryptoFortfolioDialogComponent implements OnInit {
  constructor(
    private activeModal: NgbActiveModal,
    private cryptoPortfolioService: CryptoPortfolioService,
    private alertService: AlertService,
    private cryptoTokenConfigService: CryptoTokenConfigService,
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
    this.cryptoTokenConfigService.search(sco).subscribe(
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

      this.cryptoPortfolioService.add(item).subscribe(
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
