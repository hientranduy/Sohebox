import { Injectable } from "@angular/core";
import { NgbModal, NgbModalConfig } from "@ng-bootstrap/ng-bootstrap";
import { Account } from "../_models";
import { AddAccountDialogComponent } from "./add-account-dialog.component";
import { DeleteAccountDialogComponent } from "./delete-account-dialog.component";
import { EditAccountDialogComponent } from "./edit-account-dialog.component";
import { ShowPasswordDialogComponent } from "./show-password-dialog.component";
import { ViewAccountDialogComponent } from "./view-account-dialog.component";

@Injectable({ providedIn: "root" })
export class AccountDialogService {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
  ) {
    config.backdrop = "static";
    config.keyboard = false;
  }

  public addAccount(
    title: string,
    message: string,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Add";
    modalRef.componentInstance.btnCancelText = "Cancel";
    return modalRef.result;
  }

  public deleteAccount(
    title: string,
    message: string,
    account: Account,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Delete";
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }

  public editAccount(
    title: string,
    message: string,
    account: Account,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Edit";
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }

  public viewAccount(
    title: string,
    message: string,
    account: Account,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }

  public showPassword(
    account: Account,
    dialogSize: "sm" | "sm" = "sm",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ShowPasswordDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }
}
