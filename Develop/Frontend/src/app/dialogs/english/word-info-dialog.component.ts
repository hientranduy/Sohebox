import { Component, Input, OnInit } from '@angular/core';
import { AppSettings } from '@app/app.settings';
import { English } from '@app/models/english';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  templateUrl: './word-info-dialog.component.html',
})
export class WordInfoDialogComponent implements OnInit {
  // Form value
  @Input() btnCancelText: string;
  @Input() english: English;

  displayImageUrl: string;
  displayUkVoiceUrl: string;
  displayUsVoiceUrl: string;

  constructor(
    private activeModal: NgbActiveModal,
    private toastr: ToastrService,
  ) {}

  ngOnInit() {
    this.displayImageUrl =
      AppSettings.ENGLISH_IMAGE_PATH + this.english.imageName;

    if (this.english.voiceUkFile != null) {
      this.displayUkVoiceUrl =
        AppSettings.ENGLISH_SOUND_PATH + this.english.voiceUkFile;
    } else {
      this.displayUkVoiceUrl = null;
    }

    if (this.english.voiceUsFile != null) {
      this.displayUsVoiceUrl =
        AppSettings.ENGLISH_SOUND_PATH + this.english.voiceUsFile;
    } else {
      this.displayUsVoiceUrl = null;
    }
  }

  /**
   * Click decline button
   */
  public decline() {
    this.activeModal.close(false);
  }

  /**
   * Go to google translate button
   */
  public goToGooleTranslate() {
    if (this.english) {
      window.open(AppSettings.GOOGLE_TRANSLATE_WORD_URL + this.english.keyWord);
    } else {
      // Toast warning if not found
      this.toastr.warning('Need to generate a WORD first');
    }
  }

  /**
   * Go to cambridge dictionary button
   */
  public goToCambridgeDictionary() {
    if (this.english) {
      window.open(
        AppSettings.CAMBRIDGE_DICTIONATY_WORD_URL +
          this.english.keyWord.split(' ').join('-'),
      );
    } else {
      // Toast warning if not found
      this.toastr.warning('Need to generate a WORD first');
    }
  }

  /**
   * Play UK Voice botton
   */
  public playUkVoice() {
    const audio = new Audio();
    audio.src = this.displayUkVoiceUrl;
    audio.load();
    audio.play();
  }

  /**
   * Play US Voice botton
   */
  public playUsVoice() {
    const audio = new Audio();
    audio.src = this.displayUsVoiceUrl;
    audio.load();
    audio.play();
  }
}
