import { EnumValues } from './enum-type';

export const WECARE_ROLE = {
  ADMIN: 'ADMIN',
  ACCOUNT_MANAGER: 'ACCOUNT_MANAGER',
  FINANCE: 'FINANCE',
  CUSTOMER_CARE: 'CUSTOMER_CARE',
  //   PROFESSIONAL: 'PROFESSIONAL',
} as const;
export const END_USER = {
  PATIENT: 'PATIENT',
} as const;
export const INSTITUTION = {
  I_ADMIN: 'I_ADMIN',
  I_USER: 'I_USER ',
  I_PROFESSIONAL: 'I_PROFESSIONAL ',
  I_ACCOUNT_MANAGER: 'I_ACCOUNT_MANAGER',
  I_FINANCE: 'I_FINANCE',
  I_RECEPTIONIST: 'I_RECEPTIONIST',
} as const;
export const ROLE = {
  ...WECARE_ROLE,
  ...END_USER,
  ...INSTITUTION,
} as const;
export type Role = EnumValues<typeof ROLE>;
export type WecareRole = EnumValues<typeof WECARE_ROLE>;
export type EndUserRole = EnumValues<typeof END_USER>;
export type InstitutionRole = EnumValues<typeof INSTITUTION>;
