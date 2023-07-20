import { Injectable } from "@angular/core";
import { NgbModal, NgbModalConfig } from "@ng-bootstrap/ng-bootstrap";
import { Food } from "../_model";
import { AddFoodDialogComponent } from "./add-food-dialog.component";
import { EditFoodDialogComponent } from "./edit-food-dialog.component";

@Injectable({ providedIn: "root" })
export class FoodDialogService {
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
    const modalRef = this.modalService.open(AddFoodDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Add";
    modalRef.componentInstance.btnCancelText = "Cancel";
    return modalRef.result;
  }

  public edit(
    title: string,
    message: string,
    food: Food,
    dialogSize: "sm" | "lg" = "lg",
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditFoodDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = "Edit";
    modalRef.componentInstance.btnCancelText = "Cancel";
    modalRef.componentInstance.food = food;
    return modalRef.result;
  }
}
