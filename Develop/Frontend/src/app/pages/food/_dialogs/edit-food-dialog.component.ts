import { Component, Input, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { MatCheckboxChange } from "@angular/material/checkbox";
import { ErrorStateMatcher } from "@angular/material/core";
import { AuthenticationService } from "@app/user/_service";
import { AlertService } from "@app/_common/alert";
import { ApiReponse } from "@app/_common/_models";
import { FoodType } from "@app/_common/_models/foodType";
import { SearchText, Sorter } from "@app/_common/_sco/core_sco";
import { FoodSCO } from "@app/_common/_sco/foodSCO";
import { FoodTypeSCO } from "@app/_common/_sco/foodTypeSCO";
import { SpinnerService } from "@app/_common/_services";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ToastrService } from "ngx-toastr";
import { Observable } from "rxjs";
import { map, startWith } from "rxjs/operators";
import { Food } from "../_model";
import { FoodService, FoodTypeService } from "../_services";

@Component({
  selector: "app-edit-food-dialog",
  templateUrl: "./edit-food-dialog.component.html",
  styleUrls: ["./edit-food-dialog.component.css"],
})
export class EditFoodDialogComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private activeModal: NgbActiveModal,
    private foodService: FoodService,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private foodTypeService: FoodTypeService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {}

  // Form value
  @Input() title: string;
  @Input() message: string;
  @Input() messageError: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;
  @Input() food: Food;

  // Input value
  nameValue: string;
  descriptionValue: string;
  locationNoteValue: string;
  typeValue: string;
  categoryValue: string;
  urlReferenceValue: string;
  imageFile: any;
  imageExtention: String;
  isFastFoodValue: Boolean = true;
  recipeValue: Blob;

  // Filter grade
  filteredTypes: Observable<FoodType[]>;

  // Filter category
  filteredCategories: Observable<FoodType[]>;

  /////////////////////////////////////
  // FORM FIELD VALIDATION           //
  /////////////////////////////////////
  matcher = new ErrorStateMatcher();

  // Field : name
  nameFormControl = new FormControl("", [validFoodName, Validators.required]);

  // Field : description
  descriptionFormControl = new FormControl("", []);

  // Field : location note
  locationNoteFormControl = new FormControl("", []);

  // Field : type
  typeFormControl = new FormControl("", [Validators.required]);

  // Field : category
  categoryFormControl = new FormControl("", [Validators.required]);

  // Field : Recipe
  recipeFormControl = new FormControl("", []);

  // Field : url reference
  urlReferenceFormControl = new FormControl("", []);

  // Field: is fast food
  onChangeIsFastFood($event: MatCheckboxChange) {
    if ($event.checked) {
      this.isFastFoodValue = true;
    } else {
      this.isFastFoodValue = false;
    }
  }

  ngOnInit() {
    // Set old data
    this.nameValue = this.food.name;
    this.descriptionValue = this.food.description;
    this.locationNoteValue = this.food.locationNote;
    if (this.food.type != null) {
      this.typeValue = this.food.type.typeCode;
    }
    if (this.food.category != null) {
      this.categoryValue = this.food.category.typeCode;
    }
    this.recipeValue = this.food.recipeString;
    this.urlReferenceValue = this.food.urlReference;
    this.isFastFoodValue = this.food.isFastFood;

    this.getFilterTypes();
    this.getFilterCategories();
  }

  /**
   * Get list of food type
   */
  getFilterTypes() {
    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = "FOOD_TYPE";
    const sorters: Array<Sorter> = [];
    sorters.push(new Sorter("id", "ASC"));

    const sco = new FoodTypeSCO();
    sco.typeClass = typeClass;
    sco.deleteFlag = false;
    sco.sorters = sorters;

    // Show loading
    this.spinner.show();

    // Get list
    this.foodTypeService.search(sco).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const response: ApiReponse<FoodType> = responseAPi;
        const types: FoodType[] = response.data.elements;
        if (response.data.elements != null) {
          this.filteredTypes = this.typeFormControl.valueChanges.pipe(
            startWith(""),
            map((value) =>
              types.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toLowerCase()),
              ),
            ),
          );
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  /**
   * Get list of category
   */
  getFilterCategories() {
    // Show loading
    this.spinner.show();

    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = "FOOD_CATEGORY";
    const sorters: Array<Sorter> = [];
    sorters.push(new Sorter("id", "ASC"));

    const sco = new FoodTypeSCO();
    sco.typeClass = typeClass;
    sco.deleteFlag = false;
    sco.sorters = sorters;

    // Get list
    this.foodTypeService.search(sco).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const response: ApiReponse<FoodType> = responseAPi;
        const categories: FoodType[] = response.data.elements;
        if (response.data.elements != null) {
          this.filteredCategories = this.categoryFormControl.valueChanges.pipe(
            startWith(""),
            map((value) =>
              categories.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toLowerCase()),
              ),
            ),
          );
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  //////////////////
  // Browse image //
  //////////////////
  preview(files) {
    // Return if have no image
    if (files.length === 0) {
      return;
    }

    // Return if not an image
    const mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = null;
      this.messageError = "Only images are supported.";
      return;
    }

    // Return if file is too big
    if (files[0].size > 2000000) {
      this.message = null;
      this.messageError = "Max image size is 2Mbs, please update image";
      return;
    }

    // Preview
    const reader = new FileReader();
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imageFile = reader.result;

      // Set image extention
      this.imageExtention = files[0].name.split(".").pop();
    };

    // Remove error message
    this.messageError = null;
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
   * Change type
   */
  public onChangeFoodType(foodType: FoodType) {
    this.food.type = foodType;
  }

  /**
   * Change category
   */
  public onChangeFoodCategory(foodCategory: FoodType) {
    this.food.category = foodCategory;
  }

  /**
   * Click accept button
   */
  public accept() {
    if (this.isFormValid()) {
      const typeFoodVO: FoodType = new FoodType();
      typeFoodVO.typeCode = this.typeValue;
      const categoryFoodVO: FoodType = new FoodType();
      categoryFoodVO.typeCode = this.categoryValue;

      // Prepare adding form
      const editForm: FormGroup = this.formBuilder.group({
        name: [this.nameValue],
        imageFile: [this.imageFile],
        imageExtention: [this.imageExtention],
        description: [this.descriptionValue],
        locationNote: [this.locationNoteValue],
        type: [typeFoodVO],
        category: [categoryFoodVO],
        recipeString: [this.recipeValue],
        isFastFood: [this.isFastFoodValue],
        urlReference: [this.urlReferenceValue],
      });

      // Show loading
      this.spinner.show();

      // Add
      this.foodService.editFood(editForm.value).subscribe(
        (data) => {
          // Send success toast message
          this.toastr.success(
            "Food " + this.nameValue + " is updated successful",
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

    if (this.nameFormControl.status === "INVALID") {
      result = false;
    }
    if (this.descriptionFormControl.status === "INVALID") {
      result = false;
    }
    if (this.locationNoteFormControl.status === "INVALID") {
      result = false;
    }
    if (this.typeFormControl.status === "INVALID") {
      result = false;
    }
    if (this.categoryFormControl.status === "INVALID") {
      result = false;
    }
    if (this.recipeFormControl.status === "INVALID") {
      result = false;
    }
    if (this.urlReferenceFormControl.status === "INVALID") {
      result = false;
    }

    return result;
  }
}

/**
 * Check inexistence
 *
 */
function validFoodName(control: FormControl) {
  const foodName = control.value;

  // Validate if have input
  if (foodName) {
    // Search keyword
    const nameSearch = new SearchText();
    nameSearch.eq = foodName;
    const sco = new FoodSCO();
    sco.name = nameSearch;

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
