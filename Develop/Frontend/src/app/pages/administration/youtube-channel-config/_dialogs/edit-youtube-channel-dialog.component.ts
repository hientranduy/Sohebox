import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ApiReponse, MediaType } from '@app/_common/_models';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { MediaTypeSCO } from '@app/_common/_sco/mediaTypeSCO';
import { SpinnerService } from '@app/_common/_services';
import { AlertService } from '@app/_common/alert/alert.service';
import { YoutubeChannel } from '@app/pages/media/_models';
import { YoutubeService } from '@app/pages/media/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { MediaTypeDialogService } from '../../media-type/media-type.service';

@Component({
  selector: 'app-edit-youtube-channel-dialog',
  templateUrl: './edit-youtube-channel-dialog.component.html',
  styleUrls: ['./edit-youtube-channel-dialog.component.css'],
})
export class EditYoutubeChannelDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private mediaTypeDialogService: MediaTypeDialogService,
    private youtubeService: YoutubeService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() youtubeChannel: YoutubeChannel;

  // Input value
  channelIdValue: String;
  nameValue: string;
  descriptionValue: string;
  categoryValue: string;

  // Selection item
  selectedCategoryChannel: MediaType;

  // Filter category
  filteredCategories: Observable<MediaType[]>;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : channel ID
  channelIdFormControl = new FormControl('', []);

  // Field : Name
  nameFormControl = new FormControl('', [Validators.required]);

  // Field : description
  descriptionFormControl = new FormControl('', []);

  // Field : category
  categoryFormControl = new FormControl('', [Validators.required]);

  ngOnInit() {
    // Set old data
    this.channelIdValue = this.youtubeChannel.channelId;
    this.nameValue = this.youtubeChannel.name;
    this.descriptionValue = this.youtubeChannel.description;
    if (this.youtubeChannel.category != null) {
      this.categoryValue = this.youtubeChannel.category.typeCode;
    }

    this.getFilterCategories();
  }

  /**
   * Get list of channel category
   */
  getFilterCategories() {
    // Show loading
    this.spinner.show();

    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'YOUTUBE_CHANNEL_CATEGORY';
    const sorters: Array<Sorter> = [];
    sorters.push(new Sorter('id', 'ASC'));

    const sco = new MediaTypeSCO();
    sco.typeClass = typeClass;
    sco.deleteFlag = false;
    sco.sorters = sorters;

    // Get list
    this.mediaTypeDialogService.search(sco).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const response: ApiReponse<MediaType> = responseAPi;
        const categories: MediaType[] = response.data.elements;
        if (response.data.elements != null) {
          this.filteredCategories = this.categoryFormControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              categories.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toLowerCase()),
              ),
            ),
          );
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

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
    if (this.isFormValid()) {
      const categoryChannelVO: MediaType = new MediaType();
      categoryChannelVO.typeCode = this.categoryValue;

      // Prepare adding word form
      const editForm: FormGroup = this.formBuilder.group({
        channelId: [this.channelIdValue],
        name: [this.nameValue],
        description: [this.descriptionValue],
        category: [categoryChannelVO],
      });

      // Show loading
      this.spinner.show();

      // Add
      this.youtubeService.updateChannel(editForm.value).subscribe(
        (data) => {
          // Send success toast message
          this.toastr.success(
            'New channel ' + this.nameValue + ' is added successful',
          );

          // Hide loading
          this.spinner.hide();

          // Close dialog
          this.activeModal.close(true);
        },
        (error) => {
          // Hide loading
          this.spinner.hide();

          // Send error message to dialog
          this.message = null;
          this.messageError = error;
        },
      );
    } else {
      this.message = null;
      this.messageError = 'Invalid fields, please check your input';
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;

    if (this.channelIdFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.nameFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.descriptionFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.categoryFormControl.status === 'INVALID') {
      result = false;
    }

    return result;
  }

  /**
   * Change category
   */
  public onChangeChannelIdCategory(channelCategory: MediaType) {
    this.selectedCategoryChannel = channelCategory;
  }
}
