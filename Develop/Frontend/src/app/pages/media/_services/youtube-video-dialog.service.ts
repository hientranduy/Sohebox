import { Injectable } from '@angular/core';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import {
  AddYoutubeVideoDialogComponent,
  DeleteYoutubeVideoDialogComponent,
} from '../_dialogs';
import { YoutubeVideo } from '@app/_common/_models';

@Injectable({ providedIn: 'root' })
export class YoutubeVideoDialogService {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public addPrivateVideo(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddYoutubeVideoDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public deletePrivateVideo(
    title: string,
    message: string,
    video: YoutubeVideo,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteYoutubeVideoDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.video = video;
    return modalRef.result;
  }
}
