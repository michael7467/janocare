import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({ providedIn: 'root' })
export class LanguageService {
  public languages: string[] = ['en', 'es', 'de', 'it', 'ru'];

  constructor(
    public translate: TranslateService,
    private cookieService: CookieService
  ) {
    this.translate.addLangs(this.languages);

    let browserLang: string = 'en';

    if (this.cookieService.check('lang')) {
      browserLang = this.cookieService.get('lang') || 'en';
    } else {
      browserLang = this.translate.getBrowserLang() || 'en';
    }

    const selectedLang = this.languages.includes(browserLang)
      ? browserLang
      : 'en';

    this.translate.use(selectedLang);
  }

  public setLanguage(lang: string) {
    const selectedLang = this.languages.includes(lang) ? lang : 'en';

    this.translate.use(selectedLang);
    this.cookieService.set('lang', selectedLang);
  }
}