import { Routes } from '@angular/router';
import { ProfessionalQualificationListComponent } from './professional-qualification-list/professional-qualification-list.component';
import { ProfessionalQualificationEditComponent } from './professional-qualification-edit/professional-qualification-edit.component';
import { AuthRoleGuard } from '../../../../../core/guards';


export const professionalQualificationRoutes: Routes = [
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
        component: ProfessionalQualificationListComponent,
      },
      {
        path: 'edit/:id',
        component: ProfessionalQualificationEditComponent,
      },
    ],
  },
];