import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DatasourceParameters, ProfessionalUserStatus, ResourceService } from '../../../shared';
import { environment } from '../../../../environments/environment';
import { apiRoutes } from '../../../shared/routes/api-routes';
import { IPageOption, IResultMeta, ProfessionalUser } from '../../models';
import { Observable, catchError, map, throwError } from 'rxjs';
import { ProfessionalModel } from '../../models/professional/professional-user.model';

interface DatasourceResult<T = unknown> {
  success?: boolean;
  message?: string;
  statusCode?: number;
  meta: IResultMeta;
  data: T[];
}

export interface IPopularProfessionals {
  professional_id: number;
  professional_created_at: Date;
  professional_updated_at: Date;
  professional_deleted_at: Date;
  professional_practicing_from: Date | any;
  professional_consultation_fee: number;
  professional_booking_fee: number;
  professional_instant_consultation_fee: number;
  professional_up_votes: string;
  professional_down_votes: string;
  professional_view_counts: string;
  professional_bio: string;
  professional_status: ProfessionalUserStatus;
  professional_profession_type_id: number;
  firstName: string;
  lastName: string;
  profilePic: string;
  professionType: string;
  overallRatings: number;
  totalReviews: number;
}

@Injectable({ providedIn: 'root' })
export class ProfessionalUserService extends ResourceService<ProfessionalModel> {
  httpclient: HttpClient;
  constructor(private http: HttpClient) {
    super(http, ProfessionalModel, `${environment.professionalUrl}/professionals`);
    this.httpclient = http;
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
   private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
public getAllPopularProfessionals(): Observable<IPopularProfessionals[]> {
  return this.httpclient.get<any>(`${this.apiUrl}`).pipe(
    map((result: any) =>
      result.data.map((res: any) => ({
        professional_id: res.id,
        professional_status: res.status,
        professional_consultation_fee: res.consultationFee,
        professional_booking_fee: res.bookingFee,
        professional_instant_consultation_fee: res.instantConsultationFee,
        professional_up_votes: res.upVotes,
        professional_down_votes: res.downVotes,
        professional_view_counts: res.viewCounts,
        professional_bio: res.bio,
        professional_practicing_from: res.practicingFrom,
        professional_created_at: res.createdAt,
        professional_updated_at: res.updatedAt,
        professional_deleted_at: res.deletedAt,
        professional_profession_type_id: res.professionTypeId,

        // ← these were broken before, now correctly mapped
        firstName: res.user?.userProfile?.firstName,
        lastName: res.user?.userProfile?.lastName,
        profilePic: res.user?.userProfile?.profilePic,

        professionType: res.professionType?.name ?? null,
        overallRatings: res.overallRatings ?? 0,
        totalReviews: res.totalReviews ?? 0,
      }))
    )
  );
}
  getAllByOption(option: IPageOption): Observable<{ data: ProfessionalUser[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professionals?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: ProfessionalUser[]; meta: IResultMeta }>(url,{params:new HttpParams({ fromObject: params }),headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  getSingleByOption(option: IPageOption): Observable<{ data: ProfessionalUser; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.professionalUrl}/professionals?`;
    let id;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
        params['id'] = id;
        id=option[key];
      }
    });
   let path = `${environment.professionalUrl}/professionals/${id}`;
 
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http.get<{ data: ProfessionalUser; meta: IResultMeta }>(path,{headers: this.httpHeadersNew, withCredentials: true}).pipe(catchError(this.formatErrors));
  }
  public getDataWithFilters(request: DatasourceParameters): Observable<DatasourceResult<ProfessionalModel>> {
    let url = `${this.apiUrl}`;
    // const page = request?.page > 0 ? request?.page / request?.take + 1 : request?.page + 1;
    const page = request?.page > 0 ? request?.page : request?.page + 1;
    url += `?page=${page}&take=${request.take}&`;

    if (request?.orders?.length > 0) {
      url += `sort=${request?.orders?.map((o) => o.name).join(',')}&order=${request?.orders.map((o) => o.dir.toUpperCase()).join(',')}&`;
    }

    request?.filters.forEach((filter) => {
      if (filter.value) {
        url += `${filter.name}=${filter.value}&`;
      }
    });

    if (request?.fullTextFilter) {
      url += `q=${request?.fullTextFilter}&`;
    }

    return this.http.get<DatasourceResult<ProfessionalModel>>(url, { observe: 'response', headers: this.httpHeaders }).pipe(
      map((res) => {
        const data = res?.body?.data;
        const meta = {
          page: res?.body?.meta?.page,
          take: res?.body?.meta?.take,
          itemCount: res?.body?.meta?.itemCount,
          pageCount: res?.body?.meta?.pageCount,
          hasPreviousPage: res?.body?.meta?.hasPreviousPage,
          hasNextPage: res?.body?.meta?.hasNextPage,
        };

        return { meta, data } as DatasourceResult<ProfessionalModel>;
      }),
    );
  }
}
