import { Component, OnInit } from "@angular/core";
import { CryptoTokenConfig } from "@app/pages/crypto/_models";
import { CryptoTokenConfigSCO } from "@app/pages/crypto/_sco";
import { CryptoTokenConfigService } from "@app/pages/crypto/_services";
import { AuthenticationService } from "@app/user/_service";
import { AlertService } from "@app/_common/alert";
import { ApiReponse } from "@app/_common/_models";
import { PageResultVO } from "@app/_common/_models/pageResultVO";
import { SearchText, Sorter } from "@app/_common/_sco/core_sco";
import { SpinnerService } from "@app/_common/_services";
import { ToastrService } from "ngx-toastr";
import { CryptoTokenDialogService } from "./_dialogs";

@Component({
  selector: "app-crypto-token-config",
  templateUrl: "./crypto-token-config.component.html",
  styleUrls: ["./crypto-token-config.component.css"],
})
export class CryptoTokenConfigComponent implements OnInit {
  // Table elements
  pageResult: PageResultVO<CryptoTokenConfig>;
  currentSort: Sorter;
  currentFilterValue: string;

  /**
   * Constructor
   */
  constructor(
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
    private cryptoTokenService: CryptoTokenConfigService,
    private cryptoTokenDialogService: CryptoTokenDialogService,
  ) {
    // Set default
    this.pageResult = new PageResultVO<CryptoTokenConfig>();
    this.pageResult.currentPage = 0;
    this.pageResult.pageSize = 10;
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
    const sco = new CryptoTokenConfigSCO();
    sco.pageToGet = pageNumber;
    sco.maxRecordPerPage = pageRecord;

    if (sorter) {
      const sorters: Array<Sorter> = [];
      sorters.push(sorter);
      sco.sorters = sorters;
    } else {
      const sorters: Array<Sorter> = [];
      sorters.push(new Sorter("id", "ASC"));
      sco.sorters = sorters;
    }

    if (filterValue) {
      const tokenCode = new SearchText();
      tokenCode.like = filterValue;
      sco.tokenCode = tokenCode;

      const tokenName = new SearchText();
      tokenName.like = filterValue;
      sco.tokenName = tokenName;

      const denom = new SearchText();
      denom.like = filterValue;
      sco.denom = denom;

      const addressPrefix = new SearchText();
      addressPrefix.like = filterValue;
      sco.addressPrefix = addressPrefix;

      sco.searchOr = true;
    }

    // Show Loading
    this.spinner.show();

    // Search
    this.cryptoTokenService.search(sco).subscribe(
      (data) => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<CryptoTokenConfig> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<CryptoTokenConfig>();
        }

        // Hide Loading
        this.spinner.hide();
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
    this.cryptoTokenDialogService.add("Add token", "").then(
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
        console.log("ADD reason:" + reason);
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
   * Edit chosen
   */
  public editChoosen(item: CryptoTokenConfig) {
    this.cryptoTokenDialogService.edit("EDIT", "", item).then(
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
        console.log("EDIT reason:" + reason);
      },
    );
  }
}
