import { Routes } from '@angular/router';
import { PersonalInfoComponent } from './personal-info.component';
import { PersonalInfoSettingComponent } from './personal-info-setting/personal-info-setting.component';
import { AuthRoleGuard } from '../../../../../core/guards';

export const personalInfoRoutes: Routes = [
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
        component: PersonalInfoComponent,
      },
      {
        path: 'setting',
        component: PersonalInfoSettingComponent,
      },
    ],
  },
];