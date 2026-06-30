import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full',
      },
      {
        path: 'login',
        loadComponent: () => import('./login/login.component').then((m) => m.LoginComponent),
        // canMatch: [noAuthGuard],
      },
      {
        path: 'register',
        loadChildren: () => import('./register/register.routes').then((m) => m.routes),
        // canActivate: [noAuthGuard],

      },
       {
        path: 'register-professional',
        loadChildren: () => import('./register-professional/register-professional.routes').then((m) => m.routes),
        // canActivate: [noAuthGuard],

      },
      {
        path: 'mobile-otp/:id',
        loadChildren: () =>
          import('./mobile-otp/mobile-otp.routes').then(
            (m) => m.mobileOtpRoutes
          ),                
        
          // canActivate: [noAuthGuard],

      },
      {
        path: 'verify',
        loadComponent: () =>
          import('./verify/verify.component').then(
            (m) => m.VerifyComponent
          ),                
        
          // canActivate: [noAuthGuard],

      },
      {
        path: 'verify-success',
        loadComponent: () =>
          import('./verify-success/verify-success.component').then(
            (m) => m.VerifySuccessComponent
          ),                
        
          // canActivate: [noAuthGuard],

      },
      {
        path: 'set-password',
        loadComponent: () =>
          import('./set-password/set-password.component').then(
            (m) => m.SetPasswordComponent
          ),
          // canActivate: [noAuthGuard],

      }
    ],
   
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
