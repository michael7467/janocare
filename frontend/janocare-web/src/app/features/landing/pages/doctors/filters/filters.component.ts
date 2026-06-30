import { CommonModule } from '@angular/common';
import { Component, EventEmitter, OnDestroy, OnInit, Output, ViewChild, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CityFilterComponent } from './city-filter/city-filter.component';
import { GenderFilterComponent } from './gender-filter/gender-filter.component';
import { ServiceFilterComponent } from './service-filter/service-filter.component';
import { SpecialtyFilterComponent } from './specialty-filter/specialty-filter.component';
import { Subscription } from 'rxjs';
import { DatasourceFilter } from '../../../../../shared/tp-table/model';

@Component({
  selector: 'app-filters',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    NgSelectModule,
    CityFilterComponent,
    GenderFilterComponent,
    ServiceFilterComponent,
    SpecialtyFilterComponent,
  ],
  templateUrl: './filters.component.html',
  styleUrl: './filters.component.scss',
})
export class FiltersComponent implements OnInit, OnDestroy {
  @Output() searchDoctor = new EventEmitter<DatasourceFilter[]>();
  @Output() handleFilterSearchFromQueryParams = new EventEmitter<DatasourceFilter[]>();

  @ViewChild(CityFilterComponent) cityFilter: CityFilterComponent;
  @ViewChild(GenderFilterComponent) genderFilter: GenderFilterComponent;
  @ViewChild(ServiceFilterComponent) serviceFilter: ServiceFilterComponent;
  @ViewChild(SpecialtyFilterComponent) specialtyFilter: SpecialtyFilterComponent;

  queryParamsSubscription: Subscription;

  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  queryParams: Params;

  constructor() {}

  ngOnInit(): void {
    this.queryParamsSubscription = this.activatedRoute.queryParams.subscribe((params) => {
      this.queryParams = params;
      // handle filters
    });
  }

  ngOnDestroy(): void {
    if (this.queryParamsSubscription) {
      this.queryParamsSubscription.unsubscribe();
    }
  }

  handleClearFilters() {
    this.cityFilter.selectAllLocations();
    this.genderFilter.selectBothGenders();
    this.specialtyFilter.selectAllSpecialties();
  }

  searchDoctorTerm(event: any) {
    // a debounce time of 1 second
    setTimeout(() => {
      let filters: DatasourceFilter[] = [];
      filters.push({ name: 'q', value: event.target.value });
      this.searchDoctor.emit(filters);
    }, 1000);
  }

  handleFilterSearch() {
    const filters: DatasourceFilter[] = [];
    Object.keys(this.queryParams).map((key) => {
      filters.push({ name: key, value: this.queryParams[key] });
    });
    this.handleFilterSearchFromQueryParams.emit(filters);
  }
}
