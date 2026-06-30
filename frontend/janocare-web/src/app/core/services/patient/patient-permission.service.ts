import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { ResourceService } from '../resource.service';
import { ISuccess, PatientPermission } from '../../models';
import { Observable, catchError, map, throwError } from 'rxjs';
import { IPageOption, IResultMeta } from '../../models/common/page-options';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class PatientPermissionService extends ResourceService<PatientPermission> {
  constructor(private http: HttpClient) {
    super(http, PatientPermission, `${environment.apiUrl}${professionalEndPoint}/patients/patient-permissions`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientInjuriesByPatientId(patientId: number): Observable<PatientPermission[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-permissions?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientPermission[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientPermission[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-permissions?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientPermission[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}