/* eslint-disable @typescript-eslint/no-explicit-any */
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ApiResultFormat, DoctorDashboard, PageSelection } from '../../professional-shared/models/models';
import { routes } from '../../../../shared/routes/routes';
import { ProfessionalSharedModule } from '../../professional-shared/professional-shared.module';
import { CustomPaginationComponent } from '../../professional-shared/custom-pagination/custom-pagination.component';
import { PaginationHeaderComponent } from '../../professional-shared/pagination-header/pagination-header.component';

import { DataService } from '../../professional-shared/data/data.service';
import { PaginationService, tablePageSize } from '../../professional-shared/custom-pagination/pagination.service';
// import { Sort } from '@angular/material/sort';
import { NgbHighlight, NgbNavModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';


import { BreadcrumbsComponent, ImgFallbackDirective, RemovePortPipe } from '../../../../shared';
import { AuthService } from '../../../../core';
import { featuredModel, popularModel, recentModel, topCollectionModel } from './nft.model';
import { interval, map } from 'rxjs';
import { nftfeaturedData, nftpopularData, nftrecentData, nftstatData, nfttopCollectionData } from './dashboardnft';

import { CountUpModule } from 'ngx-countup';
import { FlatpickrModule } from 'angularx-flatpickr';
import { ActiveProjects, cryptoNewsFeed, projectstatData } from './dashboardProject';
import { ProjectsStatComponent } from '../../professional-shared/common/projects-stat/projects-stat.component';
import { ActiveProjectComponent } from '../../professional-shared/common/active-project/active-project.component';
import { NewsFeedComponent } from '../../professional-shared/common/news-feed/news-feed.component';
// import { BookingAppointmentService } from '../booking/booking-appointments/booking-appointment.service';
import { FeatherModule } from 'angular-feather';
// import { SettlmentTransactionService } from '../transaction/settlement-transactions/settlment-transaction.service';
// import { T } from '@angular/cdk/keycodes';
import { FormsModule } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { environment } from '../../../../../environments/environment.development';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  standalone: true,
  imports: [
    RouterLink,
    NgbPaginationModule,
    NewsFeedComponent,
    ActiveProjectComponent,
    NgbHighlight,
    ProjectsStatComponent,
    RemovePortPipe,
    FlatpickrModule,
    ImgFallbackDirective,
    FormsModule,
    AsyncPipe,

    FeatherModule,
    BreadcrumbsComponent,
    NgbNavModule,
    
    CustomPaginationComponent,
    PaginationHeaderComponent,
    ProfessionalSharedModule,
  ],
})
export class DashboardComponent implements OnInit {
  public routes = routes;
  breadCrumbItems!: Array<{}>;
  public tableData: Array<DoctorDashboard> = [];
  public tableData2: Array<DoctorDashboard> = [];
  upcomingData = []; // replace with your actual data
  todayData = []; // replace with your actual data
  activeTab = 'upcoming-appointments'; // default to 'upcoming'
  public pageSize = 10;
  public serialNumberArray: Array<number> = [];
  public totalData = 0;
  showFilter = false;
  invoices?: any;
  dataSource!: [];
  public searchDataValue = '';

  // bread crumb items
  ActiveProjects: any;
  statData!: any;
  featuredData!: featuredModel[];
  recentData!: recentModel[];
  topCollectionData!: topCollectionModel[];
  popularData!: popularModel[];
  NewsFeed: any;
  MarketplaceChart: any;
  popularityChart: any;
  minichart1: any;
  minichart2: any;
  minichart3: any;
  minichart4: any;
  minichart5: any;
  minichart6: any;
  minichart7: any;
  minichart8: any;

  totalJanAppt: number = 0;
  totalFebAppt: number = 0;
  totalMarAppt: number = 0;
  totalAprAppt: number = 0;
  totalMayAppt: number = 0;
  totalJunAppt: number = 0;
  totalJulAppt: number = 0;
  totalAugAppt: number = 0;
  totalSepAppt: number = 0;
  totalOctAppt: number = 0;
  totalNovAppt: number = 0;
  totalDecAppt: number = 0;

