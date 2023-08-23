import { Component, HostListener, OnInit } from '@angular/core';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
import { CurrencyVietcombank } from '@app/models/currencyVietcombank';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-finance-currency',
  templateUrl: './finance-currency.component.html',
})
export class FinanceCurrencyComponent implements OnInit {
  currencyVCB: CurrencyVietcombank;

  // Width change
  windownInnerWidth = window.innerWidth;
  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.windownInnerWidth = window.innerWidth;
  }
  @HostListener('window:orientationchange', ['$event'])
  onOrientationChange(event) {
    this.windownInnerWidth = window.innerWidth;
  }

  constructor(
    private spinner: SpinnerService,
    private backendService: BackendService,
    private alertService: AlertService,
  ) {}

  ngOnInit(): void {
    this.loadCurrencyVcb();
  }

  /**
   * Load currency VCB
   */
  public loadCurrencyVcb() {
    // Show loading
    this.spinner.show();

    // Get list
    this.backendService.getCurrencyVcbRate().subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<CurrencyVietcombank> = responseAPi;
        if (typeResponse.data != null) {
          this.currencyVCB = typeResponse.data.elements[0];
        } else {
          this.currencyVCB = null;
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  ///////////////////
  // GENERAL CHECK //
  ///////////////////
  /**
   * Process error in case have error call API
   */
  public processError(error: any) {
    // Hide loading
    this.spinner.hide();

    this.alertService.error(error);
  }
}
