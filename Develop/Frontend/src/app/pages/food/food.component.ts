import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiReponse } from '@app/_common/_models';
import { Sorter } from '@app/_common/_sco/core_sco';
import { FoodSCO } from '@app/_common/_sco/foodSCO';
import { SpinnerService } from '@app/_common/_services';
import { AlertService } from '@app/_common/alert/alert.service';
import { Food } from './_model';
import { FoodService } from './_services';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.css'],
})
export class FoodComponent implements OnInit {
  foods: Array<Food>;

  constructor(
    private alertService: AlertService,
    private spinner: SpinnerService,
    private foodService: FoodService,
    private route: ActivatedRoute,
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

    this.foodService.searchFood(foodSCO).subscribe(
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
