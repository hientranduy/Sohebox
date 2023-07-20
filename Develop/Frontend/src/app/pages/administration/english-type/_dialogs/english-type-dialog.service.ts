import { Injectable } from "@angular/core";
import { EnglishType } from "@app/_common/_models";
import { NgbModal, NgbModalConfig } from "@ng-bootstrap/ng-bootstrap";
import { AddEnglishTypeDialogComponent } from "./add-english-type-dialog.component";
import { DeleteEnglishTypeDialogComponent } from "./delete-english-type-dialog.component";
import { EditEnglishTypeDialogComponent } from "./edit-english-type-dialog.component";
import { ViewEnglishTypeDialogComponent } from "./view-english-type-dialog.component";

@Injectable({ providedIn: "root" })
export class EnglishTypeDialogService {
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
    const modalRef = this.modalService.open(AddEnglishTypeDialogComponent, {
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
    const modalRef = this.modalService.open(DeleteEnglishTypeDialogComponent, {
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
    item: EnglishType,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditEnglishTypeDialogComponent, {
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
    item: EnglishType,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewEnglishTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }
}
