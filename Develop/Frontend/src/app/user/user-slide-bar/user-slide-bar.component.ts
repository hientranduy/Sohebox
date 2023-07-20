import { Component, OnDestroy, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { UserDialogService } from "../_dialogs";
import { User } from "../_models";
import { AuthenticationService } from "../_service";

@Component({
  selector: "app-user-slide-bar",
  templateUrl: "./user-slide-bar.component.html",
  styleUrls: ["./user-slide-bar.component.css"],
})
export class UserSlideBarComponent implements OnInit, OnDestroy {
  currentUser: User;
  currentUserSubscription: Subscription;
  title: string;
  message: string;
  information: string;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private userDialogService: UserDialogService,
  ) {
    this.currentUserSubscription =
      this.authenticationService.currentUser.subscribe((user) => {
        this.currentUser = user;
      });
  }

  ngOnInit() {}

  ngOnDestroy() {
    this.currentUserSubscription.unsubscribe();
  }

  public logout() {
    this.authenticationService.logout();
    this.router.navigate(["/"]);
  }

  public deleteUser() {
    this.title = "User Deletion";
    this.message =
      "Do you really want to delete your user <" +
      this.currentUser.username +
      "> ?";
    this.information =
      "All information associated to this user profile will be permanently deleted.";
    this.userDialogService.deleteUser(
      this.title,
      this.message,
      this.information,
    );
  }

  public changePassword() {
    this.title = "Change Password";
    this.message = "";
    this.userDialogService.changePassword(this.title, this.message);
  }

  public changePrivateKey() {
    this.title = "Change Private Key";
    this.message = "";
    this.userDialogService.changePrivateKey(this.title, this.message);
  }

  public updateUser() {
    this.title = "Update User Information";
    this.message = "User " + this.currentUser.username;
    this.userDialogService.updateUser(this.title, this.message);
  }

  public setEnglishGrade() {
    this.title = "Update English Level";
    this.message = "User " + this.currentUser.username;
    this.userDialogService.updateEnglishLevel(this.title, this.message);
  }

  /**
   * navigate english report page
   */
  public navigateEnglishReport() {
    this.router.navigate(["/englishReport", this.currentUser.id]);
  }
}
