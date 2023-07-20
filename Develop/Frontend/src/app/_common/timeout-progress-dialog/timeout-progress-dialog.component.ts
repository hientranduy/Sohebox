import { Component, Input, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthenticationService } from "@app/user/_service";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-timeout-progress-dialog",
  templateUrl: "./timeout-progress-dialog.component.html",
})
export class TimeoutProgressDialogComponent implements OnInit {
  @Input() countMinutes: number;
  @Input() countSeconds: number;
  @Input() progressCount: number;
  @Input() progressCountMax: number;

  constructor(
    public activeModal: NgbActiveModal,
    private router: Router,
    private authenticationService: AuthenticationService,
  ) {}

  ngOnInit() {}

  /**
   * Continue button
   */
  public continue() {
    this.activeModal.close(null);
  }

  /**
   * Logout button
   */
  public logout() {
    this.activeModal.close("logout");
    this.authenticationService.logout();
    this.router.navigate(["/login"]);
  }
}
