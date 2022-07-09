import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EnglishLearnReport } from '@app/pages/english/_model';
import { EnglishService } from '@app/pages/english/_services';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { EnglishLearnReportSCO } from '@app/_common/_sco';
import { SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-english-user-report',
  templateUrl: './english-user-report.component.html',
  styleUrls: ['./english-user-report.component.css']
})
export class EnglishUserReportComponent implements OnInit {
  // Table elements
  pageResult: PageResultVO<EnglishLearnReport>;

  /**
   * Constructor
   */
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private englishService: EnglishService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService
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
    this.englishService.searchTopLearn(new EnglishLearnReportSCO()).subscribe(
      data => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishLearnReport> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<EnglishLearnReport>();
        }

        // Hide Loading
        this.spinner.hide();
      },
      error => {
        // Hide Loading
        this.spinner.hide();

        this.alertService.error(error);
      }
    );
  }

  /**
   * Navigate to E-Dashboard
   */
  public navigateEDashboard(item: EnglishLearnReport) {
    window.open('/englishReport/' + item.user.id);
  }
}
