import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ISuccess, PatientAllergy, PatientMedicalRecords } from '../../models';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';


@Injectable({ providedIn: 'root' })
export class PatientAllergyService extends ResourceService<PatientAllergy> {
  constructor(private http: HttpClient) {
    super(http, PatientAllergy, `${environment.apiUrl}${professionalEndPoint}/patients/patient-allergies`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }

  getAllByOption(option: IPageOption): Observable<{ data: PatientAllergy[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-allergies?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientAllergy[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getPatientsAllergyByPatientId(patientId: number): Observable<PatientAllergy[]> {
    let url = `${environment.apiUrl}${professionalEndPoint}/patients/patient-allergies?`;
    url += `patientId=${patientId}`;
    return this.http.get<ISuccess<PatientAllergy[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
}