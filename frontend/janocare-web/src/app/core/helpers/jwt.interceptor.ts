import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { BehaviorSubject, EMPTY, Observable, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { TokensDto } from '../models';
import { AuthService } from '../services';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private accTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  private authService: AuthService;
  constructor(private inject: Injector) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.authService = this.inject.get(AuthService);
    if (this.authService.aToken) {
      request = this.addToken(request, this.authService.aToken);
    }
    return next.handle(request).pipe(
      catchError((error) => {
       //set-password

        if (error instanceof HttpErrorResponse && error.status === 401 && !request.url.endsWith('verify-success') && !request.url.endsWith('set-password')  && !request.url.endsWith('verify')  && !request.url.endsWith('register')  && !request.url.endsWith('login') && !request.url.endsWith('refresh')) {
          return this.handle401Error(request, next);
        } else {
          return throwError(() => error);
        }
      }),
    );
  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.accTokenSubject.next(null);

      return this.authService.refreshAccessToken().pipe(
        switchMap((token: TokensDto) => {
          this.isRefreshing = false;
          if (token) {
            this.accTokenSubject.next(token.access_token);
            return next.handle(this.addToken(request, token.access_token));
          }
          return EMPTY;
        }),
      );
    } else {
      return this.accTokenSubject.pipe(
        filter((token) => token != null),
        take(1),
        switchMap((jwt) => {
          return next.handle(this.addToken(request, jwt));
        }),
      );
    }
  }
}
