import { Component, Input, OnInit, TemplateRef, ViewChild, forwardRef, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
// import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { DatasourceParameters, ListComponent, TableColumn, TableOptions, TablePaging, TpStandardTableComponent, TpTableModule } from '../../../../../../shared';
import { routes } from '../../../../../../shared/routes/routes';
import { ProfessionalQualificationEditComponent } from '../professional-qualification-edit/professional-qualification-edit.component';
import { Order, ProfessionalQualificationService } from '../../../../../../core';
import { CommonModule } from '@angular/common';
import { DataSortColType, QualificationService } from '../qualification.service';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';

@Component({
  selector: 'app-professional-qualification-list',
  standalone: true,
  imports: [RouterLink,CommonModule, TpTableModule,
    //  ModalModule,
     CommonTableComponent, 
    //  BsDatepickerModule,
     ProfessionalQualificationEditComponent],
  viewProviders: [
    {
      provide: ListComponent,
      useExisting: forwardRef(() => ProfessionalQualificationListComponent),
    },
  ],
  // providers: [BsModalService,],
  templateUrl: './professional-qualification-list.component.html',
  styleUrls: ['./professional-qualification-list.component.scss']
})
export class ProfessionalQualificationListComponent extends ListComponent implements OnInit {
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
     this.service.getBaseData();
  }
  constructor(public service:QualificationService){
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
          name: 'qualificationName',
          sort: true,
          
        },
    
 
        {
          title: 'Institution ',
          name: 'institutionName',
          sort: true,

        },
        {
          title: 'Procrument Year',
          name: 'procrumentYear',
          sort: true,
          pipe:true,
          pipeType:'date',
          pipeBy:'YYYY-MM-dd'
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

    if (this.service.order == 'ASC') {
      this.service.setSorting(Order.DESC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.DESC });
    } else {
      this.service.setSorting(Order.ASC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.ASC });
    }
  }

}
