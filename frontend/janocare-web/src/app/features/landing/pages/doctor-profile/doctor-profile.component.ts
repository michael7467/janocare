import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Params, Router, RouterLink } from '@angular/router';
import { LightgalleryModule } from 'lightgallery/angular';
import { BeforeSlideDetail } from 'lightgallery/lg-events';
import lgZoom from 'lightgallery/plugins/zoom';
import { routes } from '../../../../shared/routes/routes';
import { NgbModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';

import { CommonModule } from '@angular/common';
import { ImgFallbackDirective, RemovePortPipe, TpTableModule } from '../../../../shared';
import { BookingSlot, BookingSlotService, ProfessionalExperience, ProfessionalReviewService } from '../../../../core';
import { InfoService } from '../../../../core/services/services/info.service';
import { QualificationService } from '../../../../core/services/services/qualification.service';
import { ProfAchievementService } from '../../../../core/services/services/professional-achievement.service';
import { MembershipService } from '../../../../core/services/services/membership.service';
import { SlotsService } from '../../../../core/services/services/slots.service';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { ReviewService } from '../../../../core/services/services/review.service';
import { DoctorService } from '../../../../core/services/services/doctor.service';
// import { PatientQuestionListComponent } from '../question/patient-question-list/patient-question-list.component';

@Component({
  selector: 'app-doctor-profile',
  standalone: true,
  imports: [
    RouterLink,
    CarouselModule,
    // PatientQuestionListComponent,
    NgbModule,
    TpTableModule,
    NgbNavModule,
    ImgFallbackDirective,
    RemovePortPipe,
    LightgalleryModule,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './doctor-profile.component.html',
  styleUrl: './doctor-profile.component.scss',
})
export class DoctorProfileComponent implements OnInit {
  id;
  isActive = false;
  selectedTimeSlot: string = '';
  public routes = routes;
  settings = {
    counter: false,
    plugins: [lgZoom],
  };
  bookingSlots: BookingSlot[];
  currentDate: Date;
  selectedOption: string = 'VIDEO';
  currentDate1 = new Date();
  currentDateIndex = 0;
  slotInterval: number = 0;
  dates: Date[] = [];
  public isClassAdded: boolean[] = [false];
  toggleClass(index: number) {
    this.isClassAdded[index] = !this.isClassAdded[index];
    this.isActive = this.isClassAdded[index];
  }
  private router = inject(Router);
  slots = {
    morning: [],
    afternoon: [],
    evening: [],
    night: [],
  };
  private everySlot = inject(BookingSlotService);
  onBeforeSlide = (detail: BeforeSlideDetail): void => {
    const { index, prevIndex } = detail;
  };
  constructor(
    public route: ActivatedRoute,
    public service:DoctorService,
    public awardService: ProfAchievementService,
    public info: InfoService,
    public qualService: QualificationService,
    public membershipService: MembershipService,
    public bookingSlotService: SlotsService,
    public reviewService: ReviewService,
  ) {}

ngOnInit(): void {
  for (let i = 0; i < 30; i++) {
    this.dates.push(new Date(this.currentDate1));
    this.currentDate1.setDate(this.currentDate1.getDate() + 1);
  }

  this.currentDate = this.dates[0];

  // ← Get id FIRST, then do everything else
  this.route.params.subscribe((params: Params) => {
    this.id = params['id'];
   
    // Now id is available
    this.bookingSlotService.professionalId = this.id;
    this.bookingSlotService.slotDate = this.currentDate;
    this.bookingSlotService.getAvailableSlots();

    this.service.status = null;
    this.service.id = this.id;
    this.service.getBaseDataSingle();

    this.service.dataSingle$.subscribe((res) => {
      if (res) {
        this.slotInterval = +res?.professionType?.slotInterval || 45;
        this.bookingSlotService.data$.subscribe((slots) => {
            console.log('=== SLOTS RECEIVED ===', slots);
         this.bookingSlots = slots;
  this.getTimeSlots();
  console.log('=== SLOTS AFTER getTimeSlots ===', this.slots);
        });
      }
    });

    this.info.professionalId = this.id;
    this.info.getBaseData();
    this.qualService.professionalId = this.id;
    this.qualService.take = 20;
    this.qualService.getBaseData();
    this.awardService.professionalId = this.id;
    this.awardService.take = 20;
    this.awardService.getBaseData();
    this.membershipService.professionalId = this.id;
    this.membershipService.take = 20;
    this.membershipService.getBaseData();
  });

  this.reviewService.take = 50;
  this.reviewService.getBaseData();
  this.reviewService.getBaseDataSummary();
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
  getAmPm(hour: string) {
    let hr = parseInt(hour);
    if (hr < 12) {
      return 'AM';
    } else {
      return 'PM';
    }
  }
  getDayName(date): number {
    return 0;
  }
  getDisplayedDates() {
    let start = Math.floor(this.currentDateIndex / 3) * 3;
    let end = start + 3;
    return this.dates.slice(start, end);
  }
  getAvailableSlots(slotsAvailable: any) {
    let arr = slotsAvailable.filter((p) => !p.checked);

    return arr.length;
  }

  getNotAvailableSlots(slotsAvailable: any) {
    let arr = slotsAvailable.filter((p) => p.checked);
    return arr.length;
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
  onDateClick(date: Date) {
    this.currentDate = date;
    const clickedDateIndex = this.dates.indexOf(date);
    this.currentDateIndex = clickedDateIndex;
    if (clickedDateIndex === this.currentDateIndex + 6) {
      this.currentDateIndex += 7;
    }
    this.bookingSlotService.professionalId = this.id;
    this.bookingSlotService.slotDate = this.currentDate;
    this.bookingSlotService.getAvailableSlots();
    this.bookingSlotService?.data$.subscribe((p) => {
      this.bookingSlots = p;
      this.getTimeSlots();
    });
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
  getExperience(exp: ProfessionalExperience[]) {
    let totalexperience = 0;
    exp?.forEach((element) => {
      const startYear = new Date(element.startYear);
      const endYear = new Date(element.endYear);
      totalexperience += endYear.getFullYear() - startYear.getFullYear();
    });
    return totalexperience;
  }

  getTimeSlots() {
  this.slots = { morning: [], afternoon: [], evening: [], night: [] };

  if (!this.bookingSlots || this.bookingSlots.length === 0) return;

  this.bookingSlots.forEach(slot => {
    const time = slot.slotTime; // "08:00 AM"
    const status = slot.bookingSlotStatus; // "AVAILABLE"

    // Parse hour from startTime "08:00:00"
    const hour = parseInt(slot.startTime?.toString().split(':')[0] ?? '0');

    let period: string;
    if (hour >= 6 && hour < 12) period = 'morning';
    else if (hour >= 12 && hour < 18) period = 'afternoon';
    else if (hour >= 18 && hour < 24) period = 'evening';
    else period = 'night';

    if (status === 'AVAILABLE') {
      this.slots[period].push({ 
        time, 
        checked: false, 
        id: slot.id 
      });
    }
  });
}
 selectTimeSlot(timeSlot: string, id: string) {
   console.log('id value:', id);  // ← what does this print?
  console.log('this.id value:', this.id);
  console.log('Selected Time Slot:', timeSlot, 'with ID:', id);
  this.selectedTimeSlot = timeSlot;
  
  this.router.navigate(['/', 'patients', 'booking-online-appointment', this.id], {
    queryParams: { 
      p: timeSlot, 
      type: this.selectedOption, 
      slotId: id, 
      date: this.currentDate.toISOString().split('T')[0], // ← "2026-06-25"
      slotInterval: this.slotInterval 
    },
  });
}
}
