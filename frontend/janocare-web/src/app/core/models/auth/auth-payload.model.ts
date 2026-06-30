export interface IVerifyOtpPayload {
  identifier: string;
  otp: number;
}
export interface IResetPasswordPayload {
  userId: number;
}
export interface IEndUserRegisterPayload {
  fullName: string;
  phone?: string;
  email?: string;
}
export interface ITelegramAuthPayload {
  id: number;
  first_name: string;
  username: string;
  auth_date: number;
  hash: string;
}
