import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LandingComponent } from './landing.component';

const routes: Routes = [
  {
    path: '',
    component: LandingComponent,
    children: [
           {
        path: '',
        loadComponent: () => import('./pages/home/home.component').then((m) => m.HomeComponent),
      },
      {
        path: 'doctors',
        loadComponent: () => import('./pages/doctors/doctors.component').then((m) => m.DoctorsComponent),
      },
      {
        path: 'doctors/:id',
        loadComponent: () => import('./pages/doctor-profile/doctor-profile.component').then((m) => m.DoctorProfileComponent),
      },
      {
        path: 'doctor-detail',
        loadComponent: () => import('./pages/doctor-detail-info/doctor-detail-info.component').then((m) => m.DoctorDetailInfoComponent),
      },
      // {
      //   path: 'articles',
      //   loadComponent: () => import('./pages/articles/articles.component').then((m) => m.ArticlesComponent),
      // },
      // {
      //   path: 'questions',
      //   loadComponent: () => import('./pages/questions/questions.component').then((m) => m.QuestionsComponent),
      // },
  
    ]
  },


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LandingRoutingModule { }
