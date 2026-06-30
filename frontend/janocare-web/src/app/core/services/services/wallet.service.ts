
import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject, tap } from 'rxjs';
import { ConsultationTransactions, ConsultationTransactionsService, IPageOption, Order, ProfessionalUser, WalletTransactions, WalletTransactionsService, cleanObject } from '../../../core';

interface IDataPageOption extends IPageOption {

  q?: string;
  transactionDate?: Date;
  previousBalance?: number;
  currentBalance?: number;
  totalAmount?:number;
  actionBy?:string;
  narration?: string;
  transactionType?: 'CREDIT' | 'DEBIT' ;
  id?:number;
  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;


@Injectable({
  providedIn: 'root'
})
export class WalletService {
  private _data$ = new BehaviorSubject<WalletTransactions[]>([]);
  private _dataSingle$ = new BehaviorSubject<WalletTransactions>(new WalletTransactions());
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _getBaseDataSingle$ = new Subject<void>();

  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: WalletTransactionsService) { }


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

  get q() {
    return this._option.q;
  }
  set q(q: string) {
    this._set({ q });
  }
  // FIELDS

  get previousBalance() {
    return this._option.previousBalance;
  }
  set previousBalance(previousBalance: number) {
    previousBalance ? (this._option.previousBalance = previousBalance) : this._set({ previousBalance });
  }

  get currentBalance() {
    return this._option.currentBalance;
  }
  set currentBalance(currentBalance: number) {
    currentBalance ? (this._option.currentBalance = currentBalance) : this._set({ currentBalance });
  }

  get totalAmount() {
    return this._option.totalAmount;
  }
  set totalAmount(totalAmount: number) {
    totalAmount ? (this._option.totalAmount = totalAmount) : this._set({ totalAmount });
  }

  get transactionDate() {
    return this._option.transactionDate;
  }
  set transactionDate(transactionDate: Date) {
    transactionDate ? (this._option.transactionDate = transactionDate) : this._set({ transactionDate });
  }
  get actionBy() {
    return this._option.actionBy;
  }
  set actionBy(actionBy: string) {
    actionBy ? (this._option.actionBy = actionBy) : this._set({ actionBy });
  }

  get narration() {
    return this._option.narration;
  }
  set narration(narration: string) {
    narration ? (this._option.narration = narration) : this._set({ narration });
  }

  get transactionType() {
    return this._option.transactionType;
  }
  set transactionType(transactionType: 'CREDIT' | 'DEBIT' ) {
    this._set({ transactionType });
  }

  get id() {
    return this._option.id;
  }
  set id(id: number) {
    id ? (this._option.id = id) : this._set({ id });
  }

}
