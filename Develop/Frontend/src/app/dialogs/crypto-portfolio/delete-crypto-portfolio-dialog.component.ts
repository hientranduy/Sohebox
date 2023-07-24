import { Component, Input, OnInit } from '@angular/core';
import { CryptoPortfolio } from '@app/models/cryptoPortfolio';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  templateUrl: 'delete-crypto-portfolio-dialog.component.html',
})
export class DeleteCryptoPortfolioDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() cryptoPortfolio: CryptoPortfolio;

  constructor(
    private backendService: BackendService,
    private toastr: ToastrService,
    private activeModal: NgbActiveModal,
    private spinner: SpinnerService,
  ) {}
  a;

  ngOnInit() {}

  public decline() {
    // Return false
    this.activeModal.close(false);
  }

  public accept() {
    // Show loading
    this.spinner.show();

    const infoData =
      this.cryptoPortfolio.wallet +
      '<' +
      this.cryptoPortfolio.token.tokenCode +
      '>';
    this.backendService
      .deleteCryptoPortfolio(this.cryptoPortfolio.id)
      .subscribe(
        (data) => {
          // Send toast success
          this.toastr.success(
            'Your wallet ' + infoData + ' is successful deleted',
          );

          // Hide loading
          this.spinner.hide();

          // Return true
          this.activeModal.close(true);
        },
        (error) => {
          // Hide loading
          this.spinner.hide();

          // Send error toast message
          this.toastr.error(error);

          // Return false
          this.activeModal.close(false);
        },
      );
  }

  public dismiss() {
    // Return false
    this.activeModal.dismiss();
  }
}
