import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MediaTypeService, YoutubeService } from '@app/pages/media/_services';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { ApiReponse, MediaType } from '@app/_common/_models';
import { YoutubeChannelSCO } from '@app/_common/_sco';
import { SearchText, Sorter } from '@app/_common/_sco/core_sco';
import { MediaTypeSCO } from '@app/_common/_sco/mediaTypeSCO';
import { SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-add-youtube-channel-dialog',
  templateUrl: './add-youtube-channel-dialog.component.html',
  styleUrls: ['./add-youtube-channel-dialog.component.css']
})
export class AddYoutubeChannelDialogComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private mediaTypeService: MediaTypeService,
    private youtubeService: YoutubeService,
    private toastr: ToastrService,
    private spinner: SpinnerService
  ) {
  }

  isLoading: Boolean;
  isLoadingContent: Boolean;

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
  nameFormControl = new FormControl('', [
    Validators.required,
  ]);

  // Field : description
  descriptionFormControl = new FormControl('', [
  ]);

  // Field : category
  categoryFormControl = new FormControl('', [
    Validators.required,
  ]);

  ngOnInit() {
    this.getFilterCategories();
  }

  /**
   * Get list of channel category
   */
  getFilterCategories() {
    // Show loading
    this.isLoadingContent = true;
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
    this.mediaTypeService.search(sco)
      .subscribe(data => {
        // Get data
        const responseAPi: any = data;
        const response: ApiReponse<MediaType> = responseAPi;
        const categories: MediaType[] = response.data.elements;
        if (response.data.elements != null) {
          this.filteredCategories = this.categoryFormControl.valueChanges
            .pipe(startWith(''), map(value => categories.filter
              (valueFilter => valueFilter.typeCode.toLowerCase().includes(value.toLowerCase()))
            ));
        }

        // Hide loading
        this.isLoadingContent = false;
        this.spinner.hide();
      }, error => {
        // Hide loading
        this.isLoadingContent = false;
        this.spinner.hide();

        this.alertService.error(error);
      });
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
        category: [categoryChannelVO]
      });

      // Show loading
      this.isLoading = true;
      this.spinner.show();

      // Add
      this.youtubeService.addChannel(addForm.value)
        .subscribe(
          data => {
            // Send success toast message
            this.toastr.success('New channel ' + this.nameValue + ' is added successful');

            // Hide loading
            this.isLoading = false;
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);

          },
          error => {
            // Hide loading
            this.isLoading = false;
            this.spinner.hide();

            // Send error message to dialog
            this.message = null;
            this.messageError = error;
          });
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
