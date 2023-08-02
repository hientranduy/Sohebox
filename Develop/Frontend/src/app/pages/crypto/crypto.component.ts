import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-crypto',
  templateUrl: './crypto.component.html',
})
export class CryptoComponent implements OnInit {
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {}

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

  ///////////////////
  // SELECT OPTION //
  ///////////////////
  // Select tab
  selectOpion = 1;

  get isPortfolioOpen() {
    return this.selectOpion === 1;
  }

  get isCoinOpen() {
    return this.selectOpion === 2;
  }

  public selectPortfolio() {
    this.selectOpion = 1;
  }

  public selectCoin() {
    this.selectOpion = 2;
  }
}
