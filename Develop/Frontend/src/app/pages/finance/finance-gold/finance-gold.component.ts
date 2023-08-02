import { Component, OnInit } from '@angular/core';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/apiReponse';
import { GoldSjc } from '@app/models/goldSjc';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-finance-gold',
  templateUrl: './finance-gold.component.html',
})
export class FinanceGoldComponent implements OnInit {
  goldSjc: GoldSjc;

  constructor(
    private spinner: SpinnerService,
    private backendService: BackendService,
    private alertService: AlertService,
  ) {}

  ngOnInit(): void {
    this.loadGoldSjc();
  }

  /**
   * Load godl SJC
   */
  public loadGoldSjc() {
    // Show loading
    this.spinner.show();

    // Get list
    this.backendService.getGoldSjcPrice().subscribe(
      (data) => {
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
