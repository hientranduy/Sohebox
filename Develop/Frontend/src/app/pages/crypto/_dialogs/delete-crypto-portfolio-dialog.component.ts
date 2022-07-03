import { Component, Input, OnInit } from '@angular/core';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { CryptoPortfolio } from '../_models';
import { CryptoPortfolioService } from '../_services';

@Component({
  templateUrl: 'delete-crypto-portfolio-dialog.component.html',
})
export class DeleteCryptoPortfolioDialogComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() cryptoPortfolio: CryptoPortfolio;

  isLoading: Boolean;

  constructor(
    private cryptoPortfolioService: CryptoPortfolioService,
    private toastr: ToastrService,
    private activeModal: NgbActiveModal,
    private spinner: SpinnerService
  ) { } a

  ngOnInit() {
  }

  public decline() {
    // Return false
    this.activeModal.close(false);
  }

  public accept() {

    // Show loading
    this.isLoading = true;
    this.spinner.show();

    const infoData = this.cryptoPortfolio.wallet + '<' + this.cryptoPortfolio.token.tokenCode + '>';
    this.cryptoPortfolioService.delete(this.cryptoPortfolio.id)
      .subscribe(
        data => {
          // Send toast success
          this.toastr.success('Your wallet ' + infoData + ' is successful deleted');

          // Hide loading
          this.isLoading = false;
          this.spinner.hide();

          // Return true
          this.activeModal.close(true);
        },
        error => {
          // Hide loading
          this.isLoading = false;
          this.spinner.hide();

          // Send error toast message
          this.toastr.error(error);

          // Return false
          this.activeModal.close(false);
        });
  }

  public dismiss() {
    // Return false
    this.activeModal.dismiss();
  }
}
