import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { NgOtpInputModule } from 'ng-otp-input';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AuthRoutingModule,
    NgOtpInputModule
  ]
})
export class AuthModule { }
