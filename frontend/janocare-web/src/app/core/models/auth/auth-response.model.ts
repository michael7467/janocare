export interface IAuthResponse {
  success: boolean;
  statusCode: number;
  message: string;
  access_token?: string;
  refresh_token?: string;
  data?: { idenfifier?: string };
}
