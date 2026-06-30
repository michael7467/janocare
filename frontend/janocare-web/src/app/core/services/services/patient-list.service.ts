import { Injectable } from '@angular/core';
import { IPageOption, Order, Patient, PatientService, cleanObject } from '../../../core';
import { BehaviorSubject, Subject, tap } from 'rxjs';
interface IDataPageOption extends IPageOption {

  dob?: Date;
  ecpFirstName?: string;
  ecpLastName?:string;
  ecpAddress?:string;
  ecpRelationship?:string;
  ecpPhoneNumber?:string;
  address?:string;
  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;

@Injectable({
  providedIn: 'root'
})
export class PatientListService {
  private _data$ = new BehaviorSubject<Patient[]>([]);
  private _dataSingle$ = new BehaviorSubject<Patient>(new Patient());
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _getBaseDataSingle$ = new Subject<void>();
  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 5, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }
  constructor(private _baseService:PatientService) { }

  public getBaseData() {
    this._loading$.next(true);
    this._baseService
      .getAllByOption(cleanObject(this._option))
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe((result) => {
        this._data$.next(result.data);
        this._total$.next(Number(result.meta.itemCount));
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


    get ecpFirstName() {
      return this._option.ecpFirstName;
    }
    set ecpFirstName(ecpFirstName: string) {
      ecpFirstName ? (this._option.ecpFirstName = ecpFirstName) : this._set({ ecpFirstName });
    }
    get ecpLastName() {
      return this._option.ecpLastName;
    }
    set ecpLastName(ecpLastName: string) {
      ecpLastName ? (this._option.ecpLastName = ecpLastName) : this._set({ ecpLastName });
    }
    get dob() {
      return this._option.dob;
    }
    set dob(dob: Date) {
      dob ? (this._option.dob = dob) : this._set({ dob });
    }
    get ecpAddress() {
      return this._option.ecpAddress;
    }
    set ecpAddress(ecpAddress: string) {
      ecpAddress ? (this._option.ecpAddress = ecpAddress) : this._set({ ecpAddress });
    }
    get address() {
      return this._option.address;
    }
    set address(address: string) {
      address ? (this._option.address = address) : this._set({ address });
    }
    get ecpRelationship() {
      return this._option.ecpRelationship;
    }
    set ecpRelationship(ecpRelationship: string) {
      ecpRelationship ? (this._option.ecpRelationship = ecpRelationship) : this._set({ ecpRelationship });
    }
    get ecpPhoneNumber() {
      return this._option.ecpPhoneNumber;
    }
    set ecpPhoneNumber(ecpPhoneNumber: string) {
      ecpPhoneNumber ? (this._option.ecpPhoneNumber = ecpPhoneNumber) : this._set({ ecpPhoneNumber });
    }
    get createdAtRange() {
      return this._option.createdAtRange;
    }
    set createdAtRange(createdAtRange: string) {
      this._set({ createdAtRange });
    }
}
