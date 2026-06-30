import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DatasourceParameters, DatasourceResult } from '../../tp-table/model';
import { ResourceModel } from '../models';

export class ISuccess<T> {
  success: boolean;
  message: string;
  statusCode: number;
  data: T;
}
interface ApiResponse<T> {
  data: T[];
  message: string;
  success: boolean;
  statusCode: number;
  // other properties like meta, etc.
}

export abstract class ResourceService<T extends ResourceModel<T>> {
  constructor(private httpClient: HttpClient, private tConstructor: { new (m: Partial<T>, ...args: unknown[]): T }, protected apiUrl: string) {}

  // header
  protected httpHeaders: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  // create a new resource
  public create(resource: Partial<T> & { toJson: () => T }): Observable<T> {
    return this.httpClient.post<T>(`${this.apiUrl}`, resource).pipe(map((result) => new this.tConstructor(result)));
  }

  // send a post request with images as form data and email and phone as JSON data
  public sendPostRequestWithImages(formData: FormData): Observable<T> {
    return this.httpClient.post<T>(`${this.apiUrl}`, formData).pipe(map((result) => new this.tConstructor(result)));
  }
  // get all - no pagination
  public getAll(): Observable<T[]> {
    return this.httpClient.get<T[]>(`${this.apiUrl}`).pipe(map((result: any) => result.data.map((i) => new this.tConstructor(i))));
  }
  public get(): Observable<T> {
    return this.httpClient.get<ISuccess<T>>(`${this.apiUrl}`).pipe(
      map((r) => r.data),
      map((result) => new this.tConstructor(result)),
    );
  }

  public getAllRoles(): Observable<T[]> {
    return this.httpClient.get<T[]>(`${this.apiUrl}`).pipe(map((result: any) => result.map((i) => new this.tConstructor(i))));
  }

  // get all - with pagination
  public getTableData(request: DatasourceParameters): Observable<DatasourceResult<T>> {
    let url = `${this.apiUrl}`;
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

    return this.httpClient.get<DatasourceResult<T>>(url, { observe: 'response', headers: this.httpHeaders }).pipe(
      map((res) => {
        const data = res?.body?.data?.map((i) => new this.tConstructor(i));
        const meta = { itemCount: res?.body?.meta?.itemCount };
        return { meta, data } as DatasourceResult<T>;
      }),
    );
  }

  // get by id
  public getById(id: number): Observable<T> {
    return this.httpClient.get<ISuccess<T>>(`${this.apiUrl}/${id}`).pipe(
      map((r) => r.data),
      map((result) => new this.tConstructor(result)),
    );
  }

  // update
  public update(resource: Partial<T> & { toJson: () => T }): Observable<T> {
    return this.httpClient.put<T>(`${this.apiUrl}/${resource.id}`, resource).pipe(map((result) => new this.tConstructor(result)));
  }
  // update
  public updateWithImages(formData: FormData, id: number): Observable<T> {
    return this.httpClient.put<T>(`${this.apiUrl}/${id}`, formData).pipe(map((result) => new this.tConstructor(result)));
  }
  public updateProfile(resource: Partial<T> & { toJson: () => T }): Observable<T> {
    return this.httpClient.put<T>(`${this.apiUrl}`, resource).pipe(map((result) => new this.tConstructor(result)));
  }
}
