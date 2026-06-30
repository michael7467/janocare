import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject, tap } from 'rxjs';
import { IPageOption, Order, ProfessionalUser, cleanObject } from '../../../core';
import { ProfessionalUserService } from '../professional/professional-user.service';

interface IDataPageOption extends IPageOption {
  practicingFrom?: string;
  consultationFee?: string;
  bookingFee?: string;
  instantConsultationFee?: string;
  upVotes?: string;
  downVotes?: string;
  viewCounts?: string;
  speciality?: string;
  location?: string;

  q?: string;
  experienceYears?: number;
  patientStoryCount?: number;
  highToLowConsultationFee?: boolean;
  lowToHighConsultationFee?: boolean;
  status?: 'PENDING' | 'APPROVED' | 'REJECTED' | 'SUSPENDED' | 'DELETED' | 'VERIFIED' | 'ACTIVE' | 'INACTIVE' | 'BLOCKED';
  gender?: 'Male' | 'Female';
  walletBalance?: string;
  id?: number;
  userId?: number;
  createdAtRange?: string;
  createdBy?: string;
  sort?: DataSortColType;
}

export type DataSortColType = Exclude<keyof IDataPageOption | 'updatedAt', keyof IPageOption | 'sort'>;

@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  private _data$ = new BehaviorSubject<ProfessionalUser[]>([]);
  private _dataSingle$ = new BehaviorSubject<ProfessionalUser>(new ProfessionalUser());
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _getBaseData$ = new Subject<void>();
  private _getBaseDataSingle$ = new Subject<void>();

  private _total$ = new BehaviorSubject<number>(0);
  private _option: IDataPageOption = { order: Order.DESC, take: 20, page: 1 };
  private _set(patch: Partial<IDataPageOption>) {
    Object.assign(this._option, patch);
    this.getBaseData();
  }

  constructor(private _baseService: ProfessionalUserService) {}

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

  get practicingFrom() {
    return this._option.practicingFrom;
  }
  set practicingFrom(practicingFrom: string) {
    practicingFrom ? (this._option.practicingFrom = practicingFrom) : this._set({ practicingFrom });
  }

  get consultationFee() {
    return this._option.consultationFee;
  }
  set consultationFee(consultationFee: string) {
    consultationFee ? (this._option.consultationFee = consultationFee) : this._set({ consultationFee });
  }
  get speciality() {
    return this._option.speciality;
  }
  set speciality(speciality: string) {
    speciality ? (this._option.speciality = speciality) : this._set({ speciality });
  }

  get location() {
    return this._option.location;
  }
  set location(location: string) {
    location ? (this._option.location = location) : this._set({ location });
  }

  get bookingFee() {
    return this._option.bookingFee;
  }
  set bookingFee(bookingFee: string) {
    bookingFee ? (this._option.bookingFee = bookingFee) : this._set({ bookingFee });
  }

  get instantConsultationFee() {
    return this._option.instantConsultationFee;
  }
  set instantConsultationFee(instantConsultationFee: string) {
    instantConsultationFee ? (this._option.instantConsultationFee = instantConsultationFee) : this._set({ instantConsultationFee });
  }
  get highToLowConsultationFee() {
    return this._option.highToLowConsultationFee;
  }
  set highToLowConsultationFee(highToLowConsultationFee: boolean) {
    this._set({ highToLowConsultationFee });
  }
  get lowToHighConsultationFee() {
    return this._option.lowToHighConsultationFee;
  }
  set lowToHighConsultationFee(lowToHighConsultationFee: boolean) {
    this._set({ lowToHighConsultationFee });
  }
  get upVotes() {
    return this._option.upVotes;
  }
  set upVotes(upVotes: string) {
    upVotes ? (this._option.upVotes = upVotes) : this._set({ upVotes });
  }
  get downVotes() {
    return this._option.downVotes;
  }
  set downVotes(downVotes: string) {
    downVotes ? (this._option.downVotes = downVotes) : this._set({ downVotes });
  }
  get viewCounts() {
    return this._option.viewCounts;
  }
  set viewCounts(viewCounts: string) {
    viewCounts ? (this._option.viewCounts = viewCounts) : this._set({ viewCounts });
  }
  get status() {
    return this._option.status;
  }
  set status(status: 'PENDING' | 'APPROVED' | 'REJECTED' | 'SUSPENDED' | 'DELETED' | 'VERIFIED' | 'ACTIVE' | 'INACTIVE' | 'BLOCKED') {
    this._set({ status });
  }

  get gender() {
    return this._option.gender;
  }
  set gender(gender: 'Male' | 'Female') {
    this._set({ gender });
  }
  get walletBalance() {
    return this._option.walletBalance;
  }
  set walletBalance(walletBalance: string) {
    walletBalance ? (this._option.walletBalance = walletBalance) : this._set({ walletBalance });
  }
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

  get patientStoryCount() {
    return this._option.patientStoryCount;
  }
  set patientStoryCount(patientStoryCount: number) {
    this._set({ patientStoryCount });
  }

  get q() {
    return this._option.q;
  }
  set q(q: string) {
    this._set({ q });
  }
  get experienceYears() {
    return this._option.experienceYears;
  }
  set experienceYears(experienceYears: number) {
    this._set({ experienceYears });
  }
}
