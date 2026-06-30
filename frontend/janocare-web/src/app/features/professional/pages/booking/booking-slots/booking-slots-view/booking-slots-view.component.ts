import { Component, ElementRef, OnInit, TemplateRef, ViewChild, inject } from '@angular/core';
import { routes } from '../../../../../../shared/routes/routes';
import { ActivatedRoute, Params, Router, RouterLink } from '@angular/router';
import { CommonModule, NgClass, NgFor, NgIf } from '@angular/common';
import { TabsModule } from 'ngx-bootstrap/tabs';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { NgbModal, NgbModalRef, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { BookingSlotsEditComponent } from '../booking-slots-edit/booking-slots-edit.component';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService, BookingSlot, BookingSlotService, NotificationService, User, cleanObject } from '../../../../../../core';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { FlatpickrDirective, FlatpickrModule } from 'angularx-flatpickr';
import { BreadcrumbsComponent } from '../../../../../../shared';
import { BehaviorSubject, tap } from 'rxjs';
import { SlotsService } from '../slots.service';
import { CountUpModule } from 'ngx-countup';

@Component({
  selector: 'app-booking-slots-view',
  standalone: true,
  imports: [
    RouterLink,
    NgbNavModule,
    BreadcrumbsComponent,
    FlatpickrModule,
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    CountUpModule,
    NgIf,
    NgFor,
    NgClass,
    BsDatepickerModule,
    TabsModule,
    // ModalModule,
    BookingSlotsEditComponent,
    NgbModalModule
  ],
  // providers: [BsModalService],
  templateUrl: './booking-slots-view.component.html',
  styleUrl: './booking-slots-view.component.scss',
})
export class BookingSlotsViewComponent {
  public routes = routes;
  times: { time: string; checked: boolean }[] = [];
  slots = {
    morning: [],
    afternoon: [],
    evening: [],
    night: [],
  };
  slotsBooked = {
    morning: [],
    afternoon: [],
    evening: [],
    night: [],
  };
  slotsCompleted = {
    morning: [],
    afternoon: [],
    evening: [],
    night: [],
  };
  slotsExpired = {
    morning: [],
    afternoon: [],
    evening: [],
    night: [],
  };
  slotsCancelled = {
    morning: [],
    afternoon: [],
    evening: [],
    night: [],
  };
  breadCrumbItems!: Array<{}>;
  public isClassAdded: boolean[] = [false];
  // private modalService = inject(BsModalService);
  private modalService = inject(NgbModal);
modalRef?: NgbModalRef;

