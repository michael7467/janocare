import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  IPageOption, IResultMeta, ISuccess } from '../../models';
import { ResourceService } from '../resource.service';
import { PatientMedicalRecordFiles } from '../../models/patient';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class PatientMedicalRecordFilesService extends ResourceService<PatientMedicalRecordFiles> {
  constructor(private http: HttpClient) {
    super(http, PatientMedicalRecordFiles, `${environment.apiUrl}${professionalEndPoint}/patients/medical-record-files`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientMedicalRecordsByPatientId(patientId: number): Observable<PatientMedicalRecordFiles[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/medical-record-files?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientMedicalRecordFiles[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientMedicalRecordFiles[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/medical-record-files?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientMedicalRecordFiles[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}