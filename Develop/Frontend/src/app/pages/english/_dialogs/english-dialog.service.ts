import { Injectable } from '@angular/core';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { English } from '@app/_common/_models';
import { AddWordDialogComponent } from './add-word-dialog.component';
import { DownloadVoiceComponent } from './download-voice.component';
import { EditWordDialogComponent } from './edit-word-dialog.component';
import { ShowLearnedWordComponent } from './show-learned-word.component';
import { WordInfoDialogComponent } from './word-info-dialog.component';

@Injectable({ providedIn: 'root' })
export class EnglishDialogService {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
  }

  public addWord(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddWordDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public editWord(
    title: string,
    message: string,
    english: English,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditWordDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.english = english;
    return modalRef.result;
  }

  public viewWordInfo(
    english: English,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(WordInfoDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.english = english;
    return modalRef.result;
  }

  public downloadVoice(
    title: string,
    message: string,
    english: English,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DownloadVoiceComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Download';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.english = english;
    return modalRef.result;
  }

  public showLearnedWord(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ShowLearnedWordComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }
}
