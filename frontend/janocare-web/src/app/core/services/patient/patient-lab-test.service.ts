import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ISuccess, PatientLabTest } from '../../models';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class PatientLabTestService extends ResourceService<PatientLabTest> {
  constructor(private http: HttpClient) {
    super(http, PatientLabTest, `${environment.apiUrl}${professionalEndPoint}/patients/patient-lab-tests`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientsMedConditionByPatientId(patientId: number): Observable<PatientLabTest[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-lab-tests?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientLabTest[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientLabTest[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-lab-tests?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });

    return this.http.get<{ data: PatientLabTest[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}