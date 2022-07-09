import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '@app/_common/alert';
import { SEOService, SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { first } from 'rxjs/operators';
import { AuthenticationService, UserService } from '../_service';

@Component({
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
    changePasswordForm: FormGroup;
    submitted = false;
    returnUrl: string;
    changePasswordRequestForm: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private route: ActivatedRoute,
        private seoService: SEOService,
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService,
        private toastr: ToastrService,
        private spinner: SpinnerService
    ) {
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) {
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        // CEO
        this.seoService.updateCEO(this.route);

        this.changePasswordForm = this.formBuilder.group({
            username: ['', Validators.required],
            oldPassword: ['', [Validators.required, Validators.minLength(6)]],
            newPassword: ['', [Validators.required, Validators.minLength(6)]]
        });

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() { return this.changePasswordForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.changePasswordForm.invalid) {
            return;
        }

        // Show loading
        this.spinner.show();

        // Process change password
        this.userService.changePassword(this.changePasswordForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    // Hide loading
                    this.spinner.hide();

                    // Send toast success
                    this.toastr.success('Password of your account ' + this.changePasswordForm.value.username + ' is successful updated');

                    this.alertService.success('Change Password successful', true);
                    this.router.navigate(['/login']);
                },
                error => {
                    // Hide loading
                    this.spinner.hide();

                    // Remove user from local storage to log user out
                    localStorage.removeItem('currentUser');

                    // Show alert message
                    this.alertService.error(error);
                });
    }
}
