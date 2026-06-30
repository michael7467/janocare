import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, EMPTY, Observable, of, throwError } from 'rxjs';
import { catchError, map, mapTo, mergeMap, switchMap, tap } from 'rxjs/operators';
import { environment } from '../../../../environments/environment.development';
import { IAuthResponse, IResetPasswordPayload, ISuccess, IVerifyOtpPayload, LoginRequest, LoginResponse, RegisterRequest, RegisterResponse, Role, TokensDto, User, UserProfile } from '../../models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor() {}
  private httpHeaders: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  private http = inject(HttpClient);
  private router = inject(Router);

  private _aToken = new BehaviorSubject<string>('');
  public aToken$: Observable<string> = this._aToken.asObservable();

  private _rToken = new BehaviorSubject<string>('');
  public rToken$: Observable<string> = this._rToken.asObservable();

  private _userPhoneNumber = new BehaviorSubject<string>('');
  public userPhoneNumber$: Observable<string> = this._userPhoneNumber.asObservable();

  private _userFullName = new BehaviorSubject<string>('');
  public userFullName$: Observable<string> = this._userFullName.asObservable();

  private _userProfile = new BehaviorSubject<User | null>(null);
  public userProfile$: Observable<User | null> = this._userProfile.asObservable();

  private _userProfileProfile = new BehaviorSubject<UserProfile | null>(null);
  public userProfileProfile$: Observable<UserProfile | null> = this._userProfileProfile.asObservable();

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

  public hasAnyOftheRoles$ = (roles: Role[]): Observable<boolean> =>
    this._userProfile.asObservable().pipe(map((u) => (!u || !roles || !roles?.length ? false : roles.includes(u.role))));

  //   menuItems: MenuItem[] = [];

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
  get userProfileProfile() {
    return this._userProfileProfile.value;
  }
  get userPhoneNumber() {
    return this._userPhoneNumber.value;
  }
  get userFullName() {
    return this._userFullName.value;
  }
  //   private getAllowed = (menuItems: MenuItem[], role: string): MenuItem[] =>
  //     menuItems.reduce((r, o) => {
  //       if ((!o.gname || o.gname.includes(role)) && !o.subItems) {
  //         r.push(o);
  //       } else if (o.subItems) {
  //         let subItems = this.getAllowed(o.subItems, role);
  //         if (subItems.length) r.push({ ...o, subItems });
  //       }
  //       return r;
  //     }, [] as MenuItem[]);


  private getUserProfileApi(): Observable<User> {
    return this.http.get<ISuccess<User>>(`${environment.apiUrl}/profile/me`).pipe(map((data) => data.data));
  }
  private navigateToLogin() {
    this.router.navigate(['auth/login'], { queryParams: { returnUrl: window.location.pathname } });
  }
  private refreshApi(): Observable<TokensDto> {
    return this.http.post<any>(`${environment.apiUrl}/auth/refresh`, {}, { headers: this.httpHeaders, withCredentials: true }).pipe(map((refResp) => refResp));
  }
  register(data: RegisterRequest): Observable<RegisterResponse> {
    this._userPhoneNumber.next(data.phone);
    console.log(data);
    return this.http.post<RegisterResponse>(`${environment.apiUrl}/auth/register`, data,{ headers: this.httpHeaders, withCredentials: true }).pipe((data) => data);
  }
  /// MENU PERM---
  //   public get() {
  //     return this.userProfile$.pipe(
  //       map((u) => {
  //         if (u && u.role) {
  //           return this.getAllowed(MENU, u.role);
  //         } else {
  //           return [];
  //         }
  //       }),
  //     );
  //   }
  private formatErrors(error: any) {
    return throwError(() => error?.error || error);
  }
  login(identifier: string, password: string): Observable<LoginResponse> {
    this.uname = identifier;
    this.password = password;
    const authUrl = `${environment.apiUrl}/auth/login`;

    return this.http
      .post<LoginResponse>(authUrl, {identifier,password}, { headers: this.httpHeaders, withCredentials: true, observe: 'response' as 'response' })
      .pipe(
        map((result) => result.body),
        tap((d) => {
          if (d.success) {
            this._aToken.next(d.access_token);
            localStorage.removeItem('access_token');
            localStorage.setItem('access_token', d.access_token);
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


  verifyOtp(data: IVerifyOtpPayload): Observable<ISuccess<IAuthResponse>> {
    return this.http.post<ISuccess<IAuthResponse>>(`${environment.apiUrl}/otp/verify`, data,{ headers: this.httpHeaders, withCredentials: true }).pipe((data) => data);
    // return this.http.post(`${environment.apiUrl}${path}`, JSON.stringify(body), { headers: this.httpHeaders, withCredentials: true }).pipe(catchError(this.formatErrors));
  }
  //   // data = {identifier:'xxxx'}
  resendOtp(identifier: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/resend-otp`, { identifier },{ headers: this.httpHeaders, withCredentials: true }).pipe((data) => data);
  }
  // data = {identifier:'xxxx'}
  forgotPassword(identifier: string): Observable<IAuthResponse> {
    return this.http.post<IAuthResponse>(`${environment.apiUrl}/auth/forgot-password`, { identifier }).pipe((data) => data);
  }

  resetPassword(data: IResetPasswordPayload): Observable<IAuthResponse> {
    return this.http.post<IAuthResponse>(`${environment.apiUrl}/auth/reset-password`, data).pipe((data) => data);
  }

  setNewPassword(identifier: string, previousPassword: string, password: string, confirmPassword: string): Observable<IAuthResponse> {
    return this.http.post<IAuthResponse>(`${environment.apiUrl}/password/set`, { identifier, previousPassword, password, confirmPassword }).pipe(
      tap((d) => {
        if (d.success) {
          this.password = password;
          this._aToken.next(d.access_token);
        }
      }),
      // mergeMap((data) => {
      //   if (data && data.success) {
      //     return this.getUserProfile().pipe(map((uProfile) => data));
      //   }
      //   return of(data);
      // }),
      map((data) => data),
    );
  }
  changeMyPassword(identifier: string, previousPassword: string, newPassword: string, confirmPassword: string): Observable<IAuthResponse> {
    return this.http.post<IAuthResponse>(`${environment.apiUrl}/auth/change-my-password`, { identifier, previousPassword, newPassword, confirmPassword }).pipe(
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
    );
  }

  logout() {
    return this.http.post(`${environment.apiUrl}/auth/logout`, {}, { withCredentials: true, observe: 'response' as 'response' }).pipe(
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

  refreshTokenOnInit() {
    return this.refreshApi().pipe(
      map((tokens) => {
        this._aToken.next(tokens.access_token || '');
        localStorage.setItem('access_token', tokens.access_token);
        return tokens;
      }),
      switchMap((tokens) => {
        return this.getUserProfileApi();
      }),
      map((user) => {
        this._userProfile.next(user);
        // this.menuItems = this.getAllowed(MENU, user.role);
        return user;
      }),
      catchError((err) => {
        this.router.navigate(['/auth/login'], { queryParams: { returnUrl: window.location.pathname } });
        return EMPTY;
      }),
    );
  }

  refreshAccessToken(): Observable<TokensDto> {
    return this.refreshApi().pipe(
      map((refResp) => {
        if (refResp && refResp.access_token) {
          this._aToken.next(refResp.access_token);
          localStorage.removeItem('access_token');
          localStorage.setItem('access_token', refResp.access_token);
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
        this._userProfile.next(data);
        this._userProfileProfile.next(data?.userProfile);
        this._userPhoneNumber.next(data?.phone);
        this._userFullName.next(data?.userProfile?.firstName + ' '+ data?.userProfile?.lastName);
        // this.menuItems = this.getAllowed(MENU, data.role);
        return data;
      }),
    );
  }

  requestGoogleRedirectUri(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/auth/google/uri`);
  }
  googleSignIn(code: string): Observable<any> {
    return this.http.post(`${environment.apiUrl}/auth/google/signin`, { code });
  }
  public updateUserProfilePic(user: User, file?: File): Observable<UserProfile> {
    const formData = new FormData();
    console.log(file);
    if (file) {
      formData.append('profile_picture', file,file.name);
    }
    console.log(formData);
    return this.http.put<UserProfile>(`${environment.apiUrl}/profile/me/profile-picture`, formData).pipe(map((result) => result));
  }
  public updateUserProfile(resource: Partial<UserProfile> & { toJson: () => UserProfile }, file?: File): Observable<UserProfile> {
   
    return this.http.put<UserProfile>(`${environment.apiUrl}/profile/me`, resource).pipe(map((result) => result));
  }
  

}
