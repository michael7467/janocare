import { Injectable } from '@angular/core';

import { BehaviorSubject, Subject, tap } from 'rxjs';
import { IPageOption, Order, ProfessionalReview, ProfessionalReviewService, cleanObject } from '../../../core';

interface IDataPageOption extends IPageOption {
  isReviewAnonymous?: boolean;
  waitTimeRating?: string;
  mannerRating?: string;
  overallRating?: number;
  q?: string;
  review?: string;
  isDoctorRecommended?: boolean;

  professionalId?: number;
  patientId?: number;

  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  private _data$ = new BehaviorSubject<ProfessionalReview[]>([]);
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();

  private _dataSummary$ = new BehaviorSubject<any>([]);
  private _loadingSummary$ = new BehaviorSubject<boolean>(true);
  private _getBaseDataSummary$ = new Subject<void>();

  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: ProfessionalReviewService) {}

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
  public getBaseDataSummary() {
    this._baseService
      .getSummary(cleanObject(this._option))
      .pipe(tap(() => this._loadingSummary$.next(true)))
      .subscribe((result) => {
        this._dataSummary$.next(result);
        this._total$.next(Number(result?.meta?.itemCount));
        this._loadingSummary$.next(false);
      });
    this._getBaseDataSummary$.next();
  }

  // GETTERS & SETTERS
  get data$() {
    return this._data$.asObservable();
  }
  get dataSummary$() {
    return this._dataSummary$.asObservable();
  }
  get total$() {
    return this._total$.asObservable();
  }
  get loading$() {
    return this._loading$.asObservable();
  }
  get loadingSummary$() {
    return this._loadingSummary$.asObservable();
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

  get isReviewAnonymous() {
    return this._option.isReviewAnonymous;
  }
  set isReviewAnonymous(isReviewAnonymous: boolean) {
    isReviewAnonymous ? (this._option.isReviewAnonymous = isReviewAnonymous) : this._set({ isReviewAnonymous });
  }

  get waitTimeRating() {
    return this._option.waitTimeRating;
  }
  set waitTimeRating(waitTimeRating: string) {
    waitTimeRating ? (this._option.waitTimeRating = waitTimeRating) : this._set({ waitTimeRating });
  }

  get mannerRating() {
    return this._option.mannerRating;
  }
  set mannerRating(mannerRating: string) {
    mannerRating ? (this._option.mannerRating = mannerRating) : this._set({ mannerRating });
  }

  get q() {
    return this._option.q;
  }
  set q(q: string) {
    this._set({ q });
  }

  get overallRating() {
    return this._option.overallRating;
  }
  set overallRating(overallRating: number) {
    overallRating ? (this._option.overallRating = overallRating) : this._set({ overallRating });
  }
  get review() {
    return this._option.review;
  }
  set review(review: string) {
    review ? (this._option.review = review) : this._set({ review });
  }
  get isDoctorRecommended() {
    return this._option.isDoctorRecommended;
  }
  set isDoctorRecommended(isDoctorRecommended: boolean) {
    isDoctorRecommended ? (this._option.isDoctorRecommended = isDoctorRecommended) : this._set({ isDoctorRecommended });
  }
  get professionalId() {
    return this._option.professionalId;
  }
  set professionalId(professionalId: number) {
    professionalId ? (this._option.professionalId = professionalId) : this._set({ professionalId });
  }
  get patientId() {
    return this._option.patientId;
  }
  set patientId(patientId: number) {
    patientId ? (this._option.patientId = patientId) : this._set({ patientId });
  }
}
