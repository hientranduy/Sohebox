import { Component, OnInit } from '@angular/core';
import { YoutubeChannel } from '@app/pages/media/_models';
import { YoutubeService } from '@app/pages/media/_services';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { YoutubeChannelSCO } from '@app/_common/_sco';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { YoutubeChannelDialogService } from './_dialogs';

@Component({
  selector: 'app-youtube-channel-config',
  templateUrl: './youtube-channel-config.component.html',
  styleUrls: ['./youtube-channel-config.component.css']
})
export class YoutubeChannelConfigComponent implements OnInit {
  // Table elements
  pageResult: PageResultVO<YoutubeChannel>;
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
    private youtubeService: YoutubeService,
    private youtubeChannelDialogService: YoutubeChannelDialogService
  ) {
    // Set default
    this.pageResult = new PageResultVO<YoutubeChannel>();
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
   * Get page result
   */
  public getPageResult(
    pageNumber: number,
    pageRecord: number,
    sorter: Sorter,
    filterValue: string
  ) {
    // Prepare search condition
    const sco = new YoutubeChannelSCO();
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
      const channelId = new SearchText();
      channelId.like = filterValue;
      sco.channelId = channelId;

      const name = new SearchText();
      name.like = filterValue;
      sco.name = name;

      const description = new SearchText();
      description.like = filterValue;
      sco.description = description;
    }

    // Show Loading
    this.spinner.show();

    // Search
    this.youtubeService.searchChannel(sco).subscribe(
      data => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<YoutubeChannel> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<YoutubeChannel>();
        }

        // Hide Loading
        this.spinner.hide();
      },
      error => {
        // Hide Loading
        this.spinner.hide();

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
    this.youtubeChannelDialogService.add('Add channel', '').then(
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
   * Edit chosen
   */
  public editChoosen(item: YoutubeChannel) {
    this.youtubeChannelDialogService.edit('EDIT', '', item).then(
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
}
