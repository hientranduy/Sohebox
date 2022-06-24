import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlertService } from '@app/_common/alert';
import { ApiReponse } from '@app/_common/_models';
import { Sorter } from '@app/_common/_sco/core_sco';
import { FoodSCO } from '@app/_common/_sco/foodSCO';
import { SEOService, SpinnerService } from '@app/_common/_services';
import { Food } from './_model';
import { FoodService } from './_services';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.css']
})
export class FoodComponent implements OnInit {

  isLoadingFood: Boolean;
  foods: Array<Food>;

  constructor(
    private alertService: AlertService,
    private spinner: SpinnerService,
    private foodService: FoodService,
    private seoService: SEOService,
    private route: ActivatedRoute
  ) {

    // Get all food
    this.refreshAllFood();
  }

  ngOnInit() {
    // CEO
    this.seoService.updateCEO(this.route);
  }

  /**
   * Reload table
   */
  public refreshAllFood() {
    // Show loading
    this.isLoadingFood = true;
    this.spinner.show();

    // Get all
    const sorters: Array<Sorter> = [];
    sorters.push(new Sorter('id', 'ASC'));
    const foodSCO = new FoodSCO();
    foodSCO.sorters = sorters;

    this.foodService.searchFood(foodSCO)
      .subscribe(
        data => {
          // Get data
          const responseAPi: any = data;
          const foodResponse: ApiReponse<Food> = responseAPi;

          if (foodResponse.data.elements != null) {
            this.foods = foodResponse.data.elements;
          }

          // Hide loading
          this.isLoadingFood = false;
          this.spinner.hide();
        },
        error => {
          // Hide loading
          this.isLoadingFood = false;
          this.spinner.hide();

          this.alertService.error(error);
        });
  }
}
