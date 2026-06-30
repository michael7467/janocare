export class routes {
 private static Url = '';
  static appoinment: string;
  static appoinment2: string;

  public static get baseUrl(): string {
    return this.Url;
  }

  // Professional routes starts
  public static get doctorBlog(): string {
    return this.baseUrl + '/blog';
  }
  public static get patientQuestion(): string {
    return this.baseUrl + '/question';
  }
  public static get professionalChat(): string {
    return this.baseUrl + '/chat';
  }
  public static get professionalVideoCall(): string {
    return this.baseUrl + '/video-call';
  }
  public static get professionalLogin(): string {
    return this.baseUrl + '/auth/login';
  }
  public static get professionalRegister(): string {
    return this.baseUrl + '/auth/register-professional';
  }
  public static get patientRegister(): string {
    return this.baseUrl + 'auth/register';
  }
  public static get professionalLockScreen(): string {
    return this.baseUrl + '/lock-screen';
  }
  public static get verify(): string {
    return this.baseUrl + '/verify';
  }
  public static get verifySuccess(): string {
    return this.baseUrl + '/auth/verify-success';
  }
  public static get professionalForgotPassword(): string {
    return this.baseUrl + '/professional-forgot-password';
  }
  public static get blankPage(): string {
    return this.baseUrl + '/blank-page';
  }
  public static get error404(): string {
    return this.baseUrl + '/errors/error404';
  }
  public static get error500(): string {
    return this.baseUrl + '/errors/error500';
  }
  public static get professionalDashboard(): string {
    return '/professionals/dashboard';
  }
  public static get professionalProfile(): string {
    return this.baseUrl + '/profile';
  }
  public static get signupSuccess(): string {
    return this.baseUrl + '/signup-success';
  }
  public static get doctorRegisterStep1(): string {
    return this.baseUrl + '/professional-register/doctor-register-step1';
  }
  public static get doctorRegisterStep2(): string {
    return this.baseUrl + '/professional-register/doctor-register-step2';
  }
  public static get doctorRegisterStep3(): string {
    return this.baseUrl + '/professional-register/doctor-register-step3';
  }
  public static get mobileOtp(): string {
    return this.baseUrl + '/mobile-otp';
  }
  public static get setPassword(): string {
    return this.baseUrl + '/auth/set-password';
  }

  // professional routes
  public static get personalInfo() {
    return this.baseUrl + '/professionals/personal-info';
  }
  public static get personalInfoSetting() {
    return this.baseUrl + '/professional/personal-info/setting';
  }
  public static get professionalInfo() {
    return this.baseUrl + '/professionals/professional-info';
  }
  public static get professionalAchievement() {
    return this.baseUrl + '/professional-achievement';
  }
  public static get professionalExperience() {
    return this.baseUrl + '/professional/professional-experience';
  }
  public static get professionalMembership() {
    return this.baseUrl + '/professional/professional-membership';
  }
  public static get professionalQualification() {
    return this.baseUrl + '/professional/professional-qualification';
  }
  public static get professionalRegistration() {
    return this.baseUrl + '/professional/professional-registration';
  }
  public static get professionalService() {
    return this.baseUrl + '/professional/professional-service';
  }
  public static get professionalSpecialization() {
    return this.baseUrl + '/professional/professional-specialization';
  }

  // Transactions
  public static get settlmentAccount() {
    return this.baseUrl + '/transaction/settlment-account';
  }
  public static get consultationTransactions() {
    return this.baseUrl + '/transaction/consultation-transactions';
  }
  public static get walletTransactions() {
    return this.baseUrl + '/transaction/wallet-transactions';
  }
  public static get settlmentTransactions() {
    return this.baseUrl + '/transaction/settlement-transactions';
  }

  // Booking
  public static get bookingSlots() {
    return this.baseUrl + '/professionals/bookings/booking-slots';
  }
  public static get bookingAppointments() {
    return this.baseUrl + '/professionals/bookings/appointments';
  }

  // Patients
  public static get patientsList() {
    return this.baseUrl + '/patients';
  }
  public static get patientDetails() {
    return this.baseUrl + '/professional/patients/patient-details';
  }
  public static get dependants() {
    return this.baseUrl + '/professional/patients/dependants';
  }
  public static get patientAllergy() {
    return this.baseUrl + '/professional/patients/patient-details/patient-allergies';
  }
  public static get patientMedicalCondition() {
    return this.baseUrl + '/professional/patients/patient-medical-conditions';
  }
  public static get patientMedication() {
    return this.baseUrl + '/professional/patients/patient-medications';
  }
  public static get patientSurgery() {
    return this.baseUrl + '/professional/patients/patient-surgeries';
  }
  public static get patientVital() {
    return this.baseUrl + '/professional/patients/patient-vitals';
  }
  public static get patientPrescription() {
    return this.baseUrl + '/professional/patients/patient-prescriptions';
  }
  public static get patientPermission() {
    return this.baseUrl + '/patients/patient-permission';
  }
  public static get patientMedicalRecords() {
    return this.baseUrl + '/professional/patients/patient-medical-record';
  }
  public static get patientMedicalRecordDetails() {
    return this.baseUrl + '/professional/patients/patient-medical-record-details';
  }

  // Reviews
  public static get patientReviews() {
    return this.baseUrl + '/reviews';
  }
 

  // Professional routes starts
  public static get patientBlog(): string {
    return this.baseUrl + '/blog';
  }
  // public static get patientQuestion(): string {
  //   return this.baseUrl + '/question';
  // }
  public static get patientChat(): string {
    return this.baseUrl + '/chat';
  }
  public static get patientVideoCall(): string {
    return this.baseUrl + '/video-call';
  }
  // public static get setPassword(): string {
  //   return this.baseUrl + '/auth/set-password';
  // }
  // public static get mobileOtp(): string {
  //   return this.baseUrl + '/mobile-otp';
  // }
  // public static get verify(): string {
  //   return this.baseUrl + '/auth/verify';
  // }
  // public static get verifySuccess(): string {
  //   return this.baseUrl + '/auth/verify-success';
  // }
  public static get patientLogin(): string {
    return this.baseUrl + '/auth/login';
  }
  // public static get patientRegister(): string {
  //   return this.baseUrl + '/auth/register';
  // }
  // public static get professionalLockScreen(): string {
  //   return this.baseUrl + '/lock-screen';
  // }
  // public static get professionalForgotPassword(): string {
  //   return this.baseUrl + '/patient-forgot-password';
  // }
  // public static get blankPage(): string {
  //   return this.baseUrl + '/blank-page';
  // }
  // public static get error404(): string {
  //   return this.baseUrl + '/errors/error404';
  // }
  // public static get error500(): string {
  //   return this.baseUrl + '/errors/error500';
  // }
  public static get patientDashboard(): string {
    return this.baseUrl + '/dashboard';
  }
  // public static get professionalProfile(): string {
  //   return this.baseUrl + '/profile';
  // }
  // public static get signupSuccess(): string {
  //   return this.baseUrl + '/signup-success';
  // }

  // Patient Favorites
  public static get favoriteClinics(): string {
    return this.baseUrl + '/favorite/clinics';
  }
  public static get favoriteDoctors(): string {
    return this.baseUrl + '/favorite/doctors';
  }
  public static get favoriteHospitals(): string {
    return this.baseUrl + '/favorite/hospitals';
  }
  public static get favoritePharmacies(): string {
    return this.baseUrl + '/favorite/pharmacies';
  }
  public static get favoriteLabs(): string {
    return this.baseUrl + '/favorite/labs';
  }

  // professional routes
  public static get patientDependants() {
    return this.baseUrl + '/dependants';
  }
  public static get permissionRequest() {
    return this.baseUrl + '/permission-request';
  }
  public static get patientPersonalInfo() {
    return this.baseUrl + '/personal-info';
  }
  public static get patientPersonalInfoSetting() {
    return this.baseUrl + '/personal-info/setting';
  }
  public static get searchDoctor() {
    return this.baseUrl + '/search-doctor';
  }
  public static get searchInstitution() {
    return this.baseUrl + '/search-institution';
  }
  public static get searchInstitutionProfessional() {
    return this.baseUrl + '/search-institution-professional';
  }
  public static get doctorSearchGrid() {
    return this.baseUrl + '/doctor-search-grid';
  }
  public static get bookingAppointment() {
    return this.baseUrl + '/booking-appointment';
  }
  public static get bookingOnlineAppointment() {
    return this.baseUrl + '/booking-online-appointment';
  }
  public static get doctorProfile() {
    return this.baseUrl + '/doctor-profile';
  }

  // Transactions
  // public static get consultationTransactions() {
  //   return this.baseUrl + '/transactions/consultation-transactions';
  // }
  public static get consultationTransactionsView() {
    return this.baseUrl + '/transactions/consultation-transactions-view';
  }
  // public static get walletTransactions() {
  //   return this.baseUrl + '/transactions/wallet-transactions';
  // }
  public static get cardransactions() {
    return this.baseUrl + '/transactions/card-transactions';
  }

  // notifications
  public static get appointemntNotifications() {
    return this.baseUrl + '/notifications/appointment';
  }
  public static get reminderNotifications() {
    return this.baseUrl + '/notifications/reminder';
  }
  public static get promotionNotifications() {
    return this.baseUrl + '/notifications/promotion';
  }

  // Booking
  // public static get bookingSlots() {
  //   return this.baseUrl + '/professional/booking/booking-slots';
  // }
  // public static get bookingAppointments() {
  //   return this.baseUrl + '/professional/booking/appointments';
  // }

  // // Patients
  // public static get patientsList() {
  //   return this.baseUrl + '/professional/patients/my-patients';
  // }
  // public static get patientDetails() {
  //   return this.baseUrl + '/professional/patients/patient-details';
  // }
  // public static get dependants() {
  //   return this.baseUrl + '/professional/patients/dependants';
  // }
  // public static get patientAllergy() {
  //   return this.baseUrl + '/allergy';
  // }
  // public static get patientMedicalCondition() {
  //   return this.baseUrl + '/medical-condition';
  // }
  // public static get patientMedication() {
  //   return this.baseUrl + '/medication';
  // }
  // public static get patientSurgery() {
  //   return this.baseUrl + '/surgery';
  // }
  public static get patientFamilyHistory() {
    return this.baseUrl + '/family-history';
  }
  // public static get patientVital() {
  //   return this.baseUrl + '/vital-sign';
  // }
  public static get patientInjury() {
    return this.baseUrl + '/injury';
  }
  public static get patientImmunization() {
    return this.baseUrl + '/immunization';
  }
  public static get patientLabTest() {
    return this.baseUrl + '/lab-test';
  }
  public static get patientLabResult() {
    return this.baseUrl + '/lab-result';
  }
  // public static get patientPrescription() {
  //   return this.baseUrl + '/prescription';
  // }
  public static get patientPrescriptionEdit() {
    return this.baseUrl + '/prescription/edit';
  }
  // public static get patientPermission() {
  //   return this.baseUrl + '/professional/patients/patient-permissions';
  // }
  // public static get patientMedicalRecords() {
  //   return this.baseUrl + '/medical-record';
  // }
  public static get patientMedicalRecordFile() {
    return this.baseUrl + '/medical-record-file';
  }
  public static get patientMedicalRecordImage() {
    return this.baseUrl + '/medical-record-image';
  }
  public static get patientMedicalRecordAudio() {
    return this.baseUrl + '/medical-record-audio';
  }
  public static get patientMedicalRecordVideo() {
    return this.baseUrl + '/medical-record-video';
  }
  // public static get patientMedicalRecordDetails() {
  //   return this.baseUrl + '/professional/patients/patient-medical-record-details';
  // }

  // Reviews
  // public static get patientReviews() {
  //   return this.baseUrl + '/professional/reviews';
  // }
}



