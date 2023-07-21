import { Component, OnInit } from '@angular/core';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { FoodSCO } from '@app/_common/_sco/foodSCO';
import { SpinnerService } from '@app/_common/_services';
import { AlertService } from '@app/_common/alert/alert.service';
import { FoodDialogService } from '@app/pages/food/_dialogs';
import { Food } from '@app/pages/food/_model';
import { FoodService } from '@app/pages/food/_services';

@Component({
  selector: 'app-food-config',
  templateUrl: './food-config.component.html',
  styleUrls: ['./food-config.component.css'],
})
export class FoodConfigComponent implements OnInit {
  // Table elements
  pageResult: PageResultVO<Food>;
  currentSort: Sorter;
  currentFilterValue: string;

  /**
   * Constructor
   */
  constructor(
    private foodDialogService: FoodDialogService,
    private foodService: FoodService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {
    // Set default
    this.pageResult = new PageResultVO<Food>();
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
    const sco = new FoodSCO();
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
      const name = new SearchText();
      name.like = filterValue;
      sco.name = name;
    }

    // Show Loading
    this.spinner.show();

    // Search
    this.foodService.searchFood(sco).subscribe(
      (data) => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<Food> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<Food>();
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
    this.foodDialogService.add('Add word', '').then(
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
   * Edit chosen
   */
  public editChoosen(item: Food) {
    this.foodDialogService.edit('EDIT', '', item).then(
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
