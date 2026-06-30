
import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { AsyncPipe, CommonModule, NgClass, NgFor, NgIf } from '@angular/common';
import { parsePhoneNumber } from 'awesome-phonenumber';
import { FormControl, FormsModule, ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { NgxMatIntlTelInputComponent } from 'ngx-mat-intl-tel-input';
import { AuthService, NotificationService, ProfessionService, ProfessionType, cleanObject } from '../../../core';
import { CountryISO, NgxIntlTelInputModule, SearchCountryField } from 'ngx-intl-tel-input';

import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { first } from 'rxjs';
import { routes } from '../../../shared/routes/routes';

@Component({
  selector: 'app-register',
  templateUrl: './register-professional.component.html',
  styleUrls: ['./register-professional.component.scss'],
  standalone: true,
  imports: [RouterLink, FormsModule, 
  NgxIntlTelInputModule,
    CommonModule, NgbDropdownModule, NgClass, AsyncPipe, NgFor, ReactiveFormsModule, NgIf,
     NgxMatIntlTelInputComponent],
})
export class RegisterProfessionalComponent {
  professionTypes: ProfessionType[];
  signupForm!: UntypedFormGroup;
  submitted = false;
  error: string;
  isLoading = false;
  returnUrl!: string;
  phone: string;
  private fb = inject(UntypedFormBuilder);
  private service = inject(AuthService);
  private professionTypeService = inject(ProfessionService);
  private router = inject(Router);
  private toastService = inject(NotificationService);
  // Login Form
  public CountryISO = CountryISO;
  SearchCountryField = SearchCountryField;
  successmsg = false;

  // set the current year
  year: number = new Date().getFullYear();
  maxLength = 9;
  public routes = routes;
  ngOnInit(): void {
    this.professionTypeService.getAll().subscribe((data) => {
      this.professionTypes = data;
    });

    this.createRegisterForm();
  }
  get f() {
    return this.signupForm.controls;
  }
  createRegisterForm() {
    this.signupForm = this.fb.group({
      username: new FormControl('', [Validators.required, Validators.minLength(5)]),
      phoneOrEmail: [''],
      phone: ['', [Validators.required, this.isPhoneValidator]],
      email: ['', [Validators.required, this.isEmailValidator]],
      professionTypeId: ['', [Validators.required]],
      role: 'PROFESSIONAL',
      deviceName: 'Chrome Browser',
      deviceType: 'WEB'
    });
  }

  isPhoneOrEmailValidator(): ValidatorFn {
    return (control): ValidationErrors => {
      const parsedPhone = parsePhoneNumber(control.value);
      const validEmailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
      const isPhoneNumber = parsedPhone.valid;
      const isEmail = control.value?.match(validEmailRegex);
      const isValidPhoneOrEmail = isPhoneNumber || isEmail;
      const phone = isPhoneNumber ? parsedPhone.number.e164 : '';
      const email = isEmail ? control.value : '';
      if (this.signupForm) {
        this.signupForm.patchValue({ phone, email });
      }

      if (!isValidPhoneOrEmail) {
        return { phoneOrEmail: true };
      } else {
        return null;
      }
    };
  }
  isEmailValidator(): ValidatorFn {
    return (control): ValidationErrors => {
      const validEmailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
      const isEmail = control.value?.match(validEmailRegex);
      const email = isEmail ? control.value : '';

      if (!isEmail) {
        return { email: true };
      } else {
        setTimeout(() => {
          if (this.signupForm) {
            this.signupForm.patchValue({ email }, { emitEvent: false });
          }
        }, 0);
        return null;
      }
    };
  }
  isPhoneValidator(): ValidatorFn {
    return (control): ValidationErrors => {
      const parsedPhone = parsePhoneNumber(control.value);
      const isPhoneNumber = parsedPhone.valid;
      const phone = isPhoneNumber ? parsedPhone.number.e164 : '';

      if (!isPhoneNumber) {
        return { phone: true };
      } else {
        setTimeout(() => {
          if (this.signupForm) {
            this.signupForm.patchValue({ phone }, { emitEvent: false });
          }
        }, 0);
        return null;
      }
    };
  }
onSubmit() {
  this.submitted = true;

  const username = this.f['username'].value;
  const internationalNumber = this.f['phone'].value;
  const phone = internationalNumber?.e164Number;
  const email = this.f['email'].value;
  const professionTypeId = this.f['professionTypeId'].value;
  const phoneOrEmail = this.f['phoneOrEmail'].value;

  if (this.signupForm.invalid) {
    return;
  }

  this.isLoading = true;

  this.service
    .register(
      cleanObject({
        username,
        phone,
        email,
        professionTypeId,
        role: 'PROFESSIONAL',
        deviceName: navigator.userAgent || 'Web Browser',
        deviceType: 'WEB'
      })
    )
    .pipe(first())
    .subscribe(
      (data) => {
        this.isLoading = false;

        if (data.success) {
          console.log(phoneOrEmail);
          this.router.navigate(['/', 'auth', 'verify'], {
            queryParams: { p: email }
          });
        } else {
          this.toastService.show(data?.message, {
            classname: 'bg-danger text-white',
            delay: 15000
          });
        }
      },
      (error) => {
        this.isLoading = false;
        throw error;
      }
    );
}
}
