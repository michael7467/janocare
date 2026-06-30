import { Component, ViewChild, inject } from '@angular/core';
import { NgbCarousel, NgbCarouselConfig, NgbCarouselModule, NgbSlideEvent, NgbSlideEventSource } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ImgFallbackDirective, RemovePortPipe } from '../../shared';
import { Specialization, SpecializationService } from '../../core';

@Component({
  selector: 'app-popular-specialities',
  standalone: true,
  imports: [NgbCarouselModule, FormsModule, CommonModule, RouterModule, RemovePortPipe, ImgFallbackDirective],
  templateUrl: './popular-specialities.component.html',
  styleUrl: './popular-specialities.component.scss',
})
export class PopularSpecialitiesComponent {
  showNavigationIndicators = false;

  paused = false;
  unpauseOnArrow = false;
  pauseOnIndicator = false;
  pauseOnHover = true;
  pauseOnFocus = true;

  PopularSpecialities!: Specialization[];

  specializationsService = inject(SpecializationService);

  @ViewChild('carousel', { static: true }) carousel: NgbCarousel;

  constructor(config: NgbCarouselConfig) {}

  togglePaused() {
    if (this.paused) {
      this.carousel.cycle();
    } else {
      this.carousel.pause();
    }
    this.paused = !this.paused;
  }

  onSlide(slideEvent: NgbSlideEvent) {
    if (this.unpauseOnArrow && slideEvent.paused && (slideEvent.source === NgbSlideEventSource.ARROW_LEFT || slideEvent.source === NgbSlideEventSource.ARROW_RIGHT)) {
      this.togglePaused();
    }
    if (this.pauseOnIndicator && !slideEvent.paused && slideEvent.source === NgbSlideEventSource.INDICATOR) {
      this.togglePaused();
    }
  }

  ngOnInit(): void {
    this._fetchData();
  }

  private _fetchData() {
    this.specializationsService.getAll().subscribe((specializations) => {
       this.PopularSpecialities = specializations;
    });
  }

  // Method to get items for each slide
  getSlideItems(slideIndex: number): any[] {
    let numberOfItemsPerSlide = 8;

    // get the number of items per slide based on the screen size
    if (window.innerWidth <= 768) {
      numberOfItemsPerSlide = 3;
    } else if (window.innerWidth <= 992) {
      numberOfItemsPerSlide = 5;
    }

    // Calculate the start and end index of items for the given slide

    const startIndex = slideIndex * numberOfItemsPerSlide;
    const endIndex = (slideIndex + 1) * numberOfItemsPerSlide;

    // Return the items for the current slide
    return this.PopularSpecialities.slice(startIndex, endIndex);
  }
}
