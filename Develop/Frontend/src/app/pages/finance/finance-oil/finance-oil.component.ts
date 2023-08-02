import { Component, OnInit } from '@angular/core';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/apiReponse';
import { TradingOilPrice } from '@app/models/tradingOilPrice';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-finance-oil',
  templateUrl: './finance-oil.component.html',
})
export class FinanceOilComponent implements OnInit {
  selectWTICrudeOil = '1m';
  selectBrentCrudeOil = '1m';
  selectNatureGas = '1m';

  tradingOilPrice: TradingOilPrice;
  currentWTIPrice;
  currentWTIPriceChange;
  currentWTIPercentChange;
  currentBrentPrice;
  currentBrentPriceChange;
  currentBrentPercentChange;

  constructor(
    private spinner: SpinnerService,
    private backendService: BackendService,
    private alertService: AlertService,
  ) {}

  ngOnInit(): void {
    this.loadOilPrice();
  }

  /**
   * Load oil price
   */
  public loadOilPrice() {
    // Show loading
    this.spinner.show();

    // Get list
    this.backendService.getOilPrice().subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<TradingOilPrice> = responseAPi;
        if (typeResponse.data != null && typeResponse.data.elements != null) {
          this.tradingOilPrice = typeResponse.data.elements[0];

          this.currentWTIPrice = this.tradingOilPrice.symbolCL1.Last;
          this.currentWTIPriceChange =
            this.tradingOilPrice.symbolCL1.DailyChange.toFixed(2);
          this.currentWTIPercentChange =
            this.tradingOilPrice.symbolCL1.DailyPercentualChange.toFixed(2);

          this.currentBrentPrice = this.tradingOilPrice.symbolCO1.Last;
          this.currentBrentPriceChange =
            this.tradingOilPrice.symbolCO1.DailyChange.toFixed(2);
          this.currentBrentPercentChange =
            this.tradingOilPrice.symbolCO1.DailyPercentualChange.toFixed(2);
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  /**
   * WTI Crude Oil options
   */
  public changeWTICrudeOil1m() {
    this.selectWTICrudeOil = '1m';
  }

  public changeWTICrudeOil1q() {
    this.selectWTICrudeOil = '1q';
  }

  public changeWTICrudeOil1y() {
    this.selectWTICrudeOil = '1y';
  }

  public changeWTICrudeOil5y() {
    this.selectWTICrudeOil = '5y';
  }

  /**
   * Brent Crude Oil options
   */
  public changeBrentCrudeOil1m() {
    this.selectBrentCrudeOil = '1m';
  }

  public changeBrentCrudeOil1q() {
    this.selectBrentCrudeOil = '1q';
  }

  public changeBrentCrudeOil1y() {
    this.selectBrentCrudeOil = '1y';
  }

  public changeBrentCrudeOil5y() {
    this.selectBrentCrudeOil = '5y';
  }

  /**
   * Nature gas options
   */
  public changeNatureGas1m() {
    this.selectNatureGas = '1m';
  }

  public changeNatureGas1q() {
    this.selectNatureGas = '1q';
  }

  public changeNatureGas1y() {
    this.selectNatureGas = '1y';
  }

  public changeNatureGas5y() {
    this.selectNatureGas = '5y';
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
