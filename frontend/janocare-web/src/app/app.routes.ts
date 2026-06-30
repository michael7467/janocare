import { Routes } from '@angular/router';
import { VerticalComponent } from './common/vertical/vertical.component';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./features/landing/landing.module')
        .then(m => m.LandingModule)
  },
{
  path: 'professionals',
  component: VerticalComponent,
  children: [
    {
      path: '',
      loadChildren: () =>
        import('./features/professional/pages/pages.routes')
          .then(m => m.routes)
    }
  ]
}
,
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module')
        .then(m => m.AuthModule)
  },
  {
    path: 'patients',
    component: VerticalComponent,
    loadChildren: () =>
      import('./features/patient/patient.module')
        .then(m => m.PatientModule)
  }
];