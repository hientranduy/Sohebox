import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MediaTypeSCO } from '@app/scos/mediaTypeSCO';
import { AlertService } from '@app/commons/alert/alert.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { ApiReponse } from '@app/models/apiReponse';
import { MediaType } from '@app/models/mediaType';
import { YoutubeChannel } from '@app/models/youtubeChannel';
import { SearchText } from '@app/scos/core_sco/searchText';
import { Sorter } from '@app/scos/core_sco/sorter';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  templateUrl: './edit-youtube-channel-dialog.component.html',
})
export class EditYoutubeChannelDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private backendService: BackendService,
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
    this.backendService.searchMediaType(sco).subscribe(
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
      this.backendService.updateChannel(editForm.value).subscribe(
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
