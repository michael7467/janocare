import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { DoctorBlog } from '../../models';
import { environment } from '../../../../environments/environment';
import { Observable, catchError, map, throwError } from 'rxjs';
import { IPageOption, IResultMeta, Order } from '../../models/common/page-options';
import { professionalEndPoint } from '../../../';
import { ISuccess } from '../../models/_base/success-response.model';

@Injectable({ providedIn: 'root'})
export class DoctorBlogService extends ResourceService<DoctorBlog>{

  constructor(private http: HttpClient) {
    super(http, DoctorBlog, `${environment.apiUrl}${professionalEndPoint}/engagements/posts`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: DoctorBlog[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/posts?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
 
    return this.http.get<{ data: DoctorBlog[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getSingleByOption(option: IPageOption): Observable<{ data: DoctorBlog; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/posts?`;
    let id;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
        params['id'] = id;
        id=option[key];
      }
    });
   let path = `${environment.apiUrl}${professionalEndPoint}/engagements/posts/${id}`;
 
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: DoctorBlog; meta: IResultMeta }>(path,{headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
    // create a new resource
    public createBlog(resource: Partial<DoctorBlog> & { toJson: () => DoctorBlog }): Observable<ISuccess<DoctorBlog>> {
      return this.http.post<ISuccess<DoctorBlog>>(`${environment.apiUrl}${professionalEndPoint}/engagements/posts`, resource).pipe(map((result) => result));
    }
    public updateBlog(resource: Partial<DoctorBlog> & { toJson: () => DoctorBlog }): Observable<ISuccess<DoctorBlog>> {
      return this.http.put<ISuccess<DoctorBlog>>(`${environment.apiUrl}${professionalEndPoint}/engagements/posts/${resource.id}`, resource).pipe(map((result) => result));
    }
  
}
