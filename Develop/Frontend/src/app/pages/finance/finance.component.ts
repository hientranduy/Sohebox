import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SEOService } from '@app/_common/_services';

@Component({
  selector: 'app-finance',
  templateUrl: './finance.component.html',
  styleUrls: ['./finance.component.css']
})
export class FinanceComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private seoService: SEOService) { }

  get isGoldOpen() {
    return this.selectOpion === 1;
  }

  get isOilOpen() {
    return this.selectOpion === 2;
  }

  get isCurrencyOpen() {
    return this.selectOpion === 3;
  }

  get isStockOpen() {
    return this.selectOpion === 4;
  }
  selectOpion = 1;

  // Width change
  windownInnerWidth = window.innerWidth;

  ngOnInit(): void {
    // CEO
    this.seoService.updateCEO(this.route);
  }
  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.windownInnerWidth = window.innerWidth;
  }
  @HostListener('window:orientationchange', ['$event'])
  onOrientationChange(event) {
    this.windownInnerWidth = window.innerWidth;
  }

  ///////////////////
  // SELECT OPTION //
  ///////////////////
  public selectGold() {
    this.selectOpion = 1;
  }

  public selectOil() {
    this.selectOpion = 2;
  }

  public selectCurrency() {
    this.selectOpion = 3;
  }

  public selectStock() {
    this.selectOpion = 4;
  }
}
