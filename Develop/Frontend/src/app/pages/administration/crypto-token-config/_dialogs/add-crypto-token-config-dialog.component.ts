import { Component, Input, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { ErrorStateMatcher } from "@angular/material/core";
import { CryptoTokenConfigService } from "@app/pages/crypto/_services";
import { AuthenticationService } from "@app/user/_service";
import { AlertService } from "@app/_common/alert";
import { SpinnerService } from "@app/_common/_services";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-add-crypto-token-config-dialog",
  templateUrl: "./add-crypto-token-config-dialog.component.html",
  styleUrls: ["./add-crypto-token-config-dialog.component.css"],
})
export class AddCryptoTokenConfigDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private cryptoTokenConfigService: CryptoTokenConfigService,
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
  tokenCodeValue: string;
  tokenNameValue: string;
  iconUrlValue: string;
  nodeUrlValue: string;
  rpcUrlValue: string;
  denomValue: string;
  decimalExponentValue: Number;
  addressPrefixValue: string;
  mintscanPrefixValue: string;
  deligateUrlValue: string;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // tokenCode
  tokenCodeFormControl = new FormControl("", [Validators.required]);

  // tokenName
  tokenNameFormControl = new FormControl("", [Validators.required]);

  // iconUrl
  iconUrlFormControl = new FormControl("", []);

  // nodeUrl
  nodeUrlFormControl = new FormControl("", []);

  // rpcUrl
  rpcUrlFormControl = new FormControl("", []);

  // denom
  denomFormControl = new FormControl("", []);

  // decimalExponent
  decimalExponentFormControl = new FormControl("", []);

  // addressPrefix
  addressPrefixFormControl = new FormControl("", []);

  // mintscanPrefix
  mintscanPrefixFormControl = new FormControl("", []);

  // deligateUrl
  deligateUrlFormControl = new FormControl("", []);

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
    if (this.isFormValid()) {
      // Prepare form
      const addForm: FormGroup = this.formBuilder.group({
        tokenCode: [this.tokenCodeValue],
        tokenName: [this.tokenNameValue],
        iconUrl: [this.iconUrlValue],
        nodeUrl: [this.nodeUrlValue],
        rpcUrl: [this.rpcUrlValue],
        denom: [this.denomValue],
        decimalExponent: [this.decimalExponentValue],
        addressPrefix: [this.addressPrefixValue],
        mintscanPrefix: [this.mintscanPrefixValue],
        deligateUrl: [this.deligateUrlValue],
      });

      // Show loading
      this.spinner.show();

      // Add
      this.cryptoTokenConfigService.add(addForm.value).subscribe(
        (data) => {
          // Send success toast message
          this.toastr.success(
            "New token " + this.tokenCodeValue + " is added successful",
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
      this.messageError = "Invalid fields, please check your input";
    }
  }

  // Validate all fields
  public isFormValid() {
    let result = true;

    if (this.tokenCodeFormControl.status === "INVALID") {
      result = false;
    }
    if (this.tokenNameFormControl.status === "INVALID") {
      result = false;
    }
    if (this.iconUrlFormControl.status === "INVALID") {
      result = false;
    }
    if (this.nodeUrlFormControl.status === "INVALID") {
      result = false;
    }
    if (this.rpcUrlFormControl.status === "INVALID") {
      result = false;
    }
    if (this.denomFormControl.status === "INVALID") {
      result = false;
    }
    if (this.decimalExponentFormControl.status === "INVALID") {
      result = false;
    }
    if (this.addressPrefixFormControl.status === "INVALID") {
      result = false;
    }
    if (this.mintscanPrefixFormControl.status === "INVALID") {
      result = false;
    }
    if (this.mintscanPrefixFormControl.status === "INVALID") {
      result = false;
    }

    return result;
  }
}
