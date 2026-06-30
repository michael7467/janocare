import { CommonModule, DatePipe, NgFor } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewChild, inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router, RouterLink } from '@angular/router';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

import { routes } from '../../../shared/routes/routes';
import { SlotsService } from '../../../core/services/services/slots.service';
import { DoctorService } from '../../../core/services/services/doctor.service';
import {
  AppointemntBookingsService,
  AppointmentBookings,
  AuthService,
  BankService,
  BookingSlot,
  ConsultationTransactions,
  ConsultationTransactionsService,
  Dependant,
  DependantService,
  NotificationService,
  ProfessionalExperience,
} from '../../../core';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DateRangePickerModule, ImgFallbackDirective, RemovePortPipe, TpInputComponent } from '../../../shared';

import { CdkStepper, CdkStepperModule, StepperSelectionEvent } from '@angular/cdk/stepper';
import { NgSelectModule } from '@ng-select/ng-select';
import { BehaviorSubject, Observable, catchError, concat, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';
import { defaultRequestParams } from '../../../shared/tp-table/config';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { NgStepperModule } from 'angular-ng-stepper';
@Component({
  selector: 'app-booking-online-appointment',
  standalone: true,
  imports: [
    RouterLink,
    RemovePortPipe,
    CarouselModule,
    NgbModule,
    SlickCarouselModule,
    NgFor,
    DateRangePickerModule,
    CommonModule,
    NgStepperModule,
    CdkStepperModule,
    ImgFallbackDirective,
    TpInputComponent,
    BsDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    NgSelectModule,
  ],
  providers: [DatePipe],
  templateUrl: './booking-online-appointment.component.html',
  styleUrl: './booking-online-appointment.component.scss',
})
export class BookingOnlineAppointmentComponent implements OnInit, AfterViewInit {
  public routes = routes;
  bookingSlots: BookingSlot[];
  private apptBookingService = inject(AppointemntBookingsService);
  private dependantService = inject(DependantService);

  private router = inject(Router);
  dates: Date[] = [];
  previousDate: Date;
  currDate: Date;
  currentDateIndex = 0;
  currentDate: Date;
  activeLabel = '1';
  currentDate1 = new Date();
  private formBuilder = inject(FormBuilder);
  @ViewChild('stepper', { static: true }) stepper: CdkStepper;

  public frm: FormGroup;
  stepLabels = ['1', '2', '3', '4'];
  selectedLabel = this.stepLabels[0]; // Default to the first label
  isActive = true;
  selectedTimeSlot: string;
  isOnDateClick = false;
  selectedTime: Date;
  slotInterval: number = 0;
  appointemntBookingId: number;
  bookingFee: number;
  fullName: string = '';
  bookingType: string = '';
  slotId: string = '';
  dependantFullName: string = '';
  slotTime: string = '';
  genderr: string = '';

  content = 'self';
  public mode: 'edit' | 'add' = 'add';
  id;
  $genders = [
    { name: 'Male', value: 'Male' },
    { name: 'Female', value: 'Female' },
  ];
  times: { time: string; checked: boolean }[] = [];
  slots = {
    morning: [],
    afternoon: [],
    evening: [],
    night: [],
  };
  ngAfterViewInit() {}
  private toastService = inject(NotificationService);
  constructor(
    private datePipe: DatePipe,
    private consultationService: ConsultationTransactionsService,
    public auth: AuthService,
    public bookingSlotService: SlotsService,
    public route: ActivatedRoute,
    public service: DoctorService,
    public bankService: BankService,
  ) {}
ngOnInit(): void {
  for (let i = 0; i < 30; i++) {
    this.dates.push(new Date(this.currentDate1));
    this.currentDate1.setDate(this.currentDate1.getDate() + 1);
  }

  this.route.params.subscribe((params: Params) => {
    this.id = params['id'];

    this.loadDependants();
    this.service.status = null;
    this.service.id = this.id;
    this.service.getBaseDataSingle();

    this.route.queryParams.subscribe((params: Params) => {
      if (params['p']) {
        this.slotTime = params['p'];
        this.selectedTimeSlot = params['p'];  // ← active slot from doctor profile
      }
      if (params['slotInterval']) this.slotInterval = +params['slotInterval'];
      if (params['type']) this.bookingType = params['type'];
      if (params['slotId']) this.slotId = params['slotId'];
       
      if (params['date']) {
        this.currDate = params['date'];
        this.selectedTime = new Date(this.currDate + 'T00:00:00');
        this.currentDate = this.selectedTime;  // ← sync currentDate too
        this.isOnDateClick = true;
        this.bookingSlotService.professionalId = this.id;
        this.bookingSlotService.slotDate = this.selectedTime;
        this.bookingSlotService.getAvailableSlots();
      } else {
        this.currentDate = this.dates[0];
        this.bookingSlotService.slotDate = this.currentDate;
        this.bookingSlotService.professionalId = this.id;
        this.bookingSlotService.getAvailableSlots();
      }
       console.log('Query Params:', this.slotId, this.slotTime,this.currDate, this.bookingType, this.id);

      this.bookingSlotService.data$.subscribe((slots) => {
        this.bookingSlots = slots;
        this.getTimeSlots();
      });
    });

    this.service.dataSingle$.subscribe((p) => {
      if (p) {
        this.fullName = `${p?.user?.userProfile?.firstName ?? ''} ${p?.user?.userProfile?.middleName ?? ''} ${p?.user?.userProfile?.lastName ?? ''}`.trim();
        this.genderr = p?.user?.userProfile?.gender;
        this.slotInterval = +p?.professionType?.slotInterval || 45;
        this.setFormValues();
      }
    });
  });

  this.createForm();
}
  private createForm() {
    this.frm = this.formBuilder.group({
      firstName: [''],
      middleName: [''],
      lastName: [''],
      nationalId: [''],
      cityId: [[]],
      stateId: [[]],
      countryId: [[]],
      dependantId: [[]],
      gender: [null],
      genderd: [null],
      dependantAge: [''],
    });
  }
  private setFormValues() {
    if (this.fullName === null || this.fullName === undefined) {
      this.fullName = '';
    }
    if (this.genderr === null || this.genderr === undefined) {
      this.genderr = '';
    }

    this.frm = this.formBuilder.group({
      firstName: [this.fullName],
      middleName: [''],
      lastName: [''],
      nationalId: [''],
      cityId: [null],
      stateId: [null],
      countryId: [null],
      dependantId: [[]],
      gender: [this.genderr],
      genderd: [null],
      dependantAge: [''],
    });
  }
  isSameDay(date1: Date, date2: Date): boolean {
    return this.datePipe.transform(date1, 'yyyy-MM-dd') === this.datePipe.transform(date2, 'yyyy-MM-dd');
  }
  isToday(date: Date): boolean {
    const today = new Date();
    return date.getDate() === today.getDate() && date.getMonth() === today.getMonth() && date.getFullYear() === today.getFullYear();
  }

  isTomorrow(date: Date): boolean {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    return date.getDate() === tomorrow.getDate() && date.getMonth() === tomorrow.getMonth() && date.getFullYear() === tomorrow.getFullYear();
  }
  // Country
  public get isRequiredCountry() {
    return this.dependantId!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errorsCountry() {
    const errors = this.dependantId.errors;
    return {
      required: `${'Country'} is Required`,
    };
  }
  public get ErrorsCountry(): { type; message }[] {
    if (!this.dependantId.errors) {
      return [];
    }

    return Object.keys(this.dependantId.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errorsCountry[errorType],
      };
    });
  }
  goToNext(form: any) {
    if (this.save(form)) {
    }
  }
  private save(form: any): boolean {
    if (!form.valid) {
      return false;
    }
    const updatedData = { ...form.value };

    return true;
  }
  changeContent(event: any) {
    if (event.target.value === 'option1') {
      this.content = 'self';
    } else if (event.target.value === 'option2') {
      this.content = 'dependant';
    }
  }
  onSelectionChange(event: StepperSelectionEvent) {
    this.selectedLabel = this.stepLabels[event.selectedIndex];
    this.activeLabel = this.selectedLabel;
  }
  selectTimeSlot(timeSlot: string, date: Date) {
    this.slotTime = timeSlot;
    this.currentDate = date;
    this.selectedTime = date;
 this.selectedTimeSlot = timeSlot;
    let appt = new AppointmentBookings({
      // bookingSlotId: this.slotId,
      bookingType: this.bookingType,
      professionalId: this.id,
    });
  }
 
 createApptBooking() {
  console.log('Query Params:', this.slotId, this.slotTime, this.currDate, this.bookingType, this.id);

  const apptPayload = {
    bookingSlotId: this.slotId,
    type: this.bookingType || 'VIDEO',
    professionalId: this.id,
    bookingDate: this.currDate,
    bookingReason: this.frm.get('nationalId').value || 'Online Appointment Booking',
    timezone: Intl.DateTimeFormat().resolvedOptions().timeZone,
  };

  console.log('Appointment Booking Payload:', apptPayload);

  this.apptBookingService.createApptBooking(apptPayload).subscribe(
    (p: any) => {
      if (p?.success) {
        this.selectedLabel = '3';
        this.activeLabel = '3';
        this.appointemntBookingId = p?.data?.id;
      } else {
        this.toastService.show(p?.message, { classname: 'bg-danger text-white', delay: 15000 });
      }
    },
    (error) => {
      this.toastService.show(error?.message, { classname: 'bg-danger text-white', delay: 15000 });
    },
  );
}

  get firstName(): AbstractControl {
    return this.frm.get('firstName');
  }
  get dependantAge(): AbstractControl {
    return this.frm.get('dependantAge');
  }
  get middleName(): AbstractControl {
    return this.frm.get('middleName');
  }
  get lastName(): AbstractControl {
    return this.frm.get('lastName');
  }
  get nationalId(): AbstractControl {
    return this.frm.get('nationalId');
  }
  get cityId(): AbstractControl {
    return this.frm.get('cityId');
  }
  get stateId(): AbstractControl {
    return this.frm.get('stateId');
  }
  get countryId(): AbstractControl {
    return this.frm.get('countryId');
  }
  get dependantId(): AbstractControl {
    return this.frm.get('dependantId');
  }
  get gender(): AbstractControl {
    return this.frm.get('gender');
  }
  get genderd(): AbstractControl {
    return this.frm.get('genderd');
  }
  public get isRequired() {
    return this.gender!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errors() {
    const errors = this.gender.errors;
    return {
      required: `${'Gender'} is Required`,
    };
  }
  public get Errors(): { type; message }[] {
    if (!this.gender.errors) {
      return [];
    }

    return Object.keys(this.gender.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
  public get isRequiredd() {
    return this.genderd!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errorsd() {
    const errors = this.genderd.errors;
    return {
      required: `${'Gender'} is Required`,
    };
  }
  public get Errorsd(): { type; message }[] {
    if (!this.genderd.errors) {
      return [];
    }

    return Object.keys(this.genderd.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errorsd[errorType],
      };
    });
  }
  onSubmitPay() {
    let bankId = 0;
 
  }
  onDependantChange(event: any) {
    console.log(event);
    this.genderd.setValue(event.gender);
    this.dependantAge.setValue(this.calculateAge(event.dob));
  }
  calculateAge(birthdate) {
    const birthDate = new Date(birthdate);
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDifference = today.getMonth() - birthDate.getMonth();

    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }

    return age;
  }
  prevDate() {
    if (this.currentDateIndex > 0) {
      this.currentDateIndex--;
    }
    this.currentDate = this.dates[this.currentDateIndex];
    this.bookingSlotService.professionalId = this.id;
    this.bookingSlotService.slotDate = this.currentDate;
    this.bookingSlotService.getAvailableSlots();
    this.bookingSlotService?.data$.subscribe((p) => {
      this.bookingSlots = p;
      this.getTimeSlots();
    });
  }
  getExperience(exp: ProfessionalExperience[]) {
    let totalexperience = 0;
    exp?.forEach((element) => {
      const startYear = new Date(element.startYear);
      const endYear = new Date(element.endYear);
      totalexperience += endYear.getFullYear() - startYear.getFullYear();
    });
    return totalexperience;
  }
  onDateClick(date: Date) {
    this.currentDate = date;
    let dateObject = new Date(date);
    for (let i: number = 0; i < this.dates.length; i++) {
      if (this.dates[i].getFullYear() === dateObject.getFullYear() && this.dates[i].getMonth() === dateObject.getMonth() && this.dates[i].getDate() === dateObject.getDate()) {
        this.currentDate = this.dates[i];
      }
    }
    const clickedDateIndex = this.dates.indexOf(this.currentDate);

    this.currentDateIndex = clickedDateIndex;
    if (clickedDateIndex === this.currentDateIndex + 4) {
      this.currentDateIndex += 5;
    }
    this.bookingSlotService.professionalId = this.id;
    this.bookingSlotService.slotDate = this.currentDate;

    this.bookingSlotService.getAvailableSlots();
    this.bookingSlotService?.data$.subscribe((p) => {
      if (p?.length > 0) {
        this.bookingSlots = p;
        this.getTimeSlots();
      }
    });
  }

  nextStep() {
    this.stepper.next();
  }

  prevStep() {
    this.stepper.previous();
  }
  nextStep1() {
    this.selectedLabel = '2';
    this.activeLabel = '2';
  }

  prevStep1() {
    this.selectedLabel = '1';
    this.activeLabel = '1';
  }
  nextStep2() {
    this.createApptBooking();
    // this.selectedLabel = '3';
    // this.activeLabel = '3';
  }

  prevStep2() {
    this.selectedLabel = '1';
    this.activeLabel = '1';
  }

  nextStep3() {
    // this.onSubmitPay();
    this.selectedLabel = '4';
    this.activeLabel = '4';
  }

  prevStep3() {
    this.selectedLabel = '2';
    this.activeLabel = '2';
  }
  getDisplayedDates() {
    let start = Math.floor(this.currentDateIndex / 5) * 5;
    let end = start + 5;
    return this.dates.slice(start, end);
  }
  getDisplayedDatesSmall() {
    let start = Math.floor(this.currentDateIndex / 3) * 3;
    let end = start + 3;
    return this.dates.slice(start, end);
  }

 getTimeSlots() {
  this.slots = { morning: [], afternoon: [], evening: [], night: [] };

  if (!this.bookingSlots || this.bookingSlots.length === 0) return;

  this.bookingSlots.forEach(slot => {
    const time = slot.slotTime;
    const status = slot.bookingSlotStatus;

    const parts = time?.split(':');
    const hourStr = parts?.[0];
    const isPM = time?.includes('PM');
    let hour = parseInt(hourStr || '0');
    if (isPM && hour !== 12) hour += 12;
    if (!isPM && hour === 12) hour = 0;

    let period: string;
    if (hour >= 6 && hour < 12) period = 'morning';
    else if (hour >= 12 && hour < 18) period = 'afternoon';
    else if (hour >= 18 && hour < 24) period = 'evening';
    else period = 'night';

    if (status === 'AVAILABLE') {
      this.slots[period].push({ time, checked: false });
    }
  });
}
  getAvailableSlots(slotsAvailable: any) {
    let arr = slotsAvailable.filter((p) => !p.checked);

    return arr.length;
  }

  getNotAvailableSlots(slotsAvailable: any) {
    let arr = slotsAvailable.filter((p) => p.checked);
    return arr.length;
  }
  getDayName(date: Date): string {
    let days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    return days[date.getDay()];
  }
  nextDate() {
    if (this.currentDateIndex < this.dates.length - 1) {
      this.currentDateIndex++;
    }

    this.currentDate = this.dates[this.currentDateIndex];

    this.bookingSlotService.professionalId = this.id;
    this.bookingSlotService.slotDate = this.currentDate;
    this.bookingSlotService.getAvailableSlots();
    this.bookingSlotService?.data$.subscribe((p) => {
      this.bookingSlots = p;
      this.getTimeSlots();
    });
  }

  // search country
  //
  dependantLoading = false;
  $dependantInput = new BehaviorSubject<string>('');
  $dependants: Observable<any[]>;

  countryTrackedBy(item: Dependant) {
    return item.id;
  }
  private loadDependants() {
    this.$dependants = concat(
      of([]), // default items
      this.$dependantInput.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => (this.dependantLoading = true)),
        switchMap((term) => {
          let opts = defaultRequestParams;
          opts.filters = [
            { name: 'firstName', value: term },
            { name: 'lastName', value: term },
          ];
          return this.dependantService
            .getTableData(opts)
            .pipe(
              map((r) =>
                r.data.map((item) => ({
                  ...item,
                  fullName: `${item.firstName} ${item.lastName}`,
                })),
              ),
            )

            .pipe(
              catchError(() => of([])), // empty list on error
              tap(() => (this.dependantLoading = false)),
            );
        }),
      ),
    );
  }
}
