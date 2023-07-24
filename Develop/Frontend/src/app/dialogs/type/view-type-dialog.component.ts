import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Type } from '@app/models/type';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  styleUrls: ['view-type-dialog.component.css'],
  templateUrl: 'view-type-dialog.component.html',
})
export class ViewTypeDialogComponent implements OnInit {
  constructor(private activeModal: NgbActiveModal) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() type: Type;

  // Input value
  typeClass: string;
  typeCode: string;
  typeName: string;
  description: string;
  iconUrl: string;
  url: string;

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

  // Field : URL
  urlFormControl = new FormControl('', []);

  ngOnInit() {
    // Set current value
    this.typeClass = this.type.typeClass;
    this.typeCode = this.type.typeCode;
    this.typeName = this.type.typeName;
    this.description = this.type.description;
    this.iconUrl = this.type.iconUrl;
    this.url = this.type.url;
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