  totalJanApptOnline: number = 0;
  totalFebApptOnline: number = 0;
  totalMarApptOnline: number = 0;
  totalAprApptOnline: number = 0;
  totalMayApptOnline: number = 0;
  totalJunApptOnline: number = 0;
  totalJulApptOnline: number = 0;
  totalAugApptOnline: number = 0;
  totalSepApptOnline: number = 0;
  totalOctApptOnline: number = 0;
  totalNovApptOnline: number = 0;
  totalDecApptOnline: number = 0;

  // set the current year
  year: number = new Date().getFullYear();
  private _trialEndsAt: any;
  private _diff?: any;
  _days?: number;
  _hours?: number;
  _minutes?: number;
  _seconds?: number;

  private _selectedDate: Date;

  get selectedDate(): Date {
    return this._selectedDate;
  }

  set selectedDate(value: Date) {
    this._selectedDate = value;
    // this.service.bookingDate = this._selectedDate;
    // this.service.take = 5;
    // this.service.getBaseDataUpSchedule();
    console.log(this._selectedDate); // Log the selected date to the console
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
  constructor(
    private data: DataService,
    // public service: BookingAppointmentService,
    public auth: AuthService,
    private route: ActivatedRoute,
    private pagination: PaginationService,
    private router: Router,
    // public patientListsService: PatientListService,
    // public settlmentService: SettlmentTransactionService,
    // public blogService: BlogService,
    // public questionService: QuestionService,
  ) {}
  flatpickrOptions = {
    altInput: true,
    convertModelValue: true,
    inline: true,
    monthSelectorType: 'dropdown',
    defaultDate: null, // Set this to null
  };
  ngOnInit() {
    console.log(this.selectedDate);
    // this.service.bookingDate = new Date();
    // this.service.take = 5;
    // this.service.getBaseDataUpSchedule();

    // this.service.data$.subscribe((data) => {
    //   this.totalJanAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 0 && res.bookingType === 'IN_PERSON').length;
    //   this.totalFebAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 1 && res.bookingType === 'IN_PERSON').length;
    //   this.totalMarAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 2 && res.bookingType === 'IN_PERSON').length;
    //   this.totalAprAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 3 && res.bookingType === 'IN_PERSON').length;
    //   this.totalMayAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 4 && res.bookingType === 'IN_PERSON').length;
    //   this.totalJunAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 5 && res.bookingType === 'IN_PERSON').length;
    //   this.totalJulAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 6 && res.bookingType === 'IN_PERSON').length;
    //   this.totalAugAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 7 && res.bookingType === 'IN_PERSON').length;
    //   this.totalSepAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 8 && res.bookingType === 'IN_PERSON').length;
    //   this.totalOctAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 9 && res.bookingType === 'IN_PERSON').length;

    //   this.totalNovAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 10 && res.bookingType === 'IN_PERSON').length;
    //   this.totalDecAppt = data?.filter((res) => new Date(res.bookingDate).getMonth() === 11 && res.bookingType === 'IN_PERSON').length;

    //   this.totalJanApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 0 && res.bookingType === 'ONLINE').length;
    //   this.totalFebApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 1 && res.bookingType === 'ONLINE').length;
    //   this.totalMarApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 2 && res.bookingType === 'ONLINE').length;
    //   this.totalAprApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 3 && res.bookingType === 'ONLINE').length;
    //   this.totalMayApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 4 && res.bookingType === 'ONLINE').length;
    //   this.totalJunApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 5 && res.bookingType === 'ONLINE').length;
    //   this.totalJulApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 6 && res.bookingType === 'ONLINE').length;
    //   this.totalAugApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 7 && res.bookingType === 'ONLINE').length;
    //   this.totalSepApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 8 && res.bookingType === 'ONLINE').length;
    //   this.totalOctApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 9 && res.bookingType === 'ONLINE').length;
    //   this.totalNovApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 10 && res.bookingType === 'ONLINE').length;
    //   this.totalDecApptOnline = data?.filter((res) => new Date(res.bookingDate).getMonth() === 11 && res.bookingType === 'ONLINE').length;

    //   this._marketplaceChart('["--vz-primary","--vz-success", "--vz-light"]');
    // });

    // this.service.bookingDate = null;
    // this.service.take = 20;
    // this.service.getBaseData();

    // this.blogService.take = 5;
    // this.blogService.getBaseData();

    // this.questionService.take = 5;
    // this.questionService.getBaseData();

    // this.settlmentService.take = 20;
    // this.settlmentService.getBaseData();

    // this.patientListsService.getBaseData();
    this.breadCrumbItems = [{ label: 'Dashboards' }, { label: 'Dashboard', active: true }];
    /**
     * BreadCrumb
     */
    this.breadCrumbItems = [{ label: 'Dashboards' }, { label: 'NFT Dashboard', active: true }];

    /**
     * Fetches the data
     */
    this.fetchData();

    this._popularityChart('["--vz-success", "--vz-warning"]');
    this._minichart1Chart('["--vz-danger"]');
    this._minichartsuccessChart('["--vz-success"]');

    // Date Set
    this._trialEndsAt = '2023-12-31';

    /**
     * Count date set
     */
    interval(1000)
      .pipe(
        map((x) => {
          this._diff = Date.parse(this._trialEndsAt) - Date.parse(new Date().toString());
        }),
      )
      .subscribe((x) => {
        this._days = this.getDays(this._diff);
        this._hours = this.getHours(this._diff);
        this._minutes = this.getMinutes(this._diff);
        this._seconds = this.getSeconds(this._diff);
      });
  }

  num: number = 0;
  option = {
    startVal: this.num,
    useEasing: true,
    duration: 2,
    decimalPlaces: 2,
  };

  /**
   * Day Set
   */
  getDays(t: number) {
    return Math.floor(t / (1000 * 60 * 60 * 24));
  }

  /**
   * Hours Set
   */
  getHours(t: number) {
    return Math.floor((t / (1000 * 60 * 60)) % 24);
  }

  /**
   * Minutes set
   */
  getMinutes(t: number) {
    return Math.floor((t / 1000 / 60) % 60);
  }

  /**
   * Secound set
   */
  getSeconds(t: number) {
    return Math.floor((t / 1000) % 60);
  }
  getInPersonAndFormatCount(data: any): string {
    // let count = data?.filter((res) => {
    //   res.bookingType === 'IN_PERSON';
    // }).length;
    let count = data?.filter((res) => res.bookingType === 'IN_PERSON').length;

    if (count >= 1000) {
      return (count / 1000).toFixed(1) + 'k';
    } else {
      return count.toString();
    }
  }
  getOnlineAndFormatCount(data: any): string {
    // let count = data?.filter((res) => {
    //   res.bookingType === 'IN_PERSON';
    // }).length;
    let count = data?.filter((res) => res.bookingType === 'ONLINE').length;

    if (count >= 1000) {
      return (count / 1000).toFixed(1) + 'k';
    } else {
      return count.toString();
    }
  }
  shortenContent(content: string, maxLength: number): string {
    const div = document.createElement('div');
    div.innerHTML = content;
    const text = div.textContent || div.innerText || '';
    return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
  }
  formatCount(count: number): string {
    if (count >= 1000) {
      return (count / 1000).toFixed(1) + 'k';
    } else {
      return count.toString();
    }
  }
  scrollTo(sectionId: string): void {
    // const isMobile = window.innerWidth <= 768;
    const element = document.getElementById(sectionId);
    if (element) element.scrollIntoView({ block: 'center', behavior: 'smooth' });
  }
  setmarketplacevalue(value: any) {
    if (value == 'all') {
      this.MarketplaceChart.series = [
        {
          name: 'In Person',
          data: [
            this.totalJanAppt,
            this.totalFebAppt,
            this.totalMarAppt,
            this.totalAprAppt,
            this.totalMayAppt,
            this.totalJunAppt,
            this.totalJulAppt,
            this.totalAugAppt,
            this.totalSepAppt,
            this.totalOctAppt,
            this.totalNovAppt,
            this.totalDecAppt,
          ],
        },
        {
          name: 'Online',
          data: [
            this.totalJanApptOnline,
            this.totalFebApptOnline,
            this.totalMarApptOnline,
            this.totalAprApptOnline,
            this.totalMayApptOnline,
            this.totalJunApptOnline,
            this.totalJulApptOnline,
            this.totalAugApptOnline,
            this.totalSepApptOnline,
            this.totalOctApptOnline,
            this.totalNovApptOnline,
            this.totalDecApptOnline,
          ],
        },
        {
          name: 'Total',
          data: [
            this.totalJanAppt + this.totalJanApptOnline,
            this.totalFebAppt + this.totalFebApptOnline,
            this.totalMarAppt + this.totalMarApptOnline,
            this.totalAprAppt + this.totalAprApptOnline,
            this.totalMayAppt + this.totalMayApptOnline,
            this.totalJunAppt + this.totalJunApptOnline,
            this.totalJulAppt + this.totalJulApptOnline,
            this.totalAugAppt + this.totalAugApptOnline,
            this.totalSepAppt + this.totalSepApptOnline,
            this.totalOctAppt + this.totalOctApptOnline,
            this.totalNovAppt + this.totalNovApptOnline,
            this.totalDecAppt + this.totalDecApptOnline,
          ],
        },
      ];
    }
    if (value == '1M') {
      this.MarketplaceChart.series = [
        {
          name: 'In Person',
          data: [
            this.totalJanAppt,
            this.totalFebAppt,
            this.totalMarAppt,
            this.totalAprAppt,
            this.totalMayAppt,
            this.totalJunAppt,
            this.totalJulAppt,
            this.totalAugAppt,
            this.totalSepAppt,
            this.totalOctAppt,
            this.totalNovAppt,
            this.totalDecAppt,
          ],
        },
        {
          name: 'Online',
          data: [
            this.totalJanApptOnline,
            this.totalFebApptOnline,
            this.totalMarApptOnline,
            this.totalAprApptOnline,
            this.totalMayApptOnline,
            this.totalJunApptOnline,
            this.totalJulApptOnline,
            this.totalAugApptOnline,
            this.totalSepApptOnline,
            this.totalOctApptOnline,
            this.totalNovApptOnline,
            this.totalDecApptOnline,
          ],
        },
        {
          name: 'Total',
          data: [
            this.totalJanAppt + this.totalJanApptOnline,
            this.totalFebAppt + this.totalFebApptOnline,
            this.totalMarAppt + this.totalMarApptOnline,
            this.totalAprAppt + this.totalAprApptOnline,
            this.totalMayAppt + this.totalMayApptOnline,
            this.totalJunAppt + this.totalJunApptOnline,
            this.totalJulAppt + this.totalJulApptOnline,
            this.totalAugAppt + this.totalAugApptOnline,
            this.totalSepAppt + this.totalSepApptOnline,
            this.totalOctAppt + this.totalOctApptOnline,
            this.totalNovAppt + this.totalNovApptOnline,
            this.totalDecAppt + this.totalDecApptOnline,
          ],
        },
      ];
    }
    if (value == '6M') {
      this.MarketplaceChart.series = [
        {
          name: 'In Person',
          data: [
            this.totalJanAppt,
            this.totalFebAppt,
            this.totalMarAppt,
            this.totalAprAppt,
            this.totalMayAppt,
            this.totalJunAppt,
            this.totalJulAppt,
            this.totalAugAppt,
            this.totalSepAppt,
            this.totalOctAppt,
            this.totalNovAppt,
            this.totalDecAppt,
          ],
        },
        {
          name: 'Online',
          data: [
            this.totalJanApptOnline,
            this.totalFebApptOnline,
            this.totalMarApptOnline,
            this.totalAprApptOnline,
            this.totalMayApptOnline,
            this.totalJunApptOnline,
            this.totalJulApptOnline,
            this.totalAugApptOnline,
            this.totalSepApptOnline,
            this.totalOctApptOnline,
            this.totalNovApptOnline,
            this.totalDecApptOnline,
          ],
        },
        {
          name: 'Total',
          data: [
            this.totalJanAppt + this.totalJanApptOnline,
            this.totalFebAppt + this.totalFebApptOnline,
            this.totalMarAppt + this.totalMarApptOnline,
            this.totalAprAppt + this.totalAprApptOnline,
            this.totalMayAppt + this.totalMayApptOnline,
            this.totalJunAppt + this.totalJunApptOnline,
            this.totalJulAppt + this.totalJulApptOnline,
            this.totalAugAppt + this.totalAugApptOnline,
            this.totalSepAppt + this.totalSepApptOnline,
            this.totalOctAppt + this.totalOctApptOnline,
            this.totalNovAppt + this.totalNovApptOnline,
            this.totalDecAppt + this.totalDecApptOnline,
          ],
        },
      ];
    }
    if (value == '1Y') {
      this.MarketplaceChart.series = [
        {
          name: 'In Person',
          data: [
            this.totalJanAppt,
            this.totalFebAppt,
            this.totalMarAppt,
            this.totalAprAppt,
            this.totalMayAppt,
            this.totalJunAppt,
            this.totalJulAppt,
            this.totalAugAppt,
            this.totalSepAppt,
            this.totalOctAppt,
            this.totalNovAppt,
            this.totalDecAppt,
          ],
        },
        {
          name: 'Online',
          data: [
            this.totalJanApptOnline,
            this.totalFebApptOnline,
            this.totalMarApptOnline,
            this.totalAprApptOnline,
            this.totalMayApptOnline,
            this.totalJunApptOnline,
            this.totalJulApptOnline,
            this.totalAugApptOnline,
            this.totalSepApptOnline,
            this.totalOctApptOnline,
            this.totalNovApptOnline,
            this.totalDecApptOnline,
          ],
        },
        {
          name: 'Total',
          data: [
            this.totalJanAppt + this.totalJanApptOnline,
            this.totalFebAppt + this.totalFebApptOnline,
            this.totalMarAppt + this.totalMarApptOnline,
            this.totalAprAppt + this.totalAprApptOnline,
            this.totalMayAppt + this.totalMayApptOnline,
            this.totalJunAppt + this.totalJunApptOnline,
            this.totalJulAppt + this.totalJulApptOnline,
            this.totalAugAppt + this.totalAugApptOnline,
            this.totalSepAppt + this.totalSepApptOnline,
            this.totalOctAppt + this.totalOctApptOnline,
            this.totalNovAppt + this.totalNovApptOnline,
            this.totalDecAppt + this.totalDecApptOnline,
          ],
        },
      ];
    }
  }
  // Chart Colors Set
  private getChartColorsArray(colors: any) {
    colors = JSON.parse(colors);
    return colors.map(function (value: any) {
      var newValue = value.replace(' ', '');
      if (newValue.indexOf(',') === -1) {
        var color = getComputedStyle(document.documentElement).getPropertyValue(newValue);
        if (color) {
          color = color.replace(' ', '');
          return color;
        } else return newValue;
      } else {
        var val = value.split(',');
        if (val.length == 2) {
          var rgbaColor = getComputedStyle(document.documentElement).getPropertyValue(val[0]);
          rgbaColor = 'rgba(' + rgbaColor + ',' + val[1] + ')';
          return rgbaColor;
        } else {
          return newValue;
        }
      }
    });
  }
  private fetchData() {
    this.statData = projectstatData;
    this.ActiveProjects = ActiveProjects;
    this.featuredData = nftfeaturedData;
    this.recentData = nftrecentData;
    this.topCollectionData = nfttopCollectionData;
    this.popularData = nftpopularData;
    this.NewsFeed = cryptoNewsFeed;
  }

  private _marketplaceChart(colors: any) {
    colors = this.getChartColorsArray(colors);
    this.MarketplaceChart = {
      series: [
        {
          name: 'In Person',
          data: [
            this.totalJanAppt,
            this.totalFebAppt,
            this.totalMarAppt,
            this.totalAprAppt,
            this.totalMayAppt,
            this.totalJunAppt,
            this.totalJulAppt,
            this.totalAugAppt,
            this.totalSepAppt,
            this.totalOctAppt,
            this.totalNovAppt,
            this.totalDecAppt,
          ],
        },
        {
          name: 'Online',
          data: [
            this.totalJanApptOnline,
            this.totalFebApptOnline,
            this.totalMarApptOnline,
            this.totalAprApptOnline,
            this.totalMayApptOnline,
            this.totalJunApptOnline,
            this.totalJulApptOnline,
            this.totalAugApptOnline,
            this.totalSepApptOnline,
            this.totalOctApptOnline,
            this.totalNovApptOnline,
            this.totalDecApptOnline,
          ],
        },
        {
          name: 'Total',
          data: [
            this.totalJanAppt + this.totalJanApptOnline,
            this.totalFebAppt + this.totalFebApptOnline,
            this.totalMarAppt + this.totalMarApptOnline,
            this.totalAprAppt + this.totalAprApptOnline,
            this.totalMayAppt + this.totalMayApptOnline,
            this.totalJunAppt + this.totalJunApptOnline,
            this.totalJulAppt + this.totalJulApptOnline,
            this.totalAugAppt + this.totalAugApptOnline,
            this.totalSepAppt + this.totalSepApptOnline,
            this.totalOctAppt + this.totalOctApptOnline,
            this.totalNovAppt + this.totalNovApptOnline,
            this.totalDecAppt + this.totalDecApptOnline,
          ],
        },
      ],
      chart: {
        height: 350,
        type: 'line',
        zoom: {
          enabled: false,
        },
        toolbar: {
          show: false,
        },
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        curve: 'smooth',
        width: 3,
      },
      colors: colors,
      xaxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
      },
    };
  }

