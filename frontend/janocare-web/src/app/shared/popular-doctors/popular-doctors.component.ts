import { Component, OnInit, ViewEncapsulation, inject } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ImgFallbackDirective, RemovePortPipe } from 'shared';
import { IPopularProfessionals, ProfessionalModel, ProfessionalUserService } from '../../../core';

@Component({
  selector: 'app-popular-doctors',
  standalone: true,
  imports: [CommonModule, RouterModule, ImgFallbackDirective, RemovePortPipe, DecimalPipe],
  templateUrl: './popular-doctors.component.html',
  styleUrl: './popular-doctors.component.scss',
  encapsulation: ViewEncapsulation.Emulated,
})
export class PopularDoctorsComponent implements OnInit {
  popularProfessionals: IPopularProfessionals[] = [];

  professionalService = inject(ProfessionalUserService);

  ngOnInit(): void {
    this.professionalService.getAllPopularProfessionals().subscribe((professionals) => {
      this.popularProfessionals = professionals;
    });
  }
}
