import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  AppointmentBookings, BookingCancellationReason, CancellationReason } from '../../models';
import { ResourceService } from '../resource.service';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class BookingCancellationReasonService extends ResourceService<BookingCancellationReason> {
  constructor(private http: HttpClient) {
    super(http, BookingCancellationReason, `${environment.bookingUrl}${professionalEndPoint}/bookings/booking-cancellation-reasons`);
  }
 
}