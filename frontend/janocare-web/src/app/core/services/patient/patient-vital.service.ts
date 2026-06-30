import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ISuccess, PatientVital } from '../../models';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class PatientVitalService extends ResourceService<PatientVital> {
  constructor(private http: HttpClient) {
    super(http, PatientVital, `${environment.apiUrl}${professionalEndPoint}/patients/patient-vitals`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientsVitalByPatientId(patientId: number): Observable<PatientVital[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-vitals?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientVital[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientVital[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-vitals?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientVital[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}