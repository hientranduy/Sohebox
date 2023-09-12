import { Component, Input, OnInit } from '@angular/core';
import { ApiReponse } from '@app/models/response/apiReponse';
import { EnglishLearnRecord } from '@app/models/englishLearnRecord';
import { User } from '@app/models/user';
import { SearchDate } from '@app/scos/core_sco/searchDate';
import { SearchNumber } from '@app/scos/core_sco/searchNumber';
import { EnglishLearnRecordSCO } from '@app/scos/englishLearnRecordSCO';
import { AuthenticationService } from '@app/services/authentication.service';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: 'show-learned-word.component.html',
})
export class ShowLearnedWordComponent implements OnInit {
  constructor(
    private activeModal: NgbActiveModal,
    private authenticationService: AuthenticationService,
    private backendService: BackendService,
    private spinner: SpinnerService,
  ) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );

    // Get learned record
    this.getLearnedListByToday();
  }

  // Current user
  currentUser: User;

  // Learned word list
  learnedRecords: EnglishLearnRecord[];

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() btnCancelText: string;

  ngOnInit() {}

  /**
   * Get learned record list by today
   */
  public getLearnedListByToday() {
    // Prepare search condition
    const updatedDateSearch = new SearchDate();
    updatedDateSearch.eq = new Date();
    const userIdSearch = new SearchNumber();
    userIdSearch.eq = this.currentUser.id;
    const sco = new EnglishLearnRecordSCO();
    sco.updatedDate = updatedDateSearch;
    sco.userId = userIdSearch;

    // Show Loading
    this.spinner.show();

    // Search
    this.backendService.searchLearnRecord(sco).subscribe({
      next: async (res) => {
        // Get data
        const responseAPi: any = res;
        const typeResponse: ApiReponse<EnglishLearnRecord> = responseAPi;
        if (typeResponse.data != null) {
          this.learnedRecords = typeResponse.data.elements;
        }

        // Hide Loading
        this.spinner.hide();
      },
      error: (err) => {
        this.processError(err);
      },
    });
  }

  /**
   * Process error in case have error call API
   */
  public processError(error: any) {
    // Hide Loading
    this.spinner.hide();

    // Close dialog
    this.activeModal.close(false);
  }

  /////////////////////////////////////
  // FORM BUTTON CONTROL             //
  /////////////////////////////////////
  /**
   * Click decline button
   */
  public decline() {
    this.activeModal.close(false);
  }

  /**
   * Click dismiss button
   */
  public dismiss() {
    this.activeModal.dismiss();
  }

  /**
   * Click accept button
   */
  public accept() {
    this.activeModal.close(true);
  }
}
