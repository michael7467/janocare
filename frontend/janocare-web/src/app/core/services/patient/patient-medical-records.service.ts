import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  ConsultationTransactions, IPageOption, IResultMeta, ISuccess } from '../../models';
import { ResourceService } from '../resource.service';
import { PatientMedicalRecords } from '../../models/patient';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class PatientMedicalRecordsService extends ResourceService<PatientMedicalRecords> {
  constructor(private http: HttpClient) {
    super(http, PatientMedicalRecords, `${environment.apiUrl}${professionalEndPoint}/patients/patient-medical-records`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientMedicalRecordsByPatientId(patientId: number): Observable<PatientMedicalRecords[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-medical-records?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientMedicalRecords[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientMedicalRecords[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-medical-records?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });

    return this.http.get<{ data: PatientMedicalRecords[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}