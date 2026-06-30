import { Component, Input, OnInit, TemplateRef, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { MatSortModule, Sort } from '@angular/material/sort';
import { CommonModule, DatePipe, NgClass, NgFor, NgIf } from '@angular/common';

import { MatTableDataSource } from '@angular/material/table';

import {
  BreadcrumbsComponent,
  DatasourceParameters,
  DateRangePickerModule,
  ImgFallbackDirective,
  ListComponent,
  RemovePortPipe,
  TableColumn,
  TableOptions,
  TablePaging,
  TpTableModule,
} from '../../../../../../shared';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { AppointemntBookingsService, AppointmentBookings, Order } from '../../../../../../core';
import { routes } from '../../../../../../shared/routes/routes';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';
import { BookingAppointmentService, DataSortColType } from '../booking-appointment.service';
import { NgbNavChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { BookingAppointmentsEditComponent } from '../booking-appointments-edit/booking-appointments-edit.component';
import { BookingCancellationReasonComponent } from '../booking-cancellation-reason/booking-cancellation-reason.component';
import { FormsModule } from '@angular/forms';
import { SlotsService } from '../../booking-slots/slots.service';
import { CountUpModule } from 'ngx-countup';

@Component({
  selector: 'app-booking-appointments-list',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    FormsModule,
    ImgFallbackDirective,
    BookingCancellationReasonComponent,
    BookingAppointmentsEditComponent,
    CommonTableComponent,
    DateRangePickerModule,
    CommonModule,
    BreadcrumbsComponent,
    TpTableModule,
    NgClass,
    MatSortModule,
    CountUpModule,
    RemovePortPipe,

    NgFor,
  ],
  // providers: [BsModalService],
  templateUrl: './booking-appointments-list.component.html',
  styleUrl: './booking-appointments-list.component.scss',
})
export class BookingAppointmentsListComponent extends ListComponent implements OnInit {
  public routes = routes;
  // private modalService = inject(BsModalService);
  public id;
  public action;
  // public modalRef: BsModalRef;
  public tableData: Array<AppointmentBookings> = [];
  private serviceOld = inject(AppointemntBookingsService);
  // pagination variables
  @Input() pageSize: number;
  breadCrumbItems!: Array<{}>;
  public serialNumberArray: Array<number> = [];
  public totalData = 0;
  showFilter = false;
  sortByType = '';
  public totalSlots: number = 0;
  public booked: number = 0;
  public pending: number = 0;

  public completed: number = 0;
  public expired: number = 0;
  public online: number = 0;
  public instant: number = 0;
  public inPerson: number = 0;

  term: any;
  dataSource!: MatTableDataSource<AppointmentBookings>;
  public searchDataValue = '';
  // pagination variables end
  constructor(private router: Router, public slotService: SlotsService, public service: BookingAppointmentService) {
    super();
  }
  ngOnInit(): void {
    this.service.bookingDate = null;
    this.service.take = this.pageSize || 20;
    this.service.getBaseData();

    this.slotService.getBaseData();
    this.service.data$.subscribe((res) => {
      this.totalSlots = res.length;
      this.online = res.filter((r) => r.bookingType === 'ONLINE')?.length;
      this.instant = res.filter((r) => r.bookingType === 'INSTANT')?.length;
      this.inPerson = res.filter((r) => r.bookingType === 'IN_PERSON')?.length;

      this.pending = res.filter((r) => r.bookingStatus === 'PENDING')?.length;

      this.booked = res.filter((r) => r.bookingStatus === 'ACCEPTED')?.length;
      this.completed = res.filter((r) => r.bookingStatus === 'COMPLETED')?.length;
      this.expired = res.filter((r) => r.bookingStatus === 'CANCELLED')?.length;
    });
  }
  num: number = 0;
  option = {
    startVal: this.num,
    useEasing: true,
    duration: 2,
    decimalPlaces: 2,
  };
  showEditModal(template: TemplateRef<any>, id?: number, action?: string) {
    this.id = id;
    this.action = action;
    console.log(this.action, this.id);
    // this.modalRef = this.modalService.show(template, { class: 'modal-dialog-centered' });
  }
  statuses = ['PENDING', 'CANCELLED', 'REJECTED', 'ACCEPTED', 'COMPLETED'];
  typeuses = ['INSTANT', 'ONLINE', 'IN_PERSON'];
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
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Payment Status',
          name: 'paymentStatus',
          innerData: true,
          bookingTransaction: true,

          h1: 'bookingTransactions',
          h2: 'paymentStatus',
          // sort: true,
          status: true,
        },
        {
          title: 'Amount',
          name: 'amount',
          sort: true,
          innerData: true,
          bookingTransaction: true,
          h1: 'bookingTransactions',
          h2: 'amount',
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'ONLINE',
      name: 'id',
      sort: false,
      icon: 'ri-wallet-fill',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'INSTANT',
      name: 'id',
      sort: false,
      icon: 'ri-bank-fill',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'IN PERSON',
      name: 'id',
      sort: false,
      icon: 'ri-bank-fill',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'PENDING',
      name: 'id',
      sort: false,
      icon: 'ri-information-fill',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'ACCEPTED',
      name: 'id',
      sort: false,
      icon: 'ri-checkbox-circle-line',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'REJECTED',
      name: 'id',
      sort: false,
      icon: 'ri-close-circle-line',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'CANCELLED',
      name: 'id',
      sort: false,
      icon: 'ri-close-circle-line',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
    {
      title: 'COMPLETED',
      name: 'id',
      sort: false,
      icon: 'ri-close-circle-line',
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Date',
          name: 'bookingDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },

        {
          title: 'Patient',
          name: 'bookingType',
          sort: true,
          patient: true,
        },
        {
          title: 'Booking Type',
          name: 'bookingType',
          sort: true,
        },
        {
          title: 'Booking Status',
          name: 'bookingStatus',
          sort: true,
          status: true,
        },
        {
          title: 'Slot Status',
          name: 'transactionDate',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'bookingSlotStatus',
        },
        {
          title: 'Start Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'End Time',
          name: 'transactionNote',
          sort: true,
          innerData: true,
          endTime: true,
          h1: 'bookingSlot',
          h2: 'slotTime',
        },
        {
          title: 'Actions',
          name: 'actions',
          IsAppt: true,
          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
  ];
  getEndTime(startTime: string, slotInterval: string) {
    if (startTime === null || startTime === '') {
      return '';
    }
    let time = new Date('01/01/2021 ' + startTime);
    time.setMinutes(time.getMinutes() + parseInt(slotInterval));
    return time.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  }

  setSort(sortBy: DataSortColType, order: Order): void {
    this.service.setSorting(order, sortBy);
    this.onSortChange.next({ column: sortBy, direction: order });
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

  public recordSaved(success: boolean) {
    this.id = null;
    if (success) {
      this.service.getBaseData();
    }
    // this.modalRef.hide();
  }
  onNavChange(changeEvent: NgbNavChangeEvent) {
    // this.orderes = this.allorderes.filter(country => country.status == status);
    console.log(changeEvent.nextId);

    if (changeEvent.nextId === 0) {
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
    if (changeEvent.nextId === 1) {
      this.service.bookingType = 'ONLINE';
      this.service.bookingStatus = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
    if (changeEvent.nextId === 2) {
      this.service.bookingType = 'INSTANT';
      this.service.bookingStatus = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }

    if (changeEvent.nextId === 3) {
      this.service.bookingType = 'IN_PERSON';
      this.service.bookingStatus = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
    if (changeEvent.nextId === 4) {
      this.service.bookingStatus = 'PENDING';
      this.service.bookingType = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
    if (changeEvent.nextId === 5) {
      this.service.bookingStatus = 'ACCEPTED';
      this.service.bookingType = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
    if (changeEvent.nextId === 6) {
      this.service.bookingStatus = 'REJECTED';
      this.service.bookingType = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
    if (changeEvent.nextId === 7) {
      this.service.bookingStatus = 'CANCELLED';
      this.service.bookingType = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
    if (changeEvent.nextId === 8) {
      this.service.bookingStatus = 'COMPLETED';
      this.service.bookingType = null;
      this.service.take = this.pageSize || 5;
      this.service.getBaseData();
    }
  }
  rangeSelected(range) {
    console.log(range);
    this.service.bookingDate = range;
    this.service.getBaseData();
  }
}
