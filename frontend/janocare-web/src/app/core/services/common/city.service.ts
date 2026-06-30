import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { ResourceService } from '../resource.service';
import { environment } from '../../../../environments/environment';
import { City, ISuccess } from '../../models';
import { map } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CityService extends ResourceService<City> {
  constructor(private http: HttpClient) {
    super(http, City, `${environment.apiUrl}/cities`);
  }
  getCityByStateId(stateId: number) {
    let url = `${environment.apiUrl}/cities?`;
    url += `stateId=${stateId}`;
    return this.http.get<ISuccess<City[]>>(url, { observe: 'response' }).pipe(
        map((response) => {
          return response.body.data;
        }));
  }
}
