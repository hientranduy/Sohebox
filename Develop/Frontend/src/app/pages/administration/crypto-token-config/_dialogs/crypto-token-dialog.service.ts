import { Injectable } from '@angular/core';
import { CryptoToken } from '@app/_common/_models';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { AddCryptoTokenDialogComponent } from './add-crypto-token-dialog.component';
import { EditCryptoTokenDialogComponent } from './edit-crypto-token-dialog.component';

@Injectable({ providedIn: 'root' })
export class CryptoTokenDialogService {
  constructor(config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public add(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg'
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddCryptoTokenDialogComponent, {
      size: dialogSize
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public edit(
    title: string,
    message: string,
    cryptoToken: CryptoToken,
    dialogSize: 'sm' | 'lg' = 'lg'
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditCryptoTokenDialogComponent, {
      size: dialogSize
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.CryptoToken = cryptoToken;
    return modalRef.result;
  }
}
