import { Routes } from '@angular/router';
import { AuthRoleGuard } from '../../../../../core/guards';
import { BookingSlotsListComponent } from './booking-slots-list/booking-slots-list.component';
import { BookingSlotsViewComponent } from './booking-slots-view/booking-slots-view.component';
import { BookingSlotsEditComponent } from './booking-slots-edit/booking-slots-edit.component';


export const bookingSlotRoutes: Routes = [
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
        component: BookingSlotsListComponent,
      },
      {
        path: ':id',
        component: BookingSlotsViewComponent,
      },
      {
        path: 'edit/:id',
        component: BookingSlotsEditComponent,
      },
    ],
  },
];