import { Component, Input, OnInit } from '@angular/core';
import { Account } from '@app/models/account';
import { ApiReponse } from '@app/models/apiReponse';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  styleUrls: ['show-password-dialog.component.css'],
  templateUrl: 'show-password-dialog.component.html',
})
export class ShowPasswordDialogComponent implements OnInit {
  constructor(
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private spinner: SpinnerService,
  ) {}

  // Form value
  @Input() messageError: string;
  @Input() btnCancelText: string;
  @Input() account: Account;

  displayPassword: String;
  privateKey: string;

  ngOnInit() {
    this.displayPassword = this.account.mdp;
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

  public checkPrivateKey() {
    if (this.privateKey) {
      this.account.user.privateKey = this.privateKey;

      // Show Loading
      this.spinner.show();

      this.backendService.showPassword(this.account).subscribe(
        (data) => {
          // Get data
          const responseAPi: any = data;
          const typeResponse: ApiReponse<Account> = responseAPi;
          if (typeResponse.data != null) {
            const accounts: Account[] = typeResponse.data.elements;
            this.displayPassword = accounts[0].mdp;
          }

          if (typeResponse.message) {
            this.messageError = typeResponse.message;
            this.displayPassword = this.account.mdp;
          } else {
            this.messageError = '';
          }

          // Hide Loading
          this.spinner.hide();
        },
        (error) => {
          // Hide Loading
          this.spinner.hide();
        },
      );
    } else {
      this.messageError = 'Input missing';
    }
  }
}
