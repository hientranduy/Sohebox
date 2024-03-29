import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AppSettings } from '@app/app.settings';
import { English } from '@app/models/english';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { environment } from '@environments/environment';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  templateUrl: './download-voice.component.html',
})
export class DownloadVoiceComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private backendService: BackendService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {}
  cambridgeDictionaryUrl = AppSettings.CAMBRIDGE_DICTIONATY_URL;
  englishAccessSoundPath =
    environment.soheboxRepo +
    AppSettings.SOHEBOX_WEB_SRC_PATH +
    AppSettings.ENGLISH_SOUND_PATH;

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() english: English;

  // Input value
  ukVoiceUrlValue: string;
  usVoiceUrlValue: string;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : Voice UK
  ukVoiceFormControl = new FormControl('', [urlValidator, urlUkVoice]);

  // Field : Voice US
  usVoiceFormControl = new FormControl('', [urlValidator, urlUsVoice]);

  ngOnInit() {}

  /////////////////////////////////////
  // FORM BUTTON CONTROL             //
  /////////////////////////////////////
  /**
   * Click decline button
   */
  public decline() {
    this.activeModal.close(false);
  }

  /**
   * Click dismiss button
   */
  public dismiss() {
    this.activeModal.dismiss();
  }

  /**
   * Click accept button
   */
  public accept() {
    switch (true) {
      // Case data is unchanged
      case !this.isHaveUpdateValue():
        // Send warning toast message
        this.toastr.warning(
          'Skip voice downloading because the URLs are not filled',
        );

        // Close dialog as cancel
        this.activeModal.close(false);
        break;

      // Case data is invalid
      case !this.isFormValid():
        this.message = null;
        this.messageError = 'Invalid fields, please check your input';
        break;

      // Case ok
      default:
        // Prepare adding account form
        let downloadFileForm: FormGroup;
        let voiceUkFileName: string;
        let voiceUsFileName: string;
        let downloadVoiceFullUrl: string;

        // Download UK Voice
        if (this.ukVoiceUrlValue != null) {
          voiceUkFileName =
            this.english.imageName.split('.').slice(0, -1).join('.') +
            '_uk.mp3';

          if (this.ukVoiceUrlValue.includes(this.cambridgeDictionaryUrl)) {
            downloadVoiceFullUrl = this.ukVoiceUrlValue;
          } else {
            downloadVoiceFullUrl =
              this.cambridgeDictionaryUrl + this.ukVoiceUrlValue;
          }

          downloadFileForm = this.formBuilder.group({
            url: [downloadVoiceFullUrl],
            destinationFolderPath: [this.englishAccessSoundPath],
            destinationFileName: [voiceUkFileName],
          });

          // Show loading
          this.spinner.show();

          // Download
          this.backendService
            .downloadFileMp3(downloadFileForm.value)
            .subscribe({
              next: async (res) => {
                // Send success toast message
                this.toastr.success(
                  'UK Voice of word <' +
                    this.english.keyWord +
                    '> is downloaded successfull',
                );

                // Hide loading
                this.spinner.hide();
              },
              error: (err) => {
                // Hide loading
                this.spinner.hide();

                // Send error toast message
                this.toastr.error(err + ' is not found');
              },
            });
        }

        // Download US Voice
        if (this.usVoiceUrlValue != null) {
          voiceUsFileName =
            this.english.imageName.split('.').slice(0, -1).join('.') +
            '_us.mp3';

          if (this.usVoiceUrlValue.includes(this.cambridgeDictionaryUrl)) {
            downloadVoiceFullUrl = this.usVoiceUrlValue;
          } else {
            downloadVoiceFullUrl =
              this.cambridgeDictionaryUrl + this.usVoiceUrlValue;
          }

          downloadFileForm = this.formBuilder.group({
            url: [downloadVoiceFullUrl],
            destinationFolderPath: [this.englishAccessSoundPath],
            destinationFileName: [voiceUsFileName],
          });

          // Show loading
          this.spinner.show();

          // Download
          this.backendService
            .downloadFileMp3(downloadFileForm.value)
            .subscribe({
              next: async (res) => {
                // Send success toast message
                this.toastr.success(
                  'US Voice of word <' +
                    this.english.keyWord +
                    '> is downloaded successfull',
                );

                // Hide loading
                this.spinner.hide();
              },
              error: (err) => {
                // Hide loading
                this.spinner.hide();

                // Send error toast message
                this.toastr.error(err + ' is not found');
              },
            });
        }

        // Update voice file name database
        const englishUpdate: English = new English();
        englishUpdate.keyWord = this.english.keyWord;
        if (voiceUkFileName != null) {
          englishUpdate.voiceUkFile = voiceUkFileName;
        }
        if (voiceUsFileName != null) {
          englishUpdate.voiceUsFile = voiceUsFileName;
        }

        // Show loading
        this.spinner.show();

        this.backendService.updateEnglish(englishUpdate).subscribe({
          next: async (res) => {
            // Hide loading
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);
          },
          error: (err) => {
            // Hide loading
            this.spinner.hide();

            // Send error toast message
            this.toastr.error(err);
          },
        });
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;
    if (this.ukVoiceFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.usVoiceFormControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }

  // Have any update infos
  public isHaveUpdateValue() {
    let result = false;
    if (this.ukVoiceUrlValue != null) {
      result = true;
    }
    if (this.usVoiceUrlValue != null) {
      result = true;
    }
    return result;
  }
}

/**
 * Function validator voice URL
 *  - Must have text ".mp3"
 *  - Must have "/us/media/english/"
 *
 */
function urlValidator(control: FormControl) {
  const url = control.value;

  // Validate URL if have input
  if (url) {
    // Voice URL must have text ".mp3"
    if (url.indexOf('.mp3') === -1) {
      return {
        notFileMp3: {
          parsedUrln: url,
        },
      };
    }

    // Voice URL must have text "/us/media/english/"
    if (url.indexOf('/media/english/') === -1) {
      return {
        notCambridgeUrl: {
          parsedUrln: url,
        },
      };
    }
  }
  return null;
}

/**
 * Function validator voice URL
 *  - Must be United Kingdom
 *
 */
function urlUkVoice(control: FormControl) {
  const url = control.value;

  // Validate URL if have input
  if (url) {
    // Voice not UK voice "/uk_pron/"
    if (url.indexOf('/uk_pron/') === -1) {
      return {
        notUkVoice: {
          parsedUrln: url,
        },
      };
    }
  }
  return null;
}

/**
 * Function validator voice URL
 *  - Must be United State
 *
 */
function urlUsVoice(control: FormControl) {
  const url = control.value;

  // Validate URL if have input
  if (url) {
    // Voice not US voice "/us_pron/"
    if (url.indexOf('/us_pron/') === -1) {
      return {
        notUkVoice: {
          parsedUrln: url,
        },
      };
    }
  }
  return null;
}
