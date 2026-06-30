import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthRoleGuard } from '../../core/guards';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'booking-online-appointment/:id', 
        canMatch: [AuthRoleGuard(['PATIENT'])], // ← add :id
        loadComponent: () => import('./booking-online-appointment/booking-online-appointment.component')
          .then((m) => m.BookingOnlineAppointmentComponent),
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
