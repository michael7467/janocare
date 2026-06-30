import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { BookingSlot, BookingSlotAnalyzed, IPageOption, IResultMeta, ISuccess, ProfessionType, User } from '../../models';
import { DatasourceParameters, DatasourceResult, ResourceService } from '../../../shared';
import { Observable, catchError, map, throwError } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class BookingSlotService extends ResourceService<BookingSlot> {
  constructor(private http: HttpClient) {
    super(http, BookingSlot, `${environment.bookingUrl}/booking-slots`);
  }
  private httpHeadersNew: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  getAllByOption(option: IPageOption): Observable<{ data: BookingSlot[]; meta: IResultMeta }> {
    const params = {};
    let url = `${environment.bookingUrl}/booking-slots?`;
    Object.keys(option).forEach((key) => {
      if (option[key]) {
        params[key] = option[key];
      }
    });
    // return this.http.get(`${environment.apiUrl}${path}`, { params, headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));

    return this.http
      .get<{ data: BookingSlot[]; meta: IResultMeta }>(url, { params: new HttpParams({ fromObject: params }), headers: this.httpHeadersNew, withCredentials: true })
      .pipe(catchError(this.formatErrors));
  }

  getPublicSlots(option: any): Observable<{ data: BookingSlot[]; meta: any }> {
  let url = `${environment.bookingUrl}/public/booking-slots?`;

  if (option.professionalId) {
    url += `professionalId=${option.professionalId}&`;
  }

  if (option.slotDate) {
    const formatted = this.formatLocalDate(new Date(option.slotDate));
    url += `slotDate=${formatted}&`;
  }

  return this.http.get<any>(url).pipe(
    map((res) => ({
      data: res?.data ?? [],
      meta: { itemCount: res?.data?.length ?? 0 }
    })),
    catchError(this.formatErrors)
  );
}
  public getBookingSlotsAnalyzed(request: DatasourceParameters): Observable<DatasourceResult<BookingSlotAnalyzed>> {
    let url = `${environment.bookingUrl}/booking-slots/analyze`;
    const page = request.page > 0 ? request.page / request.take + 1 : request.page + 1;
    url += `?page=${page}&take=${request.take}&`;

    if (request.orders.length > 0) {
      url += `sort=${request.orders.map((o) => o.name).join(',')}&order=${request.orders.map((o) => o.dir.toUpperCase()).join(',')}&`;
    }

    request.filters.forEach((filter) => {
      if (filter.value) {
        url += `${filter.name}=${filter.value}&`;
      }
    });

    if (request.fullTextFilter) {
      url += `q=${request.fullTextFilter}&`;
    }

    return this.http.get<DatasourceResult<BookingSlotAnalyzed>>(url, { observe: 'response' }).pipe(
      map((res) => {
        const data = res?.body?.data?.map((i) => i);
        const meta = { itemCount: res?.body?.meta?.itemCount };
        return { meta, data } as DatasourceResult<BookingSlotAnalyzed>;
      }),
    );
  }
private formatLocalDate(date: Date): string {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
}
getBookingSlotsByDate(slotDate: Date): Observable<BookingSlot[]> {
  const formattedDate = this.formatLocalDate(slotDate);

  const url = `${environment.bookingUrl}/booking-slots?slotDate=${formattedDate}`;

  return this.http.get<ISuccess<BookingSlot[]>>(url, { observe: 'response' }).pipe(
    map((response) => {
      return response.body?.data ?? [];
    }),
  );
}
  // getBookingSlotsByDate(slotDate: Date): Observable<BookingSlot[]> {
  //   let url = `${environment.bookingUrl}/booking-slots?`;
  //   url += `slotDate=${slotDate}`;
  //   return this.http.get<ISuccess<BookingSlot[]>>(url, { observe: 'response' }).pipe(
  //     map((response) => {
  //       return response.body.data;
  //     }),
  //   );
  // }
  getBookingSlotsByMonth(): Observable<BookingSlot[]> {
    let url = `${environment.bookingUrl}/booking-slots/organized`;
    // url += `slotDate=${slotDate}`;
    return this.http.get<ISuccess<BookingSlot[]>>(url, { observe: 'response' }).pipe(
      map((response) => {
        return response.body.data;
      }),
    );
  }
  deleteBookingSlotById(id: number) {
    let url = `${environment.bookingUrl}/booking-slots/${id}`;

    return this.http.delete<ISuccess<BookingSlot>>(url, { observe: 'response' }).pipe(
      map((response) => {
        return response.body.data;
      }),
    );
  }
  // update
  // public update(resource: Partial<T> & { toJson: () => T }): Observable<T> {
  //   return this.httpClient.put<T>(`${this.apiUrl}/${resource.id}`, resource).pipe(map((result) => new this.tConstructor(result)));
  // }

  public updateBookingSlot(resource: Partial<number[]>): Observable<number[]> {
    return this.http.put<number[]>(`${environment.bookingUrl}/booking-slots/delete`, { slotIds: resource }).pipe(map((result) => result));
  }
  public createSingleBookingSlot(resource: Partial<BookingSlot>): Observable<BookingSlot> {
    return this.http.post<BookingSlot>(`${environment.bookingUrl}/booking-slots/single`, resource).pipe(map((result) => result));
  }
}
