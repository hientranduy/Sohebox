import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { EnglishLearnRecordSCO, EnglishLearnReportSCO } from '@app/_common/_sco';
import { SearchDate, SearchNumber, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { EnglishDialogService } from '../english/_dialogs';
import { EnglishLearnRecord, EnglishLearnReport } from '../english/_model';
import { EnglishService } from '../english/_services';

@Component({
  selector: 'app-english-report',
  templateUrl: './english-report.component.html',
  styleUrls: ['./english-report.component.css']
})
export class EnglishReportComponent implements OnInit {
  userId: number;
  today: Date = new Date();
  currentSortLearnReport: Sorter;
  currentSortLearnRecord: Sorter;
  pageResultLearnReport: PageResultVO<EnglishLearnReport>;
  pageResultLearnRecord: PageResultVO<EnglishLearnRecord>;

  // Show total learned
  learnedRecords: EnglishLearnRecord[];
  numberOfLearned: any = 0;

  constructor(
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private spinner: SpinnerService,
    private alertService: AlertService,
    private englishService: EnglishService,
    private englishDialogService: EnglishDialogService
  ) {
    // Set default
    this.pageResultLearnReport = new PageResultVO<EnglishLearnReport>();
    this.pageResultLearnReport.pageSize = 5;
    this.pageResultLearnReport.currentPage = 0;

    this.pageResultLearnRecord = new PageResultVO<EnglishLearnRecord>();
    this.pageResultLearnRecord.pageSize = 10;
    this.pageResultLearnRecord.currentPage = 0;
  }

  ngOnInit(): void {
    // Get user id from parameters
    this.route.params.subscribe(
      (params: Params) => (this.userId = params['id'])
    );

    // Initial
    this.getLearnedReport(
      this.pageResultLearnReport.currentPage,
      this.pageResultLearnReport.pageSize,
      null
    );
    this.getLearnedRecordByToday(
      this.pageResultLearnRecord.currentPage,
      this.pageResultLearnRecord.pageSize,
      null
    );

    // Get learned list by today
    this.getLearnedListByToday();
  }

  /**
   * handle event of paginator footer
   */
  setPageLearnReport(pageInfo) {
    this.getLearnedReport(
      pageInfo.offset,
      this.pageResultLearnReport.pageSize,
      this.currentSortLearnReport
    );
  }

  /**
   * handle event of sort
   */
  onSortLearnReport(event) {
    const sort = event.sorts[0];
    this.currentSortLearnReport = new Sorter(sort.prop, sort.dir);
    this.getLearnedReport(
      0,
      this.pageResultLearnReport.pageSize,
      this.currentSortLearnReport
    );
  }

  /**
   * handle event of paginator header
   */
  handlePaginatorEventLearnReport(e: any) {
    this.getLearnedReport(e.pageIndex, e.pageSize, this.currentSortLearnReport);
  }

  /**
   * Get learned report
   */
  public getLearnedReport(
    pageNumber: number,
    pageRecord: number,
    sorter: Sorter
  ) {
    // Prepare search condition
    const userIdSearch = new SearchNumber();
    userIdSearch.eq = this.userId;
    const sorters: Array<Sorter> = [];
    if (sorter) {
      sorters.push(sorter);
    } else {
      sorters.push(new Sorter('learnedDate', 'DESC'));
    }

    const sco = new EnglishLearnReportSCO();
    sco.userId = userIdSearch;
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;
    sco.sorters = sorters;

    // Show Loading
    this.spinner.show();

    // Search
    this.englishService.searchLearnReport(sco).subscribe(
      data => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishLearnReport> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResultLearnReport = typeResponse.data;
        }

        // Hide Loading
        this.spinner.hide();
      },
      error => {
        // Hide Loading
        this.spinner.hide();
      }
    );
  }

  /**
   * handle event of paginator footer
   */
  setPageLearnRecord(pageInfo) {
    this.getLearnedRecordByToday(
      pageInfo.offset,
      this.pageResultLearnRecord.pageSize,
      this.currentSortLearnRecord
    );
  }

  /**
   * handle event of sort
   */
  onSortLearnRecord(event) {
    const sort = event.sorts[0];
    this.currentSortLearnRecord = new Sorter(sort.prop, sort.dir);
    this.getLearnedRecordByToday(
      this.pageResultLearnRecord.currentPage,
      this.pageResultLearnRecord.pageSize,
      this.currentSortLearnRecord
    );
  }

  /**
   * handle event of paginator header
   */
  handlePaginatorEventLearnRecord(e: any) {
    this.getLearnedRecordByToday(
      e.pageIndex,
      e.pageSize,
      this.currentSortLearnRecord
    );
  }

  /**
   * Get learned report
   */
  public getLearnedRecordByToday(
    pageNumber: number,
    pageRecord: number,
    sorter: Sorter
  ) {
    // Prepare search condition
    const userIdSearch = new SearchNumber();
    userIdSearch.eq = this.userId;
    const updatedDateSearch = new SearchDate();
    updatedDateSearch.eq = new Date();

    const sorters: Array<Sorter> = [];
    if (sorter) {
      sorters.push(sorter);
    } else {
      sorters.push(new Sorter('updatedDate', 'ASC'));
    }

    const sco = new EnglishLearnRecordSCO();
    sco.userId = userIdSearch;
    sco.updatedDate = updatedDateSearch;
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;
    sco.sorters = sorters;

    // Show Loading
    this.spinner.show();

    // Search
    this.englishService.searchLearnRecord(sco).subscribe(
      data => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishLearnRecord> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResultLearnRecord = typeResponse.data;
        }

        // Hide Loading
        this.spinner.hide();
      },
      error => {
        // Hide Loading
        this.spinner.hide();

        // Process error
        this.toastr.error(error);
        this.router.navigate(['/']);
      }
    );
  }

  /**
   * View english info
   */
  public viewEnglishInfo(item: EnglishLearnRecord) {
    this.englishDialogService.viewWordInfo(item.english).then(
      result => {
        if (result) {
        }
      },
      reason => {
        console.log('VIEW WORD reason:' + reason);
      }
    );
  }

  /**
   * Get learned record list by today
   */
  public getLearnedListByToday() {
    // Prepare search condition
    const updatedDateSearch = new SearchDate();
    updatedDateSearch.eq = new Date();
    const userIdSearch = new SearchNumber();
    userIdSearch.eq = this.userId;
    const sco = new EnglishLearnRecordSCO();
    sco.updatedDate = updatedDateSearch;
    sco.userId = userIdSearch;

    // Show Loading
    this.spinner.show();

    // Search
    this.englishService.searchLearnRecord(sco).subscribe(
      data => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishLearnRecord> = responseAPi;
        if (typeResponse.data != null) {
          this.learnedRecords = typeResponse.data.elements;

          // Calculate total times of learn
          if (this.learnedRecords != null) {
            this.learnedRecords.forEach(element => {
              this.numberOfLearned =
                this.numberOfLearned + element.learnedToday;
            });
          } else {
            this.numberOfLearned = 0;
          }
        }
      },
      error => {
        // Hide Loading
        this.spinner.hide();

        // Process error
        this.toastr.error(error);
        this.router.navigate(['/']);
      }
    );
  }

  /**
   * Back to food home
   */
  public closeTab() {
    window.close();
  }
}
