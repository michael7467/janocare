import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  ConsultationTransactions, IPageOption, IResultMeta, ISuccess } from '../../models';
import { ResourceService } from '../resource.service';
import { PatientPrescriptions } from '../../models/patient';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class PatientPrescriptionService extends ResourceService<PatientPrescriptions> {
  constructor(private http: HttpClient) {
    super(http, PatientPrescriptions, `${environment.apiUrl}${professionalEndPoint}/patients/patient-prescriptions`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientPrescByPatientId(patientId: number): Observable<PatientPrescriptions[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-prescriptions?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientPrescriptions[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
  
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientPrescriptions[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-prescriptions?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientPrescriptions[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}