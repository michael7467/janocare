import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-service-filter',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './service-filter.component.html',
  styleUrl: './service-filter.component.scss',
})
export class ServiceFilterComponent implements OnInit {
  isInpersonEnabled$ = new BehaviorSubject<boolean>(true);
  isOnlineConsultationEnabled$ = new BehaviorSubject<boolean>(true);
  isInstantCallEnabled$ = new BehaviorSubject<boolean>(true);

  previousQueryParams: Params;

  router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      this.previousQueryParams = params;
    });

    this.isInpersonEnabled$.subscribe((isInpersonEnabled) => {
      this.router.navigate([], {
        relativeTo: this.activatedRoute,
        queryParams: { ...this.previousQueryParams, isInpersonEnabled },
      });
    });
    this.isOnlineConsultationEnabled$.subscribe((isOnlineConsultationEnabled) => {
      this.router.navigate([], {
        relativeTo: this.activatedRoute,
        queryParams: { ...this.previousQueryParams, isOnlineConsultationEnabled },
      });
    });
    this.isInstantCallEnabled$.subscribe((isInstantCallEnabled) => {
      this.router.navigate([], {
        relativeTo: this.activatedRoute,
        queryParams: { ...this.previousQueryParams, isInstantCallEnabled },
      });
    });

    // mounting to this component will add the following query params
    this.router.navigate([], {
      relativeTo: this.activatedRoute,
      queryParams: {
        ...this.previousQueryParams,
        isInpersonEnabled: this.isInpersonEnabled$.value,
        isOnlineConsultationEnabled: this.isOnlineConsultationEnabled$.value,
        isInstantCallEnabled: this.isInstantCallEnabled$.value,
      },
    });
  }

  selectInpersonEnabled() {
    const value: boolean = !this.isInpersonEnabled$.value;
    this.isInpersonEnabled$.next(value);
  }
  selectOnlineConsultationEnabled() {
    const value: boolean = !this.isOnlineConsultationEnabled$.value;
    this.isOnlineConsultationEnabled$.next(value);
  }
  selectInstantCallEnabled() {
    const value: boolean = !this.isInstantCallEnabled$.value;
    this.isInstantCallEnabled$.next(value);
  }
}
