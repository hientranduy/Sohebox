import { Component, HostListener, OnInit } from '@angular/core';
import { AppSettings } from '@app/appSettings';
import { User } from '@app/user/_models';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService, UtilsService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { CryptoPortfolioDialogService } from '../_dialogs';
import { CryptoPortfolio } from '../_models';
import { CryptoPortfolioSCO } from '../_sco';
import { CryptoPortfolioService } from '../_services';

@Component({
  selector: 'app-crypto-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
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
    private toastr: ToastrService,
    private spinner: SpinnerService,
    public utilsService: UtilsService
  ) {
    // Get user info
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);

    // Set default
    this.pageResult = new PageResultVO<CryptoPortfolio>();
    this.pageResult.currentPage = 0;
    this.pageResult.pageSize = 10;
  }

  // Loading
  isLoading: Boolean;

  // Logged user
  currentUser: User;

  // Table elements
  pageResult: PageResultVO<CryptoPortfolio>;
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
      this.currentFilterValue
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
      this.currentFilterValue
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
      this.currentFilterValue
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
      this.currentFilterValue
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
      this.currentFilterValue
    );
  }

  /**
   * Get page result
   */
  public getPageResult(
    pageNumber: number,
    pageRecord: number,
    sorter: Sorter,
    filterValue: string
  ) {
    // Prepare search condition
    const sco = new CryptoPortfolioSCO();
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

    // Show Loading
    this.isLoading = true;
    this.spinner.show();

    // Search
    this.cryptoPortfolioService.search(sco).subscribe(
      data => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<CryptoPortfolio> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<CryptoPortfolio>();
        }

        // Hide Loading
        this.spinner.hide();
        this.isLoading = false;
      },
      error => {
        // Hide Loading
        this.spinner.hide();
        this.isLoading = false;

        this.alertService.error(error);
      }
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
      result => {
        if (result) {
          // Refresh table
          this.getPageResult(
            0,
            this.pageResult.pageSize,
            this.currentSort,
            this.currentFilterValue
          );
        }
      },
      reason => {
        console.log('ADD reason:' + reason);
      }
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
      result => {
        if (result) {
        }
      },
      reason => {
        console.log('DETAIL reason:' + reason);
      }
    );
  }

  /**
   * Edit chosen
   */
  public editChoose(item: CryptoPortfolio) {
    this.cryptoPortfolioDialogService.edit('EDIT WALLET', '', item).then(
      result => {
        if (result) {
          this.getPageResult(
            this.pageResult.currentPage,
            this.pageResult.pageSize,
            this.currentSort,
            this.currentFilterValue
          );
        }
      },
      reason => {
        console.log('EDIT reason:' + reason);
      }
    );
  }

  /**
   * Delete chosen
   */
  public deleteChoose(item: CryptoPortfolio) {
    this.cryptoPortfolioDialogService
      .delete(
        'DELETION',
        'Are you sure deleting: ' + item.wallet + ' ?',
        item
      )
      .then(
        result => {
          if (result) {
            // Refresh page
            if (result) {
              this.getPageResult(
                this.pageResult.currentPage,
                this.pageResult.pageSize,
                this.currentSort,
                this.currentFilterValue
              );
            }
          }
        },
        reason => {
          console.log('DELETE reason:' + reason);
        }
      );
  }

  /**
 * View explorer
 */
  public viewExplorer(item: CryptoPortfolio) {
    window.open(AppSettings.CRYPTO_MINTSCAN + item.token.addressPrefix + "/account/" + item.wallet);
  }
}
