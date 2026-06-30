import { Injectable } from '@angular/core';
import { IPageOption, IResultMeta, ProfessionalExperience } from '../../models';
import { ResourceService } from '../resource.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Observable, catchError, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({
  providedIn: 'root'
})
export class ProfessionalExperienceService extends ResourceService<ProfessionalExperience> {

  constructor(private http:HttpClient) {
    super(http, ProfessionalExperience, `${environment.professionalUrl}/professional-experiences`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalExperience[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professional-experiences?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: ProfessionalExperience[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
}
