import { Component, OnDestroy, OnInit, HostListener } from '@angular/core';
import { UserStatus } from '@app/user/_models';
import { UserService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { UserSCO } from '@app/_common/_sco';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-control',
  templateUrl: './user-control.component.html',
  styleUrls: ['./user-control.component.css']
})
export class UserControlComponent implements OnInit, OnDestroy {
  isLoadingUserControl: Boolean = false;
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
    private userService: UserService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService
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
    this.getPageResult(pageInfo.offset, this.pageResult.pageSize, this.currentSort, this.currentFilterValue);
  }

  /**
  * handle event of sort
  */
  onSort(event) {
    const sort = event.sorts[0];
    this.currentSort = new Sorter(sort.prop, sort.dir);
    this.getPageResult(0, this.pageResult.pageSize, this.currentSort, this.currentFilterValue);

  }

  /**
  * handle event of filter
  */
  onFilter(event) {
    this.currentFilterValue = event.target.value;
    this.getPageResult(0, this.pageResult.pageSize, this.currentSort, this.currentFilterValue);
  }

  /**
  * handle event of paginator
  */
  handlePaginatorEvent(e: any) {
    this.getPageResult(e.pageIndex, e.pageSize, this.currentSort, this.currentFilterValue);
  }

  /**
   * Get page result
   */
  public getPageResult(pageNumber: number, pageRecord: number, sorter: Sorter, filterValue: string) {
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
    this.isLoadingUserControl = true;
    this.spinner.show();

    // Search
    this.userService.searchUserStatus(sco)
      .subscribe(data => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<UserStatus> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<UserStatus>();
        }

        // Hide Loading
        this.spinner.hide();
        this.isLoadingUserControl = false;

      }, error => {
        // Hide Loading
        this.spinner.hide();
        this.isLoadingUserControl = false;

        this.alertService.error(error);
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
    this.isLoadingUserControl = true;
    this.spinner.show();

    // Search
    this.userService.searchActiveUser(sco)
      .subscribe(data => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<UserStatus> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        }

        // Hide Loading
        this.spinner.hide();
        this.isLoadingUserControl = false;

      }, error => {
        // Hide Loading
        this.spinner.hide();
        this.isLoadingUserControl = false;

        this.alertService.error(error);
      });
  }
}
