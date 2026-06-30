import { Routes } from '@angular/router';
import { ProfessionalServiceListComponent } from './professional-service-list/professional-service-list.component';
import { ProfessionalServiceEditComponent } from './professional-service-edit/professional-service-edit.component';
import { AuthRoleGuard } from '../../../../../core/guards';

export const professionalServiceRoutes: Routes = [
  {
    path: '',
    canMatch: [AuthRoleGuard()],
    children: [
      {
        path: '',
        redirectTo: '',
        pathMatch: 'full',
      },
      {
        path: '',
        component: ProfessionalServiceListComponent,
      },
      {
        path: 'edit/:id',
        component: ProfessionalServiceEditComponent,
      },
    ],
  },
];