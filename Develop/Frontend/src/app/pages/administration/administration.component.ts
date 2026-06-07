import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
})
export class AdministrationComponent implements OnInit {
  constructor(private route: ActivatedRoute) { }


  get isSettingTypeOpen() {
    return this.selectOpion === 3;
  }

  get isEnglishConfigOpen() {
    return this.selectOpion === 4;
  }

  get isEnglishTypeOpen() {
    return this.selectOpion === 5;
  }

  get isEnglishUserReportOpen() {
    return this.selectOpion === 8;
  }

  get isCryptoTokenConfigOpen() {
    return this.selectOpion === 11;
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

  ngOnInit() { }

  ///////////////////
  // SELECT OPTION //
  ///////////////////

  public selectSettingType() {
    this.selectOpion = 3;
  }

  public selectEnglishConfig() {
    this.selectOpion = 4;
  }

  public selectEnglishType() {
    this.selectOpion = 5;
  }

  public selectEnglishUserReport() {
    this.selectOpion = 8;
  }

  public selectCryptoTokenConfig() {
    this.selectOpion = 11;
  }
}
