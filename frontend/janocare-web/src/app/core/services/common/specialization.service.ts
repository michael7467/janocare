import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { ProfessionalAchievement, Specialization } from '../../models';
import { environment } from '../../../../environments/environment';
import { BehaviorSubject, Observable, Subject, catchError, tap, throwError } from 'rxjs';
import { IPageOption, IResultMeta, Order } from '../../models/common/page-options';
import { cleanObject } from '../../helpers';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class SpecializationService extends ResourceService<Specialization> {
  constructor(private http: HttpClient) {
    super(http, Specialization, `${environment.professionalUrl}${professionalEndPoint}/specializations`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: Specialization[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}${professionalEndPoint}/specializations?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });

    return this.http
      .get<{ data: Specialization[]; meta: IResultMeta }>(url, { params: new HttpParams({ fromObject: params }), headers: this.httpHeadersNew, withCredentials: true })
      .pipe(catchError(this.formatErrors));
  }
}
