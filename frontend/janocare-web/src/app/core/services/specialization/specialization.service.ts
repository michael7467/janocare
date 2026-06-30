import { HttpClient } from '@angular/common/http';
import { ResourceService } from '../../../shared';
import { environment } from '../../../../environments/environment';
import { Injectable } from '@angular/core';
import { SpecializationModel } from '../../models';
import { apiRoutes } from '../../../shared/routes/api-routes';

@Injectable({ providedIn: 'root' })
export class SpecializationService extends ResourceService<SpecializationModel> {
  constructor(private http: HttpClient) {
    super(http, SpecializationModel, `${environment.apiUrl}${apiRoutes.specializations}`);
  }
}
