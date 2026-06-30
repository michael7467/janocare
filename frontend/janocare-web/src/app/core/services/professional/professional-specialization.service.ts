import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ProfessionalSpecialization } from '../../models';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Observable, catchError, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({
  providedIn: 'root',
})
export class ProfessionalSpecializationService extends ResourceService<ProfessionalSpecialization> {
  constructor(private http: HttpClient) {
    super(
      http,
      ProfessionalSpecialization,
      `${environment.professionalUrl}/professional-specializations
    `,
    );
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalSpecialization[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professional-specializations?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });

    return this.http
      .get<{ data: ProfessionalSpecialization[]; meta: IResultMeta }>(url, { params: new HttpParams({ fromObject: params }), headers: this.httpHeadersNew, withCredentials: true })
      .pipe(catchError(this.formatErrors));
  }
}
