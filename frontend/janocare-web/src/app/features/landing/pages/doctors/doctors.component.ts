import { AsyncPipe, CommonModule } from '@angular/common';
import { Component, NgModule, OnDestroy, OnInit, TemplateRef, inject } from '@angular/core';
import { FormsModule, UntypedFormBuilder } from '@angular/forms';
import { NgbModal, NgbModule, NgbPagination } from '@ng-bootstrap/ng-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { BehaviorSubject, Observable, Subscription, catchError, concat, debounceTime, distinctUntilChanged, filter, map, of, switchMap, tap } from 'rxjs';
import { ActivatedRoute, NavigationEnd, Router, RouterModule } from '@angular/router';
import {  IResultMeta, PaginationService, } from '../../../../core';
import { DatasourceFilter, DatasourceParameters, DatasourceResult, GENDER_TYPE, GenderType, ImgFallbackDirective, RemovePortPipe } from '../../../../shared';
import { defaultRequestParams } from '../../../../shared/tp-table/config';
import { calculateYearsFromDate } from '../../../../core/utils';
import { FiltersComponent } from './filters/filters.component';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ProfessionalUserService } from '../../../../core/services/professional/professional-user.service';
import { ProfessionalModel } from '../../../../core/models/professional/professional-user.model';

@Component({
  selector: 'app-doctors',
  standalone: true,
  imports: [NgbModule, NgSelectModule, CommonModule, FormsModule, AsyncPipe, NgbPagination, RouterModule, ImgFallbackDirective, RemovePortPipe, FiltersComponent],
  providers: [BsModalService],
  templateUrl: './doctors.component.html',
  styleUrl: './doctors.component.scss',
})
export class DoctorsComponent implements OnInit, OnDestroy {
  // consultationTypes: 'inperson' | 'video' | 'instant' = 'video';

  private modalService = inject(BsModalService);
  public modalRef: BsModalRef;

  private professionalsService = inject(ProfessionalUserService);
  //
  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  filterFromParams: DatasourceFilter[] = [];

  pageService = inject(PaginationService);

  itemCount: number;
  professionals: ProfessionalModel[] = [];
  professionals$ = new BehaviorSubject<DatasourceResult<ProfessionalModel>>(undefined);
  private professionalsSubscription: Subscription;

  multiDefaultOption = 'Watches';
  Default = [{ name: 'Watches' }, { name: 'Headset' }, { name: 'Sweatshirt' }];

  ngOnInit(): void {
    // Subscribe to NavigationEnd event
    this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {
      // Reset scroll position to the top of the page
      // window.scrollTo(0, 0);
      window.scrollTo({ top: 0, behavior: 'auto' });
    });
    this.fetchProfessionals();

    this.professionalsSubscription = this.professionals$.subscribe((professionals) => {
      this.professionals = professionals?.data;
      // pagination
      // this.pageService.setPageMeta(professionals?.meta as IResultMeta);
    });
  }

  ngOnDestroy(): void {
    if (this.professionalsSubscription) {
      this.professionalsSubscription.unsubscribe();
    }
  }

  public fetchProfessionals(filters?: DatasourceFilter[]) {
    const datasource: DatasourceParameters = { ...defaultRequestParams };
    if (filters && filters.length > 0) {
      const f: DatasourceFilter[] = [...filters, { name: 'patientStoryCount', value: 1 }];
      datasource.filters = filters;
    }

    this.professionalsService.getDataWithFilters(datasource).subscribe((professionals) => {
      this.professionals$.next(professionals);
      this.itemCount = professionals.meta.itemCount;
    });
  }

  // Pagination
  changePage() {
    const filters: DatasourceFilter[] = [];
    this.professionalsService.getDataWithFilters({ ...defaultRequestParams, page: this.pageService.page, take: 50, filters: filters }).subscribe((articles) => {
      this.professionals$.next(articles);
    });
  }

  public getPracticingYears(startDate: Date) {
    const years = calculateYearsFromDate(startDate);
    return years;
  }

  getVotingPercentage(upVote: string, downVote: string) {
    const totalVotes = parseInt(upVote) + parseInt(downVote);
    const upVotePercentage = totalVotes === 0 ? 0 : (parseInt(upVote) / totalVotes) * 100;
    return Math.round(upVotePercentage);
  }

  // search by doctor firstName and/or lastName
  searchDoctor(filters: DatasourceFilter[]) {
    this.fetchProfessionals(filters);
  }

  handleFilterSearch(filters: DatasourceFilter[]) {
    this.fetchProfessionals(filters);
    // close modal
    this.modalRef.hide();
  }

  showEditModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template, { class: 'modal-dialog-centered' });
  }
}
