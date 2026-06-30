import { Injectable } from '@angular/core';
import { IPageOption, Order } from '../../../../../core/models/common/page-options';
import { BookingSlot, BookingSlotService,  cleanObject } from '../../../../../core';
import { BehaviorSubject, Subject, tap } from 'rxjs';

interface IDataPageOption extends IPageOption {
  slotDate?: Date;
  slotInterval?: number;
  slotTime?: string;

  bookingSlotStatus?: string;
  professionalId?: string;
  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;


@Injectable({
  providedIn: 'root'
})
export class SlotsService {
  private _data$ = new BehaviorSubject<BookingSlot[]>([]);
  private _dataDetail$ = new BehaviorSubject<BookingSlot[]>([]);

  private _loading$ = new BehaviorSubject<boolean>(true);
  private _loadingDetail$ = new BehaviorSubject<boolean>(true);

  private _getBaseData$ = new Subject<void>();
  private _getBaseDataDetail$ = new Subject<void>();

  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: BookingSlotService) { }


 public getBaseData() {
  this._loading$.next(true);

  this._baseService
    .getBookingSlotsAnalyzed({
      page: this._option.page - 1,
      take: this._option.take,
      orders: [],
      filters: [],
      fullTextFilter: ''
    })
    .subscribe({
      next: (result) => {
        const data = result?.data ?? [];
        this._data$.next(data as any);
        this._total$.next(result?.meta?.itemCount ?? data.length);
        this._loading$.next(false);
      },
      error: (err) => {
        console.error(err);
        this._data$.next([]);
        this._total$.next(0);
        this._loading$.next(false);
      }
    });

  this._getBaseData$.next();
}

 public getBaseDataDetail() {
  this._loading$.next(true);

  this._baseService
    .getAllByOption(cleanObject(this._option))
    .subscribe({
      next: (result) => {
        const data = result?.data ?? [];

        this._data$.next(data);
        this._total$.next(result?.meta?.itemCount ?? data.length);
        this._loadingDetail$.next(false);
      },
      error: (err) => {
        console.error(err);
        this._data$.next([]);
        this._total$.next(0);
        this._loadingDetail$.next(false);
      }
    });

  this._getBaseDataDetail$.next();
}
  // public getBaseDataDetail() {
 
  //   this._baseService
  //     .getAllByOptionDetail(cleanObject(this._option))
  //     .pipe(tap(() => this._loadingDetail$.next(true)))
  //     .subscribe((result) => {
  //       this._dataDetail$.next(result.data);
  //       this._total$.next(Number(result.meta?.itemCount));
  //       this._loadingDetail$.next(false);
  //     });
  //   this._getBaseDataDetail$.next();
  // }
  // GETTERS & SETTERS
  get data$() {
    return this._data$.asObservable();
  }
  get dataDetail$() {
    return this._dataDetail$.asObservable();
  }
  get total$() {
    return this._total$.asObservable();
  }
  get loading$() {
    return this._loading$.asObservable();
  }
  get loadingDetail$() {
    return this._loadingDetail$.asObservable();
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

  
  // FIELDS

  get slotDate() {
    return this._option.slotDate;
  }
  set slotDate(slotDate: Date) {
    slotDate ? (this._option.slotDate = slotDate) : this._set({ slotDate });
  }

  get slotInterval() {
    return this._option.slotInterval;
  }
  set slotInterval(slotInterval: number) {
    slotInterval ? (this._option.slotInterval = slotInterval) : this._set({ slotInterval });
  }

  get slotTime() {
    return this._option.slotTime;
  }
  set slotTime(slotTime: string) {
    slotTime ? (this._option.slotTime = slotTime) : this._set({ slotTime });
  }
  get bookingSlotStatus() {
    return this._option.bookingSlotStatus;
  }
  set bookingSlotStatus(bookingSlotStatus: string) {
    bookingSlotStatus ? (this._option.bookingSlotStatus = bookingSlotStatus) : this._set({ bookingSlotStatus });
  }
  get professionalId() {
    return this._option.professionalId;
  }
  set professionalId(professionalId: string) {
    professionalId ? (this._option.professionalId = professionalId) : this._set({ professionalId });
  }

}
