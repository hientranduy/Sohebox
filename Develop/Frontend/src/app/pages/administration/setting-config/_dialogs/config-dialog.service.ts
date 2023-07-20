import { Injectable } from "@angular/core";
import { Config } from "@app/_common/_models";
import { NgbModal, NgbModalConfig } from "@ng-bootstrap/ng-bootstrap";
import { AddConfigDialogComponent } from "./add-config-dialog.component";
import { DeleteConfigDialogComponent } from "./delete-config-dialog.component";
import { EditConfigDialogComponent } from "./edit-config-dialog.component";
import { ViewConfigDialogComponent } from "./view-config-dialog.component";

@Injectable({ providedIn: "root" })
export class ConfigDialogService {
  constructor(
    modal: NgbModalConfig,
    private modalService: NgbModal,
  ) {
    modal.backdrop = "static";
    modal.keyboard = false;
  }

  public add(
    title: string,
    message: string,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddConfigDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Add";
    modalRef.componentInstance.btnCancelText = "Cancel";
    return modalRef.result;
  }

  public delete(
    title: string,
    message: string,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteConfigDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Delete";
    modalRef.componentInstance.btnCancelText = "Cancel";
    return modalRef.result;
  }

  public edit(
    title: string,
    message: string,
    item: Config,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditConfigDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Edit";
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  public view(
    title: string,
    message: string,
    item: Config,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewConfigDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }
}
