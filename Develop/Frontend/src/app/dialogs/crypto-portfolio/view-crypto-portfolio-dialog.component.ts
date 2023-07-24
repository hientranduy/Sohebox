import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { CryptoPortfolio } from '@app/models/cryptoPortfolio';
import { CryptoTokenConfig } from '@app/models/cryptoTokenConfig';
import { RequireMatchForm } from '@app/services/requireMatchForm';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';

@Component({
  styleUrls: ['view-crypto-portfolio-dialog.component.css'],
  templateUrl: 'view-crypto-portfolio-dialog.component.html',
})
export class ViewCryptoPortfolioDialogComponent implements OnInit {
  constructor(private activeModal: NgbActiveModal) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() cryptoPortfolio: CryptoPortfolio;

  // Field token
  tokenValue: CryptoTokenConfig;
  filteredTokens: Observable<CryptoTokenConfig[]>;
  tokenFormControl = new FormControl('', [
    Validators.required,
    RequireMatchForm,
  ]);

  // Field wallet
  walletValue: string;
  walletFormControl = new FormControl('', [Validators.required]);

  // Field starname
  starnameValue: string;
  starnameFormControl = new FormControl('', []);

  ngOnInit() {
    // Set current value
    this.tokenValue = this.cryptoPortfolio.token;
    this.walletValue = this.cryptoPortfolio.wallet;
    this.starnameValue = this.cryptoPortfolio.starname;
  }

  public displayToken(item: CryptoTokenConfig) {
    if (item) {
      return item.tokenCode + ' (' + item.tokenName + ')';
    }
    return null;
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
}
