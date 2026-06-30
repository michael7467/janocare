import { Routes } from '@angular/router';
import { ProfessionalExperienceListComponent } from './professional-experience-list/professional-experience-list.component';
import { ProfessionalExperienceEditComponent } from './professional-experience-edit/professional-experience-edit.component';
import { AuthRoleGuard } from '../../../../../core/guards';


export const professionalExperienceRoutes: Routes = [
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
        component: ProfessionalExperienceListComponent,
      },
      {
        path: 'edit/:id',
        component: ProfessionalExperienceEditComponent,
      },
    ],
  },
];