export type AzpType = 'ss-customer-app' | 'ss-manager-app' | 'ss-customer-web' | 'ss-manager-web' | 'ss-customer-api';
export class TokenPayload {
  exp: number;
  iat: number;
  typ: string; // Bearer | Refresh
  iss: string; // "http://localhost:8080/auth/realms/tp_admin"
  azp: AzpType; // ss-customer-app | ss-manager-app | ss-customer-web | ss-manager-web | ss-customer-api
  sub: string; // idpid
  role: string; // idpid
  realm: string; // idpid
}
export class RefreshTokenPayload extends TokenPayload {}
export class AccessTokenPayload extends TokenPayload {
  name: string;
  phone: string;
  email: string;
  uid: number;
  cid: number;
  pcid: number;
}
export class AccessTokenDto {
  accessToken: string;
}
export class RefreshTokenDto {
  refreshToken: string;
}

export class TokensDto {
  access_token?: string;
  refresh_token?: string;
}
