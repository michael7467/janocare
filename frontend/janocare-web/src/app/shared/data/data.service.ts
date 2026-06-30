import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ApiResultFormat } from '../models/models';
import { routes } from '../routes/routes';
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

  public patientNewSidebar: MenuItem[] = [
    {
      id: 10,
      label: 'MENUITEMS.MENU.TEXT',
      isTitle: true,
    },
    {
      id: 20,
      label: 'MENUITEMS.DASHBOARD.TEXT',
      icon: 'ri-dashboard-2-line',
      link: routes.patientDashboard,
    },
        {
      id: 40,
      label: 'MENUITEMS.BOOKINGAPPOINTMENT.TEXT',
      link: routes.searchDoctor,
      icon: 'ri-calendar-line',
      gname: ['PATIENT_USER'],
    },
    {
      id: 41,
      label: 'MENUITEMS.OFFLINEAPPOINTMENT.TEXT',
      link: routes.searchInstitution,
      icon: 'ri-calendar-line',
      gname: ['PATIENT_USER'],
    },
    {
      id: 22,
      label: 'MENUITEMS.MYAPPOINTMENT.TEXT',
      icon: 'ri-calendar-todo-fill',
      link: routes.searchDoctor,
    },
      {
      id: 148,
      label: 'MENUITEMS.PRESCRIPTIONS.TEXT',
      icon: 'ri-calendar-todo-fill',
      link: routes.patientPrescription,
      gname: ['PATIENT_USER'],
    },
 
       {
      id: 75,
      label: 'MENUITEMS.TRANSACTIONS.TEXT',
      icon: 'ri-wallet-line',
      gname: ['PATIENT_USER'],
      subItems: [
        {
          id: 76,
          label: 'MENUITEMS.TRANSACTIONS.LIST.CONSULTATIONTRANSACTION',
          link: routes.consultationTransactions,
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
        {
          id: 77,
          label: 'MENUITEMS.TRANSACTIONS.LIST.CARDTRANSACTIONS',
          link: routes.cardransactions,
          parentId: 75,
          gname: ['PATIENT_USER'],
        },
      ],
    },
      {
      id: 110,
      label: 'MENUITEMS.MEDICALHISTORY.TEXT',
      icon: 'las la-notes-medical',
      gname: ['PATIENT_USER'],
      subItems: [
        {
          id: 111,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.ALLERGY',
          link: routes.patientAllergy,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },
        {
          id: 112,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.MEDICATIONS',
          link: routes.patientMedication,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },

        {
          id: 113,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.MEDICALCONDITIONS',
          link: routes.patientMedicalCondition,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },
        {
          id: 114,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.SURGERY',
          link: routes.patientSurgery,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },
        {
          id: 117,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.FAMILYHISTORY',
          link: routes.patientFamilyHistory,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },
        {
          id: 115,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.IMMUNIZATIONS',
          link: routes.patientImmunization,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },
        {
          id: 118,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.INJURY',
          link: routes.patientInjury,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },
        {
          id: 116,
          label: 'MENUITEMS.MEDICALHISTORY.LIST.VITALSIGNS',
          link: routes.patientVital,
          parentId: 110,
          gname: ['PATIENT_USER'],
        },
      ],
    },
    {
      id: 130,
      label: 'MENUITEMS.MEDICALRECORDS.TEXT',
      icon: 'las la-file-medical',
      gname: ['PATIENT_USER'],
      subItems: [
        {
          id: 111,
          label: 'MENUITEMS.MEDICALRECORDS.LIST.PRESCRIPTIONS',
          link: routes.patientPrescription,
          parentId: 130,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.MEDICALRECORDS.LIST.NOTES',
          link: routes.patientMedicalRecords,
          parentId: 130,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.MEDICALRECORDS.LIST.FILES',
          link: routes.patientMedicalRecordFile,
          parentId: 130,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.MEDICALRECORDS.LIST.IMAGES',
          link: routes.patientMedicalRecordImage,
          parentId: 130,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.MEDICALRECORDS.LIST.VIDEOS',
          link: routes.patientMedicalRecordVideo,
          parentId: 130,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.MEDICALRECORDS.LIST.AUDIOS',
          link: routes.patientMedicalRecordAudio,
          parentId: 130,
          gname: ['PATIENT_USER'],
        },
      ],
    },

  
    {
      id: 60,
      label: 'MENUITEMS.DEPENDANT.TEXT',

      icon: 'ri-group-line',
      link: routes.patientDependants,
      gname: ['PATIENT_USER'],
    },
    {
      id: 135,
      label: 'MENUITEMS.FAVORITES.TEXT',
      icon: 'las la-heart',
      gname: ['PATIENT_USER'],
      subItems: [
        {
          id: 111,
          label: 'MENUITEMS.FAVORITES.LIST.DOCTORS',
          link: routes.favoriteDoctors,
          parentId: 135,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.FAVORITES.LIST.HOSPITALS',
          link: routes.favoriteHospitals,
          parentId: 135,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.FAVORITES.LIST.CLINICS',
          link: routes.favoriteClinics,
          parentId: 135,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.FAVORITES.LIST.PHARMACIES',
          link: routes.favoritePharmacies,
          parentId: 135,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.FAVORITES.LIST.LABORATORIES',
          link: routes.favoriteLabs,
          parentId: 135,
          gname: ['PATIENT_USER'],
        },
      ],
    },
  
    {
      id: 120,
      label: 'MENUITEMS.LABORATORY.TEXT',
      icon: 'mdi mdi-test-tube',
      gname: ['PATIENT_USER'],
      subItems: [
        {
          id: 111,
          label: 'MENUITEMS.LABORATORY.LIST.LABTESTS',
          link: routes.patientLabTest,
          parentId: 120,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.LABORATORY.LIST.LABRESULTS',
          link: routes.patientLabResult,
          parentId: 120,
          gname: ['PATIENT_USER'],
        },
      ],
    },
    // {
    //   id: 30,
    //   label: 'MENUITEMS.BOOKING.TEXT',
    //   isTitle: true,
    // },

    // {
    //   id: 50,
    //   label: 'MENUITEMS.PAGE.TEXT',
    //   isTitle: true,
    // },

    {
      id: 60,
      label: 'MENUITEMS.PERMISSIONREQUEST.TEXT',
      link: routes.permissionRequest,
      icon: 'bx bx-git-pull-request',
      gname: ['PATIENT_USER'],
    },
    // {
    //   id: 70,
    //   label: 'MENUITEMS.MANAGETRANSACTION.TEXT',
    //   isTitle: true,
    // },

    // {
    //   id: 130,
    //   label: 'MENUITEMS.PATIENTDETAIL.TEXT',
    //   isTitle: true,
    // },

    // {
    //   id: 136,
    //   label: 'MENUITEMS.NOTIFICATIONS.TEXT',
    //   icon: 'las la-bell',
    //   gname: ['PATIENT_USER'],
    //   subItems: [
    //     {
    //       id: 111,
    //       label: 'MENUITEMS.NOTIFICATIONS.LIST.APPOINTMENTS',
    //       link: routes.appointemntNotifications,
    //       parentId: 136,
    //       gname: ['PATIENT_USER'],
    //     },
    //     {
    //       id: 111,
    //       label: 'MENUITEMS.NOTIFICATIONS.LIST.REMINDERS',
    //       link: routes.reminderNotifications,
    //       parentId: 136,
    //       gname: ['PATIENT_USER'],
    //     },
    //     {
    //       id: 111,
    //       label: 'MENUITEMS.NOTIFICATIONS.LIST.PROMOTIONS',
    //       link: routes.promotionNotifications,
    //       parentId: 136,
    //       gname: ['PATIENT_USER'],
    //     },
    //   ],
    // },
        {
      id: 140,
      label: 'MENUITEMS.CHAT.TEXT',
      icon: 'ri-message-2-line',
      link: routes.patientChat,
      gname: ['PATIENT_USER'],
    },
    {
      id: 148,
      label: 'MENUITEMS.NOTIFICATION.TEXT',
      icon: 'ri-notification-line',
      link: routes.reminderNotifications,
      gname: ['PATIENT_USER'],
    },
    {
      id: 137,
      label: 'MENUITEMS.DISCUSSIONS.TEXT',
      icon: 'ri-discuss-fill',
      gname: ['PATIENT_USER'],
      subItems: [
        {
          id: 111,
          label: 'MENUITEMS.DISCUSSIONS.LIST.MYQUESTIONS',
          link: routes.patientQuestion,
          parentId: 137,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.DISCUSSIONS.LIST.TOPICS',
          link: '/activity-logs',
          parentId: 137,
          gname: ['PATIENT_USER'],
        },
        {
          id: 111,
          label: 'MENUITEMS.DISCUSSIONS.LIST.POST',
          link: routes.patientBlog,
          parentId: 137,
          gname: ['PATIENT_USER'],
        },
      ],
    },

    {
      id: 150,
      label: 'MENUITEMS.MYPROFILE.TEXT',
      icon: 'ri-user-settings-line',
      link: routes.patientPersonalInfo,
      gname: ['ADMIN', 'FINANCE', 'ACCOUNTS_MANAGER'],
    },
  ];

public professionalSidebar: MenuItem[] = [
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
    id: 30,
    label: 'MENUITEMS.PROFESSIONALINFO.TEXT',
    icon: 'ri-user-heart-line',
    link: routes.professionalInfo,
  },
  {
    id: 40,
    label: 'MENUITEMS.MANAGEBOOKINGS.TEXT',
    icon: 'ri-calendar-check-line',
    subItems: [
      {
        id: 41,
        label: 'MENUITEMS.BOOKINGSLOT.TEXT',
        link: routes.bookingSlots,
        parentId: 40,
      },
      {
        id: 42,
        label: 'MENUITEMS.APPOINTMENT.TEXT',
        link: routes.bookingAppointments,
        parentId: 40,
      },
    ],
  },
  {
    id: 50,
    label: 'MENUITEMS.MANAGEPATIENTS.TEXT',
    icon: 'las la-user-friends',
    subItems: [
      {
        id: 51,
        label: 'MENUITEMS.MANAGEPATIENTS.LIST.PATIENTLIST',
        link: routes.patientsList,
        parentId: 50,
      },
      {
        id: 52,
        label: 'Dependants',
        link: routes.dependants,
        parentId: 50,
      },
      {
        id: 53,
        label: 'Patient Permission',
        link: routes.patientPermission,
        parentId: 50,
      },
      {
        id: 54,
        label: 'Patient Notes',
        link: routes.patientsList,
        parentId: 50,
      },
    ],
  },
  {
    id: 60,
    label: 'MENUITEMS.TRANSACTIONS.TEXT',
    icon: 'ri-coin-line',
    subItems: [
      {
        id: 61,
        label: 'MENUITEMS.TRANSACTIONS.LIST.SETTLMENTACCOUNT',
        link: routes.settlmentAccount,
        parentId: 60,
      },
      {
        id: 62,
        label: 'MENUITEMS.TRANSACTIONS.LIST.CONSULTATIONTRANSACTION',
        link: routes.consultationTransactions,
        parentId: 60,
      },
      {
        id: 63,
        label: 'MENUITEMS.TRANSACTIONS.LIST.WALLETTRANSACTIONS',
        link: routes.walletTransactions,
        parentId: 60,
      },
      {
        id: 64,
        label: 'MENUITEMS.TRANSACTIONS.LIST.SETTLMENTTRANSACTIONS',
        link: routes.settlmentTransactions,
        parentId: 60,
      },
    ],
  },
  {
    id: 70,
    label: 'MENUITEMS.REVIEWS.TEXT',
    icon: 'ri-star-line',
    link: routes.patientReviews,
  },
  {
    id: 80,
    label: 'MENUITEMS.NOTIFICATION.TEXT',
    icon: 'ri-notification-line',
    link: routes.professionalChat,
  },
  {
    id: 90,
    label: 'MENUITEMS.CHAT.TEXT',
    icon: 'ri-message-2-line',
    link: routes.professionalChat,
  },
  {
    id: 100,
    label: 'MENUITEMS.DISCUSSIONS.TEXT',
    icon: 'ri-discuss-line',
    subItems: [
      {
        id: 101,
        label: 'MENUITEMS.MYBLOG.TEXT',
        link: routes.doctorBlog,
        parentId: 100,
      },
      {
        id: 102,
        label: 'MENUITEMS.QACONSULT.TEXT',
        link: routes.patientQuestion,
        parentId: 100,
      },
    ],
  },
  {
    id: 110,
    label: 'MENUITEMS.ACTIVITYLOGS.TEXT',
    icon: 'ri-history-line',
    link: routes.professionalDashboard,
  },
  {
    id: 120,
    label: 'MENUITEMS.SETTINGS.TEXT',
    icon: 'ri-settings-2-line',
    link: routes.professionalInfo,
  },
];

}
