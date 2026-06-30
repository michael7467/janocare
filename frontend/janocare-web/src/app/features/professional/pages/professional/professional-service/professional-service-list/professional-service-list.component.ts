import { Component, Input, OnInit, TemplateRef, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import {  ListComponent,TpTableModule } from '../../../../../../shared';
import { routes } from '../../../../../../shared/routes/routes';
import { ProfessionalServiceEditComponent } from '../professional-service-edit/professional-service-edit.component';
import { DataSortColType, ServiceService } from '../service.service';
import { Order } from '../../../../../../core';
import { CommonModule } from '@angular/common';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';

@Component({
  selector: 'app-professional-service-list',
  standalone: true,
  imports: [RouterLink, TpTableModule,CommonModule,
    //  ModalModule,
     CommonTableComponent,ProfessionalServiceEditComponent],
  // providers: [BsModalService],
  templateUrl: './professional-service-list.component.html',
  styleUrls: ['./professional-service-list.component.scss']
})
export class ProfessionalServiceListComponent extends ListComponent implements OnInit {
  // private modalService = inject(BsModalService);

  sortByType='';
  @Input() pageSize: number;
  // public modalRef: BsModalRef;
  public routes = routes;
  public id;
  showEditModal(template: TemplateRef<any>, id?: number) {
    this.id = id;
    // this.modalRef = this.modalService.show(template, { class: 'modal-dialog-centered' });
  }
  ngOnInit(): void {
    this.service.take=20;
    // this.service.getBaseData();
  }
  constructor(public service:ServiceService){
    super();
  }
  public columns: any[] = [
    
    {
      title: 'All Transactions',
      name: 'id',
      icon:'ri-store-2-fill',
      sort: false,
      sortOrder: 'desc',
      template: 'IdTemplate',
      subItems: [
   
        {
          title: 'Name',
          name: 'serviceName',
          sort: true,
          
        },
        {
          title: 'Actions',
          name: 'actions',
          
          actions: [
     
            { name: 'Edit'},
            { name: 'Delete'},
          ],
          sort: true,
        },
    
      ]
    },
  
  ];
  public recordSaved(success: boolean) {
    this.id = null;
    if (success) {
      
      this.service.getBaseData();
    }
    // this.modalRef.hide();
  }

  setSort(sortBy: DataSortColType, order: Order): void {
  
    this.service.setSorting(order, sortBy);
    this.onSortChange.next({ column: sortBy, direction: order });
 }
  setSortNew(sortBYY: DataSortColType) {
    this.sortByType = sortBYY;
    console.log(sortBYY);
    console.log(this.service.order);

    if (this.service.order === 'ASC') {
      this.service.setSorting(Order.DESC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.DESC });
    } else {
      this.service.setSorting(Order.ASC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.ASC });
    }
  }
}
