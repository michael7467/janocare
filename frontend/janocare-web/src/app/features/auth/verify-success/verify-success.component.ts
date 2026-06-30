import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { first } from 'rxjs';
import { NotificationService, AuthService } from '../../../core';

import { NgIf } from '@angular/common';
import { routes } from '../../../shared/routes/routes';

@Component({
  selector: 'app-verify-success',
  templateUrl: './verify-success.component.html',
  styleUrls: ['./verify-success.component.scss'],
  standalone:true,
  imports:[NgIf]
})
export class VerifySuccessComponent implements OnInit {
  public routes = routes;
  // set the currenr year
  year: number = new Date().getFullYear();
  constructor(private router: Router, private notify: NotificationService, private authenticationService: AuthService) {}

  ngOnInit(): void {
    document.body.classList.remove('auth-body-bg');
  }
  isLoading = false;
  proceedToMyAccount() {
    this.router.navigate([routes.professionalLogin]);

    // const uname = this.authenticationService.uname;
    // const password = this.authenticationService.password;
    // console.log(uname,password);
    // this.isLoading = true;
    // if (uname && password) {
    //   this.authenticationService
    //     .login(uname, password)
    //     .pipe(first())
    //     .subscribe(
    //       (data) => {
    //         console.log(data);
    //         if (!data.success && data.statusCode === 412) {
          
    //           this.isLoading = false;
    //           this.router.navigate(['/','auth','verify'], { queryParams: { p: uname } });
    //         } else if (!data.success && data.statusCode === 419) {
    //            console.log('eeeeeeeeeeeeeeeeee');
    //           this.isLoading = false;
    //           this.router.navigate([routes.setPassword], { queryParams: { p: uname } });
    //         } else if (!data.success) {

    //           this.notify.showError(`${data?.message}`);
    //           this.router.navigate([routes.professionalLogin]);

    //           this.isLoading = false;
    //         } else {
    //           this.isLoading = false;
    //           this.router.navigate(['/']);
    //         }
    //       },
    //       (error) => {
    //         this.router.navigate([routes.professionalLogin]);
    //         this.isLoading = false;

    //         throw error;
    //       },
    //     );
    // } else {
    //   this.isLoading = false;
    //   this.router.navigate([routes.professionalLogin]);
    // }
  }
}
