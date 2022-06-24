import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '@app/_common/alert';
import { SEOService, SpinnerService } from '@app/_common/_services';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../_service';

@Component({
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    submitted = false;
    returnUrl: string;
    isLoadingLogin: Boolean;

    accountDisplay: string;
    passwordDisplay: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private seoService: SEOService,
        private authenticationService: AuthenticationService,
        private alertService: AlertService,
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

        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        // Show loading
        this.isLoadingLogin = true;
        this.spinner.show();

        // Authenticate
        this.authenticationService.login(this.f.username.value, this.f.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    // Hide loading
                    this.isLoadingLogin = false;
                    this.spinner.hide();

                    // Navigate to page "/"
                    this.router.navigate([this.returnUrl]);
                },
                error => {
                    // Hide loading
                    this.isLoadingLogin = false;
                    this.spinner.hide();

                    // Alert error message
                    this.alertService.error(error);
                });
    }

    fillVisitorAccount() {
        this.accountDisplay = 'visitor';
        this.passwordDisplay = 'visitor';
        this.onSubmit();
    }
}
