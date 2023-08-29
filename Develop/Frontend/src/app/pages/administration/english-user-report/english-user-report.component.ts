import { Component, OnInit } from '@angular/core';
import { PageResultVO } from '@app/models/response/pageResultVO';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
import { EnglishLearnReport } from '@app/models/englishLearnReport';
import { EnglishLearnReportSCO } from '@app/scos/englishLearnReportSCO';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-english-user-report',
  templateUrl: './english-user-report.component.html',
})
export class EnglishUserReportComponent implements OnInit {
  // Table elements
  pageResult: PageResultVO<EnglishLearnReport>;

  /**
   * Constructor
   */
  constructor(
    private backendService: BackendService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {
    // Set default
    this.pageResult = new PageResultVO<EnglishLearnReport>();
  }

  /**
   * Initial
   */
  ngOnInit() {
    this.getPageResult();
  }

  /**
   * Get page result
   */
  public getPageResult() {
    // Show Loading
    this.spinner.show();

    // Search
    this.backendService.searchTopLearn(new EnglishLearnReportSCO()).subscribe({
      next: async (res) => {
        const responseAPi: any = res;
        const typeResponse: ApiReponse<EnglishLearnReport> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<EnglishLearnReport>();
        }

        // Hide Loading
        this.spinner.hide();
      },
      error: (err) => {
        // Hide Loading
        this.spinner.hide();

        this.alertService.error(err);
      },
    });
  }

  /**
   * Navigate to E-Dashboard
   */
  public navigateEDashboard(item: EnglishLearnReport) {
    window.open('/englishReport/' + item.user.id);
  }
}
