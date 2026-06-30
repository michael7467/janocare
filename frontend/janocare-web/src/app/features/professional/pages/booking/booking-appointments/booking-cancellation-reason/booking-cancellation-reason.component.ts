
import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { AppointemntBookingsService, AppointmentBookings, AuthService, BookingCancellationReason, BookingCancellationReasonService, CancellationReason, CancellationReasonService, User, cleanObject } from '../../../../../../core';
import { CommonModule, NgIf } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TpInputComponent } from '../../../../../../shared';

@Component({
  selector: 'app-booking-cancellation-reason',
  standalone: true,
  imports: [NgIf,TpInputComponent,ReactiveFormsModule,NgIf, TpInputComponent, CommonModule],
  templateUrl: './booking-cancellation-reason.component.html',
  styleUrl: './booking-cancellation-reason.component.scss'
})

export class BookingCancellationReasonComponent implements OnInit{
  @Input() id: number;
  @Input() action: string;
  @Output() onSaveComplete = new EventEmitter<boolean>();
  private service= inject(AppointemntBookingsService);
  private bookingCanService= inject(BookingCancellationReasonService);
  private cancelReasonService= inject(CancellationReasonService);
  private formBuilder = inject(FormBuilder);
  private authService=inject(AuthService); 
  appointemntBookings: AppointmentBookings;
  cancellationReason:CancellationReason[];
  timezone:string;

  user:User;
  public frm: FormGroup;
  public isError = false;
  public isLoaded = true;
  public saveLabel: 'Save' | 'Update' = 'Save';
  public mode: 'edit' | 'add' = 'add';

  ngOnInit(): void {
    this.getData(this.id);
    this.getCancellationReasons();
    this.getUserData();
    this.createForm();
    this.timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
  }

 private getData(id: number): void {
    this.service.getById(id).subscribe((data) => {
      if (data) {
       this.appointemntBookings = data;
      }
    });
  }
  private getCancellationReasons(): void {
    this.cancelReasonService.getAll().subscribe((data) => {
      if (data) {
        this.cancellationReason = data;
      }
    });
  }
  private getUserData(): void {
    this.authService.userProfile$.subscribe((data) => {
      if (data) {
        this.user = data;
      }
    });
  }
  close(isSaved = false) {
    this.onSaveComplete.emit(isSaved);
  }
  private createForm() {
    this.frm = this.formBuilder.group({
      cancellationReasonId: ['', [Validators.required]],
      comment: ['', ],
    });
  }

  get cancellationReasonId(): AbstractControl {
    return this.frm.get('cancellationReasonId');
  }

  get comment(): AbstractControl {
    return this.frm.get('comment');
  }
  goToNext(form: any) {
    if (this.save(form)) {
    }
  }
  private addNew(value: Partial<BookingCancellationReason> & { toJson: () => BookingCancellationReason; }) {
    const valuee={...value,appointmentBookingId:this.id,userId:this.user.id,timezone:this.timezone};
    this.bookingCanService.create(valuee).subscribe((p) => {
      this.appointemntBookings.bookingStatus = 'CANCELLED';
      this.service.update({ ...this.appointemntBookings.toJson(), id: this.id }).subscribe((p) => {
        this.close(true);
      });
    });
  }
  private update(value) {
    const valuee={...value,appointmentBookingId:this.id,userId:this.user.id};
    this.bookingCanService.update({ ...valuee, id: this.id }).subscribe((p) => {
      this.close(true);
    });
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
