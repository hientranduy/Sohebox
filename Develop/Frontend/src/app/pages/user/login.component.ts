import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '@app/commons/alert/alert.service';
import { AuthenticationService } from '@app/services/authentication.service';
import { SpinnerService } from '@app/services/spinner.service';

@Component({
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;

  accountDisplay: string;
  passwordDisplay: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private spinner: SpinnerService,
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    // Show loading
    this.spinner.show();

    // Authenticate
    this.authenticationService
      .login(this.f['username'].value, this.f['password'].value)
      .subscribe({
        next: async (res) => {
          this.router.navigate([this.returnUrl]);
        },
        error: (err) => {
          this.alertService.error(err);
        },
      });

    // Hide loading
    this.spinner.hide();
  }

  fillVisitorAccount() {
    this.accountDisplay = 'visitor';
    this.passwordDisplay = 'visitor';
    this.onSubmit();
  }
}
