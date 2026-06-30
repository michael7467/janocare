import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

import { BehaviorSubject, EMPTY, Observable, catchError, delay, map, mapTo, mergeMap, of, switchMap, tap, throwError } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { InstitutionUser, LoginRequest, LoginResponse, RegisterRequest, RegisterResponse, TokensDto, User, UserRole } from '../../models';
import { ResourceService } from 'shared';
import { Router } from '@angular/router';
import { VerifyOtpRequest, VerifyOtpResponse } from '../../models/auth/verify-otp.model';
import { SetPasswordRequest, SetPasswordResponse } from '../../models/auth/set-password.model';
import { ISuccess, Role } from '../../models';

class AuthResponse {
  access_token: string;
  success: boolean;
  statusCode: number;
  message: string;
}
interface ApiResponse<T> {
  data: T[];
  message:string;
  success:boolean;
  statusCode:number;
  // other properties like meta, etc.
}


@Injectable({ providedIn: 'root' })
export class AuthService{

  private httpHeaders: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  
  private http = inject(HttpClient);
  private router = inject(Router);
  private _aToken = new BehaviorSubject<string>('');
  public aToken$: Observable<string> = this._aToken.asObservable();

  private _rToken = new BehaviorSubject<string>('');
  public rToken$: Observable<string> = this._rToken.asObservable();

  private _userPhoneNumber = new BehaviorSubject<string>('');
  public userPhoneNumber$: Observable<string> = this._userPhoneNumber.asObservable();

  private _userProfile = new BehaviorSubject<User | null>(null);
  public userProfile$: Observable<User | null> = this._userProfile.asObservable();
  // public isLoggedIn$: Observable<boolean> = this._userProfile.asObservable().pipe(map((u) => !!u));
  public isLoggedIn$: Observable<boolean> = this._userProfile.asObservable().pipe(
    switchMap((u) => {
      const isAuthed = !!u;
      return isAuthed
        ? of(isAuthed)
        : this.refreshAccessToken().pipe(
            map((t) => t && !!t.access_token),
            catchError((err) => of(false)),
          );
    }),
    map((u) => u),
  );
  //   menuItems: MenuItem[] = [];

  public hasAnyOftheRoles$ = (roles: Role[]): Observable<boolean> =>
    this._userProfile.asObservable().pipe(map((u) => (!u || !roles || !roles?.length ? false : roles.includes(u.role))));


  public uname: string;
  public password: string;

