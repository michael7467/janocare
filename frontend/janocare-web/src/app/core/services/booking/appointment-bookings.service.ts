import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  AppointmentBookings, IPageOption, IResultMeta, ISuccess } from '../../models';
import { ResourceService } from '../resource.service';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class AppointemntBookingsService extends ResourceService<AppointmentBookings> {
  constructor(private http: HttpClient) {
    super(http, AppointmentBookings, `${environment.bookingUrl}${professionalEndPoint}/bookings/appointment-bookings`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: AppointmentBookings[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.bookingUrl}/professionals/bookings/appointment-bookings?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: AppointmentBookings[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
createApptBooking(appt: any): Observable<ISuccess<any>> {
  const url = `${environment.bookingUrlPatient}/patient/bookings/appointment-bookings`;
  return this.http.post<ISuccess<any>>(url, appt, {
    headers: this.httpHeadersNew,
    withCredentials: true
  }).pipe(catchError(this.formatErrors));
}
  getBookingApptByPatientId(): Observable<AppointmentBookings[]> {
   
    let url = `${environment.bookingUrl}/professionals/bookings/appointment-bookings?`;
    // url += `patientId=${patientId}&professionalId=${professinalId}`;
    return this.http.get<ISuccess<AppointmentBookings[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
}