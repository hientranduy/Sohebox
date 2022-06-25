import { Component, OnInit, HostListener } from '@angular/core';
import { EnglishTypeService } from '@app/pages/english/_services';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { EnglishType } from '@app/_common/_models/englishType';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { EnglishTypeSCO } from '@app/_common/_sco/englishTypeSCO';
import { SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { EnglishTypeDialogService } from './_dialogs';

@Component({
  selector: 'app-english-type',
  templateUrl: './english-type.component.html',
  styleUrls: ['./english-type.component.css']
})
export class EnglishTypeComponent implements OnInit {
  // Loading
  isLoading: Boolean;

  // Table elements
  pageResult: PageResultVO<EnglishType>;
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
    private englishTypeDialogService: EnglishTypeDialogService,
    private englishTypeService: EnglishTypeService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService
  ) {
    // Set default
    this.pageResult = new PageResultVO<EnglishType>();
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
    filterValue: string
  ) {
    // Prepare search condition
    const sco = new EnglishTypeSCO();
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
      const typeClass = new SearchText();
      typeClass.like = filterValue;
      sco.typeClass = typeClass;

      const typeCode = new SearchText();
      typeCode.like = filterValue;
      sco.typeCode = typeCode;

      const typeName = new SearchText();
      typeName.like = filterValue;
      sco.typeName = typeName;

      const description = new SearchText();
      description.like = filterValue;
      sco.description = description;

      sco.searchOr = true;
    }

    // Show Loading
    this.isLoading = true;
    this.spinner.show();

    // Search
    this.englishTypeService.search(sco).subscribe(
      data => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishType> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<EnglishType>();
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
    this.toastr.warning('[Admin notice] Not allow to create');
    // this.englishTypeDialogService.add("ADD", "").then(
    //   result => {
    //     if (result) {
    //       // Refresh table
    //       this.getPageResult(
    //         0,
    //         this.pageResult.pageSize,
    //         this.currentSort,
    //         this.currentFilterValue
    //       );
    //     }
    //   },
    //   reason => {
    //     console.log("ADD reason:" + reason);
    //   }
    // );
  }

  /**
   * Delete button
   */
  public delete() {
    if (this.selected.length > 0) {
      this.selected.forEach(element => {
        this.deleteChoose(element);
      });
    } else {
      this.toastr.info('No selected item', 'Information', {
        timeOut: 2000
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
  public viewDetailChoose(item: EnglishType) {
    this.englishTypeDialogService.view('DETAIL', '', item).then(
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
  public editChoose(item: EnglishType) {
    this.englishTypeDialogService.edit('EDIT', '', item).then(
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
  public deleteChoose(item: EnglishType) {
    this.englishTypeDialogService
      .delete('DELETION', 'Are you sure deleting: ' + item.typeCode + ' ?')
      .then(
        result => {
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
        reason => {
          console.log('DELETE reason:' + reason);
        }
      );
  }
}