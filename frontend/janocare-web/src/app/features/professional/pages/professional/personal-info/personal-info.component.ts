import { CommonModule, NgClass, NgIf } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {
  AbstractControl,
  FormControl,
  ReactiveFormsModule,
  UntypedFormBuilder,
  UntypedFormGroup,
  ValidatorFn,
  Validators
} from '@angular/forms';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';

import { routes } from '../../../../../shared/routes/routes';
import { PersonalInfoSettingComponent } from './personal-info-setting/personal-info-setting.component';
import {
  AuthService,
  NotificationService,
  User,
  UserProfile
} from '../../../../../core';

import {
  ImgFallbackDirective,
  RemovePortPipe,
  TpInputComponent
} from '../../../../../shared';
import { environment } from '../../../../../../environments/environment.development';

@Component({
  selector: 'app-personal-info-list',
  standalone: true,
  imports: [
    RouterLink,
    NgClass,
    NgIf,
    CommonModule,
    NgbNavModule,
    PersonalInfoSettingComponent,
    ReactiveFormsModule,
    RemovePortPipe,
    ImgFallbackDirective,
    TpInputComponent
  ],
  templateUrl: './personal-info.component.html',
  styleUrls: ['./personal-info.component.scss']
})
export class PersonalInfoComponent implements OnInit {
  setPassForm!: UntypedFormGroup;

  public routes = routes;

  submitted = false;

  fieldTextType = false;
  fieldTextTypeOld = false;
  fieldTextTypeNew = false;

  user!: UserProfile;
  userProfile!: User;

  imageUrl: string | ArrayBuffer =
    'assets/img/patients/patient.jpg';

  public authService = inject(AuthService);

  private service = inject(AuthService);
  private fb = inject(UntypedFormBuilder);
  private router = inject(Router);
  private toastService = inject(NotificationService);

  ngOnInit(): void {
    this.getData();
    this.createSetPasswordForm();
  }

  getData(): void {
    this.service.userProfile$.subscribe(user => {
      if (!user) {
        return;
      }

      this.userProfile = user;
      this.user = user.userProfile;
      this.imageUrl =
        user.userProfile?.profilePic ||
        'assets/img/patients/patient.jpg';
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];

    if (!file) {
      return;
    }

    this.authService
      .updateUserProfilePic(this.userProfile, file)
      .subscribe({
        next: (result) => {
          if (result) {
            const reader = new FileReader();

            reader.onload = (e: ProgressEvent<FileReader>) => {
              this.imageUrl =
                e.target?.result ||
                'assets/img/patients/patient.jpg';
            };

            reader.readAsDataURL(file);

            this.router.navigateByUrl(
              routes.professionalInfo
            );
          }
        },
        error: (error) => {
          this.toastService.showError(
            error?.error?.message ||
            'Failed to update profile picture'
          );
        }
      });
  }
getProfileImageUrl(profilePic?: string | null): string {
  if (!profilePic) {
    return 'assets/images/users/user-dummy-img.jpg';
  }

  if (profilePic.startsWith('http')) {
    return profilePic;
  }

  return `${environment.apiUrl}/${profilePic}`;
}
  createSetPasswordForm(): void {
    this.setPassForm = this.fb.group(
      {
        previousPassword: new FormControl('', [
          Validators.required
        ]),
        password: new FormControl('', [
          Validators.required
        ]),
        confirmPassword: new FormControl('', [
          Validators.required
        ])
      },
      {
        validators: this.matchValidator(
          'password',
          'confirmPassword'
        )
      }
    );
  }

  get previousPassword(): AbstractControl | null {
    return this.setPassForm.get('previousPassword');
  }

  get password(): AbstractControl | null {
    return this.setPassForm.get('password');
  }

  get confirmPassword(): AbstractControl | null {
    return this.setPassForm.get('confirmPassword');
  }

  onSubmitPassword(): void {
    this.submitted = true;

    if (this.setPassForm.invalid) {
      return;
    }

    this.service
      .setNewPassword(
        this.service.userPhoneNumber,
        this.setPassForm.value.previousPassword,
        this.setPassForm.value.password,
        this.setPassForm.value.confirmPassword
      )
      .subscribe({
        next: () => {
          this.router.navigate([
            routes.professionalDashboard
          ]);
        },
        error: (error) => {
          this.toastService.showError(
            error?.error?.message ||
            'Failed to change password'
          );
        }
      });
  }

  matchValidator(
    controlName: string,
    matchingControlName: string
  ): ValidatorFn {
    return (abstractControl: AbstractControl) => {
      const control = abstractControl.get(controlName);
      const matchingControl =
        abstractControl.get(matchingControlName);

      if (!control || !matchingControl) {
        return null;
      }

      if (
        matchingControl.errors &&
        !matchingControl.errors['confirmedValidator']
      ) {
        return null;
      }

      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({
          confirmedValidator: 'Passwords do not match.'
        });

        return {
          confirmedValidator: true
        };
      }

      matchingControl.setErrors(null);
      return null;
    };
  }

  toggleFieldTextType(): void {
    this.fieldTextType = !this.fieldTextType;
  }

  toggleFieldTextTypeOld(): void {
    this.fieldTextTypeOld = !this.fieldTextTypeOld;
  }

  toggleFieldTextTypeNew(): void {
    this.fieldTextTypeNew = !this.fieldTextTypeNew;
  }
}