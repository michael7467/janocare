import { Routes } from '@angular/router';
import { AuthRoleGuard } from '../../../../../core/guards';
import { BookingAppointmentsListComponent } from './booking-appointments-list/booking-appointments-list.component';
import { BookingAppointmentsEditComponent } from './booking-appointments-edit/booking-appointments-edit.component';


export const bookingAppointmentsRoutes: Routes = [
  {
    path: '',
     // canMatch: [AuthRoleGuard()],
    children: [
      {
        path: '',
        redirectTo: '',
        pathMatch: 'full',
      },
      {
        path: '',
        component: BookingAppointmentsListComponent,
      },
      {
        path: 'edit/:id',
        component: BookingAppointmentsEditComponent,
      },
    ],
  },
];