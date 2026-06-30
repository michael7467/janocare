import { Component, Input, OnInit, TemplateRef, ViewChild, forwardRef, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
// import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import {ListComponent, RemovePortPipe, TpTableModule } from '../../../../../../shared';
import { routes } from '../../../../../../shared/routes/routes';

import { Order, ProfessionalRegistrationService } from '../../../../../../core';
import { CommonModule } from '@angular/common';
import { ProfessionalRegistrationEditComponent } from '../professional-registration-edit/professional-registration-edit.component';
import { DataSortColType, RegistrationService } from '../registration.service';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';

@Component({
  selector: 'app-professional-registration-list',
  standalone: true,
  imports: [RouterLink, CommonModule, RemovePortPipe, TpTableModule, 
    // ModalModule, BsDatepickerModule, 
    CommonTableComponent, ProfessionalRegistrationEditComponent],
  viewProviders: [
    {
      provide: ListComponent,
      useExisting: forwardRef(() => ProfessionalRegistrationListComponent),
    },
  ],
  // providers: [BsModalService],
  templateUrl: './professional-registration-list.component.html',
  styleUrls: ['./professional-registration-list.component.scss'],
})
export class ProfessionalRegistrationListComponent extends ListComponent implements OnInit {
  // private modalService = inject(BsModalService);
  sortByType = '';
  @Input() pageSize: number;
  // public modalRef: BsModalRef;
  public routes = routes;
  public id;

  showEditModal(template: TemplateRef<any>, id?: number) {
    this.id = id;
    // this.modalRef = this.modalService.show(template, { class: 'modal-dialog-centered' });
  }

  constructor(public service: RegistrationService) {
    super();
  }

  ngOnInit(): void {
    this.service.take = 20;
    this.service.getBaseData();
  }

  public columns: any[] = [
    {
      title: 'All Transactions',
      name: 'id',
      icon: 'ri-store-2-fill',
      sort: false,
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
        {
          title: 'Name',
          name: 'registrationName',
          sort: true,
        },

        {
          title: 'Date',
          name: 'registrationDate',
          sort: true,
          pipe: true,

          pipeType: 'date',
          pipeBy: 'YYYY-MM-dd',
        },
        {
          title: 'Attachment',
          name: 'certificatePhoto',
          pipe: true,
          pipeImage: true,
          sort: true,
        },
        {
          title: 'Actions',
          name: 'actions',

          actions: [{ name: 'Edit' }, { name: 'Delete' }],
          sort: true,
        },
      ],
    },
  ];

  public recordSaved(success: boolean) {
    this.id = null;
    if (success) {
      this.service.getBaseData();
    }
    // this.modalRef.hide();
  }

  getFileName(filePath: string): string {
    let parts = filePath.split('/');
    let fileName = parts.pop() || parts.pop(); // handle potential trailing slash
    return fileName;
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
}
