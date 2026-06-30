import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router, RouterLink } from '@angular/router';

import { NgClass } from '@angular/common';
import { routes } from '../../../shared/routes/routes';
import { AuthService } from '../../../core/services';

@Component({
  selector: 'app-mobile-otp',
  templateUrl: './mobile-otp.component.html',
  styleUrls: ['./mobile-otp.component.scss'],
  standalone: true,
  imports: [RouterLink, FormsModule,ReactiveFormsModule,NgClass],
})
export class MobileOtpComponent implements OnInit{
  id;
  public routes = routes;
  private route=inject(ActivatedRoute);

  private service = inject(AuthService);
  otpForm:FormGroup;
  isAllvalid=false;
  isAllChcked=false;
  countDown: number;

  phoneNumber:string;
  lastFourDigits:string;
  public oneTimePassword = {
    data1: '',
    data2: '',
    data3: '',
    data4: '',
    data5: '',
    data6: '',
  };
  constructor(private router: Router,private fb:FormBuilder) {}
  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.id = this.route.snapshot.paramMap.get('id')});
    this.service.userPhoneNumber$.subscribe(phoneNumber => {
      this.phoneNumber = phoneNumber;
      this.lastFourDigits = phoneNumber.slice(-4);
    });

    this.createRegisterForm();
    this.startCountDown();
  }

  createRegisterForm() {

    this.otpForm =this.fb.group({
      data1: new FormControl('', [
        Validators.required,
      ]),
      data2: new FormControl('', [
        Validators.required,
      ]), 
      data3: new FormControl('', [
        Validators.required,
      ]),
      data4: new FormControl('', [
        Validators.required,
      ]), 
      data5: new FormControl('', [
        Validators.required,
      ]),
      data6: new FormControl('', [
        Validators.required,
      ]),
    });
  }
  public ValueChanged(data: string, box: string): void {
    let value=this.otpForm.value.data1+this.otpForm.value.data2+this.otpForm.value.data3+this.otpForm.value.data4+this.otpForm.value.data5+this.otpForm.value.data6;
    
    if (box == 'digit-1' && data.length > 0) {
      document.getElementById('digit-2')?.focus();
      this.isAllChcked=false;
    } else if (box == 'digit-2' && data.length > 0) {
      this.isAllChcked=false;
      document.getElementById('digit-3')?.focus();
    } else if (box == 'digit-3' && data.length > 0) {
      this.isAllChcked=false;
      document.getElementById('digit-4')?.focus();
    } else if (box == 'digit-4' && data.length > 0) {
      this.isAllChcked=false;
      document.getElementById('digit-5')?.focus();
    } else if (box == 'digit-5' && data.length > 0) {
      this.isAllChcked=false;
      document.getElementById('digit-6')?.focus();
    } else {
      this.isAllChcked=true;
      if(value.length==6){
          this.phoneNumber=this.id;
        
        this.service.verifyOtp({ identifier: this.phoneNumber, otp: +value }).subscribe((data) => {
          console.log(data);
          if(data.success){
            this.isAllvalid=true;
            this.router.navigate([routes.setPassword]);
          }
        });
      }
   
      return;
    }
  }
  public tiggerBackspace(data: string, box: string) {
    let StringyfyData;
    if (data) {
      StringyfyData = data.toString();
    } else {
      StringyfyData = null;
    }
    if (box == 'digit-6' && StringyfyData == null) {
      document.getElementById('digit-5')?.focus();
    } else if (box == 'digit-5' && StringyfyData == null) {
      document.getElementById('digit-4')?.focus();
    }else if (box == 'digit-4' && StringyfyData == null) {
      document.getElementById('digit-3')?.focus();
    }else if (box == 'digit-3' && StringyfyData == null) {
      document.getElementById('digit-2')?.focus();
    } else if (box == 'digit-2' && StringyfyData == null) {
      document.getElementById('digit-1')?.focus();
    }
  }
  resendOtp(){
    this.phoneNumber=this.id;
    this.service.resendOtp(this.phoneNumber).subscribe((data) => {
    });
    this.startCountDown();
  }
  startCountDown() {
    this.countDown = 60; // Set the countdown time here
    let intervalId = setInterval(() => {
      if(this.countDown > 0) {
        this.countDown--;
      } else {
        clearInterval(intervalId);
      }
    }, 1000);
  }
  onSubmit(){
    let value=this.otpForm.value.data1+this.otpForm.value.data2+this.otpForm.value.data3+this.otpForm.value.data4+this.otpForm.value.data5+this.otpForm.value.data6;
     
    this.router.navigate([routes.setPassword]);

  }
  public navigation() {
    this.router.navigate([routes.setPassword]);
  }
}
