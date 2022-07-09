import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { SpinnerService, TypeService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-type-dialog',
  templateUrl: './add-type-dialog.component.html',
  styleUrls: ['./add-type-dialog.component.css']
})
export class AddTypeDialogComponent implements OnInit {
  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  // Input value
  typeClass: string;
  typeCode: string;
  typeName: string;
  description: string;
  iconUrl: string;
  url: string;

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private typeService: TypeService,
    private toastr: ToastrService,
    private spinner: SpinnerService
  ) { }

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : type class
  typeClassFormControl = new FormControl('', [
    Validators.required,
  ]);

  // Field : type code
  typeCodeFormControl = new FormControl('', [
    Validators.required,
  ]);

  // Field : type name
  typeNameFormControl = new FormControl('', [
    Validators.required,
  ]);

  // Field : description
  descriptionFormControl = new FormControl('', [
  ]);

  // Field : icon URL
  iconUrlFormControl = new FormControl('', [
  ]);

  // Field : URL
  urlFormControl = new FormControl('', [
  ]);

  ngOnInit() {
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
      // Case data is invalid
      case (!this.isFormValid()):
        this.message = null;
        this.messageError = 'Invalid fields, please check your input';
        break;

      // Case ok
      default:
        // Prepare type form
        let typeForm: FormGroup;
        typeForm = this.formBuilder.group({
          typeClass: [this.typeClass],
          typeCode: [this.typeCode],
          typeName: [this.typeName],
          description: [this.description],
          iconUrl: [this.iconUrl],
          url: [this.url],
        });

        // Show loading
        this.spinner.show();

        // Create
        this.typeService.create(typeForm.value)
          .subscribe(
            data => {
              // Send success toast message
              this.toastr.success('<Type class ' + this.typeClass + ' & type code ' + this.typeCode + '> is updated successful');

              // Hide loading
              this.spinner.hide();

              // Close dialog
              this.activeModal.close(true);

            },
            error => {
              // Hide loading
              this.spinner.hide();

              // Send error toast message
              this.toastr.error(error);

              // Close dialog
              this.activeModal.close(false);
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
    if (this.urlFormControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }
}
