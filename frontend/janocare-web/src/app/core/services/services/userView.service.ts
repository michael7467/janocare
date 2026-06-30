import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject, tap } from 'rxjs';
import { IPageOption, Order, ProfessionalUser, User, UserService, cleanObject } from '../../../core';

interface IDataPageOption extends IPageOption {

  id?:number;
  userId?: number;
  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;


@Injectable({
  providedIn: 'root'
})
export class UserViewService {
  private _data$ = new BehaviorSubject<User[]>([]);
  private _dataSingle$ = new BehaviorSubject<User>(new User());
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _getBaseDataSingle$ = new Subject<void>();

  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: UserService) { }


  public getBaseData() {
 
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
 
    this._baseService
      .getSingleByOption(cleanObject(this._option))
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe((result) => {
        this._dataSingle$.next(result.data);
        this._total$.next(Number(result.meta?.itemCount));
        this._loading$.next(false);
      });
    this._getBaseDataSingle$.next();
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

 


  get id() {
    return this._option.id;
  }
  set id(id: number) {
    id ? (this._option.id = id) : this._set({ id });
  }
  get userId() {
    return this._option.userId;
  }
  set userId(userId: number) {
    userId ? (this._option.userId = userId) : this._set({ userId });
  }

 

  get q() {
    return this._option.q;
  }
  set q(q: string) {
   this._set({ q });
  }

}