  /**
   * Market Place Chart
   */
  private _popularityChart(colors: any) {
    colors = this.getChartColorsArray(colors);
    this.popularityChart = {
      series: [
        {
          name: 'Like',
          data: [12.45, 16.2, 8.9, 11.42, 12.6, 18.1, 18.2, 14.16],
        },
        {
          name: 'Share',
          data: [-11.45, -15.42, -7.9, -12.42, -12.6, -18.1, -18.2, -14.16],
        },
      ],
      chart: {
        type: 'bar',
        height: 260,
        stacked: true,
        toolbar: {
          show: false,
        },
      },
      plotOptions: {
        bar: {
          columnWidth: '20%',
          borderRadius: [4, 4],
        },
      },
      colors: colors,
      fill: {
        opacity: 1,
      },
      dataLabels: {
        enabled: false,
        textAnchor: 'top',
      },
      yaxis: {
        labels: {
          show: false,
          formatter: function (y: any) {
            return y.toFixed(0) + '%';
          },
        },
      },
      legend: {
        position: 'top',
        horizontalAlign: 'right',
      },
      xaxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug'],
        labels: {
          rotate: -90,
        },
      },
    };
  }

  /**
   * Danger Mini Chart
   */
  private _minichart1Chart(colors: any) {
    colors = this.getChartColorsArray(colors);
    this.minichart1 = {
      series: [
        {
          data: [25, 66, 41, 89, 63, 25, 44, 12],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };

    // mini chart 2
    this.minichart2 = {
      series: [
        {
          data: [50, 15, 35, 62, 23, 56, 44, 12],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };

    // mini chart 3
    this.minichart3 = {
      series: [
        {
          data: [25, 35, 35, 89, 63, 25, 44, 12],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };

    // mini chart 6
    this.minichart6 = {
      series: [
        {
          data: [50, 15, 35, 62, 23, 56, 44, 12],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };

    // mini chart 8
    this.minichart8 = {
      series: [
        {
          data: [45, 53, 24, 89, 63, 60, 36, 50],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };
  }

  /**
   * Success Mini Chart
   */
  private _minichartsuccessChart(colors: any) {
    colors = this.getChartColorsArray(colors);
    this.minichart4 = {
      series: [
        {
          data: [50, 15, 20, 34, 23, 56, 65, 41],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };

    // mini chart 5
    this.minichart5 = {
      series: [
        {
          data: [45, 53, 24, 89, 63, 60, 36, 50],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };

    // mini chart 7
    this.minichart7 = {
      series: [
        {
          data: [50, 15, 20, 34, 23, 56, 65, 41],
        },
      ],
      chart: {
        type: 'line',
        width: 80,
        height: 30,
        sparkline: {
          enabled: true,
        },
      },
      colors: colors,
      stroke: {
        curve: 'smooth',
        width: 2.3,
      },
      tooltip: {
        fixed: {
          enabled: false,
        },
        x: {
          show: false,
        },
        y: {
          title: {
            formatter: function (seriesName: any) {
              return '';
            },
          },
        },
        marker: {
          show: false,
        },
      },
    };
  }
}
