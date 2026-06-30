import { Injectable } from '@angular/core';
import { IPageOption, Order } from './../../../../../core/models/common/page-options';
import { ProfessionalAchievementService, ProfessionalInfo, ProfessionalInfoService, cleanObject } from '../../../../../core';
import { BehaviorSubject, Subject, tap } from 'rxjs';

interface IDataPageOption extends IPageOption {
  officeNumber?: string;
  daysOfWeek?: string;
  startTime?: string;
  endTime?: string;

  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;


@Injectable({
  providedIn: 'root'
})
export class InfoService {
  private _data$ = new BehaviorSubject<ProfessionalInfo[]>([]);
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: ProfessionalInfoService) { }

 public getBaseData() {
  this._loading$.next(true);

  this._baseService
    .getAllByOption(cleanObject(this._option))
    .subscribe({
      next: (result) => {
        const data = result?.data ?? [];

        this._data$.next(data);
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

  // GETTERS & SETTERS
  get data$() {
    return this._data$.asObservable();
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

  
  // FIELDS

  get officeNumber() {
    return this._option.officeNumber;
  }
  set officeNumber(officeNumber: string) {
    officeNumber ? (this._option.officeNumber = officeNumber) : this._set({ officeNumber });
  }

  get daysOfWeek() {
    return this._option.daysOfWeek;
  }
  set daysOfWeek(daysOfWeek: string) {
    daysOfWeek ? (this._option.daysOfWeek = daysOfWeek) : this._set({ daysOfWeek });
  }

  get startTime() {
    return this._option.startTime;
  }
  set startTime(startTime: string) {
    startTime ? (this._option.startTime = startTime) : this._set({ startTime });
  }

  get endTime() {
    return this._option.endTime;
  }
  set endTime(endTime: string) {
    endTime ? (this._option.endTime = endTime) : this._set({ endTime });
  }
}
