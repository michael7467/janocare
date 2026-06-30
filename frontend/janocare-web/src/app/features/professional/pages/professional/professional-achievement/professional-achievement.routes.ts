import { Routes } from '@angular/router';
import { ProfessionalAchievementListComponent } from './professional-achievement-list/professional-achievement-list.component';
import { ProfessionalAchievementEditComponent } from './professional-achievement-edit/professional-achievement-edit.component';
import { AuthRoleGuard } from '../../../../../core/guards';

export const professionalAchievementRoutes: Routes = [
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
        component: ProfessionalAchievementListComponent,
      },
      {
        path: 'edit/:id',
        component: ProfessionalAchievementEditComponent,
      },
    ],
  },
];