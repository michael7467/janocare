import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import {  IPageOption, IResultMeta, ProfessionType, User } from '../../models';
import { ResourceService } from '../resource.service';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProfessionService extends ResourceService<ProfessionType> {
  constructor(private http: HttpClient) {
    super(http, ProfessionType, `${environment.professionalUrl}/profession-types`);
  }

  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionType[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.apiUrl}/patients/patient-permissions?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });

    return this.http.get<{ data: ProfessionType[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}
