import { Component, HostListener, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppSettings } from '@app/appSettings';
import { AuthenticationService } from '@app/user/_service';
import { AlertService } from '@app/_common/alert';
import { SpinnerService, UtilsService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { Food } from '../_model';

@Component({
  selector: 'app-food-item',
  templateUrl: './food-item.component.html',
  styleUrls: ['./food-item.component.css'],
})
export class FoodItemComponent implements OnInit {
  @Input() food: Food;

  displayImageUrl: string;

  // Adjust styles fields
  foodCardWidth: String;
  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.foodCardWidth = this.utilsService.getAdjustCardWidth() + 'px';
  }
  @HostListener('window:orientationchange', ['$event'])
  onOrientationChange(event) {
    this.foodCardWidth = this.utilsService.getAdjustCardWidth() + 'px';
  }

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
    private utilsService: UtilsService,
  ) {}

  ngOnInit() {
    this.displayImageUrl = AppSettings.FOOD_IMAGE_PATH + this.food.imageName;

    // Set card styles
    this.foodCardWidth = this.utilsService.getAdjustCardWidth() + 'px';
  }

  /**
   * Open new page food detail
   */
  public onClickDetail() {
    window.open('/foodDetail/' + this.food.id);
  }
}
