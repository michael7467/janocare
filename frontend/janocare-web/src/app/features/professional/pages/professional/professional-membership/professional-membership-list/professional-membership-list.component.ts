import { Component, Input, OnInit, TemplateRef, ViewChild, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
// import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';

import { ListComponent, TpTableModule } from '../../../../../../shared';
import { routes } from '../../../../../../shared/routes/routes';
import { ProfessionalMembershipEditComponent } from '../professional-membership-edit/professional-membership-edit.component';
import { Order } from '../../../../../../core';
import { DataSortColType, MembershipService } from '../membership.service';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';
@Component({
  selector: 'app-professional-membership-list',
  standalone: true,
  imports: [RouterLink, TpTableModule, CommonModule,
    //  ModalModule, 
     CommonTableComponent,
      // BsDatepickerModule, 
      ProfessionalMembershipEditComponent],
  // providers: [BsModalService],
  templateUrl: './professional-membership-list.component.html',
  styleUrls: ['./professional-membership-list.component.scss'],
})
export class ProfessionalMembershipListComponent extends ListComponent implements OnInit {
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
  ngOnInit(): void {
    this.service.take = 20;
     this.service.getBaseData();
  }
  constructor(public service: MembershipService) {
    super();
  }

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
