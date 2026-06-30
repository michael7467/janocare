export class LoginRequest {
    identifier: string;
    password: string;
  }
export class LoginResponse {
access_token: string;
success: boolean;
statusCode: number;
message: string;
}