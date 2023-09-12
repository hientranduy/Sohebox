import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { PageResultVO } from '@app/models/response/pageResultVO';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
import { UserStatus } from '@app/models/userStatus';
import { SearchText } from '@app/scos/core_sco/searchText';
import { Sorter } from '@app/scos/core_sco/sorter';
import { UserSCO } from '@app/scos/userSCO';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-user-control',
  templateUrl: './user-control.component.html',
})
export class UserControlComponent implements OnInit, OnDestroy {
  pageResult: PageResultVO<UserStatus>;

  currentSort: Sorter;
  currentFilterValue: string;

  // Interval
  interval: any;
  intervalTime = 300000;

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

  /**
   * Constructor
   */
  constructor(
    private backendService: BackendService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {
    // Set default
    this.pageResult = new PageResultVO<UserStatus>();
    this.pageResult.pageSize = 20;
    this.pageResult.currentPage = 0;

    // Refresh page each 10 seconds
    if (this.interval) {
      clearInterval(this.interval);
    }

    this.interval = setInterval(() => {
      this.getActiveUser(0, this.pageResult.pageSize);
    }, this.intervalTime);
  }

  /**
   * Initial
   */
  ngOnInit() {
    // Initial
    this.getActiveUser(0, this.pageResult.pageSize);
  }

  /**
   * Destroy
   */
  ngOnDestroy() {
    clearInterval(this.interval);
  }

  /**
   * handle event of select page
   */
  setPage(pageInfo) {
    this.getPageResult(
      pageInfo.offset,
      this.pageResult.pageSize,
      this.currentSort,
      this.currentFilterValue,
    );
  }

  /**
   * handle event of sort
   */
  onSort(event) {
    const sort = event.sorts[0];
    this.currentSort = new Sorter(sort.prop, sort.dir);
    this.getPageResult(
      0,
      this.pageResult.pageSize,
      this.currentSort,
      this.currentFilterValue,
    );
  }

  /**
   * handle event of filter
   */
  onFilter(event) {
    this.currentFilterValue = event.target.value;
    this.getPageResult(
      0,
      this.pageResult.pageSize,
      this.currentSort,
      this.currentFilterValue,
    );
  }

  /**
   * handle event of paginator
   */
  handlePaginatorEvent(e: any) {
    this.getPageResult(
      e.pageIndex,
      e.pageSize,
      this.currentSort,
      this.currentFilterValue,
    );
  }

  /**
   * Get page result
   */
  public getPageResult(
    pageNumber: number,
    pageRecord: number,
    sorter: Sorter,
    filterValue: string,
  ) {
    // Prepare search condition
    const sco = new UserSCO();
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;
    if (sorter) {
      const sorters: Array<Sorter> = [];
      sorters.push(sorter);
      sco.sorters = sorters;
    }
    if (filterValue) {
      const userName = new SearchText();
      userName.like = filterValue;
      sco.userName = userName;

      const firstName = new SearchText();
      firstName.like = filterValue;
      sco.firstName = firstName;

      const lastName = new SearchText();
      lastName.like = filterValue;
      sco.lastName = lastName;

      sco.searchOr = true;
    }

    // Show Loading
    this.spinner.show();

    // Search
    this.backendService.searchUserStatus(sco).subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<UserStatus> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<UserStatus>();
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
   * Get page result
   */
  public getActiveUser(pageNumber: number, pageRecord: number) {
    // Prepare search condition
    const sco = new UserSCO();
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;

    // Show Loading
    this.spinner.show();

    // Search
    this.backendService.searchActiveUser(sco).subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<UserStatus> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
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
}
