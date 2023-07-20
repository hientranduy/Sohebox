import { Component, Input, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { User } from "@app/user/_models";
import { AuthenticationService } from "@app/user/_service";
import { ApiReponse } from "@app/_common/_models";
import { EnglishLearnRecordSCO } from "@app/_common/_sco";
import { SearchDate, SearchNumber } from "@app/_common/_sco/core_sco";
import { SpinnerService } from "@app/_common/_services";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { EnglishLearnRecord } from "../_model";
import { EnglishService } from "../_services";

@Component({
  styleUrls: ["show-learned-word.component.css"],
  templateUrl: "show-learned-word.component.html",
})
export class ShowLearnedWordComponent implements OnInit {
  constructor(
    private router: Router,
    private activeModal: NgbActiveModal,
    private authenticationService: AuthenticationService,
    private englishService: EnglishService,
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
    this.englishService.searchLearnRecord(sco).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishLearnRecord> = responseAPi;
        if (typeResponse.data != null) {
          this.learnedRecords = typeResponse.data.elements;
        }

        // Hide Loading
        this.spinner.hide();
      },
      (error) => {
        this.processError(error);
      },
    );
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
