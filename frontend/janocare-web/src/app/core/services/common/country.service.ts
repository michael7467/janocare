import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { ResourceService } from '../resource.service';
import { environment } from '../../../../environments/environment';
import { Country } from '../../models';

@Injectable({ providedIn: 'root' })
export class CountryService extends ResourceService<Country> {
  constructor(private http: HttpClient) {
    super(http, Country, `${environment.apiUrl}/countries`);
  }
}
