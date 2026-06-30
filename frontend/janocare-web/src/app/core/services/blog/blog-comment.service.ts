import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';
import { BlogComment, IPageOption, IResultMeta } from '../../models';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { professionalEndPoint } from '../../../';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BlogCommentService extends ResourceService<BlogComment>{

  constructor(private http: HttpClient) {
    super(http, BlogComment, `${environment.apiUrl}${professionalEndPoint}/engagements/post-comments`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: BlogComment[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/post-comments?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
 
    return this.http.get<{ data: BlogComment[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getSingleByOption(option: IPageOption): Observable<{ data: BlogComment; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/post-comments?`;
    let id;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
        params['id'] = id;
        id=option[key];
      }
    });
   let path = `${environment.apiUrl}${professionalEndPoint}/engagements/post-comments/${id}`;
 
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: BlogComment; meta: IResultMeta }>(path,{headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}
