import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SEOService, SpinnerService } from '@app/_common/_services';
import { ToastrService } from 'ngx-toastr';
import { first } from 'rxjs/operators';
import { AuthenticationService, UserService } from '../_service';
import { AlertService } from '@app/_common/alert/alert.service';

@Component({
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private seoService: SEOService,
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }

  ngOnInit() {
    // CEO
    this.seoService.updateCEO(this.route);

    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    // Show loading
    this.spinner.show();

    // Register
    this.userService
      .register(this.registerForm.value)
      .pipe(first())
      .subscribe(
        (data) => {
          // Hide loading
          this.spinner.hide();

          // Send toast success
          this.toastr.success(
            'Your account ' +
              this.registerForm.value.username +
              ' is successful created',
          );

          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        (error) => {
          // Hide loading
          this.spinner.hide();

          // Alert error message
          this.alertService.error(error);
        },
      );
  }
}
