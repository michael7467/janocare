import { CommonModule } from '@angular/common';
import { Component, OnInit, Input } from '@angular/core';
import { CountUpModule } from 'ngx-countup';
import { FeatherModule } from 'angular-feather';

@Component({
  standalone:true,
  selector: 'app-projects-stat',
  imports: [CommonModule,CountUpModule, FeatherModule],
  templateUrl: './projects-stat.component.html',
  styleUrls: ['./projects-stat.component.scss']
})

/**
 * Projects Stat Component
 * 
 */
export class ProjectsStatComponent implements OnInit {

  @Input() title: string | undefined;
  @Input() value: any | undefined;
  @Input() icon: string | undefined;
  @Input() persantage: string | undefined;
  @Input() profit: string | undefined;
  @Input() month: string | undefined;

  constructor() { }

  ngOnInit(): void {
  }
}
