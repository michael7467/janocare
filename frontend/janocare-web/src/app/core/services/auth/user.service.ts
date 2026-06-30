import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import {  IPageOption, IResultMeta, ProfessionType, User } from '../../models';
import { ResourceService } from '../resource.service';

import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService extends ResourceService<User> {
  constructor(private http: HttpClient) {
    super(http, User, `${environment.apiUrl}/web/auth/profile`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: User[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}/professionals/professional-users?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: User[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getSingleByOption(option: IPageOption): Observable<{ data: User; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}/professionals/professional-users?`;
    let id;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
        params['id'] = id;
        id=option[key];
      }
    });
   let path = `${environment.apiUrl}/web/auth/profile/${id}`;
 
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: User; meta: IResultMeta }>(path,{headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  
}