export class SetPasswordRequest {
    identifier: string;
    password: string;
    confirmPassword: string;
  }
export class SetPasswordResponse {
    access_token: string;
    success: boolean;
    statusCode: number;
    message: string;
}