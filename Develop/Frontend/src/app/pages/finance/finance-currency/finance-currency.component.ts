import { Component, HostListener, OnInit } from '@angular/core';
import { ApiReponse } from '@app/_common/_models';
import { SpinnerService } from '@app/_common/_services';
import { CurrencyVietcombank } from '../_models';
import { FinanceService } from '../_services';
import { AlertService } from '@app/_common/alert/alert.service';

@Component({
  selector: 'app-finance-currency',
  templateUrl: './finance-currency.component.html',
  styleUrls: ['./finance-currency.component.css'],
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
    private financeService: FinanceService,
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
    this.financeService.getCurrencyVcbRate().subscribe(
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
