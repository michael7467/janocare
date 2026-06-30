import { Component, TemplateRef, inject } from '@angular/core';
import { ProfessionalSubSpecialityEditComponent } from '../professional-sub-speciality-edit/professional-sub-speciality-edit.component';
import { RouterLink } from '@angular/router';
import { TpTableModule } from '../../../../../../../shared';
import { CommonModule } from '@angular/common';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { SpecialityService } from '../../professional-speciality.service';
import { SubSpecialityService } from '../professional-sub-speciality.service';

@Component({
  selector: 'app-professional-sub-speciality-list',
  standalone: true,
  imports: [RouterLink, ProfessionalSubSpecialityEditComponent, TpTableModule, CommonModule, 
    // ModalModule
  ],
  // providers: [BsModalService],
  templateUrl: './professional-sub-speciality-list.component.html',
  styleUrls: ['./professional-sub-speciality-list.component.scss'],
})
export class ProfessionalSubSpecialityListComponent {
  // private modalService = inject(BsModalService);
  // public modalRef: BsModalRef;
  public id;
  showEditModal(template: TemplateRef<any>, id?: number) {
    this.id = id;
    // this.modalRef = this.modalService.show(template, { class: 'modal-dialog-centered' });
  }

  constructor(public service: SubSpecialityService) {}
  ngOnInit(): void {
    this.service.take = 50;
    this.service.getBaseData();
  }

  public recordSaved(success: boolean) {
    this.id = null;
    if (success) {
      this.service.getBaseData();
    }
    // this.modalRef.hide();
  }
}
