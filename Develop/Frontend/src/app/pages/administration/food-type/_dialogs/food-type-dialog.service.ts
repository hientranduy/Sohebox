import { Injectable } from '@angular/core';
import { FoodType } from '@app/_common/_models/foodType';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { AddFoodTypeDialogComponent } from './add-food-type-dialog.component';
import { DeleteFoodTypeDialogComponent } from './delete-food-type-dialog.component';
import { EditFoodTypeDialogComponent } from './edit-food-type-dialog.component';
import { ViewFoodTypeDialogComponent } from './view-food-type-dialog.component';


@Injectable({ providedIn: 'root' })
export class FoodTypeDialogService {

  constructor(config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public add(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(AddFoodTypeDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public delete(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteFoodTypeDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public edit(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(EditFoodTypeDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  public view(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(ViewFoodTypeDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }
}
