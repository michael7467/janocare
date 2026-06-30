import { Routes } from '@angular/router';
import { ProfessionalMembershipListComponent } from './professional-membership-list/professional-membership-list.component';
import { ProfessionalMembershipEditComponent } from './professional-membership-edit/professional-membership-edit.component';
import { AuthRoleGuard } from '../../../../../core/guards';


export const professionalMembershipRoutes: Routes = [
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
        component: ProfessionalMembershipListComponent,
      },
      {
        path: 'edit/:id',
        component: ProfessionalMembershipEditComponent,
      },
    ],
  },
];