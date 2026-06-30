import { Injectable } from '@angular/core';
import { AppointemntBookingsService, AppointmentBookings, IPageOption, Order, cleanObject } from '../../../../../core';
import { BehaviorSubject, Subject, tap } from 'rxjs';
interface IDataPageOption extends IPageOption {
  q?: string;
  bookingDate?: Date;
  patientId?: string;
  createdAtRange?: string;
  createdBy?: string;
  timezone?: string;
  bookingType?: 'ONLINE' | 'INSTANT' | 'IN_PERSON';
  bookingStatus?: 'PENDING' | 'ACCEPTED' | 'REJECTED' | 'CANCELLED' | 'COMPLETED';
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;

@Injectable({
  providedIn: 'root',
})
export class BookingAppointmentService {
  private _data$ = new BehaviorSubject<AppointmentBookings[]>([]);
  private _dataUpSchedule$ = new BehaviorSubject<AppointmentBookings[]>([]);

  private _loading$ = new BehaviorSubject<boolean>(true);
  private _loadingUpSchedule$ = new BehaviorSubject<boolean>(true);

  private _getBaseData$ = new Subject<void>();
  private _getBaseDataUpSchedule$ = new Subject<void>();

  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 5, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }
  constructor(private _baseService: AppointemntBookingsService) {}

  public getBaseData() {
    this._loading$.next(true);
    this._baseService
      .getAllByOption(cleanObject(this._option))
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe((result) => {
        this._data$.next(result.data);
        this._total$.next(Number(result.meta?.itemCount));
        this._loading$.next(false);
      });
    this._getBaseData$.next();
  }
  public getBaseDataUpSchedule() {
    this._loadingUpSchedule$.next(true);
    this._baseService
      .getAllByOption(cleanObject(this._option))
      .pipe(tap(() => this._loadingUpSchedule$.next(true)))
      .subscribe((result) => {
        this._dataUpSchedule$.next(result.data);
        this._total$.next(Number(result.meta?.itemCount));
        this._loadingUpSchedule$.next(false);
      });
    this._getBaseDataUpSchedule$.next();
  }
  // GETTERS & SETTERS
  get data$() {
    return this._data$.asObservable();
  }
  get dataUpSchedule$() {
    return this._dataUpSchedule$.asObservable();
  }
  get loadingUpSchedule$() {
    return this._loadingUpSchedule$.asObservable();
  }
  get total$() {
    return this._total$.asObservable();
  }
  get loading$() {
    return this._loading$.asObservable();
  }

  get page() {
    return this._option.page;
  }
  set page(page: number) {
    this._set({ page });
  }
  get pageSize() {
    return this._option.take;
  }
  set pageSize(take: number) {
    this._set({ take });
  }
  get take() {
    return this._option.take;
  }
  set take(take: number) {
    this._option.take = take;
  }
  get sort() {
    return this._option.sort;
  }
  set sort(sort: DataSortColType) {
    this._set({ sort });
  }
  setSorting(order: Order, sort: DataSortColType) {
    this._set({ order, sort });
  }
  get order() {
    return this._option.order;
  }
  set order(order: Order) {
    this._set({ order });
  }

  get q() {
    return this._option.q;
  }
  set q(q: string) {
    this._set({ q });
  }
  get timezone() {
    return this._option.timezone;
  }
  set timezone(timezone: string) {
    timezone ? (this._option.timezone = timezone) : this._set({ timezone });
  }
  get bookingDate() {
    return this._option.bookingDate;
  }
  set bookingDate(bookingDate: Date) {
    bookingDate ? (this._option.bookingDate = bookingDate) : this._set({ bookingDate });
  }
  get bookingStatus() {
    return this._option.bookingStatus;
  }
  set bookingStatus(bookingStatus: 'PENDING' | 'ACCEPTED' | 'REJECTED' | 'CANCELLED' | 'COMPLETED') {
    this._set({ bookingStatus });
  }
  get bookingType() {
    return this._option.bookingType;
  }
  set bookingType(bookingType: 'ONLINE' | 'INSTANT' | 'IN_PERSON') {
    this._set({ bookingType });
  }
  get createdAtRange() {
    return this._option.createdAtRange;
  }
  set createdAtRange(createdAtRange: string) {
    this._set({ createdAtRange });
  }
}
