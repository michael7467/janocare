import { Component, OnInit, ViewEncapsulation, inject } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ImgFallbackDirective, RemovePortPipe } from '../../../shared';
import { IPopularProfessionals, ProfessionalUserService } from '../../../core/services/professional/professional-user.service';
import { environment } from '../../../../environments/environment.development';


@Component({
  selector: 'app-popular-doctors',
  standalone: true,
  imports: [CommonModule, RouterModule, ImgFallbackDirective, RemovePortPipe, DecimalPipe],
  templateUrl: './popular-doctors.component.html',
  styleUrls: ['./popular-doctors.component.scss'],
  encapsulation: ViewEncapsulation.Emulated,
})
export class PopularDoctorsComponent implements OnInit {
  popularProfessionals: IPopularProfessionals[] = [];

  professionalService = inject(ProfessionalUserService);

  ngOnInit(): void {
    this.professionalService.getAllPopularProfessionals().subscribe((professionals) => {
      this.popularProfessionals = professionals;
      console.log('Popular Professionals:', this.popularProfessionals);
    });
  }
getProfileImageUrl(profilePic?: string | null): string {
  if (!profilePic) {
    return 'assets/images/users/user-dummy-img.jpg';
  }

  if (profilePic.startsWith('http')) {
    return profilePic;
  }

  return `${environment.apiUrl}/${profilePic}`;
}
}
