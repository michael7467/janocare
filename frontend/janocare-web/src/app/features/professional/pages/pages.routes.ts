import { RouterModule, Routes } from '@angular/router';
import { PagesComponent } from './pages.component';
// import { ProfessionalAuthGuard } from '../professional-shared/auth/auth.guard';
import { NgModule } from '@angular/core';
import { AuthRoleGuard } from '../../../core/guards';

export const routes: Routes = [
   {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
          // canMatch: [AuthRoleGuard(['PROFESSIONAL'])],
        loadComponent: () => import('./dashboard/dashboard.component').then((m) => m.DashboardComponent),
      },
      {
        path: 'personal-info',

        loadChildren: () => import('./professional/personal-info/personal-info.routes').then((m)=>m.personalInfoRoutes)
      },
          {
        path: 'professional-info',

        loadChildren: () => import('./professional/professional-info/professional-info.routes').then((m)=>m.professionalInfoRoutes)
      },
      {
        path:'bookings',
        loadChildren:()=>import('./booking/booking.routes').then((m)=>m.bookingRoutes)
      },
     
     
      
  //     {
  //       path: 'patients',
            
  //       loadChildren: () => import('./patient-list/patient-list.routes').then((m) => m.patientRoutes),
  //     },
  //     {
  //       path:'blog',
  //       loadChildren:()=>import('./blog/blog.routes').then((m)=>m.blogRoutes)
  //     },      
  //     {
  //       path:'question',
  //       loadChildren:()=>import('./question/question.routes').then((m)=>m.questionRoutes)
  //     },
  //     {
  //       path:'chat',
  //       canMatch: [AuthRoleGuard()],
  //       loadComponent:()=>import('./chat/chat.component').then((m)=>m.ChatComponent)
  //     },
  //     {
  //       path:'video-call/:id',
  //       canMatch: [AuthRoleGuard()],
  //       loadComponent:()=>import('./video-call/video-call.component').then((m)=>m.VideoCallComponent)
  //     },
  //     {
  //       path:'audio-call',
  //       canMatch: [AuthRoleGuard()],
  //       loadComponent:()=>import('./audio-call/audio-call.component').then((m)=>m.AudioCallComponent)
  //     },
  //     {
  //       path: 'reviews',
  //       canMatch: [AuthRoleGuard()],
  //       loadComponent: () => import('./reviews/reviews.component').then((m) => m.ReviewsComponent),
  //     },
  //   ],

  // },
  // {
  //   path: 'transaction',
    
  //   loadChildren: () => import('./transaction/transaction.routes').then((m) => m.transactionRoutes),
  // },
  // {
  //   path: 'booking',
    
  //   loadChildren: () => import('./booking/booking.routes').then((m) => m.bookingRoutes),
  // },

  // {
  //   path: 'reviews',
    
  //   loadComponent: () => import('./reviews/reviews.component').then((m) => m.ReviewsComponent),
  // },


];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {}