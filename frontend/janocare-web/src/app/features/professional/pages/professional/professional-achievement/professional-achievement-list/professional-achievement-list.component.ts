import { Component, OnInit, TemplateRef, ViewChild, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { BsModalRef, BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { NgbModal, NgbModalRef, NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { ImgFallbackDirective, TpStandardTableComponent, TpTableModule, } from '../../../../../../shared';
import { routes } from '../../../../../../shared/routes/routes';
import { ProfessionalAchievementEditComponent } from '../professional-achievement-edit/professional-achievement-edit.component';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { AuthService, PaginationService, ProfessionalAchievement, ProfessionalAchievementService } from '../../../../../../core';
import { NgbNavModule, NgbPaginationModule, NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { ProfAchievementService } from '../professional-achievement.service';

@Component({
  selector: 'app-professional-achievement-list',
  standalone: true,
  imports: [
    RouterLink,
    ImgFallbackDirective,
    NgFor,
    NgIf,
    TpTableModule,
    CommonModule,
    FormsModule,
    NgbTypeaheadModule,
    // ModalModule,
    NgbModalModule,
    NgbPaginationModule,
    NgbNavModule,
    ProfessionalAchievementEditComponent,
  ],
  //  providers: [BsModalService],
  templateUrl: './professional-achievement-list.component.html',
  styleUrls: ['./professional-achievement-list.component.scss'],
})
export class ProfessionalAchievementListComponent implements OnInit {
  // private modalService = inject(BsModalService);
  // public modalRef: BsModalRef;
  private modalService = inject(NgbModal);
modalRef?: NgbModalRef;
  submitted = false;
  masterSelected!: boolean;
  content?: any;
  allorderes: any;
  searchResults: any;
  searchTerm: any;
  payment: any = '';
  date: Date;
  status: any = '';
  public auth = inject(AuthService);
  professionalAchievement: ProfessionalAchievement[] = [];
  private serviceAchievement = inject(ProfessionalAchievementService);
  @ViewChild('table')
  private table: TpStandardTableComponent;

  public routes = routes;
  public id;

  showEditModal(template: TemplateRef<any>, id?: number) {
    this.id = id;
    this.modalRef = this.modalService.open(template, {
    size: 'lg',
    centered: true,
    backdrop: 'static'
  });
  }
  ngOnInit(): void {
    this.date = new Date();

    this.service.take = 50;
    this.service.getBaseData();
  }

  constructor(public service: ProfAchievementService) {}
  getData() {}
closeModal(): void {
  this.modalRef?.close();
  this.modalRef = undefined;
}
  public recordSaved(success: boolean) {
    this.id = null;
    if (success) {
      this.service.take = 50;
      this.service.getBaseData();
    }
     this.modalRef.close();
  }
}
