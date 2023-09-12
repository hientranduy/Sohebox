import { Component, Input, OnInit } from '@angular/core';
import { Account } from '@app/models/account';
import { ApiReponse } from '@app/models/response/apiReponse';
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
    this.displayPassword = this.account.mdpPlain;
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
      this.account.mdpPlain = this.privateKey;

      // Show Loading
      this.spinner.show();

      this.backendService.showPassword(this.account).subscribe({
        next: async (res) => {
          // Get data
          const responseAPi: any = res;
          const typeResponse: ApiReponse<Account> = responseAPi;
          if (typeResponse.data != null) {
            const accounts: Account[] = typeResponse.data.elements;
            this.displayPassword = accounts[0].mdpPlain;
          }

          if (typeResponse.message) {
            this.messageError = typeResponse.message;
          } else {
            this.messageError = '';
          }

          // Hide Loading
          this.spinner.hide();
        },
        error: (err) => {
          // Hide Loading
          this.spinner.hide();
        },
      });
    } else {
      this.messageError = 'Input missing';
    }
  }
}
