import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgbDate, NgbCalendar, NgbInputDatepickerConfig, NgbDropdown, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-date-range-picker',
  templateUrl: './date-range-picker.component.html',
  styleUrls: ['./date-range-picker.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DateRangePickerComponent implements OnInit {
  @Input() restrictDateRange: boolean = false;
  @Input() monthsToDisplay: number = 2;
  @Input() backgroundColor: string;
  @Output() selectedRange = new EventEmitter<string>();

  minDate: NgbDateStruct;
  maxDate: NgbDateStruct;

  ngOnInit(): void {
    this.setAllowedDateRange();
    console.log('date picker', this.backgroundColor);
  }

  hoveredDate: NgbDate | null = null;
  fromDate: NgbDate;
  toDate: NgbDate | null = null;
  @ViewChild('dp', { static: true }) datePicker: any;
  @ViewChild('ddown', { static: false, read: NgbDropdown }) ddown: NgbDropdown;

  constructor(public calendar: NgbCalendar, config: NgbInputDatepickerConfig) {
    config.autoClose = true;
  }

  private setAllowedDateRange(from: NgbDate = null) {
    if (from && this.restrictDateRange) {
      this.minDate = from;
      this.maxDate = this.calendar.getNext(from, 'd', 30);
    } else {
      this.minDate = null;
      this.maxDate = null;
    }
  }
  clearSelection() {
    this.fromDate = null;
    this.toDate = null;

    this.setAllowedDateRange();

    this.selectedRange.emit(``);

    this.ddown.close();
  }

  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
      this.setAllowedDateRange(date);
    } else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
      this.toDate = date;
      //emit
      const f = new Date(this.fromDate.year, this.fromDate.month - 1, this.fromDate.day);
      const t = new Date(this.toDate.year, this.toDate.month - 1, this.toDate.day);
      this.selectedRange.emit(`${f.toISOString()},${t.toISOString()}`);
      // this.selectedRange.emit({from: this.fromDate, to: this.toDate})
      this.ddown.close();
    } else {
      this.toDate = null;
      this.fromDate = date;
      this.setAllowedDateRange(date);
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  get inputValue() {
    let start, end;
    if (this.fromDate && this.fromDate?.year && this.fromDate?.month && this.fromDate?.day) {
      start = `${this.fromDate?.year}-${this.fromDate?.month}-${this.fromDate?.day}`;
    }
    if (this.toDate && this.toDate?.year && this.toDate?.month && this.toDate?.day) {
      end = `${this.toDate?.year}-${this.toDate?.month}-${this.toDate?.day}`;
    }
    return start && end ? `${start} to ${end}` : `Select date range...`;
  }

  isInside(date: NgbDate) {
    return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) || this.isInside(date) || this.isHovered(date);
  }
}
