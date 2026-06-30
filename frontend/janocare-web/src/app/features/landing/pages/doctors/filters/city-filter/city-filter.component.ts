import { Component, OnInit, inject } from '@angular/core';
import { BehaviorSubject, Observable, catchError, concat, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';
import { City, CityService } from '../../../../../../core';
import { defaultRequestParams } from '../../../../../../shared/tp-table/config';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-city-filter',
  standalone: true,
  imports: [CommonModule, NgSelectModule],
  templateUrl: './city-filter.component.html',
  styleUrl: './city-filter.component.scss',
})
export class CityFilterComponent implements OnInit {
  // cities
  cityLoading = false;
  cityInput$ = new BehaviorSubject<string>('');
  cities$: Observable<City[]>;

  allLocationsSelected = true;
  Locations$ = new BehaviorSubject<{ id: number; name: string; selected: boolean }[]>([]);

  previousQueryParams: Params;

  private citiesService = inject(CityService);

  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.previousQueryParams = params;
    });

    this.loadCities();

    // for the locations buttons
    this.citiesService.getTableData(defaultRequestParams).subscribe((cities) => {
      let locationWrapper: { id: number; name: string; selected: boolean }[] = [];
      cities.data.forEach((city) => {
        if (locationWrapper.length > 5) {
          return;
        }
        locationWrapper.push({ id: city.id, name: city.cityName, selected: false });
      });

      this.Locations$.next(locationWrapper);
    });

    this.Locations$.subscribe((locations) => {
      this.allLocationsSelected = locations.some((location) => location.selected);

      let locationFilters = '';
      locations.forEach((location) => {
        if (location.selected) {
          locationFilters += (locationFilters ? ',' : '') + location.name;

          this.router.navigate([], {
            relativeTo: this.activatedRoute,
            queryParams: { ...this.previousQueryParams, locations: locationFilters },
          });
        }
      });

      // remove if all locations unselected
      if (!this.allLocationsSelected) {
        Object.keys(this.previousQueryParams).map((key) => {
          if (key === 'locations') {
            const { locations, ...restPreviousQueryParams } = this.previousQueryParams;
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

  private loadCities() {
    this.cities$ = concat(
      of([]), // default items
      this.cityInput$.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        switchMap((term) => {
          let opts = defaultRequestParams;
          opts.filters = [{ name: 'cityName', value: term }];
          return this.citiesService
            .getTableData(opts)
            .pipe(map((r) => [...r.data]))
            .pipe(
              catchError(() => of([])), // empty list on error
              tap(() => {
                this.cityLoading = false;
              }),
            );
        }),
      ),
    );
  }

  cityTrackedBy(item: City) {
    return item.id;
  }
  locationChanged(loc: any) {
    if (loc) {
      const location = { id: loc.id, name: loc.name, selected: false };
      location.selected = !location.selected;

      this.Locations$.next(
        this.Locations$.value.map((s) => {
          // location is within the current list of locations that are being displayed in the buttons
          if (s.id === location.id) {
            s.selected = location.selected;
          }
          // location is not within the current list of locations that are being displayed in the buttons
          else {
            // add new query param for this location
            if (location.selected) {
              this.router.navigate([], {
                relativeTo: this.activatedRoute,
                queryParams: { ...this.previousQueryParams, location: location.name },
              });
            }
          }
          return s;
        }),
      );
    }
  }

  selectLocation(location: { id: number; name: string; selected: boolean }) {
    location.selected = !location.selected;
    this.Locations$.next(
      this.Locations$.value.map((l) => {
        if (l.id === location.id) {
          l.selected = location.selected;
        }
        return l;
      }),
    );
  }

  selectAllLocations() {
    this.Locations$.value.forEach((location) => {
      location.selected = false;
    });
    this.Locations$.next([...this.Locations$.value]);
  }
}
