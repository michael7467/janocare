import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Params, Router, RouterConfigurationFeature, RouterLink } from '@angular/router';

import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, ValidatorFn, Validators } from '@angular/forms';
import { CommonModule, NgClass, NgIf } from '@angular/common';
import { AuthService, NotificationService } from '../../../core';
import { first } from 'rxjs';
import { routes } from '../../../shared/routes/routes';

@Component({
  selector: 'app-set-password',
  standalone: true,
  imports: [CommonModule,RouterLink,ReactiveFormsModule,NgIf,NgClass],
  templateUrl: './set-password.component.html',
  styleUrls: ['./set-password.component.scss']
})
export class SetPasswordComponent implements OnInit {
  resetForm: FormGroup;
  submitted = false;
  error = '';
  success = '';
  uname = '';
  loading = false;
  showPrevPassword = false;
  showNewPassword = false;
  showConfirmPassword = false;
  public routes=routes;

  // set the currenr year
  year: number = new Date().getFullYear();

  // tslint:disable-next-line: max-line-length
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private notify: NotificationService,
    private router: Router,
    private authenticationService: AuthService,
  ) {
    this.uname = this.authenticationService.uname;
  }

  ngOnInit() {
    this.route.queryParams.subscribe((params: Params) => {
      if (Object.keys(params).length && params['p']) {
        this.uname = params['p'];
        this.resetForm = this.formBuilder.group({
          identifier: [this.uname, [Validators.required]],
          previousPassword: ['', [Validators.required]],
          newPassword: ['', [Validators.required]],
          confirmPassword: ['', [Validators.required]],
        });
      }
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.resetForm.controls;
  }
  toggleConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }
  togglePassword() {
    console.log(this.showNewPassword);
    this.showNewPassword = !this.showNewPassword;
    console.log(this.showNewPassword);

  }
  /**
   * On submit form
   */
  onSubmit() {
    this.success = '';
    this.submitted = true;
    console.log(this.resetForm.value);
    // stop here if form is invalid
    if (this.resetForm.invalid) {
      return;
    }
    this.proceedToMyAccount();
  }
  proceedToMyAccount() {
    const uname = this.resetForm.value.identifier;
    const password = this.f['newPassword'].value;
    this.loading = true;
   
    if (uname && password) {
      this.authenticationService
        .setNewPassword(uname, this.f['previousPassword'].value, this.f['newPassword'].value, this.f['confirmPassword'].value)
        .pipe(first())
        .subscribe(
          (data) => {
            console.log(data);
          
            console.log(this.router.navigate([routes.verifySuccess]))
            if (!data.success && data.statusCode === 412) {
              //OTP_NOT_VERIFIED
              // verify account...
              this.loading = false;
              this.router.navigate([routes.verify], { queryParams: { p: uname } });
            } else if (!data.success) {
              // {"success":false,"statusCode":401,"message":"Invalid username and/or password!","errors":null,"timestamp":"2022-07-21T23:45:17.227Z","path":"/api/auth/user/signin"}
              this.notify.showError(`${data?.message}`);

              this.loading = false;
            } 
            else if(data.success){
              this.loading = false;
              
              this.router.navigate(['/','auth','verify-success']);
              console.log(this.router.navigate(['/','auth','verify-success']))
            }
            else {
              // this.router.navigate(['/','auth','verify-success']);
              // this.router.navigate([routes.verifySuccess]);
              this.router.navigate(['/','auth','verify-success']);
              console.log(this.router.navigate(['/','auth','verify-success']))
            }
          },
          (error) => {
            this.router.navigate([routes.professionalLogin]);
            this.loading = false;

            throw error;
          },
        );
    } else {
      this.loading = false;
      this.router.navigate([routes.professionalLogin]);
    }
  }
}
