import { Injectable } from '@angular/core';

import { BehaviorSubject, Subject, tap } from 'rxjs';
import { IPageOption, Order, ProfessionalExperience, ProfessionalExperienceService, cleanObject } from '../../../../../core';
interface IDataPageOption extends IPageOption {
  experience?: string;
  startYear?: string;
  endYear?: string;
  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;

@Injectable({
  providedIn: 'root'
})
export class ExperienceService {
  private _data$ = new BehaviorSubject<ProfessionalExperience[]>([]);
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 5, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }
  constructor(private _baseService:ProfessionalExperienceService) { }

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

    get experience() {
      return this._option.experience;
    }
    set experience(experience: string) {
      experience ? (this._option.experience = experience) : this._set({ experience });
    }
    
    get startYear() {
      return this._option.startYear;
    }
    set startYear(startYear: string) {
      startYear ? (this._option.startYear = startYear) : this._set({ startYear });
    }
    get endYear() {
      return this._option.endYear;
    }
    set endYear(endYear: string) {
      endYear ? (this._option.endYear = endYear) : this._set({ endYear });
    }

    get createdAtRange() {
      return this._option.createdAtRange;
    }
    set createdAtRange(createdAtRange: string) {
      this._set({ createdAtRange });
    }
}
