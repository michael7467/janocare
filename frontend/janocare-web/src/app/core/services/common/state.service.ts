import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { ResourceService } from '../resource.service';
import { environment } from '../../../../environments/environment';
import { ISuccess, State } from '../../models';
import { map } from 'rxjs';
  // private httpHeaders: HttpHeaders = new HttpHeaders({
  //   'Content-Type': 'application/json',
  // });
@Injectable({ providedIn: 'root' })
export class StateService extends ResourceService<State> {
  

  constructor(private http: HttpClient) {
    super(http, State, `${environment.apiUrl}/states`);
  }
  getStateByCountryId(countryId: number) {
    let url = `${environment.apiUrl}/states?`;
    url += `countryId=${countryId}`;
    return this.http.get<ISuccess<State[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
}