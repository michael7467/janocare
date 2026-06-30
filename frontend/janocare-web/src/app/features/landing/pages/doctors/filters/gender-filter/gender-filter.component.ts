import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { GenderType } from '../../../../../../shared';

@Component({
  selector: 'app-gender-filter',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gender-filter.component.html',
  styleUrl: './gender-filter.component.scss',
})
export class GenderFilterComponent implements OnInit {
  allGendersSelected = true;
  Genders$ = new BehaviorSubject<{ id: number; name: GenderType; selected: boolean }[]>([
    { id: 1, name: 'Male', selected: false },
    { id: 2, name: 'Female', selected: false },
  ]);

  previousQueryParams: Params;

  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  constructor() {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.previousQueryParams = params;
    });

    this.Genders$.subscribe((genders) => {
      this.allGendersSelected = genders.some((gender) => gender.selected);
      let genderFilters = '';
      genders.forEach((gender) => {
        if (gender.selected) {
          genderFilters += gender.name;

          this.router.navigate([], {
            relativeTo: this.activatedRoute,
            queryParams: { ...this.previousQueryParams, gender: genderFilters },
          });
        }
      });
    });
  }

  selectGender(gender: { id: number; name: string; selected: boolean }) {
    gender.selected = !gender.selected;
    this.Genders$.value.forEach((g) => {
      if (g.id !== gender.id) {
        g.selected = false;
      }
    });
    this.Genders$.next([...this.Genders$.value]);
  }

  selectBothGenders() {
    this.Genders$.value.forEach((gender) => {
      gender.selected = false;
    });
    this.Genders$.next([...this.Genders$.value]);

    // clear url query params of gender
    Object.keys(this.previousQueryParams).map((key) => {
      if (key === 'gender') {
        const { gender, ...restPreviousQueryParams } = this.previousQueryParams;
        this.previousQueryParams = { ...restPreviousQueryParams };
      }
    });
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: { ...this.previousQueryParams },
    });
  }
}
