import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { Config } from '@app/_common/_models';
import { ConfigService, SpinnerService } from '@app/_common/_services';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  styleUrls: ['edit-config-dialog.component.css'],
  templateUrl: 'edit-config-dialog.component.html',
})
export class EditConfigDialogComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private configService: ConfigService,
    private toastr: ToastrService,
    private spinner: SpinnerService
  ) {
  }

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() item: Config;

  // Input value
  configKey: string;
  configValue: string;
  description: string;

  isLoadingConfigEdit: Boolean;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : config key
  configKeyFormControl = new FormControl('', [
  ]);

  // Field : config value
  configValueFormControl = new FormControl('', [
    Validators.required,
  ]);

  // Field : description
  descriptionFormControl = new FormControl('', [
  ]);


  ngOnInit() {
    // Set current value
    this.configKey = this.item.configKey;
    this.configValue = this.item.configValue;
    this.description = this.item.description;
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
      case (!this.isHaveUpdateValue()):
        // Send warning toast message
        this.toastr.warning('Skip update because the value is not changed');

        // Close dialog as cancel
        this.activeModal.close(false);
        break;

      // Case data is invalid
      case (!this.isFormValid()):
        this.message = null;
        this.messageError = 'Invalid fields, please check your input';
        break;

      // Case ok
      default:
        // Prepare type form
        let configForm: FormGroup;
        configForm = this.formBuilder.group({
          id: [this.item.id],
          configKey: [this.item.configKey],
          configValue: [this.configValue],
          description: [this.description],
        });

        // Show loading
        this.isLoadingConfigEdit = true;
        this.spinner.show();

        // Update
        this.configService.update(configForm.value)
          .subscribe(
            data => {
              // Send success toast message
              this.toastr.success('<Config Key ' + this.item.configKey + '> is updated successful');

              // Hide loading
              this.isLoadingConfigEdit = false;
              this.spinner.hide();

              // Close dialog
              this.activeModal.close(true);

            },
            error => {
              // Hide loading
              this.isLoadingConfigEdit = false;
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

  // Have any update infos
  public isHaveUpdateValue() {
    let result = false;
    if (this.configKey !== this.item.configKey) {
      result = true;
    }
    if (this.configValue !== this.item.configValue) {
      result = true;
    }
    if (this.description !== this.item.description) {
      result = true;
    }
    return result;
  }
}
