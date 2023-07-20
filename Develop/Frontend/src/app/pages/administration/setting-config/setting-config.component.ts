import { Component, HostListener, OnInit } from '@angular/core';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse, Config } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { ConfigSCO } from '@app/_common/_sco';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { ConfigService, SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { ConfigDialogService } from './_dialogs';

@Component({
  selector: 'app-setting-config',
  templateUrl: './setting-config.component.html',
  styleUrls: ['./setting-config.component.css'],
})
export class SettingConfigComponent implements OnInit {
  // Table elements
  pageResult: PageResultVO<Config>;
  currentSort: Sorter;
  currentFilterValue: string;
  selected = [];

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
    private authenticationService: AuthenticationService,
    private configDialogService: ConfigDialogService,
    private configService: ConfigService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {
    // Set default
    this.pageResult = new PageResultVO<Config>();
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
   * Handle select check box
   */
  onSelect({ selected }) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
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
    const sco = new ConfigSCO();
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
      const configKey = new SearchText();
      configKey.like = filterValue;
      sco.configKey = configKey;

      const configValue = new SearchText();
      configValue.like = filterValue;
      sco.configValue = configValue;

      const description = new SearchText();
      description.like = filterValue;
      sco.description = description;

      sco.searchOr = true;
    }

    // Show Loading
    this.spinner.show();

    // Search
    this.configService.search(sco).subscribe(
      (data) => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<Config> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<Config>();
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
    this.configDialogService.add('ADD', '').then(
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
   * Delete button
   */
  public delete() {
    if (this.selected.length > 0) {
      this.selected.forEach((element) => {
        this.deleteChoose(element);
      });
    } else {
      this.toastr.info('No selected item', 'Information', {
        timeOut: 2000,
      });
    }
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
  public viewDetailChoose(item: Config) {
    this.configDialogService.view('DETAIL', '', item).then(
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
  public editChoose(item: Config) {
    this.configDialogService.edit('EDIT', '', item).then(
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
  public deleteChoose(item: Config) {
    this.configDialogService
      .delete('DELETION', 'Are you sure deleting: ' + item.configKey + ' ?')
      .then(
        (result) => {
          if (result) {
            this.toastr.warning('[Admin notice] Not allow to delete');

            // this.configService.delete(item.id).subscribe(
            //   data => {
            //     // Send toast success
            //     this.toastr.success(
            //       "Your config key " + item.configKey + " is successful deleted"
            //     );

            //     // Refresh page
            //     if (result) {
            //       this.getPageResult(
            //         this.pageResult.currentPage,
            //         this.pageResult.pageSize,
            //         this.currentSort,
            //         this.currentFilterValue
            //       );
            //     }
            //   },
            //   error => {
            //     this.toastr.error(error);
            //   }
            // );
          }
        },
        (reason) => {
          console.log('DELETE reason:' + reason);
        },
      );
  }
}
