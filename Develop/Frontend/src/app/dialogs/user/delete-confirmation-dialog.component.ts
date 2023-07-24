import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService } from '@app/commons/alert/alert.service';
import { AuthenticationService } from '@app/services/authentication.service';
import { BackendService } from '@app/services/backend.service';
import { SpinnerService } from '@app/services/spinner.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: 'delete-confirmation-dialog.component.html',
})
export class DeleteConfirmationDialogComponent implements OnInit {
  @Input() title: string;
  @Input() message: string;
  @Input() btnOkText: string;
  @Input() btnCancelText: string;

  constructor(
    private activeModal: NgbActiveModal,
    private router: Router,
    private authenticationService: AuthenticationService,
    private backendService: BackendService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {}

  ngOnInit() {}

  public decline() {
    this.activeModal.close(false);
  }

  public accept() {
    // Delete user
    this.spinner.show();
    this.backendService.deleteLoggedUser().subscribe(
      (data) => {
        // Logout current user
        this.authenticationService.logout();

        // Send alert message
        this.alertService.success('User delete successful', true);

        // Navigate to login page
        this.router.navigate(['/login']);

        this.spinner.hide();
      },
      (error) => {
        this.spinner.hide();
        this.alertService.error(error);
      },
    );

    // Close dialog
    this.activeModal.close(true);
  }

  public dismiss() {
    this.activeModal.dismiss();
  }
}
