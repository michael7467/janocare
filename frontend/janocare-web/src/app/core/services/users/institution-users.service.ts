import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

import { Observable, catchError, map, throwError } from 'rxjs';
import { IPageOption, IResultMeta, InstitutionUser } from '../../models';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class InstitutionUserService extends ResourceService<InstitutionUser> {
  constructor(private http: HttpClient) {
    super(http, InstitutionUser, `${environment.apiUrl}${professionalEndPoint}/institution-users`);
  }
 
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: InstitutionUser[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/institution-users?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: InstitutionUser[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getSingleByOption(option: IPageOption): Observable<{ data: InstitutionUser; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/institution-users?`;
    let id;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
        params['id'] = id;
        id=option[key];
      }
    });
   let path = `${environment.apiUrl}${professionalEndPoint}/institution-users/${id}`;
 
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: InstitutionUser; meta: IResultMeta }>(path,{headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}
