import { CommonModule, ViewportScroller } from '@angular/common';
import { Component } from '@angular/core';
import { NgbCarouselModule, NgbTooltipModule, NgbCollapseModule, NgbScrollSpyModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { SharedModule } from '../../../../shared/shared.module';
import { LandingScrollspyDirective } from '../../../../shared/directives/landingscrollspy.directive';
import { ScrollspyDirective } from '../../../../shared/directives/scrollspy.directive';

import { environment } from '../../../../../environments/environment.uat';
import { HowItWorksComponent } from '../../../../shared/landing/how-it-works/how-it-works.component';
import { PatientAdvantageComponent } from '../../../../shared/landing/patient-advantage/patient-advantage.component';
import { DoctorsAdvantageComponent } from '../../../../shared/landing/doctors-advantage/doctors-advantage.component';
import { PopularSpecialitiesComponent } from '../../../../shared/popular-specialities/popular-specialities.component';
import { PopularDoctorsComponent } from '../../../../shared/landing/popular-doctors/popular-doctors.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgbScrollSpyModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbCarouselModule,
    HowItWorksComponent,
    PatientAdvantageComponent,
    DoctorsAdvantageComponent,
    NgbTooltipModule,
    NgbCollapseModule,
    SharedModule,
    LandingScrollspyDirective,
    ScrollspyDirective,
    RouterModule,
    PopularSpecialitiesComponent,
    PopularDoctorsComponent
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {
  environment = environment;

  currentSection = 'home';
  showNavigationArrows: any;
  showNavigationIndicators: any;

  constructor(private scroller: ViewportScroller) {}

  ngOnInit(): void {}

  scrollTo(anchor: string) {
    this.scroller.scrollToAnchor(anchor);
  }

  windowScroll() {
    const navbar = document.getElementById('navbar');
    if (document.body.scrollTop > 40 || document.documentElement.scrollTop > 40) {
      navbar?.classList.add('is-sticky');
    } else {
      navbar?.classList.remove('is-sticky');
    }

    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
      (document.getElementById('back-to-top') as HTMLElement).style.display = 'block';
    } else {
      (document.getElementById('back-to-top') as HTMLElement).style.display = 'none';
    }
  }

  onSectionChange(sectionId: string) {
    this.currentSection = sectionId;
  }

  toggleMenu() {
    document.getElementById('navbarSupportedContent')?.classList.toggle('show');
  }

  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }
}
