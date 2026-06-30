import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ProfessionalService } from '../../models';
import { Observable, catchError, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({
  providedIn: 'root'
})
export class ProfessionalServiceService extends ResourceService<ProfessionalService> {
  constructor(private http: HttpClient) {
    super(http, ProfessionalService, `${environment.professionalUrl}/professional-services`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalService[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professional-services?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: ProfessionalService[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}


