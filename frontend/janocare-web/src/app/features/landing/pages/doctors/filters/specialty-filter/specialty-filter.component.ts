import { Component, OnInit, inject } from '@angular/core';
import { BehaviorSubject, Observable, concat, of, debounceTime, distinctUntilChanged, switchMap, map, catchError, tap } from 'rxjs';
import { defaultRequestParams } from '../../../../../../shared/tp-table/config';
import { SpecializationModel, SpecializationService } from '../../../../../../core';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { Router, ActivatedRoute, Params } from '@angular/router';

interface ISpecialty {
  id: number;
  name: string;
  selected: boolean;
}

@Component({
  selector: 'app-specialty-filter',
  standalone: true,
  imports: [CommonModule, NgSelectModule],
  templateUrl: './specialty-filter.component.html',
  styleUrl: './specialty-filter.component.scss',
})
export class SpecialtyFilterComponent implements OnInit {
  specialtyLoading = false;
  specialtyInput$ = new BehaviorSubject<string>('');
  specialties$: Observable<SpecializationModel[]>;

  allSpecialtiesSelected = true;
  Specialties$ = new BehaviorSubject<ISpecialty[]>([]);

  previousQueryParams: Params;

  private specialtiesService = inject(SpecializationService);

  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.previousQueryParams = params;
    });

    this.loadSpecialties();

    this.specialtiesService.getTableData(defaultRequestParams).subscribe((specialties) => {
      let specialtyWrapper: ISpecialty[] = [];
      specialties.data.forEach((spec) => {
        if (specialtyWrapper.length > 5) {
          return;
        }
        specialtyWrapper.push({ id: spec.id, name: spec.name, selected: false });
      });

      this.Specialties$.next(specialtyWrapper);
    });

    this.Specialties$.subscribe((specialties) => {
      this.allSpecialtiesSelected = specialties.some((specialty) => specialty.selected);

      let specialtyFilters = '';
      specialties.forEach((specialty) => {
        if (specialty.selected) {
          specialtyFilters += (specialtyFilters ? ',' : '') + specialty.name;

          this.router.navigate([], {
            relativeTo: this.activatedRoute,
            queryParams: { ...this.previousQueryParams, specializations: specialtyFilters },
          });
        }
      });

      // remove if all specialty unselected
      if (!this.allSpecialtiesSelected) {
        Object.keys(this.previousQueryParams).map((key) => {
          if (key === 'specializations') {
            const { specializations, ...restPreviousQueryParams } = this.previousQueryParams;
            this.previousQueryParams = { ...restPreviousQueryParams };
          }
        });

        this.router.navigate([], {
          relativeTo: this.activatedRoute,
          queryParams: { ...this.previousQueryParams },
        });
      }
    });
  }

  private loadSpecialties() {
    // this.specialties$ = concat(
    //   of([]), // default items
    //   this.specialtyInput$.pipe(
    //     debounceTime(300),
    //     distinctUntilChanged(),
    //     switchMap((term) => {
    //       let opts = defaultRequestParams;
    //       opts.filters = [{ name: 'name', value: term }];
    //       return this.specialtiesService
    //         .getTableData(opts)
    //         .pipe(map((r) => [...r.data]))
    //         .pipe(
    //           catchError(() => of([])), // empty list on error
    //           tap(() => {
    //             this.specialtyLoading = false;
    //           }),
    //         );
    //     }),
    //   ),
    // );
  }
  specialtiesTrackedBy(item: SpecializationModel) {
    return item.id;
  }
  specialtyChanged(spec: any) {
    // this.selectSpecialty({ id: specialty.id, name: specialty.name, selected: false }); // not working for some reason so using below code
    if (spec) {
      const specialty = { id: spec.id, name: spec.name, selected: false };
      specialty.selected = !specialty.selected;

      this.Specialties$.next(
        this.Specialties$.value.map((s) => {
          // specialty is within the current list of specialties that are being displayed in the buttons
          if (s.id === specialty.id) {
            s.selected = specialty.selected;
          }
          // specialty is not within the current list of specialties that are being displayed in the buttons
          else {
            // add new query param for this specialty
            if (specialty.selected) {
              this.router.navigate([], {
                relativeTo: this.activatedRoute,
                queryParams: { ...this.previousQueryParams, specializations: specialty.name },
              });
            }
          }
          return s;
        }),
      );
    }
  }

  selectSpecialty(specialty: { id: number; name: string; selected: boolean }) {
    specialty.selected = !specialty.selected;
    this.Specialties$.next(
      this.Specialties$.value.map((s) => {
        if (s.id === specialty.id) {
          s.selected = specialty.selected;
        }
        return s;
      }),
    );
  }

  selectAllSpecialties() {
    this.Specialties$.value.forEach((specialty) => {
      specialty.selected = false;
    });
    this.Specialties$.next([...this.Specialties$.value]);

    // clear url query params of specialty
    Object.keys(this.previousQueryParams).map((key) => {
      if (key === 'specializations') {
        const { specializations, ...restPreviousQueryParams } = this.previousQueryParams;
        this.previousQueryParams = { ...restPreviousQueryParams };
      }
    });
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: { ...this.previousQueryParams },
    });
  }
}
