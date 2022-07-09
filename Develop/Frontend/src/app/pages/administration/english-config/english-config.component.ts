import { Component, HostListener, OnInit } from '@angular/core';
import { AppSettings } from '@app/appSettings';
import { EnglishDialogService } from '@app/pages/english/_dialogs';
import { English } from '@app/pages/english/_model';
import { EnglishService } from '@app/pages/english/_services';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { PageResultVO } from '@app/_common/_models/pageResultVO';
import { EnglishSCO } from '@app/_common/_sco';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-english-config',
  templateUrl: './english-config.component.html',
  styleUrls: ['./english-config.component.css']
})
export class EnglishConfigComponent implements OnInit {

  // Table elements
  pageResult: PageResultVO<English>;
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
   * Constructor
   */
  constructor(
    private authenticationService: AuthenticationService,
    private englishDialogService: EnglishDialogService,
    private englishService: EnglishService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService
  ) {
    // Set default
    this.pageResult = new PageResultVO<English>();
    this.pageResult.currentPage = 0;
    this.pageResult.pageSize = 20;
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
    const sco = new EnglishSCO();
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
      const keyWord = new SearchText();
      keyWord.like = filterValue;
      sco.keyWord = keyWord;
    }

    // Show Loading
    this.spinner.show();

    // Search
    this.englishService.searchEnglish(sco).subscribe(
      data => {
        const responseAPi: any = data;
        const typeResponse: ApiReponse<English> = responseAPi;
        if (typeResponse.data != null) {
          this.pageResult = typeResponse.data;
        } else {
          this.pageResult = new PageResultVO<English>();
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
    this.englishDialogService.addWord('Add word', '').then(
      result => {
        if (result) {
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
  public editChoosen(item: English) {
    this.englishDialogService.editWord('EDIT', '', item).then(
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
   * Download voice
   */
  public downloadVoice(english: English) {
    this.gotoCambridgeDictionary(english);

    this.englishDialogService
      .downloadVoice(
        'Download Voice',
        'You are downloading voice of word <' + english.keyWord + '>',
        english
      )
      .then(
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
          console.log('downloadVoice reason:' + reason);
        }
      );
  }

  /**
   * Go to google translate button
   */
  public gotoGoogleTranslate(english: English) {
    window.open(AppSettings.GOOGLE_TRANSLATE_WORD_URL + english.keyWord);
  }

  /**
   * Go to cambridge dictionary button
   */
  public gotoCambridgeDictionary(english: English) {
    window.open(
      AppSettings.CAMBRIDGE_DICTIONATY_WORD_URL +
      english.keyWord.split(' ').join('-')
    );
  }
}
