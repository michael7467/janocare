import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-booking-slots-edit',
  standalone: true,
  imports: [NgFor,NgIf],
  templateUrl: './booking-slots-edit.component.html',
  styleUrl: './booking-slots-edit.component.scss'
})
export class BookingSlotsEditComponent {
  public hours = [0];
  addHours() {
    this.hours.push(1);
  }
  dltHours(index: any) {
    this.hours.splice(index, 1);
  }
}
