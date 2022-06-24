import { Injectable } from '@angular/core';
import { FoodType } from '@app/_common/_models/foodType';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { DeleteMediaTypeDialogComponent } from './delete-media-type-dialog.component';
import { EditMediaTypeDialogComponent } from './edit-media-type-dialog.component';
import { ViewMediaTypeDialogComponent } from './view-media-type-dialog.component';


@Injectable({ providedIn: 'root' })
export class MediaTypeDialogService {

  constructor(config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public delete(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg'): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteMediaTypeDialogComponent, { size: dialogSize });
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
    const modalRef = this.modalService.open(EditMediaTypeDialogComponent, { size: dialogSize });
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
    const modalRef = this.modalService.open(ViewMediaTypeDialogComponent, { size: dialogSize });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }
}
