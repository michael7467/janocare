import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-doctor-detail-info',
  standalone: true,
  imports: [CommonModule, RouterModule, NgbModule],
  templateUrl: './doctor-detail-info.component.html',
  styleUrl: './doctor-detail-info.component.scss',
})
export class DoctorDetailInfoComponent {}
