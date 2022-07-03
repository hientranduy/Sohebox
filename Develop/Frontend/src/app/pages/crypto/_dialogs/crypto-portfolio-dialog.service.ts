import { Injectable } from '@angular/core';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { CryptoPortfolio } from '../_models';
import { AddCryptoFortfolioDialogComponent } from './add-crypto-portfolio-dialog.component';
import { DeleteCryptoPortfolioDialogComponent } from './delete-crypto-portfolio-dialog.component';
import { EditCryptoPortfolioDialogComponent } from './edit-crypto-portfolio-dialog.component';
import { ViewCryptoPortfolioDialogComponent } from './view-crypto-portfolio-dialog.component';

@Injectable({ providedIn: 'root' })
export class CryptoPortfolioDialogService {

  constructor(config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public add(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(AddCryptoFortfolioDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public delete(
    title: string,
    message: string,
    cryptoPortfolio: CryptoPortfolio,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteCryptoPortfolioDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoPortfolio = cryptoPortfolio;
    return modalRef.result;
  }

  public edit(
    title: string,
    message: string,
    cryptoPortfolio: CryptoPortfolio,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(EditCryptoPortfolioDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoPortfolio = cryptoPortfolio;
    return modalRef.result;
  }

  public view(
    title: string,
    message: string,
    cryptoPortfolio: CryptoPortfolio,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(ViewCryptoPortfolioDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoPortfolio = cryptoPortfolio;
    return modalRef.result;
  }
}