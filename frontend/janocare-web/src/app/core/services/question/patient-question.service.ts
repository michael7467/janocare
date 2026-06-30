import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { PatientQuestion } from '../../models';
import { environment } from '../../../../environments/environment';
import { Observable, catchError, map, throwError } from 'rxjs';
import { IPageOption, IResultMeta, Order } from '../../models/common/page-options';
import { professionalEndPoint } from '../../../';
import { ISuccess } from '../../models/_base/success-response.model';

@Injectable({ providedIn: 'root'})
export class PatientQuestionService extends ResourceService<PatientQuestion>{

  constructor(private http: HttpClient) {
    super(http, PatientQuestion, `${environment.apiUrl}${professionalEndPoint}/engagements/patient-questions`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: PatientQuestion[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/patient-questions?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
 
    return this.http.get<{ data: PatientQuestion[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getSingleByOption(option: IPageOption): Observable<{ data: PatientQuestion; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/patient-questions?`;
    let id;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
        params['id'] = id;
        id=option[key];
      }
    });
   let path = `${environment.apiUrl}${professionalEndPoint}/engagements/patient-questions/${id}`;
 
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PatientQuestion; meta: IResultMeta }>(path,{headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
    // create a new resource
    public createBlog(resource: Partial<PatientQuestion> & { toJson: () => PatientQuestion }): Observable<ISuccess<PatientQuestion>> {
      return this.http.post<ISuccess<PatientQuestion>>(`${environment.apiUrl}${professionalEndPoint}/engagements/patient-questions`, resource).pipe(map((result) => result));
    }
    public updateBlog(resource: Partial<PatientQuestion> & { toJson: () => PatientQuestion }): Observable<ISuccess<PatientQuestion>> {
      return this.http.put<ISuccess<PatientQuestion>>(`${environment.apiUrl}${professionalEndPoint}/engagements/patient-questions/${resource.id}`, resource).pipe(map((result) => result));
    }
  
}