  get aToken() {
    return this._aToken.value;
  }
  get rToken() {
    return this._rToken.value;
  }
  get userProfile() {
    return this._userProfile.value;
  }
  get userPhoneNumber() {
    return this._userPhoneNumber.value;
  }
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }

  private getUserProfileApi(): Observable<User> {
    return this.http.get<ISuccess<User>>(`${environment.apiUrl}/web/auth/profile`).pipe(map((data) => data.data));
  }
  private navigateToLogin() {
    this.router.navigate(['/professional-login'], { queryParams: { returnUrl: window.location.pathname } });
  }
  private refreshApi(): Observable<TokensDto> {
    return this.http.post<any>(`${environment.apiUrl}/web/auth/refresh`, {}, { headers: this.httpHeaders, withCredentials: true }).pipe(map((refResp) => refResp));
  }
  verifyOtp(data: VerifyOtpRequest): Observable<VerifyOtpResponse> {
    return this.http.post<VerifyOtpResponse>(`${environment.apiUrl}/web/auth/verify-otp`, data).pipe((data) => data);
  }
  register(data: RegisterRequest): Observable<RegisterResponse> {
    this._userPhoneNumber.next(data.phone);
    return this.http.post<RegisterResponse>(`${environment.apiUrl}/web/auth/register`, data).pipe((data) => data);
  }

  resendOtp(identifier: string): Observable<VerifyOtpResponse> {
    return this.http.post<VerifyOtpResponse>(`/web/auth/resend-otp`, { identifier }).pipe((data) => data);
  }
  login(data: LoginRequest): Observable<LoginResponse> {
    this.uname = data.identifier;
    this.password = data.password;
    const authUrl = `${environment.apiUrl}/web/auth/login`;

    return this.http
      .post<LoginResponse>(authUrl, data, { headers: this.httpHeaders, withCredentials: true, observe: 'response' as 'response' })

      .pipe(
        map((result) => result.body),
        tap((d) => {
          if (d.success) {
            this._aToken.next(d.access_token);
          }
        }),
        mergeMap((data) => {
          if (data && data.success) {
            return this.getUserProfile().pipe(map((uProfile) => data));
          }
          return of(data);
        }),
    
        
      )
      .pipe(catchError(this.formatErrors));
  }
  // login(data: LoginRequest): Observable<LoginResponse> {
  //   this.uname = data.identifier;
  //   this.password = data.password;
  //   const authUrl = `${environment.apiUrl}/web/auth/login`;

  //   return this.http
  //     .post<LoginResponse>(authUrl, data, { headers: this.httpHeaders, withCredentials: true, observe: 'response' as 'response' })
  //     .pipe(
  //       map((result) => result.body),
  //       tap((d) => {
  //         if (d.success) {
  //           this._aToken.next(d.access_token);
  //         }
  //       }),
  //       mergeMap((data) => {
  //         if (data && data.success) {
  //           return this.getUserProfile().pipe(map((uProfile) => data));
  //         }
  //         return of(data);
  //       }),
  //     )
  //     .pipe(catchError(this.formatErrors));
  // }

  setNewPassword(identifier: string, previousPassword: string, password: string, confirmPassword: string): Observable<SetPasswordResponse> {
    // console.log(data);
    return this.http.post<SetPasswordResponse>(`${environment.apiUrl}/web/auth/set-password`, { identifier, previousPassword, password, confirmPassword }).pipe(
      map((result) => result),
      tap((d) => {
        if (d.success) {
          this._aToken.next(d.access_token);
        }
      }),
      mergeMap((data) => {
        if (data && data.success) {
          return this.getUserProfile().pipe(map((uProfile) => data));
        }
        return of(data);
      }),
      
    )
    .pipe(catchError(this.formatErrors))
  }

  logout() {
      return this.http.post(`/web/auth/logout`, {}).pipe(
        tap(() => {
          this._aToken.next('');
          this._userProfile.next(null);
          this.navigateToLogin();
        }),
        mapTo(true),
        catchError((error) => {
          // alert(error.error);
          this.navigateToLogin();
          return of(false);
        }),
      );
  }
  refreshAccessToken(): Observable<TokensDto> {
    return this.refreshApi().pipe(
      map((refResp) => {
        if (refResp && refResp.access_token) {
          this._aToken.next(refResp.access_token);
          console.log(refResp.access_token);
          console.log('refreshed access token');

          localStorage.setItem('authenticated-professional', JSON.stringify(refResp.access_token));
        }
        return refResp;
      }),
      mergeMap((refResp) => {
        if (refResp && refResp.access_token) {
          return this.getUserProfile().pipe(
            map((r) => {
              if (r) {
                this._userProfile.next(r);
              }
              return refResp;
            }),
          );
        } else {
          this.router.navigate(['/auth/login'], { queryParams: { returnUrl: window.location.pathname } });
          return EMPTY;
        }
      }),
    );
  }

  getUserProfile(): Observable<User> {
      return this.getUserProfileApi().pipe(
        map((data) => {
          console.log(data);
          this._userProfile.next(data);
          return data;
        }),
      );
    }
  autoLogin() {
    const token = localStorage.getItem('authenticated-professional');
    if (!token) {
      return;
    }
      
    this._aToken.next(token);
    }


    getUserInstitutionById(id:number): Observable<ApiResponse<InstitutionUser>> {
      return this.http.get<ApiResponse<InstitutionUser>>(`${environment.apiUrl}/institution-users/${id}`).pipe(map((data) => data));
    }
}