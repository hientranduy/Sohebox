import { Component, HostListener, OnInit } from '@angular/core';
import { AppSettings } from '@app/app.settings';
import { User } from '@app/_common/_models';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService, UtilsService } from '@app/_common/_services';
import { CryptoPortfolioDialogService } from '../_dialogs';
import { CryptoPortfolio, CryptoPortfolioHistory } from '../_models';
import { CryptoPortfolioHistorySCO, CryptoPortfolioSCO } from '../_sco';
import { CryptoPortfolioService } from '../_services';
import { AlertService } from '@app/_common/alert/alert.service';
import { AuthenticationService } from '@app/_common/_services/';

@Component({
  selector: 'app-crypto-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css'],
})
export class PortfolioComponent implements OnInit {
  /**
   * Constructor
   */
  constructor(
    private authenticationService: AuthenticationService,
    private cryptoPortfolioDialogService: CryptoPortfolioDialogService,
    private cryptoPortfolioService: CryptoPortfolioService,
    private alertService: AlertService,
    private spinner: SpinnerService,
    public utilsService: UtilsService,
  ) {
    // Get user info
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );

    // Set default
    this.pageResult = new PageResultVO<CryptoPortfolio>();
    this.pageResult.currentPage = 0;
    this.pageResult.pageSize = 50;

    // Set default history
    this.pageResultSummary = new PageResultVO<CryptoPortfolioHistory>();
    this.pageResultSummary.currentPage = 0;
    this.pageResultSummary.pageSize = 10;
  }

  // Logged user
  currentUser: User;

  // Select mode
  isDeligatorMode: Boolean = false;
  isValidatorMode: Boolean = false;
  isSummaryMode: Boolean = true;

  // Table elements
  pageResult: PageResultVO<CryptoPortfolio>;
  currentSort: Sorter;
  currentFilterValue: string;

  pageResultSummary: PageResultVO<CryptoPortfolioHistory>;
  currentSummarySort: Sorter;

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

    this.getPageResultSummary(
      this.pageResultSummary.currentPage,
      this.pageResultSummary.pageSize,
      this.currentSummarySort,
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
   * Handle sort event summary
   */
  onSortSummary(event) {
    const sort = event.sorts[0];
    this.currentSummarySort = new Sorter(sort.prop, sort.dir);
    this.getPageResultSummary(
      0,
      this.pageResultSummary.pageSize,
      this.currentSummarySort,
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
   * Handle paginator event summary
   */
  handleSummaryPaginatorEvent(e: any) {
    this.getPageResultSummary(e.pageIndex, e.pageSize, this.currentSummarySort);
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
   * Handle select page event summary
   */
  setPageSummary(pageInfo) {
    this.getPageResultSummary(
      pageInfo.offset,
      this.pageResultSummary.pageSize,
      this.currentSummarySort,
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
    this.alertService.clear();

    // Prepare search condition
    const sco = new CryptoPortfolioSCO();
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;
    if (sorter) {
      const sorters: Array<Sorter> = [];
      sorters.push(sorter);
      if (sorter.property === 'token') {
        sorters.push(new Sorter('starname', 'ASC'));
      }
      sco.sorters = sorters;
    } else {
      const sorters: Array<Sorter> = [];
      // sorters.push(new Sorter('id', 'ASC'));
      sorters.push(new Sorter('token', 'ASC'));
      sorters.push(new Sorter('starname', 'ASC'));
      sco.sorters = sorters;
    }
    if (filterValue) {
      const tokenCode = new SearchText();
      tokenCode.like = filterValue;
      sco.tokenCode = tokenCode;

      const wallet = new SearchText();
      wallet.like = filterValue;
      sco.wallet = wallet;

      const starname = new SearchText();
      starname.like = filterValue;
      sco.starname = starname;

      sco.searchOr = true;
    }

    // Search
    this.cryptoPortfolioService.search(sco).subscribe(
      (data) => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<CryptoPortfolio> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<CryptoPortfolio>();
        }
      },
      (error) => {
        // Hide Loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  /**
   * Get page result summary
   */
  public getPageResultSummary(
    pageNumber: number,
    pageRecord: number,
    sorter: Sorter,
  ) {
    this.alertService.clear();

    // Prepare search condition
    const sco = new CryptoPortfolioHistorySCO();
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;
    if (sorter) {
      const sorters: Array<Sorter> = [];
      sorters.push(sorter);
      sco.sorters = sorters;
    } else {
      const sorters: Array<Sorter> = [];
      sorters.push(new Sorter('token', 'ASC'));
      sco.sorters = sorters;
    }

    // Search
    this.cryptoPortfolioService.getPortfolioSummary(sco).subscribe(
      (data) => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<CryptoPortfolioHistory> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResultSummary = typeResponse.data;
        } else {
          this.pageResultSummary = new PageResultVO<CryptoPortfolioHistory>();
        }
      },
      (error) => {
        // Hide Loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  /////////////////////////////////////
  // METHOD BELONG TO UI CONTROLE //
  /////////////////////////////////////
  /**
   * Add button
   */
  public add() {
    this.cryptoPortfolioDialogService.add('ADD WALLET', '').then(
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
   * View detail chosen
   */
  public viewDetailChoose(item: CryptoPortfolio) {
    this.cryptoPortfolioDialogService.view('DETAIL WALLET', '', item).then(
      (result) => {
        if (result) {
        }
      },
      (reason) => {
        console.log('DETAIL reason:' + reason);
      },
    );
  }

  /**
   * Edit chosen
   */
  public editChoose(item: CryptoPortfolio) {
    this.cryptoPortfolioDialogService.edit('EDIT WALLET', '', item).then(
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
  public deleteChoose(item: CryptoPortfolio) {
    this.cryptoPortfolioDialogService
      .delete('DELETION', 'Are you sure deleting: ' + item.wallet + ' ?', item)
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
   * View explorer
   */
  public viewExplorer(item: CryptoPortfolio) {
    window.open(
      AppSettings.CRYPTO_MINTSCAN +
        item.token.mintscanPrefix +
        '/account/' +
        item.wallet,
    );
  }

  /**
   * View validator
   */
  public viewValidator(item: CryptoPortfolio) {
    window.open(
      AppSettings.CRYPTO_MINTSCAN +
        item.token.mintscanPrefix +
        '/validators/' +
        item.validator.validatorAddress,
    );
  }

  /**
   * Go deligate
   */
  public deligatePage(item: CryptoPortfolio) {
    window.open(item.token.deligateUrl);
  }

  /**
   * Select view mode
   */
  public selectDeligatorMode() {
    this.isDeligatorMode = true;
    this.isValidatorMode = false;
    this.isSummaryMode = false;
  }

  public selectValidatorMode() {
    this.isDeligatorMode = false;
    this.isValidatorMode = true;
    this.isSummaryMode = false;
  }

  public selectSummaryMode() {
    this.isDeligatorMode = false;
    this.isValidatorMode = false;
    this.isSummaryMode = true;
  }
}
