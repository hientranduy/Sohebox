import { Component, HostListener, OnInit } from '@angular/core';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { SpinnerService } from '@app/_common/_services';
import { TradingStockPrice } from '../_models';
import { TradingService } from '../_services';

@Component({
  selector: 'app-finance-stock',
  templateUrl: './finance-stock.component.html',
  styleUrls: ['./finance-stock.component.css'],
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
    private tradingService: TradingService,
    private alertService: AlertService,
  ) {}

  ngOnInit(): void {
    this.loadStockPrice();
  }

  /**
   * Load oil price
   */
  public loadStockPrice() {
    // Show loading
    this.spinner.show();

    // Get list
    this.tradingService.getStockPrice().subscribe(
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