  private service = inject(BookingSlotService);
  private authService = inject(AuthService);
  public route = inject(ActivatedRoute);
  private router = inject(Router);
  private toastService = inject(NotificationService);
  bookingSlots: BookingSlot[];
  private formBuilder = inject(FormBuilder);
  // @ViewChild('datePicker', { read: FlatpickrDirective }) datePicker: FlatpickrDirective;
  @ViewChild('datePicker', { read: ElementRef }) datePicker: ElementRef;
  public frm: FormGroup;
  professional: User;
  professionId: number;
  public dateToShow: Date;
  dateString: string;
  date: Date;
  slotInterval: number;
  bookingSlotId: number;
  singleBookingSlot: BookingSlot;
  generateBookingSlot: boolean = false;
  minDate: Date;
  isUpdate: boolean = false;
  public totalSlots: number = 0;
//faf2eb0a-a2c9-46cf-86e7-23acd30c7f85
  public booked: number = 0;
  public completed: number = 0;
  public expired: number = 0;
  private _loading$ = new BehaviorSubject<boolean>(true);
  public saveLabel: 'Save' | 'Update' = 'Save';
  public mode: 'edit' | 'add' = 'add';
  constructor(public slotService: SlotsService) {}
  ngOnInit() {
    this.slotService.getBaseData();
    this.slotService.data$.subscribe((res) => {
      this.totalSlots = res.reduce((total, item) => total + item.Total, 0);
      this.booked = res.reduce((total, item) => total + item.ACCEPTED, 0);
      this.completed = res.reduce((total, item) => total + item.COMPLETED, 0);
      this.expired = res.reduce((total, item) => total + item.REJECTED, 0);
    });
    this.minDate = new Date();
    this.route.params.subscribe((params: Params) => {
      this.dateString = this.route.snapshot.paramMap.get('id');
    });
    if (this.dateString !== null && this.dateString !== '' && this.dateString !== 'add') {
      this.mode = 'edit';
      this.saveLabel = 'Update';
      this.isUpdate = true;
      // anotherconst date = new Date(dateString + 'T00:00:00');
      this.date = new Date(this.dateString);
      // console.log(this.date);
      // this.getAllSlots();
      this.generateSlot();
    } else {
    }
    this.createForm();
    this.authService.userProfile$.subscribe((p) => {
      this.professional = p;
      this.slotInterval = +p?.professional?.professionType.slotInterval || 45;
      this.professionId = this.professional.professionalId;
    });
  }
  get loading$() {
    return this._loading$.asObservable();
  }
  num: number = 0;
  option = {
    startVal: this.num,
    useEasing: true,
    duration: 2,
    decimalPlaces: 2,
  };
  public id;
  openDatePicker() {
    this.datePicker.nativeElement.click();
  }
  getTimeSlots() {
  if (!this.bookingSlots || this.bookingSlots.length === 0) {
    if (this.generateBookingSlot) {
      this.goToNext();
    }
    return;
  }

  this.bookingSlots.forEach(slot => {
    const time = slot.slotTime;
    const status = slot.bookingSlotStatus;

    // Determine period from slotTime string e.g. "08:00 AM"
    const hourStr = time?.split(':')[0];
    const isPM = time?.includes('PM');
    let hour = parseInt(hourStr || '0');
    if (isPM && hour !== 12) hour += 12;
    if (!isPM && hour === 12) hour = 0;

    let period: string;
    if (hour >= 6 && hour < 12) period = 'morning';
    else if (hour >= 12 && hour < 18) period = 'afternoon';
    else if (hour >= 18 && hour < 24) period = 'evening';
    else period = 'night';

    // Check if past date
    let isPastDate = false;
    const slotDate = new Date(slot.slotDate);
    slotDate.setHours(0, 0, 0, 0);
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    isPastDate = slotDate < currentDate;

    const slotObj = { time, checked: false };

    if (status === 'ACCEPTED') {
      this.slotsBooked[period].push(slotObj);
    } else if (status === 'COMPLETED') {
      this.slotsCompleted[period].push(slotObj);
    } else if (status === 'EXPIRED') {
      this.slotsExpired[period].push(slotObj);
    } else if (status === 'CANCELLED') {
      this.slotsCancelled[period].push(slotObj);
    } else if (status === 'AVAILABLE' || status === 'PENDING') {
      if (isPastDate) {
        this.slotsExpired[period].push(slotObj);
      } else {
        this.slots[period].push(slotObj);
      }
    }
  });

  if (this.generateBookingSlot) {
    this.goToNext();
  }
}
  generateSlot() {
    this.slots = { night: [], morning: [], afternoon: [], evening: [] };
    this.bookingSlots = [];
    // let slotDateValue = this.frm.valid ? true:false;
    if ((this.frm?.get('slotDate') && this.frm?.get('slotDate').value !== null) || (this.frm?.get('slotDate') && this.frm?.get('slotDate').value !== '')) {
      this.date = this.frm.value?.slotDate;
      this.dateToShow = this.date;
    }
    if (this.date) {
      this.service
        .getBookingSlotsByDate(this.date)
        .pipe(tap(() => this._loading$.next(true)))
        .subscribe(
          (res) => {
            if (res.length > 0) {
              this.bookingSlots = res;
              this.generateBookingSlot = false;
              this.getTimeSlots();
              this.setFormValues(res[0]);
            } else {
              this.bookingSlots = [];
              this.generateBookingSlot = true;
              this.saveLabel = 'Save';
              this.getTimeSlots();
            }
            this._loading$.next(false);
          },
          (error) => {
            this.toastService.showError(error.error.message);
            this._loading$.next(false);
          },
        );
    } else {
      this.service
        .getBookingSlotsByDate(this.frm?.get('slotDate').value)
        .pipe(tap(() => this._loading$.next(true)))
        .subscribe(
          (res) => {
            if (res.length > 0) {
              this.bookingSlots = res;
              this.getTimeSlots();
              this.setFormValues(res[0]);
            } else {
              this.getTimeSlots();
            }
            this._loading$.next(false);
          },
          (error) => {
            this.toastService.showError(error.error.message);
            this._loading$.next(false);
          },
        );
    }
  }

