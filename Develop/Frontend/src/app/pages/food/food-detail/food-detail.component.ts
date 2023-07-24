import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { AppSettings } from '@app/app.settings';
import { AlertService } from '@app/commons/alert/alert.service';
import { Food } from '@app/models/food';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  selector: 'app-food-detail',
  templateUrl: './food-detail.component.html',
  styleUrls: ['./food-detail.component.css'],
})
export class FoodDetailComponent implements OnInit {
  food: Food;
  displayImageUrl: string;

  constructor(
    private route: ActivatedRoute,
    private alertService: AlertService,
    private spinner: SpinnerService,
    private backendService: BackendService,
  ) {}

  ngOnInit() {
    // Show loading
    this.spinner.show();

    // Get food id from parameters
    let foodId: number;
    this.route.params.subscribe((params: Params) => (foodId = params['id']));

    // Get food infos
    this.backendService.getFood(foodId).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        if (responseAPi.data != null) {
          this.food = responseAPi.data;
        }

        // Set image path
        this.displayImageUrl =
          AppSettings.FOOD_IMAGE_PATH + this.food.imageName;

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
   * Click image
   */
  public onClickImage() {
    const elem = document.getElementById('foodImage') as HTMLElement & {
      mozRequestFullScreen(): Promise<void>;
      webkitRequestFullscreen(): Promise<void>;
      msRequestFullscreen(): Promise<void>;
    };

    if (elem.requestFullscreen) {
      elem.requestFullscreen();
    } else if (elem.mozRequestFullScreen) {
      /* Firefox */
      elem.mozRequestFullScreen();
    } else if (elem.webkitRequestFullscreen) {
      /* Chrome, Safari and Opera */
      elem.webkitRequestFullscreen();
    } else if (elem.msRequestFullscreen) {
      /* IE/Edge */
      elem.msRequestFullscreen();
    }
  }

  /**
   * Close tab
   */
  public closeTab() {
    window.close();
  }
}
