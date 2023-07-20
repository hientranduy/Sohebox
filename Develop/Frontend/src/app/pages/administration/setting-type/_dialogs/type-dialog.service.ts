import { Injectable } from "@angular/core";
import { Type } from "@app/_common/_models";
import { NgbModal, NgbModalConfig } from "@ng-bootstrap/ng-bootstrap";
import { AddTypeDialogComponent } from "./add-type-dialog.component";
import { DeleteTypeDialogComponent } from "./delete-type-dialog.component";
import { EditTypeDialogComponent } from "./edit-type-dialog.component";
import { ViewTypeDialogComponent } from "./view-type-dialog.component";

@Injectable({ providedIn: "root" })
export class TypeDialogService {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
  ) {
    config.backdrop = "static";
    config.keyboard = false;
  }

  public add(
    title: string,
    message: string,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddTypeDialogComponent, {
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
    const modalRef = this.modalService.open(DeleteTypeDialogComponent, {
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
    type: Type,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Edit";
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.type = type;
    return modalRef.result;
  }

  public view(
    title: string,
    message: string,
    type: Type,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.type = type;
    return modalRef.result;
  }
}
