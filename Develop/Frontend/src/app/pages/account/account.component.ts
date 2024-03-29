import { Component, HostListener, OnInit } from '@angular/core';
import { AppSettings } from '@app/app.settings';

import { ToastrService } from 'ngx-toastr';
import { AlertService } from '@app/commons/alert/alert.service';
import { Account } from '@app/models/account';
import { ApiReponse } from '@app/models/response/apiReponse';
import { PageResultVO } from '@app/models/response/pageResultVO';
import { User } from '@app/models/user';
import { AccountSCO } from '@app/scos/accountSCO';
import { SearchText } from '@app/scos/core_sco/searchText';
import { Sorter } from '@app/scos/core_sco/sorter';
import { AuthenticationService } from '@app/services/authentication.service';
import { BackendService } from '@app/services/backend.service';
import { DialogService } from '@app/services/dialog.service';
import { SpinnerService } from '@app/services/spinner.service';
import { UtilsService } from '@app/services/utils.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
})
export class AccountComponent implements OnInit {
  /**
   * Constructor
   */
  constructor(
    private authenticationService: AuthenticationService,
    private dialogService: DialogService,
    private backendService: BackendService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
    public utilsService: UtilsService,
  ) {
    // Get user info
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );

    // Set default
    this.pageResult = new PageResultVO<Account>();
    this.pageResult.currentPage = 0;
    this.pageResult.pageSize = 10;
  }

  // Logged user
  currentUser: User;

  // Table elements
  pageResult: PageResultVO<Account>;
  currentSort: Sorter;
  currentFilterValue: string;

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
   * Initial
   */
  ngOnInit() {
    this.getPageResult(
      this.pageResult.currentPage,
      this.pageResult.pageSize,
      this.currentSort,
      this.currentFilterValue,
    );
  }

  /////////////////////////////////////
  // METHOD BELONG TO TABLE CONTROLE //
  /////////////////////////////////////
  /**
   * Handle filter event
   */
  onFilter(event) {
    this.currentFilterValue = event.target.value;
    this.getPageResult(
      0,
      this.pageResult.pageSize,
      null,
      this.currentFilterValue,
    );
  }

  /**
   * Handle sort event
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
   * Handle paginator event
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
   * Handle select page event
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
   * Get page result
   */
  public getPageResult(
    pageNumber: number,
    pageRecord: number,
    sorter: Sorter,
    filterValue: string,
  ) {
    // Prepare search condition
    const sco = new AccountSCO();
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;
    if (sorter) {
      const sorters: Array<Sorter> = [];
      sorters.push(sorter);
      sco.sorters = sorters;
    } else {
      const sorters: Array<Sorter> = [];
      sorters.push(new Sorter('id', 'ASC'));
      sco.sorters = sorters;
    }
    if (filterValue) {
      const accountTypeName = new SearchText();
      accountTypeName.like = filterValue;
      sco.accountTypeName = accountTypeName;

      const accountName = new SearchText();
      accountName.like = filterValue;
      sco.accountName = accountName;

      const note = new SearchText();
      note.like = filterValue;
      sco.note = note;

      sco.searchOr = true;
    }

    // Show Loading
    this.spinner.show();

    // Search
    this.backendService.searchAccount(sco).subscribe({
      next: async (res) => {
        const responseAPi: any = res;
        const typeResponse: ApiReponse<Account> = responseAPi;
        if (typeResponse.data != null && typeResponse.data.totalElement > 0) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<Account>();
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

  /////////////////////////////////////
  // METHOD BELONG TO UI CONTROLE //
  /////////////////////////////////////
  /**
   * Add button
   */
  public add() {
    this.dialogService.addAccount('ADD ACCOUNT', '').then(
      (result) => {
        if (result) {
          // Refresh table
          this.getPageResult(
            0,
            this.pageResult.pageSize,
            this.currentSort,
            this.currentFilterValue,
          );
        }
      },
      (reason) => {
        console.log('ADD reason:' + reason);
      },
    );
  }

  /**
   * Refresh button
   */
  public refresh() {
    this.getPageResult(0, this.pageResult.pageSize, null, null);
  }

  /**
   * Show password
   */
  public showPassword(item: Account) {
    if (item.mdpPlain) {
      let mdpPaint: string = item.mdpPlain;
      this.dialogService.showPassword(item).then(
        (result) => {
          if (result) {
          }
          item.mdpPlain = mdpPaint;
        },
        (reason) => {
          console.log('Show password reason:' + reason);
        },
      );
    } else {
      this.toastr.info('This account does not set password');
    }
  }

  /**
   * Edit chosen
   */
  public editChoose(item: Account) {
    this.dialogService.editAccount('EDIT ACCOUNT', '', item).then(
      (result) => {
        if (result) {
          this.getPageResult(
            this.pageResult.currentPage,
            this.pageResult.pageSize,
            this.currentSort,
            this.currentFilterValue,
          );
        }
      },
      (reason) => {
        console.log('EDIT reason:' + reason);
      },
    );
  }

  /**
   * Delete chosen
   */
  public deleteChoose(item: Account) {
    this.dialogService
      .deleteAccount(
        'DELETION',
        'Are you sure deleting: ' + item.accountName + ' ?',
        item,
      )
      .then(
        (result) => {
          if (result) {
            // Refresh page
            if (result) {
              this.getPageResult(
                this.pageResult.currentPage,
                this.pageResult.pageSize,
                this.currentSort,
                this.currentFilterValue,
              );
            }
          }
        },
        (reason) => {
          console.log('DELETE reason:' + reason);
        },
      );
  }

  /**
   * Open login URL
   */
  public openLoginUrl(account: Account) {
    switch (account.type.typeCode) {
      case AppSettings.ACCOUNT_TYPE_GMAIL:
      case AppSettings.ACCOUNT_TYPE_GOOGLE: {
        window.open(
          AppSettings.GOOGLE_LOGIN_URL + account.accountName + '#password',
        );
        break;
      }

      default: {
        this.toastr.info('In construction: You are opening login page');
        break;
      }
    }
  }

  /**
   * Open change password URL
   */
  public openChangePassWord(account: Account) {
    switch (account.type.typeCode) {
      case AppSettings.ACCOUNT_TYPE_GMAIL:
      case AppSettings.ACCOUNT_TYPE_GOOGLE: {
        window.open(AppSettings.GOOGLE_CHANGE_PASSWORD_URL);
        break;
      }

      default: {
        this.toastr.info('In construction: You are opening login page');
        break;
      }
    }
  }

  /**
   * Open reset password URL
   */
  public openResetPassWord(account: Account) {
    switch (account.type.typeCode) {
      case AppSettings.ACCOUNT_TYPE_GMAIL:
      case AppSettings.ACCOUNT_TYPE_GOOGLE: {
        window.open(AppSettings.GOOGLE_RESET_PASSWORD_URL);
        break;
      }

      default: {
        this.toastr.info('In construction: You are opening login page');
        break;
      }
    }
  }
}
