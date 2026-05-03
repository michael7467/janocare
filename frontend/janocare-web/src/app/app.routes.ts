import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./features/landing/landing.module')
        .then(m => m.LandingModule)
  },
  {
    path: 'professionals',
    loadChildren: () =>
      import('./features/professional/professional.module')
        .then(m => m.ProfessionalModule)
  },
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module')
        .then(m => m.AuthModule)
  },
  {
    path: 'patients',
    loadChildren: () =>
      import('./features/patient/patient.module')
        .then(m => m.PatientModule)
  }
];