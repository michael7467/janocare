import { Component, NgModule, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { NgbAlert, NgbAlertModule, NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, Subject } from 'rxjs';
import { AuthService, NotificationService } from '../../../core';

import { CommonModule } from '@angular/common';
import { routes } from '../../../shared/routes/routes';
import { NgOtpInputModule } from 'ng-otp-input';

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.scss'],
  standalone: true,
  imports: [NgbAlertModule, NgOtpInputModule, FormsModule, NgbToastModule, ReactiveFormsModule, CommonModule]
})
export class VerifyComponent implements OnInit {
  public routes=routes;
  year: number = new Date().getFullYear();
  message = 'Verify your account Registration';
  verifyForm: FormGroup;
  isLoading = false;
  config = {
    allowNumbersOnly: true,
    length: 6,
    isPasswordInput: false,
    disableAutoFocus: false,
    placeholder: '',
    inputStyles: {
      width: '40px',
      height: '40px',
    },
  };
  constructor(private formBuilder: FormBuilder, private notify: NotificationService, private authService: AuthService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params: Params) => {
      if (Object.keys(params).length && params['p']) {
        this.createForm();
        this.email.patchValue(params['p']);
      } else if (Object.keys(params).length && params['ndevice']) {
        this.createForm();
        this.message = `New Device / Browser access detected. A 6-digit Verification is sent to your email. Please Verify.`;
        this.email.patchValue(params['ndevice']);
      } else {
        this.router.navigate([routes.professionalLogin]);
      }
    });

    this._success.subscribe((message) => (this.successMessage = message));
    this._success.pipe(debounceTime(5000)).subscribe(() => {
      if (this.selfClosingAlert) {
        this.selfClosingAlert.close();
      }
    });
  }
  private createForm() {
    this.verifyForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      otp: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
    });
  }
  get email(): AbstractControl {
    return this.verifyForm.get('email') as AbstractControl;
  }
  get otp(): AbstractControl {
    return this.verifyForm.get('otp') as AbstractControl;
  }

  onOtpChange(otp) {
    this.otp.patchValue(otp);
  }
  resend() {
    this.changeSuccessMessage();
  }
  goToNext(frm) {
    const payload = {
      identifier: this.email.value,
      otp: this.otp.value,
    };
    this.authService.verifyOtp(payload).subscribe(
      (r) => {
    
        if (r.success) {
          
          
          this.router.navigate([routes.setPassword], { queryParamsHandling: 'merge', queryParams: { p: this.email.value } });
        } else {
          this.notify.info(`Invalid Account or Verification code provided, Please try again with the correct values`);
        }
      },
      (e) => {
        this.notify.showError(`Invalid Account or Verification code provided, Please try again with the correct values.`);
      },
    );
  }
  // alert
  private _success = new Subject<string>();

  successMessage = '';

  @ViewChild('selfClosingAlert', { static: false }) selfClosingAlert: NgbAlert;

  public changeSuccessMessage() {
    this.authService.resendOtp(this.email.value).subscribe((r) => {
      this.disableResendFor(60)
      this._success.next(`A new verification code is sent to ${this.email.value}.`);
    });
  }
  resendButtonLabel = 'Resend'
  isResendEnabled = true
  disableResendFor(seconds) {
    this.isResendEnabled = false
    let textSec: any = "0";
    let statSec: number = seconds;

    const timer = setInterval(() => {
      seconds--;
      if (statSec != 0) statSec--;
      else statSec = 59;

      if (statSec < 10) {
        textSec = "0" + statSec;
      } else textSec = statSec;

      this.resendButtonLabel = `Resend Again in ${Math.floor(seconds / 60)}:${textSec}`;
      if (seconds == 0) {
        clearInterval(timer);
        this.resendButtonLabel = 'Resend'
        this.isResendEnabled = true
      }
    }, 1000);
  }
}
