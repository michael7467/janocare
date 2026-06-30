import { Injectable } from '@angular/core';
import { AppointemntBookingsService, AppointmentBookings, IPageOption, Order, cleanObject } from '../../../core';
import { BehaviorSubject, Subject, tap } from 'rxjs';
interface IDataPageOption extends IPageOption {
  id?: string;
  bookingDate?: Date;
  patientId?: string;
  createdAtRange?: string;
  createdBy?: string;
  patientName?: string;
  type?: 'WRITE' | 'READ';
  status?: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;

@Injectable({
  providedIn: 'root',
})
export class BookingAppointmentService {
  private _data$ = new BehaviorSubject<AppointmentBookings[]>([]);
  private _dataUpSchedule$ = new BehaviorSubject<AppointmentBookings[]>([]);
  private _dataSingle$ = new BehaviorSubject<AppointmentBookings>(new AppointmentBookings());
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _loadingUpSchedule$ = new BehaviorSubject<boolean>(true);

  private _getBaseData$ = new Subject<void>();
  private _getBaseDataUpSchedule$ = new Subject<void>();

  private _getBaseDataSingle$ = new Subject<void>();

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
  public getBaseDataSingle() {
    this._loading$.next(true);
    this._baseService
      .getSingleByOption(cleanObject(this._option))
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe((result) => {
        this._dataSingle$.next(result?.data);
        this._total$.next(Number(result.meta?.itemCount));
        this._loading$.next(false);
      });
    this._getBaseDataSingle$.next();
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
  get dataSingle$() {
    return this._dataSingle$.asObservable();
  }
  get total$() {
    return this._total$.asObservable();
  }
  get loading$() {
    return this._loading$.asObservable();
  }
  get dataUpSchedule$() {
    return this._dataUpSchedule$.asObservable();
  }
  get loadingUpSchedule$() {
    return this._loadingUpSchedule$.asObservable();
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

  get patientName() {
    return this._option.patientName;
  }
  set patientName(patientName: string) {
    patientName ? (this._option.patientName = patientName) : this._set({ patientName });
  }
  get patientId() {
    return this._option.patientId;
  }
  set patientId(patientId: string) {
    patientId ? (this._option.patientId = patientId) : this._set({ patientId });
  }
  get id() {
    return this._option.id;
  }
  set id(id: string) {
    id ? (this._option.id = id) : this._set({ id });
  }
  get bookingDate() {
    return this._option.bookingDate;
  }
  set bookingDate(bookingDate: Date) {
    bookingDate ? (this._option.bookingDate = bookingDate) : this._set({ bookingDate });
  }
  get status() {
    return this._option.status;
  }
  set status(status: 'PENDING' | 'ACCEPTED' | 'REJECTED') {
    this._set({ status });
  }
  get type() {
    return this._option.type;
  }
  set type(type: 'WRITE' | 'READ') {
    this._set({ type });
  }
  get createdAtRange() {
    return this._option.createdAtRange;
  }
  set createdAtRange(createdAtRange: string) {
    this._set({ createdAtRange });
  }
}
