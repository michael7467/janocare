import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';
import { environment } from '../../../../environments/environment';
import { IPageOption, IResultMeta, ProfessionalQualification } from '../../models';
import { Observable, catchError, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({
  providedIn: 'root'
})
export class ProfessionalQualificationService extends ResourceService<ProfessionalQualification>{

  constructor(private http:HttpClient) {
    super(http, ProfessionalQualification, `${environment.professionalUrl}/professional-qualifications`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalQualification[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professional-qualifications?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: ProfessionalQualification[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}
