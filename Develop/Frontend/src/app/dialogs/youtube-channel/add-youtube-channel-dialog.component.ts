import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MediaTypeSCO } from '@app/scos/mediaTypeSCO';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/apiReponse';
import { MediaType } from '@app/models/mediaType';
import { SearchText } from '@app/scos/core_sco/searchText';
import { Sorter } from '@app/scos/core_sco/sorter';
import { YoutubeChannelSCO } from '@app/scos/youtubeChannelSCO';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  templateUrl: './add-youtube-channel-dialog.component.html',
})
export class AddYoutubeChannelDialogComponent implements OnInit {
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

  // Input value
  channelIdValue: string;
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
  channelIdFormControl = new FormControl('', [
    validChannel,
    Validators.required,
  ]);

  // Field : Name
  nameFormControl = new FormControl('', [Validators.required]);

  // Field : description
  descriptionFormControl = new FormControl('', []);

  // Field : category
  categoryFormControl = new FormControl('', [Validators.required]);

  ngOnInit() {
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
      const addForm: FormGroup = this.formBuilder.group({
        channelId: [this.channelIdValue],
        name: [this.nameValue],
        description: [this.descriptionValue],
        category: [categoryChannelVO],
      });

      // Show loading
      this.spinner.show();

      // Add
      this.backendService.addChannel(addForm.value).subscribe(
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

/**
 * Check inexistence
 *
 */
function validChannel(control: FormControl) {
  const channelId = control.value;

  // Validate if have input
  if (channelId) {
    // Search channel id
    const channelIdSearch = new SearchText();
    channelIdSearch.eq = channelId;
    const sco = new YoutubeChannelSCO();
    sco.channelId = channelIdSearch;

    // this.foodService.searchFood(sco)
    //   .subscribe(data => {
    //     const responseAPi: any = data;
    //     const typeResponse: ApiReponse<Food> = responseAPi;
    //     if (typeResponse.data != null) {
    //       // Invalid because new work is existed
    //       return {
    //         foodIsExisted: {
    //           parsedUrln: keyWord
    //         }
    //       };
    //     } else {
    //       // New food is not existed
    //     }
    //   }, error => {
    //     this.toastr.info('error:' + error);
    //   });
  }
  return null;
}
