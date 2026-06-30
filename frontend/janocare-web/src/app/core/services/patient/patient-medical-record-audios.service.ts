import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  ConsultationTransactions, IPageOption, IResultMeta, ISuccess } from '../../models';
import { ResourceService } from '../resource.service';
import { PatientMedicalRecordAudios, PatientMedicalRecordImages } from '../../models/patient';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class PatientMedicalRecordAudiosService extends ResourceService<PatientMedicalRecordAudios> {
  constructor(private http: HttpClient) {
    super(http, PatientMedicalRecordAudios, `${environment.apiUrl}${professionalEndPoint}/patients/medical-record-audios`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getPatientMedicalRecordsByPatientId(patientId: number): Observable<PatientMedicalRecordAudios[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/medical-record-audios?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientMedicalRecordAudios[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientMedicalRecordAudios[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/medical-record-audios?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientMedicalRecordAudios[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}