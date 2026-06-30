import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateRangePickerComponent } from './date-range-picker/date-range-picker.component';
import { NgbDatepickerModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [DateRangePickerComponent],
  imports: [CommonModule, NgbDropdownModule, NgbDatepickerModule],
  exports: [DateRangePickerComponent],
})
export class DateRangePickerModule {}
