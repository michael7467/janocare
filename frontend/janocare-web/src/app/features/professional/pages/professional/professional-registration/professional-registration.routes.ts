import { Routes } from '@angular/router';
import { ProfessionalRegistrationListComponent } from './professional-registration-list/professional-registration-list.component';
import { ProfessionalAchievementEditComponent } from '../professional-achievement/professional-achievement-edit/professional-achievement-edit.component';
import { AuthRoleGuard } from '../../../core/guards';

export const professionalRegistrationRoutes: Routes = [
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
        component: ProfessionalRegistrationListComponent,
      },
      {
        path: 'edit/:id',
        component: ProfessionalAchievementEditComponent,
      },
    ],
  },
];