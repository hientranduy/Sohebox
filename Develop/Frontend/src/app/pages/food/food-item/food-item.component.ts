import { Component, HostListener, Input, OnInit } from '@angular/core';
import { AppSettings } from '@app/app.settings';
import { Food } from '@app/models/food';
import { UtilsService } from '@app/services/utils.service';

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

  constructor(private utilsService: UtilsService) {}

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
