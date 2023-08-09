import { Component, OnInit } from '@angular/core';
import { AlertService } from '@app/commons/alert/alert.service';
import { ApiReponse } from '@app/models/apiReponse';
import { Food } from '@app/models/food';
import { Sorter } from '@app/scos/core_sco/sorter';
import { FoodSCO } from '@app/scos/foodSCO';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
})
export class FoodComponent implements OnInit {
  foods: Array<Food>;

  constructor(
    private alertService: AlertService,
    private spinner: SpinnerService,
    private backendService: BackendService,
  ) {
    // Get all food
    this.refreshAllFood();
  }

  ngOnInit() {}

  /**
   * Reload table
   */
  public refreshAllFood() {
    // Show loading
    this.spinner.show();

    // Get all
    const sorters: Array<Sorter> = [];
    sorters.push(new Sorter('id', 'ASC'));
    const foodSCO = new FoodSCO();
    foodSCO.sorters = sorters;

    this.backendService.searchFood(foodSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const foodResponse: ApiReponse<Food> = responseAPi;

        if (foodResponse.data.elements != null) {
          this.foods = foodResponse.data.elements;
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
}
