import { Component, Input, OnInit, TemplateRef, forwardRef, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';

import { ListComponent, TpTableModule } from '../../../../../../shared';
import { Order } from '../../../../../../core';
import { routes } from '../../../../../../shared/routes/routes';
import { ProfessionalExperienceEditComponent } from '../professional-experience-edit/professional-experience-edit.component';

import { DataSortColType, ExperienceService } from '../experience.service';
import { CommonTableComponent } from '../../../common/common-table/common-table.component';
@Component({
  selector: 'app-professional-experience-list',
  standalone: true,
  imports: [RouterLink, TpTableModule, CommonModule, CommonTableComponent, 
    // ModalModule, 
    ProfessionalExperienceEditComponent],
  viewProviders: [
    {
      provide: ListComponent,
      useExisting: forwardRef(() => ProfessionalExperienceListComponent),
    },
  ],
  // providers: [BsModalService],
  templateUrl: './professional-experience-list.component.html',
  styleUrls: ['./professional-experience-list.component.scss'],
})
export class ProfessionalExperienceListComponent extends ListComponent implements OnInit {
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
  constructor(public service: ExperienceService) {
    super();
  }

  ngOnInit(): void {
    this.service.take = 20;
    this.service.getBaseData();
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
    if (this.service.order == 'ASC') {
      this.service.setSorting(Order.DESC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.DESC });
    } else {
      this.service.setSorting(Order.ASC, sortBYY);
      this.onSortChange.next({ column: sortBYY, direction: Order.ASC });
    }
  }
}
