import { Component, HostListener, OnInit } from '@angular/core';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/apiReponse';
import { TradingStockPrice } from '@app/models/tradingStockPrice';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-finance-stock',
  templateUrl: './finance-stock.component.html',
})
export class FinanceStockComponent implements OnInit {
  stocks: TradingStockPrice;

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
    this.loadStockPrice();
  }

  /**
   * Load stock price
   */
  public loadStockPrice() {
    // Show loading
    this.spinner.show();

    // Get list
    this.backendService.getStockPrice().subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<TradingStockPrice> = responseAPi;
        if (typeResponse.data != null && typeResponse.data.elements != null) {
          this.stocks = typeResponse.data.elements[0];
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
