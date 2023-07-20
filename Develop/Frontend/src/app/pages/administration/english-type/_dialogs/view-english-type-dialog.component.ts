import { Component, Input, OnInit } from "@angular/core";
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { ErrorStateMatcher } from "@angular/material/core";
import { EnglishTypeService } from "@app/pages/english/_services";
import { AuthenticationService } from "@app/user/_service";
import { AlertService } from "@app/_common/alert";
import { EnglishType } from "@app/_common/_models";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from "ngx-toastr";

@Component({
  styleUrls: ["view-english-type-dialog.component.css"],
  templateUrl: "view-english-type-dialog.component.html",
})
export class ViewEnglishTypeDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private englishTypeService: EnglishTypeService,
    private toastr: ToastrService,
  ) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() item: EnglishType;

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
