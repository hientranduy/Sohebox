import { Injectable } from '@angular/core';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { ChangePasswordDialogComponent } from './change-password-dialog.component';
import { ChangePrivateKeyDialogComponent } from './change-private-key-dialog.component';
import { DeleteConfirmationDialogComponent } from './delete-confirmation-dialog.component';
import { HelpGuestDialogComponent } from './help-guest-dialog.component';
import { UpdateEnglishLevelDialogComponent } from './update-english-level-dialog.component';
import { UpdateInforDialogComponent } from './update-infor-dialog.component';

@Injectable({ providedIn: 'root' })
export class UserDialogService {

  constructor(config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public deleteUser(
    title: string,
    message: string,
    information: string,
    dialogSize: 'sm' | 'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteConfirmationDialogComponent, { size: 'lg' });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public changePassword(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(ChangePasswordDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Change';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public updateUser(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(UpdateInforDialogComponent, { size: 'lg' });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Update';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public updateEnglishLevel(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(UpdateEnglishLevelDialogComponent, { size: 'lg' });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Update';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public openHelpGuest(
    dialogSize: 'sm' | 'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(HelpGuestDialogComponent, { size: 'lg' });
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public changePrivateKey(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(ChangePrivateKeyDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Change';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }
}
