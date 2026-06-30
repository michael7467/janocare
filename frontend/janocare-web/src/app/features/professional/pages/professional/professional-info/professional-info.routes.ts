import { Routes } from '@angular/router';
import { ProfessionalInfoListComponent } from './professional-info-list/professional-info-list.component';
import { ProfessionalInfoEditComponent } from './professional-info-edit/professional-info-edit.component';
import { AuthRoleGuard } from '../../../../../core/guards';


export const professionalInfoRoutes: Routes = [
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
        component: ProfessionalInfoListComponent,
      },
      {
        path: 'edit/:id',
        component: ProfessionalInfoEditComponent,
      },
    ],
  },
];