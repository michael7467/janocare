import { Component } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule, NgFor } from '@angular/common';
import { QualificationService } from '../../professional-qualification/qualification.service';
import { ExperienceService } from '../../professional-experience/experience.service';
import { ProfAchievementService } from '../../professional-achievement/professional-achievement.service';
import { ServiceService } from '../../professional-service/service.service';

@Component({
  selector: 'app-professional-info-view',
  standalone: true,
  imports: [NgbDropdownModule,CommonModule,NgFor],
  templateUrl: './professional-info-view.component.html',
  styleUrls: ['./professional-info-view.component.scss']
})
export class ProfessionalInfoViewComponent {
constructor(
  public qualificationService:QualificationService,
  public experienceService:ExperienceService,
  public achievementService:ProfAchievementService,
  public service:ServiceService){}

ngOnInit(): void {
  this.qualificationService.getBaseData();
  this.experienceService.getBaseData();
  this.achievementService.getBaseData();
  this.service.getBaseData();
 }
}
