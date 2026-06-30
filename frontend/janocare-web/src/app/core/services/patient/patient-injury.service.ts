import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ISuccess,PatientInjury } from '../../models';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class PatientInjuryService extends ResourceService<PatientInjury> {
  constructor(private http: HttpClient) {
    super(http, PatientInjury, `${environment.apiUrl}${professionalEndPoint}/patients/patient-injuries`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientInjuriesByPatientId(patientId: number): Observable<PatientInjury[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-injuries?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientInjury[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientInjury[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-injuries?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientInjury[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}