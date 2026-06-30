export class VerifyOtpRequest {
    identifier: string;
    otp: number;
  }
export class VerifyOtpResponse {
success: boolean;
statusCode: number;
message: string;
}