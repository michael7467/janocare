import { Router, RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, NgClass, NgFor, NgIf } from '@angular/common';
import { Component, Input, OnInit, TemplateRef, forwardRef, inject } from '@angular/core';

// import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { NgbModal, NgbModalRef, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';

import { AuthService, Order } from '../../../../../../core';
import { routes } from '../../../../../../shared';
import { BreadcrumbsComponent, DateRangePickerModule, ImgFallbackDirective, ListComponent, TpTableModule } from '../../../../../../shared';
import { BookingSlotsEditComponent } from '../booking-slots-edit/booking-slots-edit.component';
import { DataSortColType, SlotsService } from '../slots.service';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';
import { CountUpModule } from 'ngx-countup';

@Component({
  selector: 'app-booking-slots-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TpTableModule,
    CommonTableComponent,
    CountUpModule,
    RouterLink,
    DateRangePickerModule,
    NgIf,
    NgFor,
    NgClass,
    NgbModalModule,
    BookingSlotsEditComponent,
    BreadcrumbsComponent,
    ImgFallbackDirective,
  ],
  viewProviders: [
    {
      provide: ListComponent,
      useExisting: forwardRef(() => BookingSlotsListComponent),
    },
  ],
  // providers: [BsModalService],
  templateUrl: './booking-slots-list.component.html',
  styleUrl: './booking-slots-list.component.scss',
})
export class BookingSlotsListComponent extends ListComponent implements OnInit {
  statuses = ['PENDING', 'ACCEPTED', 'REJECTED'];
  typeuses = ['WRITE', 'READ'];
  // public modalRef: BsModalRef;
  @Input() pageSize: number;
  breadCrumbItems!: Array<{}>;
  public totalSlots: number = 0;
  public booked: number = 0;
  public completed: number = 0;
  public expired: number = 0;
    private modalService = inject(NgbModal);
    modalRef?: NgbModalRef;
  // private modalService = inject(BsModalService);
  public id;
  public routes = routes;
  sortByType = '';
  constructor(public service: SlotsService, public authService: AuthService, private router: Router) {
    super();
  }
  ngOnInit(): void {
    this.service.take = this.pageSize || 20;
    this.service.slotDate = null;
    this.service.getBaseData();

    this.service.data$.subscribe((res) => {
      this.totalSlots = res.reduce((total, item) => total + item.Total, 0);
      this.booked = res.reduce((total, item) => total + item.ACCEPTED, 0);
      this.completed = res.reduce((total, item) => total + item.COMPLETED, 0);
      this.expired = res.reduce((total, item) => total + item.REJECTED, 0);
    });
  }
  num: number = 0;
  option = {
    startVal: this.num,
    useEasing: true,
    duration: 2,
    decimalPlaces: 2,
  };
  public columns: any[] = [
    {
      title: 'All Appointments',
      name: 'id',
      icon: 'ri-store-2-fill',
      sort: false,
      sortOrder: 'desc',
      template: 'IdTemplate',
      dateFilter: true,
      subItems: [
        {
          title: 'Date',
          name: 'slotDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Slot Interval',
          name: 'slotInterval',
          sort: true,
        },
        {
          title: 'Total',
          name: 'Total',
          sort: true,
        },
        {
          title: 'Available',
          name: 'PENDING',
          sort: true,
        },
        {
          title: 'Booked',
          name: 'ACCEPTED',
          sort: true,
        },
        {
          title: 'Completed',
          name: 'COMPLETED',
          sort: true,
        },
        {
          title: 'Cancelled',
          name: 'CANCELLED',
          sort: true,
        },
        {
          title: 'Rejected',
          name: 'REJECTED',
          sort: true,
        },
        {
          title: 'Actions',
          name: 'actions',

          IsBookingSlot: true,

          sort: true,
        },
      ],
    },
  ];
  routeToView(slotDate: any) {
    this.router.navigateByUrl(`/booking/booking-slots/${slotDate}`);
  }
  setSortNew(sortBYY: DataSortColType) {
    this.sortByType = sortBYY;
    console.log(sortBYY);
    console.log(this.service.order);

    if (this.service.order == 'ASC') {
      this.service.setSorting(Order.DESC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.DESC });
    } else {
      this.service.setSorting(Order.ASC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.ASC });
    }
  }
  setSort(sortBy: DataSortColType, order: Order): void {
    this.service.setSorting(order, sortBy);
    this.onSortChange.next({ column: sortBy, direction: order });
  }
  showEditModal(template: TemplateRef<any>, id?: number) {
    this.id = id;
    this.modalRef = this.modalService.open(template, {
    size: 'lg',
    centered: true,
    backdrop: 'static'
  });
  }
  public recordSaved(success: boolean) {
    this.id = null;
    if (success) {
      this.service.getBaseData();
    }
    this.modalRef?.close();
  }
  rangeSelected(range) {
    console.log(range);
    this.service.slotDate = range;
    this.service.getBaseData();
  }
}
