import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  AppointmentBookings, CancellationReason } from '../../models';
import { ResourceService } from '../resource.service';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class CancellationReasonService extends ResourceService<CancellationReason> {
  constructor(private http: HttpClient) {
    super(http, CancellationReason, `${environment.bookingUrl}${professionalEndPoint}/bookings/cancellation-reasons`);
  }
 
}