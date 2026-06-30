import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ApiResultFormat } from '../models/models';
import { routes } from '../../../../shared/routes/routes';
import { MenuItem } from '../models/sidebar-model';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private http: HttpClient) {}
  public getDoctorDashboard1(): Observable<ApiResultFormat> {
    return this.http.get<ApiResultFormat>('assets/json/doctor-dashboard1.json').pipe(
      map((res: ApiResultFormat) => {
        return res;
      }),
    );
  }

  public getDoctorDashboard2(): Observable<ApiResultFormat> {
    return this.http.get<ApiResultFormat>('assets/json/doctor-dashboard2.json').pipe(
      map((res: ApiResultFormat) => {
        return res;
      }),
    );
  }


 public header=[
  
 ]


 public patientNewSidebar:MenuItem[] = [
  {
    id: 10,
    label: 'MENUITEMS.MENU.TEXT',
    isTitle: true,
  },
  {
    id: 20,
    label: 'MENUITEMS.DASHBOARD.TEXT',
    icon: 'ri-dashboard-2-line',
    link: routes.professionalDashboard,
  },
  {
    id: 22,
    label: 'MENUITEMS.BOOKINGSLOT.TEXT',
    icon: 'ri-time-line',
    link: routes.bookingSlots,
  },
  {
    id: 22,
    label: 'MENUITEMS.APPOINTMENT.TEXT',
    icon: 'ri-calendar-todo-fill',
    link: routes.bookingAppointments,
  },

  {
    id: 110,
    label: 'MENUITEMS.MANAGEPATIENTS.TEXT',
    icon: 'las la-user-friends',
    gname: ['PATIENT_USER'],
    subItems: [
      {
        id: 111,
        label: 'MENUITEMS.MANAGEPATIENTS.LIST.PATIENTLIST',
        link: routes.patientsList,
        parentId: 110,
        gname: ['PATIENT_USER'],
      },

      {
        id: 113,
        label: 'MENUITEMS.MANAGEPATIENTS.LIST.PATIENTSREVIEW',
        link: routes.patientReviews,
        parentId: 110,
        gname: ['PATIENT_USER'],
      },

    ],
  },
  {
    id: 75,
    label: 'MENUITEMS.TRANSACTIONS.TEXT',
    icon: 'ri-coin-line',
    gname: ['PATIENT_USER'],
    subItems: [
      {
        id: 77,
        label: 'MENUITEMS.TRANSACTIONS.LIST.SETTLMENTACCOUNT',
        link: routes.settlmentAccount,
        parentId: 75,
        gname: ['PATIENT_USER'],
      },
      {
        id: 76,
        label: 'MENUITEMS.TRANSACTIONS.LIST.CONSULTATIONTRANSACTION',
        link: routes.consultationTransactions,
        parentId: 75,
        gname: ['PATIENT_USER'],
      },
      {
        id: 79,
        label: 'MENUITEMS.TRANSACTIONS.LIST.SETTLMENTTRANSACTIONS',
        link: routes.settlmentTransactions,
        parentId: 75,
        gname: ['PATIENT_USER'],
      },
      {
        id: 77,
        label: 'MENUITEMS.TRANSACTIONS.LIST.WALLETTRANSACTIONS',
        link: routes.walletTransactions,
        parentId: 75,
        gname: ['PATIENT_USER'],
      },

    ],

  },


  // {
  //   id: 136,
  //   label: 'MENUITEMS.NOTIFICATIONS.TEXT',
  //   icon: 'las la-bell',
  //   link:'/activity-logs',
  //   gname: ['PATIENT_USER'],
  //   subItems: [
  //     {
  //       id: 111,
  //       label: 'MENUITEMS.NOTIFICATIONS.LIST.APPOINTMENTS',
  //       link: '/activity-logs',
  //       parentId: 136,
  //       gname: ['PATIENT_USER'],
  //     },
  //     {
  //       id: 111,
  //       label: 'MENUITEMS.NOTIFICATIONS.LIST.REMINDERS',
  //       link: '/api-logs',
  //       parentId: 136,
  //       gname: ['PATIENT_USER'],
  //     },
  //     {
  //       id: 111,
  //       label: 'MENUITEMS.NOTIFICATIONS.LIST.PROMOTIONS',
  //       link: '/activity-logs',
  //       parentId: 136,
  //       gname: ['PATIENT_USER'],
  //     },
  //   ],
  // },
  {
    id: 148,
    label: 'MENUITEMS.NOTIFICATION.TEXT',
    icon: 'ri-notification-line',
    link: routes.professionalChat,
    gname: ['PATIENT_USER'],
  },
  {
    id: 140,
    label: 'MENUITEMS.CHAT.TEXT',
    icon: 'ri-message-2-line',
    link: routes.professionalChat,
    gname: ['PATIENT_USER'],
  },
  {
    id: 140,
    label: 'MENUITEMS.MYBLOG.TEXT',
    icon: 'ri-file-list-line',
    link: routes.doctorBlog,
    gname: ['PATIENT_USER'],
  },
  {
    id: 140,
    label: 'MENUITEMS.QACONSULT.TEXT',
    icon: 'bx bx-question-mark',
    link: routes.patientQuestion,
    gname: ['PATIENT_USER'],
  },
  // {
  //   id: 30,
  //   label: 'MENUITEMS.PROFESSIONALINFO.TEXT',
  //   isTitle: true,
  // },
  
  // {
  //   id: 40,
  //   label: 'MENUITEMS.PROFESSIONALINFO.TEXT',
  //   link: routes.professionalInfo,
  //   icon: 'ri-calendar-line',
  //   gname: ['PATIENT_USER'],
  // },
  // {
  //   id: 70,
  //   label: 'MENUITEMS.MANAGETRANSACTION.TEXT',
  //   isTitle: true,
  // },

  // {
  //   id: 80,
  //   label: 'MENUITEMS.MANAGEBOOKING.TEXT',
  //   isTitle: true,
  // },
  {
    id: 82,
    label: 'MENUITEMS.REPORTS.TEXT',
    icon: 'ri-line-chart-line',
    gname: ['PATIENT_USER'],
    subItems: [
      {
        id: 83,
        label: 'MENUITEMS.REPORTS.LIST.DAILYREPORT',
        link: routes.bookingSlots,
        parentId: 82,
        gname: ['PATIENT_USER'],
      },
      {
        id: 83,
        label: 'MENUITEMS.REPORTS.LIST.MONTHLYREPORT',
        link: routes.bookingAppointments,
        parentId: 82,
        gname: ['PATIENT_USER'],
      },
    ],

  },


  // {
  //   id: 130,
  //   label: 'MENUITEMS.MANAGEPATIENTS.TEXT',
  //   isTitle: true,
  // },




  // {
  //   id: 137,
  //   label: 'MENUITEMS.DISCUSSIONS.TEXT',
  //   icon: 'ri-discuss-fill',
  //   gname: ['PATIENT_USER'],
  //   subItems: [
  //     {
  //       id: 111,
  //       label: 'MENUITEMS.DISCUSSIONS.LIST.POST',
  //       link: routes.doctorBlog,
  //       parentId: 137,
  //       gname: ['PATIENT_USER'],
  //     },
  //     {
  //       id: 111,
  //       label: 'MENUITEMS.DISCUSSIONS.LIST.QUESTIONS',
  //       link: routes.patientQuestion,
  //       parentId: 137,
  //       gname: ['PATIENT_USER'],
  //     },

 
  //   ],
  // },


  {
    id: 150,
    label: 'MENUITEMS.MYPROFILE.TEXT',
    icon: 'ri-user-line',
    link: routes.professionalInfo,
    gname: ['ADMIN', 'FINANCE', 'ACCOUNTS_MANAGER'],
  },
  {
    id: 120,
    label: 'MENUITEMS.SETTINGS.TEXT',
    link: routes.patientReviews,
    icon: 'ri-settings-2-line',

  },
];
  public professionalSidebar = [
    {
      tittle: 'Main',
      showAsTab: false,
      separateRoute: false,
      menu: [
        {
          menuValue: 'Dashboard',
          hasSubRoute: false,
          showSubRoute: false,
          route: routes.professionalDashboard,
          icon: 'fe fe-home',
        },
        {
          menuValue: 'Professional Info',
          hasSubRoute: false,
          showSubRoute: false,
          route: routes.professionalInfo,
          icon: 'fe fe-layout',
        },
        {
          menuValue: 'Transactions',
          hasSubRoute: true,
          showSubRoute: false,
          icon: 'fe fe-credit-card',
          subMenus: [
            {
              menuValue: 'Settlment Accounts',
              route: routes.settlmentAccount,
            },
            {
              menuValue: 'Consultation Transactions',
              route: routes.consultationTransactions,
            },
            {
              menuValue: 'Wallet Transactions',
              route: routes.walletTransactions,
            },
            {
              menuValue: 'Settlment Transactions',
              route: routes.settlmentTransactions,
            },
          ],
        },
        {
          menuValue: 'Manage Booking',
          hasSubRoute: true,
          showSubRoute: false,
          icon: 'fe fe-money',
          subMenus: [
            {
              menuValue: 'Booking Slots',
              route: routes.bookingSlots,
            },
            {
              menuValue: 'Appointments',
              route: routes.bookingAppointments,
            },
          ],
        },
        {
          menuValue: 'Manage Patients',
          hasSubRoute: true,
          showSubRoute: false,
          icon: 'fe fe-users',
          subMenus: [
            {
              menuValue: 'Patient List',
              route: routes.patientsList,
            },
            {
              menuValue: 'Dependants',
              route: routes.dependants,
            },
            {
              menuValue: 'Patient Permission',
              route: routes.patientPermission,
            },
            // {
            //   menuValue: 'Medical Records',
            //   route: routes.patientMedicalRecords,
            // },
            {
              menuValue: 'Patient Notes',
              route: 'routes.tablesBasic',
            },
            // {
            //   menuValue: 'Messages',
            //   route: 'routes.dataTables',
            // },
            // {
            //   menuValue: 'Reviews',
            //   route: 'routes.dataTables',
            // },
          ],
        },
        {
          menuValue: 'Reviews',
          hasSubRoute: false,
          showSubRoute: false,
          route: routes.patientReviews,
          icon: 'fe fe-star',
        },
        {
          menuValue: 'Notifications',
          hasSubRoute: true,
          showSubRoute: false,
          icon: 'fe fe-bell',
          subMenus: [
            {
              menuValue: 'Appointments',
              route: 'routes.tablesBasic',
            },
            {
              menuValue: 'Reminders',
              route: 'routes.dataTables',
            },
          ],
        },
        {
          menuValue: 'Message',
          hasSubRoute: false,
          showSubRoute: false,
          icon: 'fe fe-comments',
        },
        {
          menuValue: 'Discussions',
          hasSubRoute: true,
          showSubRoute: false,
          icon: 'fe fe-columns',
          subMenus: [
            {
              menuValue: 'My Posts',
              route: 'routes.tablesBasic',
            },
            {
              menuValue: 'My Answers',
              route: 'routes.dataTables',
            },
            {
              menuValue: 'Engagment Report',
              route: 'routes.tablesBasic',
            }
          ],
        },
        {
          menuValue: 'Activity Log',
          hasSubRoute: false,
          showSubRoute: false,
          icon: 'fe fe-activity',
        },
        {
          menuValue: 'Profile Setting',
          hasSubRoute: false,
          showSubRoute: false,
          route: routes.personalInfo,
          icon: 'fas fe-user-plus',
        },
      ],
    },
   
  ];
}
