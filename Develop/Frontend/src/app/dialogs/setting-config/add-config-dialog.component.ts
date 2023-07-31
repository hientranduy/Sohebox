import { Component, Input, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  templateUrl: 'add-config-dialog.component.html',
})
export class AddConfigDialogComponent implements OnInit {
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

  // Input value
  configKey: string;
  configValue: string;
  description: string;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : config key
  configKeyFormControl = new FormControl('', [Validators.required]);

  // Field : config value
  configValueFormControl = new FormControl('', [Validators.required]);

  // Field : description
  descriptionFormControl = new FormControl('', []);

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
      // Case data is invalid
      case !this.isFormValid():
        this.message = null;
        this.messageError = 'Invalid fields, please check your input';
        break;

      // Case ok
      default:
        // Prepare type form
        let configForm: FormGroup;
        configForm = this.formBuilder.group({
          configKey: [this.configKey],
          configValue: [this.configValue],
          description: [this.description],
        });

        // Show loading
        this.spinner.show();

        // Update
        this.backendService.createConfig(configForm.value).subscribe(
          (data) => {
            // Send success toast message
            this.toastr.success(
              '<Config Key ' + this.configKey + '> is created successful',
            );

            // Hide loading
            this.spinner.hide();

            // Close dialog
            this.activeModal.close(true);
          },
          (error) => {
            // Hide loading
            this.spinner.hide();

            // Send error toast message
            this.toastr.error(error);

            // Close dialog
            this.activeModal.close(false);
          },
        );
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;
    if (this.configKeyFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.configValueFormControl.status === 'INVALID') {
      result = false;
    }
    if (this.descriptionFormControl.status === 'INVALID') {
      result = false;
    }
    return result;
  }
}
