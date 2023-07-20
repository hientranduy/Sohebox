import { Component, Input, OnInit } from "@angular/core";
import { FormControl, Validators } from "@angular/forms";
import { ErrorStateMatcher } from "@angular/material/core";
import { AuthenticationService } from "@app/user/_service";
import { MediaType } from "@app/_common/_models/mediaType";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  styleUrls: ["view-media-type.component.css"],
  templateUrl: "view-media-type.component.html",
})
export class ViewMediaTypeDialogComponent implements OnInit {
  constructor(private activeModal: NgbActiveModal) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() item: MediaType;

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
  typeClassFormControl = new FormControl("", []);

  // Field : type code
  typeCodeFormControl = new FormControl("", []);

  // Field : type name
  typeNameFormControl = new FormControl("", [Validators.required]);

  // Field : description
  descriptionFormControl = new FormControl("", []);

  // Field : icon URL
  iconUrlFormControl = new FormControl("", []);

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
    this.activeModal.close(true);
  }
}
