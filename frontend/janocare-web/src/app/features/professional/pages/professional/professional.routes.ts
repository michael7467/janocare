import { Routes } from '@angular/router';
import { AuthRoleGuard } from '../../../../core/guards';


export const professionalRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        // canMatch: [AuthRoleGuard()],
        loadComponent: () => import('../dashboard/dashboard.component').then((m) => m.DashboardComponent),
      },
  {
        path: 'personal-info',

        loadChildren: () => import('./personal-info/personal-info.routes').then((m)=>m.personalInfoRoutes)
      },
      //    {
      //   path: 'professional-info',

      //   loadChildren: () => import('./').then((m) => m.professionalInfoRoutes),
      // },
    ],
  },
];