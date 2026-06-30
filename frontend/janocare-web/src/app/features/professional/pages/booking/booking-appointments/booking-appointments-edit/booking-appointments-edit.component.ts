import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { AppointemntBookingsService, AppointmentBookings, cleanObject } from '../../../../../../core';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-booking-appointments-edit',
  standalone: true,
  imports: [NgIf],
  templateUrl: './booking-appointments-edit.component.html',
  styleUrl: './booking-appointments-edit.component.scss'
})
export class BookingAppointmentsEditComponent implements OnInit{
  @Input() id: number;
  @Input() action: string;
  @Output() onSaveComplete = new EventEmitter<boolean>();
  private service= inject(AppointemntBookingsService) 
  appointemntBookings: AppointmentBookings;
  isView: boolean = false;
  isCancel: boolean = false;
  ngOnInit(): void {
    this.getData(this.id);
    this.isView = !this.action;
  }

 private getData(id: number): void {
    this.service.getById(id).subscribe((data) => {
      if (data) {
       this.appointemntBookings = data;
      }
    });
  }
  close(isSaved = false) {
    this.onSaveComplete.emit(isSaved);
  }
  closeModal(){
    this.close(true);
  }
  private confirmAction() {
    if (this.action === 'accept') {
      this.appointemntBookings.bookingStatus = 'ACCEPTED';
    } else if (this.action === 'reject') {
      this.appointemntBookings.bookingStatus = 'REJECTED';
    } else if (this.action === 'cancel') {
      this.appointemntBookings.bookingStatus = 'CANCELLED';
    }
    else if(this.action === 'complete'){
      this.appointemntBookings.bookingStatus = 'COMPLETED';
    }

    this.service.update({ ...this.appointemntBookings.toJson(), id: this.id }).subscribe((p) => {
      this.close(true);
    });
  }



}
