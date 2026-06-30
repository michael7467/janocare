import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';
import { IPageOption, IResultMeta, ProfessionalSubSpecialization } from '../../models';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { Observable, catchError, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({
  providedIn: 'root',
})
export class ProfessionalSubSpecializationService extends ResourceService<ProfessionalSubSpecialization> {
  constructor(private http: HttpClient) {
    super(
      http,
      ProfessionalSubSpecialization,
      `${environment.professionalUrl}/professional-sub-specializations
    `,
    );
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalSubSpecialization[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professional-sub-specializations?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });

    return this.http
      .get<{ data: ProfessionalSubSpecialization[]; meta: IResultMeta }>(url, {
        params: new HttpParams({ fromObject: params }),
        headers: this.httpHeadersNew,
        withCredentials: true,
      })
      .pipe(catchError(this.formatErrors));
  }
}
