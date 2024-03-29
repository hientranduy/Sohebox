import { Component, HostListener, OnInit } from '@angular/core';
import { MediaType } from '@app/models/mediaType';
import { PageResultVO } from '@app/models/response/pageResultVO';
import { MediaTypeSCO } from '@app/scos/mediaTypeSCO';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/response/apiReponse';
import { SearchText } from '@app/scos/core_sco/searchText';
import { Sorter } from '@app/scos/core_sco/sorter';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { DialogService } from '@app/services/dialog.service';
import { SelectionType } from '@swimlane/ngx-datatable';

@Component({
  selector: 'app-media-type',
  templateUrl: './media-type.component.html',
})
export class MediaTypeComponent implements OnInit {
  // Table elements
  pageResult: PageResultVO<MediaType>;
  currentSort: Sorter;
  currentFilterValue: string;
  selected = [];
  checkbox: SelectionType = SelectionType.checkbox;

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
    private dialogService: DialogService,
    private alertService: AlertService,
    private spinner: SpinnerService,
    private backendService: BackendService,
  ) {
    // Set default
    this.pageResult = new PageResultVO<MediaType>();
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
    const sco = new MediaTypeSCO();
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
    this.spinner.show();

    // Search
    this.backendService.searchMediaType(sco).subscribe({
      next: async (res) => {
        const responseAPi: any = res;
        const typeResponse: ApiReponse<MediaType> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<MediaType>();
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
   * Refresh button
   */
  public refresh() {
    this.getPageResult(0, this.pageResult.pageSize, null, null);
  }

  /**
   * Edit chosen
   */
  public editChoose(item: MediaType) {
    this.dialogService.editMediaType('EDIT', '', item).then(
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
}
