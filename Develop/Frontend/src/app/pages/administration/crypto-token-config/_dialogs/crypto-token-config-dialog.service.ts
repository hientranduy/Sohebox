import { Injectable } from '@angular/core';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { AddCryptoTokenConfigDialogComponent } from './add-crypto-token-config-dialog.component';
import { EditCryptoTokenConfigDialogComponent } from './edit-crypto-token-config-dialog.component';
import { CryptoTokenConfig } from '@app/_common/_models';

@Injectable({ providedIn: 'root' })
export class CryptoTokenDialogService {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public add(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(
      AddCryptoTokenConfigDialogComponent,
      {
        size: dialogSize,
      },
    );
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public edit(
    title: string,
    message: string,
    cryptoTokenConfig: CryptoTokenConfig,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(
      EditCryptoTokenConfigDialogComponent,
      {
        size: dialogSize,
      },
    );
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoTokenConfig = cryptoTokenConfig;
    return modalRef.result;
  }
}
