import { CommonModule, NgClass } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbScrollSpyModule, NgbCarouselModule, NgbTooltipModule, NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';
import { ScrollspyDirective } from '../../shared/directives/scrollspy.directive';
import { ScrollToModule, ScrollToService } from '@nicky-lenaers/ngx-scroll-to';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LandingScrollspyDirective } from '../../shared/directives/landingscrollspy.directive';
import { environment } from '../../../environments/environment.development';
import { FooterComponent } from '../../shared/landing/footer/footer.component';
import { CarouselModule } from 'ngx-owl-carousel-o';
@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [
        RouterModule,
    CommonModule,
    FooterComponent,
    NgbScrollSpyModule,
    ScrollToModule,
    FormsModule,
    ReactiveFormsModule,
    NgbCarouselModule,
  
    CarouselModule,
    NgbTooltipModule,
    NgbCollapseModule,
    LandingScrollspyDirective,
    ScrollspyDirective,
  ],
  providers: [ScrollToService],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.scss'
})
export class LandingComponent implements OnInit {
  environment = environment;

  currentSection = 'home';
  showNavigationArrows: any;
  showNavigationIndicators: any;

  constructor() {}

  ngOnInit(): void {}

  /**
   * Window scroll method
   */
  // tslint:disable-next-line: typedef
  windowScroll() {
    const navbar = document.getElementById('navbar');
    if (document.body.scrollTop > 40 || document.documentElement.scrollTop > 40) {
      navbar?.classList.add('is-sticky');
    } else {
      navbar?.classList.remove('is-sticky');
    }

    // Top Btn Set
    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
      (document.getElementById('back-to-top') as HTMLElement).style.display = 'block';
    } else {
      (document.getElementById('back-to-top') as HTMLElement).style.display = 'none';
    }
  }

  /**
   * Section changed method
   * @param sectionId specify the current sectionID
   */
  onSectionChange(sectionId: string) {
    this.currentSection = sectionId;
  }

  /**
   * Toggle navbar
   */
  toggleMenu() {
    document.getElementById('navbarSupportedContent')?.classList.toggle('show');
  }

  // When the user clicks on the button, scroll to the top of the document
  topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }
}
