import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';

import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

import { ProfessionalAchievement } from '../../models';
import { environment } from '../../../../environments/environment';
import { BehaviorSubject, Observable, Subject, catchError, tap, throwError } from 'rxjs';
import { IPageOption, IResultMeta, Order } from '../../models/common/page-options';
import { cleanObject } from '../../helpers';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root'})
export class ProfessionalAchievementService extends ResourceService<ProfessionalAchievement>{

  constructor(private http: HttpClient) {
    super(http, ProfessionalAchievement, `${environment.professionalUrl}/professional-achievements`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalAchievement[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professional-achievements?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
 
    return this.http.get<{ data: ProfessionalAchievement[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  
}
