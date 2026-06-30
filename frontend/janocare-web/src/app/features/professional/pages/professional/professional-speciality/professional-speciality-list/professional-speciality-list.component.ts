import { Component, TemplateRef, inject } from '@angular/core';
import { ProfessionalSpecialityEditComponent } from '../professional-speciality-edit/professional-speciality-edit.component';
import { RouterLink } from '@angular/router';
import { ImgFallbackDirective, TpTableModule } from '../../../../../../shared';
import { CommonModule } from '@angular/common';
// import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { SpecialityService } from '../professional-speciality.service';
import { ProfessionalSubSpecialityListComponent } from '../professional-sub-speciality/professional-sub-speciality-list/professional-sub-speciality-list.component';

@Component({
  selector: 'app-professional-speciality-list',
  standalone: true,
  imports: [RouterLink, ImgFallbackDirective, ProfessionalSubSpecialityListComponent, ProfessionalSpecialityEditComponent, TpTableModule, CommonModule, 
    // ModalModule
  ],
  // providers: [BsModalService],
  templateUrl: './professional-speciality-list.component.html',
  styleUrls: ['./professional-speciality-list.component.scss'],
})
export class ProfessionalSpecialityListComponent {
  // private modalService = inject(BsModalService);
  // public modalRef: BsModalRef;
  public id;
  showEditModal(template: TemplateRef<any>, id?: number) {
    this.id = id;
    // this.modalRef = this.modalService.show(template, { class: 'modal-dialog-centered' });
  }

  constructor(public service: SpecialityService) {}
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
