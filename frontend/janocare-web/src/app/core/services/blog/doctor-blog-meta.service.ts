

import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import {  DoctorBlogMeta } from '../../models';
import { environment } from '../../../../environments/environment';
import { Observable, catchError, map, throwError } from 'rxjs';
import { IPageOption, IResultMeta, Order } from '../../models/common/page-options';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root'})
export class DoctorBlogMetaService extends ResourceService<DoctorBlogMeta>{

  constructor(private http: HttpClient) {
    super(http, DoctorBlogMeta, `${environment.apiUrl}${professionalEndPoint}/engagements/post-metas`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: DoctorBlogMeta[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/post-metas?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
 
    return this.http.get<{ data: DoctorBlogMeta[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  public createBlogMeta(postId:number,metaTitle: string, file?: File): Observable<DoctorBlogMeta> {
    const formData = new FormData();

    if (file) {
      formData.append('meta_image', file,file.name);
      formData.append('imageTitle', metaTitle);
      formData.append('postId', postId.toString());
    }
    console.log(formData);
    return this.http.post<DoctorBlogMeta>(`${environment.apiUrl}${professionalEndPoint}/engagements/post-metas`, formData).pipe(map((result) => result));
  }
  public updateBlogMeta(id:number,postId:number,metaTitle: string, file?: File): Observable<DoctorBlogMeta> {
    const formData = new FormData();
    console.log(file);
    if (file && file.size > 0) {
      formData.append('meta_image', file,file.name);
      formData.append('imageTitle', metaTitle);
      formData.append('postId', postId.toString());
    }
    else{
      return null;
    }
    console.log(formData);
    return this.http.put<DoctorBlogMeta>(`${environment.apiUrl}${professionalEndPoint}/engagements/post-metas/${id}`, formData).pipe(map((result) => result));
  }
}