import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { FoodType } from '@app/models/foodType';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  templateUrl: 'edit-food-type-dialog.component.html',
})
export class EditFoodTypeDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
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
  @Input() item: FoodType;

  // Input value
  typeClass: string;
  typeCode: string;
  typeName: string;
  description: string;
  iconUrl: string;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : type class
  typeClassFormControl = new FormControl('', []);

  // Field : type code
  typeCodeFormControl = new FormControl('', []);

  // Field : type name
  typeNameFormControl = new FormControl('', [Validators.required]);

  // Field : description
  descriptionFormControl = new FormControl('', []);

  // Field : icon URL
  iconUrlFormControl = new FormControl('', []);

  ngOnInit() {
    // Set current value
    this.typeClass = this.item.typeClass;
    this.typeCode = this.item.typeCode;
    this.typeName = this.item.typeName;
    this.description = this.item.description;
    this.iconUrl = this.item.iconUrl;
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
    switch (true) {
      // Case data is unchanged
      case !this.isHaveUpdateValue():
        // Send warning toast message
        this.toastr.warning('Skip update because the value is not changed');

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
        // Prepare type form
        let updateForm: FormGroup;
        updateForm = this.formBuilder.group({
          id: [this.item.id],
          typeClass: [this.item.typeClass],
          typeCode: [this.item.typeCode],
          typeName: [this.typeName],
          description: [this.description],
          iconUrl: [this.iconUrl],
        });

        // Show loading
        this.spinner.show();

        // Update
        this.backendService.updateFoodType(updateForm.value).subscribe({
          next: async (res) => {
            // Send success toast message
            this.toastr.success(
              '<Type class ' +
                this.typeClass +
                ' & type code ' +
                this.typeCode +
                '> is updated successful',
            );

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

            // Close dialog
            this.activeModal.close(false);
          },
        });
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;
    if (this.typeClassFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.typeCodeFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.typeNameFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.descriptionFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.iconUrlFormControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }

  // Have any update infos
  public isHaveUpdateValue() {
    let result = false;
    if (this.typeClass !== this.item.typeClass) {
      result = true;
    }
    if (this.typeCode !== this.item.typeCode) {
      result = true;
    }
    if (this.typeName !== this.item.typeName) {
      result = true;
    }
    if (this.description !== this.item.description) {
      result = true;
    }
    if (this.iconUrl !== this.item.iconUrl) {
      result = true;
    }
    return result;
  }
}
