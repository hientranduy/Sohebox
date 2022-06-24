import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SEOService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
  styleUrls: ['./administration.component.css']
})
export class AdministrationComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private seoService: SEOService,
    private toastr: ToastrService
  ) { }

  get isUserControlOpen() {
    return this.selectOpion === 1;
  }

  get isSettingConfigOpen() {
    return this.selectOpion === 2;
  }

  get isSettingTypeOpen() {
    return this.selectOpion === 3;
  }

  get isEnglishConfigOpen() {
    return this.selectOpion === 4;
  }

  get isEnglishTypeOpen() {
    return this.selectOpion === 5;
  }

  get isFoodConfigOpen() {
    return this.selectOpion === 6;
  }

  get isFoodTypeOpen() {
    return this.selectOpion === 7;
  }

  get isEnglishUserReportOpen() {
    return this.selectOpion === 8;
  }

  get isYoutubeChannelConfigOpen() {
    return this.selectOpion === 9;
  }

  get isMediaTypeOpen() {
    return this.selectOpion === 10;
  }
  selectOpion = 1;

  // Width change
  windownInnerWidth = window.innerWidth;
  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.windownInnerWidth = window.innerWidth;
  }
  @HostListener('window:orientationchange', ['$event'])
  onOrientationChange(event) {
    this.windownInnerWidth = window.innerWidth;
  }

  ngOnInit() {
    // CEO
    this.seoService.updateCEO(this.route);
  }

  ///////////////////
  // SELECT OPTION //
  ///////////////////
  public selectUserList() {
    this.selectOpion = 1;
  }

  public selectSettingConfig() {
    this.selectOpion = 2;
  }

  public selectSettingType() {
    this.selectOpion = 3;
  }

  public selectEnglishConfig() {
    this.selectOpion = 4;
  }

  public selectEnglishType() {
    this.selectOpion = 5;
  }

  public selectFoodConfig() {
    this.selectOpion = 6;
  }

  public selectFoodType() {
    this.selectOpion = 7;
  }

  public selectEnglishUserReport() {
    this.selectOpion = 8;
  }

  public selectYoutubeChannelConfig() {
    this.selectOpion = 9;
  }

  public selectMediaType() {
    this.selectOpion = 10;
  }
}
