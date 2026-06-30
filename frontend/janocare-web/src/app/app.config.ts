import { ApplicationConfig, ErrorHandler, importProvidersFrom } from '@angular/core';
import { Router, UrlHandlingStrategy, provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { BrowserModule } from '@angular/platform-browser';

import { provideAnimations } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { GlobalErrorHandler, JwtInterceptor } from './core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { FlatpickrModule } from 'angularx-flatpickr';
// import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { allIcons } from 'angular-feather/icons';
import { FeatherModule } from 'angular-feather';
import { NgxEchartsModule } from 'ngx-echarts';

export function createTranslateLoader(http: HttpClient): any {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),

    importProvidersFrom(
      BrowserModule,
      FlatpickrModule.forRoot(),
      // CKEditorModule,
      NgxEchartsModule.forRoot({
        echarts: () => import('echarts'),
      }),
      FeatherModule.pick(allIcons),
      TranslateModule.forRoot({
        defaultLanguage: 'en',
        loader: {
          provide: TranslateLoader,
          useFactory: createTranslateLoader,
          deps: [HttpClient],
        },
      }),
    ),
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()),

    { provide: ErrorHandler, useClass: GlobalErrorHandler },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },

    // { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor },
  ],
};
