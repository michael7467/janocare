import { Routes } from '@angular/router';
import { AuthRoleGuard } from '../../../../core/guards';


export const bookingRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'booking-slots',
        pathMatch: 'full',
      },
      {
        path: 'booking-slots',
        canMatch:[AuthRoleGuard()],
        loadChildren: () => import('./booking-slots/booking-slots.routes').then((m) => m.bookingSlotRoutes),
      },
      {
        path: 'appointments',
        canMatch:[AuthRoleGuard()],
        loadChildren: () => import('./booking-appointments/booking-appointments.routes').then((m) => m.bookingAppointmentsRoutes),

      }
    ],
  },
];