import { Injectable } from '@angular/core';
import { YoutubeChannel } from '@app/pages/media/_models';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { AddYoutubeChannelDialogComponent } from './add-youtube-channel-dialog.component';
import { EditYoutubeChannelDialogComponent } from './edit-youtube-channel-dialog.component';

@Injectable({ providedIn: 'root' })
export class YoutubeChannelDialogService {
  constructor(config: NgbModalConfig, private modalService: NgbModal) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public add(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg'
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddYoutubeChannelDialogComponent, {
      size: dialogSize
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public edit(
    title: string,
    message: string,
    youtubeChannel: YoutubeChannel,
    dialogSize: 'sm' | 'lg' = 'lg'
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditYoutubeChannelDialogComponent, {
      size: dialogSize
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.youtubeChannel = youtubeChannel;
    return modalRef.result;
  }
}
