import { Component, OnInit } from '@angular/core';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { SpinnerService } from '@app/_common/_services';
import { GoldSjc } from '../_models';
import { FinanceService } from '../_services';

@Component({
  selector: 'app-finance-gold',
  templateUrl: './finance-gold.component.html',
  styleUrls: ['./finance-gold.component.css']
})
export class FinanceGoldComponent implements OnInit {
  isLoading: Boolean = false;
  goldSjc: GoldSjc;

  constructor(
    private spinner: SpinnerService,
    private financeService: FinanceService,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.loadGoldSjc();
  }

  /**
     * Load godl SJC
     */
  public loadGoldSjc() {
    // Show loading
    this.spinner.show();
    this.isLoading = false;

    // Get list
    this.financeService.getGoldSjcPrice()
      .subscribe(data => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<GoldSjc> = responseAPi;
        if (typeResponse.data != null) {
          this.goldSjc = typeResponse.data.elements[0];
        } else {
          this.goldSjc = null;
        }

        // Hide loading
        this.spinner.hide();
        this.isLoading = false;

      }, error => {
        this.processError(error);
      });
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
    this.isLoading = false;

    this.alertService.error(error);
  }

}
