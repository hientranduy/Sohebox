import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Config } from '@app/_common/_models';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  styleUrls: ['view-config-dialog.component.css'],
  templateUrl: 'view-config-dialog.component.html',
})
export class ViewConfigDialogComponent implements OnInit {
  constructor(private activeModal: NgbActiveModal) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnCancelText: string;
  @Input() item: Config;

  // Input value
  configKey: string;
  configValue: string;
  description: string;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : config key
  configKeyFormControl = new FormControl('', []);

  // Field : config value
  configValueFormControl = new FormControl('', [Validators.required]);

  // Field : description
  descriptionFormControl = new FormControl('', []);

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
    this.activeModal.close(true);
  }
}
