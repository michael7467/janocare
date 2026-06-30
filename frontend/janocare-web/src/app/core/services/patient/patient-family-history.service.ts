import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ISuccess, PatientAllergy, PatientFamilyHistory } from '../../models';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class PatientFamilyHistoryService extends ResourceService<PatientFamilyHistory> {
  constructor(private http: HttpClient) {
    super(http, PatientFamilyHistory, `${environment.apiUrl}${professionalEndPoint}/patients/patient-family-histories`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientsFamillyHistoryByPatientId(patientId: number): Observable<PatientFamilyHistory[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-family-histories?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientFamilyHistory[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientFamilyHistory[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-family-histories?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientFamilyHistory[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}