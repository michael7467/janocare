import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';
import { BlogComment, IPageOption, IResultMeta, PostVote } from '../../models';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { professionalEndPoint } from '../../../';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostVoteService extends ResourceService<PostVote>{

  constructor(private http: HttpClient) {
    super(http, PostVote, `${environment.apiUrl}${professionalEndPoint}/engagements/post-votes`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: PostVote[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/post-votes?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
 
    return this.http.get<{ data: PostVote[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getSingleByOption(option: IPageOption): Observable<{ data: PostVote; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}${professionalEndPoint}/engagements/post-votes?`;
    let id;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
        params['id'] = id;
        id=option[key];
      }
    });
   let path = `${environment.apiUrl}${professionalEndPoint}/engagements/post-votes/${id}`;
 
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: PostVote; meta: IResultMeta }>(path,{headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}
