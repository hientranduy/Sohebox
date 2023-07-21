import { Injectable } from '@angular/core';
import { User } from '@app/_common/_models';
import { AuthenticationService } from '@app/user/_service';

@Injectable({ providedIn: 'root' })
export class UtilsService {
  // Logged user
  currentUser: User;

  constructor(private authenticationService: AuthenticationService) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );
  }

  get isAdmin() {
    let result = false;
    if (this.currentUser && this.currentUser.roleName === 'creator') {
      result = true;
    }
    return result;
  }

  public IsSafari() {
    let result = false;

    const vendor = navigator.vendor.toLowerCase();
    if (vendor && vendor.indexOf('apple') !== -1) {
      result = true;
    }

    return result;
  }

  public IsSchrome() {
    let result = false;

    const vendor = navigator.vendor.toLowerCase();
    if (vendor && vendor.indexOf('google') !== -1) {
      result = true;
    }

    return result;
  }

  /**
   * Return card width
   */
  public getAdjustCardWidth() {
    const windownInnerWidth = window.innerWidth;

    let result = 250;

    if (windownInnerWidth >= 1000) {
      result = (windownInnerWidth - 70) / 5;
    } else if (windownInnerWidth >= 910) {
      result = (windownInnerWidth - 60) / 4;
    } else if (windownInnerWidth >= 540) {
      result = (windownInnerWidth - 50) / 3;
    } else if (windownInnerWidth >= 270) {
      result = (windownInnerWidth - 40) / 2;
    } else {
      result = (windownInnerWidth - 30) / 1;
    }

    return result;
  }
}
