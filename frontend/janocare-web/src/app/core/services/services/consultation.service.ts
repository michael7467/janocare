import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject, tap } from 'rxjs';
import { ConsultationTransactions, ConsultationTransactionsService, IPageOption, Order, ProfessionalUser, cleanObject } from '../../../core';

interface IDataPageOption extends IPageOption {
  q?: string;
  transactionNote?: string;
  appointmentBookingId?: number;
  professionalId?: number;
  transactionDate?: Date;
  settlementStatus?:number;
  amount?:number;
  referenceNumber?:string;
  paymentType?: 'WALLET' | 'ONLINE' ;
  paymentStatus?: 'PENDING' | 'FAILED' | 'REJECTED' |'SUCCESS' | 'REFUNDED' ;

  id?:number;
  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;


@Injectable({
  providedIn: 'root'
})
export class ConsultationService {
  private _data$ = new BehaviorSubject<ConsultationTransactions[]>([]);
  private _dataSingle$ = new BehaviorSubject<ConsultationTransactions>(new ConsultationTransactions());
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _getBaseDataSingle$ = new Subject<void>();

  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: ConsultationTransactionsService) { }


  public getBaseData() {
 
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

  get transactionNote() {
    return this._option.transactionNote;
  }
  set transactionNote(transactionNote: string) {
    transactionNote ? (this._option.transactionNote = transactionNote) : this._set({ transactionNote });
  }

  get appointmentBookingId() {
    return this._option.appointmentBookingId;
  }
  set appointmentBookingId(appointmentBookingId: number) {
    appointmentBookingId ? (this._option.appointmentBookingId = appointmentBookingId) : this._set({ appointmentBookingId });
  }

  get professionalId() {
    return this._option.professionalId;
  }
  set professionalId(professionalId: number) {
    professionalId ? (this._option.professionalId = professionalId) : this._set({ professionalId });
  }

  get transactionDate() {
    return this._option.transactionDate;
  }
  set transactionDate(transactionDate: Date) {
    transactionDate ? (this._option.transactionDate = transactionDate) : this._set({ transactionDate });
  }
  get settlementStatus() {
    return this._option.settlementStatus;
  }
  set settlementStatus(settlementStatus: number) {
    settlementStatus ? (this._option.settlementStatus = settlementStatus) : this._set({ settlementStatus });
  }
  get amount() {
    return this._option.amount;
  }
  set amount(amount: number) {
    amount ? (this._option.amount = amount) : this._set({ amount });
  }
  get referenceNumber() {
    return this._option.referenceNumber;
  }
  set referenceNumber(referenceNumber: string) {
    referenceNumber ? (this._option.referenceNumber = referenceNumber) : this._set({ referenceNumber });
  }
  get paymentStatus() {
    return this._option.paymentStatus;
  }
  set paymentStatus(paymentStatus: 'PENDING' | 'FAILED' | 'REJECTED' |'SUCCESS' | 'REFUNDED' ) {
    this._set({ paymentStatus });
  }
  get paymentType() {
    return this._option.paymentType;
  }
  set paymentType(paymentType: 'WALLET' | 'ONLINE' ) {
    this._set({ paymentType });
  }
  get q() {
    return this._option.q;
  }
  set q(q: string) {
    this._set({ q });
  }
  get id() {
    return this._option.id;
  }
  set id(id: number) {
    id ? (this._option.id = id) : this._set({ id });
  }

}
