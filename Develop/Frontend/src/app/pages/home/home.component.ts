import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '@app/models/user';
import { AuthenticationService } from '@app/services/authentication.service';
import { UtilsService } from '@app/services/utils.service';

@Component({
  templateUrl: 'home.component.html',
  styleUrls: ['home.component.css'],
})
export class HomeComponent implements OnInit {
  currentUser: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    public utilsService: UtilsService,
  ) {
    // Get user info
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );
  }

  ngOnInit() {}

  /**
   * navigate english report page
   */
  public navigateEnglishReport() {
    this.router.navigate(['/englishReport', this.currentUser.id]);
  }

  public openAdminApplication() {
    this.router.navigate(['/adminDev']);
  }

  public openAccountApplication() {
    this.router.navigate(['/account']);
  }

  public openFoodApplication() {
    this.router.navigate(['/food']);
  }

  public openEnglishApplication() {
    this.router.navigate(['/english']);
  }

  public openMediaApplication() {
    this.router.navigate(['/media']);
  }

  public openFinanceApplication() {
    this.router.navigate(['/finance']);
  }

  public openCryptoApplication() {
    this.router.navigate(['/crypto']);
  }
}
