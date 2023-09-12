import { Component, OnInit } from '@angular/core';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
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
    this.backendService.getGoldSjcPrice().subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<GoldSjc> = responseAPi;
        if (typeResponse.data != null) {
          this.goldSjc = typeResponse.data.elements[0];
        } else {
          this.goldSjc = null;
        }

        // Hide loading
        this.spinner.hide();
      },
      error: (err) => {
        this.processError(err);
      },
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

    this.alertService.error(error);
  }
}
