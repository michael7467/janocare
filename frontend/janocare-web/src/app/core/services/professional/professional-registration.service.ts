import { Injectable } from '@angular/core';
import { IPageOption, IResultMeta, ProfessionalRegistration } from '../../models';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { ResourceService } from '../resource.service';
import { Observable, catchError, map, throwError } from 'rxjs';
import { professionalEndPoint } from '../../../';

@Injectable({
  providedIn: 'root'
})
export class ProfessionalRegistrationService extends ResourceService<ProfessionalRegistration> {

  constructor(private http:HttpClient) { 
    super(http, ProfessionalRegistration, `${environment.professionalUrl}/professional-registrations`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalRegistration[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professional-registrations?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: ProfessionalRegistration[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  public createProfessionalRegistration(resource: Partial<ProfessionalRegistration>, file?: File): Observable<ProfessionalRegistration> {
    const formData = new FormData();
    formData.append('registrationName', resource.registrationName);
    formData.append('registrationDate', resource.registrationDate);

    if (file) {
      formData.append('certiphicate_photo', file);
    }
    return this.http.post<ProfessionalRegistration>(`${this.apiUrl}`, formData).pipe(map((result) => result));
  }
  public updateProfessinalRegistration(resource: Partial<ProfessionalRegistration> & { toJson: () => ProfessionalRegistration },file?:File): Observable<ProfessionalRegistration> {
    const formData = new FormData();
    formData.append('registrationName', resource.registrationName);
    formData.append('registrationDate', resource.registrationDate);

    if (file) {
      formData.append('certiphicate_photo', file);
    }
    return this.http.put<ProfessionalRegistration>(`${this.apiUrl}/${resource.id}`, formData).pipe(map((result) => result));
  }

}
