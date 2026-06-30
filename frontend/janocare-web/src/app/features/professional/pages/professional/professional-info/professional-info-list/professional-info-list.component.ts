import { Component, Input, OnInit, TemplateRef, forwardRef, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
 import { MatTabsModule } from '@angular/material/tabs';
import { CommonModule, NgFor, NgIf } from '@angular/common';
 import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { FormsModule } from '@angular/forms';
import { NgbDropdownModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbModal, NgbModalRef, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ImgFallbackDirective, ListComponent, RemovePortPipe, TpTableModule } from '../../../../../../shared';
import { AuthService, NotificationService, Order, ProfessionalInfo, ProfessionalInfoService } from '../../../../../../core';
import { routes } from '../../../../../../shared/routes/routes';


import { DataSortColType, InfoService } from '../info.service';
// import { ReviewService } from '../../../reviews/review.service';
// import { TruncatePipe } from '../../../reviews/truncate.pipe';
// import { PatientListService } from '../../../patient-list/patient-list.service';
// import { BookingAppointmentService } from '../../../booking/booking-appointments/booking-appointment.service';

import { QualificationService } from '../../professional-qualification/qualification.service';
import { ProfessionalRegistrationListComponent } from '../../professional-registration/professional-registration-list/professional-registration-list.component';
import { ProfessionalAchievementListComponent } from '../../professional-achievement/professional-achievement-list/professional-achievement-list.component';
import { ProfessionalExperienceListComponent } from '../../professional-experience/professional-experience-list/professional-experience-list.component';
import { ProfessionalMembershipListComponent } from '../../professional-membership/professional-membership-list/professional-membership-list.component';
import { ProfessionalQualificationListComponent } from '../../professional-qualification/professional-qualification-list/professional-qualification-list.component';
import { ProfessionalServiceListComponent } from '../../professional-service/professional-service-list/professional-service-list.component';
import { ProfessionalInfoViewComponent } from '../professional-info-view/professional-info-view.component';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';
import { ProfessionalInfoEditComponent } from '../professional-info-edit/professional-info-edit.component';
import { RecommendationPipe } from '../../../common/pipes/recommendation.pipe';
import { ThousandSuffixPipePipe } from '../../../common/pipes/thousand-suffix-pipe.pipe';
import { ProfessionalSpecialityListComponent } from '../../professional-speciality/professional-speciality-list/professional-speciality-list.component';
import { environment } from '../../../../../../../environments/environment.development';
// import { NgxBootstrapModalModule } from '../../../../../../shared/modules/ngx-bootstrap-modal.module';
@Component({
  selector: 'app-professional-info-list',
  standalone: true,
  imports: [
    RouterLink,
    MatTabsModule,
    NgFor,
    NgIf,
    TpTableModule,
    ProfessionalRegistrationListComponent,
    ProfessionalAchievementListComponent,
    ProfessionalExperienceListComponent,
    ProfessionalMembershipListComponent,
    ProfessionalQualificationListComponent,
    ProfessionalRegistrationListComponent,
    ProfessionalServiceListComponent,
    ProfessionalInfoViewComponent,
    CommonTableComponent,
    TpTableModule,
    RemovePortPipe,
       TabsModule,
    NgbNavModule,
    NgbModalModule,
    ImgFallbackDirective,
    //  ModalModule,
      // ModalModule.forRoot(),
      // NgxBootstrapModalModule,
      BsDatepickerModule,
    NgbDropdownModule,
    FormsModule,
    NgIf,
    CommonModule,
    ProfessionalInfoEditComponent,
    // TruncatePipe,
    RecommendationPipe,
    ThousandSuffixPipePipe,
    ProfessionalSpecialityListComponent,
  ],
  viewProviders: [
    {
      provide: ListComponent,
      useExisting: forwardRef(() => ProfessionalInfoListComponent),
    },
  ],
  // providers: [BsModalService],
  templateUrl: './professional-info-list.component.html',
  styleUrls: ['./professional-info-list.component.scss'],
})
export class ProfessionalInfoListComponent extends ListComponent implements OnInit {
  sortByType = '';
  @Input() pageSize: number;
  public authService = inject(AuthService);
  private serviceProfessionalINfo = inject(ProfessionalInfoService);
  private notificationService = inject(NotificationService);
    private modalService = inject(NgbModal);
     modalRef?: NgbModalRef;
  public routes = routes;
  public id;
  stars = Array(5).fill(0);

  showEditModal(template: TemplateRef<any>, id?: number) {
    this.id = id;
      this.modalRef = this.modalService.open(template, {
    size: 'lg',
    centered: true,
    backdrop: 'static'
  });
  }

  ngOnInit(): void {
    this.service.take = 20;
    this.service.getBaseData();
    this.authService.getUserProfile().subscribe((data) => {});

    // this.reviewService.take = 50;
    // this.reviewService.getBaseData();

    // this.reviewService.take = 50;
    // this.reviewService.getBaseDataSummary();

    // this.patientService.take = 50;
    // this.patientService.getBaseData();

    // this.apptBookingService.take = 50;
    // this.apptBookingService.getBaseData();
  }

  constructor(
    public auth: AuthService,
    // public reviewService: ReviewService,
    // public patientService: PatientListService,
    public service: InfoService,
    public qualificationService: QualificationService,
    // public apptBookingService: BookingAppointmentService,
  ) {
    super();
  }

  onChange(checked, item: ProfessionalInfo) {
    item.isAvailable = checked;
    this.update(item);
  }
  private update(value) {
    const valuee = { ...value };
    this.serviceProfessionalINfo.update({ ...valuee }).subscribe(
      (p) => {
        this.notificationService.success('Availabilty Updated Successfully');
        this.service.take = 20;
        this.service.getBaseData();
      },
      (error) => {
        this.notificationService.error('Error Updating Availabilty');
      },
    );
  }
  getProfileImageUrl(profilePic?: string | null): string {
    if (!profilePic) {
      return 'assets/images/users/user-dummy-img.jpg';
    }
  
    if (profilePic.startsWith('http')) {
      return profilePic;
    }
  
    return `${environment.apiUrl}/${profilePic}`;
  }
  public recordSaved(success: boolean) {
    this.id = null;
    if (success) {
      this.service.getBaseData();
    }
      this.modalRef.close();
  }
  setSort(sortBy: DataSortColType, order: Order): void {
    this.service.setSorting(order, sortBy);
    this.onSortChange.next({ column: sortBy, direction: order });
  }
  closeModal(): void {
  this.modalRef?.close();
  this.modalRef = undefined;
}
  setSortNew(sortBYY: DataSortColType) {
    this.sortByType = sortBYY;
    if (this.service.order == 'ASC') {
      this.service.setSorting(Order.DESC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.DESC });
    } else {
      this.service.setSorting(Order.ASC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.ASC });
    }
  }
}