  getAvailableSlots(slotsAvailable: any) {
    let arr = slotsAvailable.filter((p) => !p.checked);
    return arr.length;
  }
  getNotAvailableSlots(slotsAvailable: any) {
    let arr = slotsAvailable.filter((p) => p.checked);
    return arr.length;
  }
  onCheckboxChangeAdd(time: string) {
    const valuee = { ...this.frm.value, slotInterval: this.slotInterval, startTime: time.toString() };
    this.service
      .createSingleBookingSlot(valuee)
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe(
        (p) => {
          this.saveLabel = 'Update';
          this.toastService.success(`Slot updated successfully!`, {
            positionClass: 'toast-top-right',
          });
          this._loading$.next(false);
          this.generateSlot();
        },
        (error) => {
          this.toastService.showError(error.error.message);
          this._loading$.next(false);
        },
      );
  }
  onCheckboxChangeDelete(time: any) {
    let slotIdLists = [];
    slotIdLists.push(time);

    let slotIds = this.bookingSlots.filter((value) => value.slotTime === time).map((p) => p.id);

    this.service
      .updateBookingSlot(slotIds)
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe(
        (p) => {
          this.toastService.success(`Slot updated successfully!`);
          this._loading$.next(false);
          this.generateSlot();
        },
        (error) => {
          this.toastService.showError(error.error.message);
          this._loading$.next(false);
        },
      );
  }

  private createForm() {
    this.frm = this.formBuilder.group({
      slotDate: ['', [Validators.required]],
    });
  }
  get slotDate(): AbstractControl {
    return this.frm.get('slotDate');
  }
  private setFormValues(data: BookingSlot) {
    this.frm = this.formBuilder.group({
      slotDate: [new Date(data?.slotDate)],
    });
  }

  private getData(id: number): void {
    this.service.getById(id).subscribe((data) => {
      if (data) {
        this.setFormValues(data);
      }
    });
  }
  goToNext() {
    // if(this.saveLabel==='Save'){
    const totalSlots = [
      ...this.slots.morning.filter((p) => !p.checked).map((p) => p.time),
      ...this.slots.afternoon.filter((p) => !p.checked).map((p) => p.time),
      ...this.slots.evening.filter((p) => !p.checked).map((p) => p.time),
      ...this.slots.night.filter((p) => !p.checked).map((p) => p.time),
    ];

    const valuee = { ...this.frm.value, slotInterval: this.slotInterval, startTimes: totalSlots };

    this.service
      .create(valuee)
      .pipe(tap(() => this._loading$.next(true)))
      .subscribe(
        (p) => {
          this.saveLabel = 'Update';
          this.toastService.success(`Slot added successfully!`);
          // this.router.navigate([routes.bookingSlots]);
          this.slotService.getBaseData();
          this._loading$.next(false);
        },
        (error) => {
          this.toastService.showError(error.error.message);
          this._loading$.next(false);
        },
      );
  }
  private addNew(value: Partial<BookingSlot> & { toJson: () => BookingSlot }) {}
  private update(value) {
    const valuee = { ...value, professionalId: this.professionId };
    this.service.update({ ...valuee, id: this.id }).subscribe((p) => {});
  }
  getSlots(slots: BookingSlot[]) {
    this.times = [];

    let startTimes = slots.map((slot) => slot.slotTime);

    let interval = slots[0]?.slotInterval;
    this.slotInterval = interval;
    for (let i = 0; i < 24; i++) {
      for (let j = 0; j < 60; j += interval) {
        if (j < 60) {
          let hour = i;
          let minute = j < 10 ? '0' + j : j;
          let ampm = hour >= 12 ? 'PM' : 'AM';
          hour = hour % 12;
          hour = hour ? hour : 12; // the hour '0' should be '12'
          let strTime = hour + ':' + minute + ' ' + ampm;
          let containsStartTime = startTimes.includes(strTime);
          if (!containsStartTime) {
            this.times.push({ time: strTime, checked: true });
          } else {
            this.times.push({ time: strTime, checked: false });
          }
        }
      }
    }
  }

  private save(form: any): boolean {
    if (!form.valid) {
      return false;
    }
    const updatedData = { ...form.value };
    if (this.mode === 'add') {
      this.addNew(cleanObject(updatedData));
    } else if (this.mode === 'edit') {
      this.update(cleanObject(updatedData));
    } else {
      return false;
    }
    return true;
  }
}
