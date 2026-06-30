import { CommonModule, NgClass, ViewportScroller } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbScrollSpyModule, NgbCarouselModule, NgbTooltipModule, NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { ScrollspyDirective } from '../../shared/directives/scrollspy.directive';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LandingScrollspyDirective } from '../../shared/directives/landingscrollspy.directive';
import { environment } from '../../../environments/environment.development';
import { FooterComponent } from '../../shared/landing/footer/footer.component';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [
    RouterModule,
    CommonModule,
    FooterComponent,
    NgbScrollSpyModule,
    FormsModule,
    ReactiveFormsModule,
    NgbCarouselModule,
    NgbTooltipModule,
    NgbCollapseModule,
    LandingScrollspyDirective,
    ScrollspyDirective,
  ],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
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
