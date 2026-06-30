import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import {  FormControl, ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { NgClass, NgIf } from '@angular/common';

import { first } from 'rxjs';
import { routes } from '../../../shared/routes/routes';
import { AuthService, NotificationService } from '../../../core/services';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [RouterLink,ReactiveFormsModule,NgClass,NgIf],
})
export class LoginComponent implements OnInit {
    // Login Form
    loginForm!: UntypedFormGroup;
    submitted = false;
    fieldTextType!: boolean;
    error = '';
    returnUrl!: string;
    isLoading = false;
  
    toast!: false;
  // set the current year
  year: number = new Date().getFullYear();
  inBrowser: boolean;

  public routes = routes;
  public navigation() {
    this.router.navigate(['/']);
  }
  ngOnInit(): void {
     this.returnUrl = this.route.snapshot.queryParamMap.get('returnUrl') ?? '';
    this.createLoginForm();
  }

  private fb = inject(UntypedFormBuilder);
  private route = inject(ActivatedRoute);
  private authService = inject(AuthService);
  private router = inject(Router);
  private toastService = inject(NotificationService);
  createLoginForm() {
    this.loginForm = this.fb.group({
      password: new FormControl('', Validators.required),
      phoneOrEmail: ['', [Validators.required]],
      identifier: ['', []],
      email: ['', []],
    })
  }
  get f() {
    return this.loginForm.controls;
  }
  isPhoneOrEmailValidator(): ValidatorFn {
    return (control): ValidationErrors => {
      // const parsedPhone = parsePhoneNumber(control.value);
      const validEmailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
      // const isPhoneNumber = parsedPhone.valid;
      const isEmail = control.value?.match(validEmailRegex);
      // const isValidPhoneOrEmail = isPhoneNumber || isEmail;
       const isValidPhoneOrEmail = isEmail? true: false;

      // const identifier = isPhoneNumber ? parsedPhone.number.e164 : '';
        const identifier = isEmail ? control.value : '';
      const email = isEmail ? control.value : '';
      if (this.loginForm) {
        this.loginForm.patchValue({ identifier, email });
      }

      if (!isValidPhoneOrEmail) {
        return { phoneOrEmail: true };
      } else {
        return null;
      }
    };
  }
  onSubmit() {
    this.submitted = true;
    const phoneOrEmail = this.f['phoneOrEmail'].value;
    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    } else {
      this.isLoading = true;
      const loginReqData = { identifier: phoneOrEmail, password: this.f['password'].value };
      console.log('returnUrl', this.returnUrl);
      this.authService
        .login(phoneOrEmail,this.f['password'].value )
        .pipe(first())
        .subscribe(
          (data) => {
          
            if (!data.success && data.statusCode === 412) {
              //OTP_NOT_VERIFIED
              // verify account...
              this.isLoading = false;
            
              this.router.navigate([routes.verify], { queryParamsHandling: 'merge', queryParams: { p: this.f['identifier'].value } });
            } else if (!data.success && data.statusCode === 419) {
              // initial / password reset......
               
              this.isLoading = false;
              this.router.navigate([routes.setPassword], { queryParamsHandling: 'merge', queryParams: { p: this.f['identifier'].value } });
            } 
            else if (!data.success && data.statusCode === 416) {
              // initial / password reset......
              this.authService.resendOtp(this.f['identifier'].value).subscribe((r) => {
                if (r.success) {
                  this.isLoading = false;
                  this.router.navigate([routes.setPassword], { queryParamsHandling: 'merge', queryParams: { p: this.f['identifier'].value } });
                }
              });
  
            }else if (!data.success) {
              this.toastService.show(data?.message, { classname: 'bg-danger text-white', delay: 15000 });
              this.isLoading = false;
            } else {
              this.isLoading = false;
              this.toastService.success(data?.message || `Login successful!`);
              if(this.returnUrl === undefined || this.returnUrl === null || this.returnUrl === ''){
               console.log('no return url, navigating to dashboard')
                this.router.navigate([routes.professionalDashboard]);
                
              }else{
                 this.router.navigateByUrl(this.returnUrl);
              }
            }
          },
          (error) => {
            this.isLoading = false;
            throw error;
          },
        );
    }
  }

  toggleFieldTextType() {
    this.fieldTextType = !this.fieldTextType;
  }
}
