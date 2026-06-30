export interface PageSelection {
  skip: number;
  limit: number;
}
export interface ApiResultFormat {
  data: [];
  totalData: number;
}
export interface Url {
  url: string;
}

// User pages start

export interface DoctorDashboard {
  id: number;
  patientId: string;
  patientName: string;
  apptDate: string;
  purpose: string;
  type: string;
  paidAmount: string;
  action1: string;
  action2: string;
  action3: string;
  img: string;
  apptTime: string;
}


export interface PatientProfile {
  id: number;
  doctor: string;
  appDate: number;
  appTime: number;
  specfication: string;
  bookingDate: string;
  specification: string;
  fbcstatus: number;
  amount: number;
  followup: string;
  status: string;
  date: string;
  name: string;
  createdBy: number;
  description: string;
  attachement: string;
  created: string;
  invoiceNo: string;
  doctorimg: string;
  paidOn: string;
  orderDate: string;
  orderTime: string;
  action1: string;
  action2: string;
  action3: string;
  img: string;
}

