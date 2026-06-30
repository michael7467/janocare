import { Injectable } from '@angular/core';
import { IPageOption, Order } from '../../../../../../core/models/common/page-options';
import { ProfessionalSubSpecialization, ProfessionalSubSpecializationService, cleanObject } from '../../../../../../core';
import { BehaviorSubject, Subject, tap } from 'rxjs';

interface IDataPageOption extends IPageOption {
  subSpecializationsId?: string;

  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;

@Injectable({
  providedIn: 'root',
})
export class SubSpecialityService {
  private _data$ = new BehaviorSubject<ProfessionalSubSpecialization[]>([]);
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: ProfessionalSubSpecializationService) {}

  public getBaseData() {
    this._baseService
      .getAllByOption(cleanObject(this._option))
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe((result) => {
        this._data$.next(result.data);
        this._total$.next(Number(result?.meta?.itemCount));
        this._loading$.next(false);
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

  get subSpecializationsId() {
    return this._option.subSpecializationsId;
  }
  set subSpecializationsId(subSpecializationsId: string) {
    subSpecializationsId ? (this._option.subSpecializationsId = subSpecializationsId) : this._set({ subSpecializationsId });
  }
}
