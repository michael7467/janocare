import { Component, EventEmitter, Inject, Output, TemplateRef, ViewChild, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

import { AuthService, EventService, LanguageService } from '../../core';

import { CommonModule, DOCUMENT, NgClass, NgFor, NgIf, TitleCasePipe } from '@angular/common';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { CookieService } from 'ngx-cookie-service';
import { NgbCollapseModule, NgbDropdownModule, NgbModal, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';


import { routes } from '../../shared/routes/routes';
import { ImgFallbackDirective, RemovePortPipe } from '../../shared';
import { allNotification, cartData, messages } from './data';
import { CartModel } from './topbar.model';
import { SimplebarAngularModule } from 'simplebar-angular';
import { environment } from '../../../environments/environment.development';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  standalone: true,
   imports: [RouterLink,NgbCollapseModule,RemovePortPipe,ImgFallbackDirective,SimplebarAngularModule,NgbNavModule,NgbDropdownModule,CommonModule],
  // imports: [NgScrollbar,NgbNavModule,NgbDropdownModule,SimplebarAngularModule,TranslateModule,NgFor,CommonModule, NgIf, RouterLinkActive, RouterLink, NgClass, TitleCasePipe],
})
export class HeaderComponent {
  element: any;
  mode: string | undefined;
  @Output() mobileMenuButtonClicked = new EventEmitter();
  allnotifications: any
  total = 0;
  messages: any
  cart_length: any = 0;
  totalNotify: number = 0;
  flagvalue: any;
  cartData!: CartModel[];
  valueset: any;
  countryName: any;
  cookieValue: any;
  userData: any;
  listLang = [{ text: 'English', flag: 'assets/images/flags/us.svg', lang: 'en' }];
  @ViewChild('removenotification') removenotification !: TemplateRef<any>;
  notifyId: any;
  public routes = routes;
  public miniSidebar = false;
  private authService = inject(AuthService);
  constructor(
    @Inject(DOCUMENT) private document: any,
    private eventService: EventService,
    public languageService: LanguageService,
    public _cookiesService: CookieService,
    public translate: TranslateService,
    public auth: AuthService,
    private modalService: NgbModal,
    private router: Router,
  ) {}
  // constructor(public router: Router, private sidebar: SidebarService) {
  //   this.sidebar.toggleSideBar.subscribe((res: string) => {
  //     if (res == 'true') {
  //       this.miniSidebar = true;
  //     } else {
  //       this.miniSidebar = false;
  //     }
  //   });
  // }

  // public miniSideBarMouseHover(position: string): void {
  //   if (position == 'over') {
  //     this.sidebar.expandSideBar.next(true);
  //   } else {
  //     this.sidebar.expandSideBar.next(false);
  //   }
  // }
  // public toggleAdminSideBar(): void {
  //   this.sidebar.switchAdminSideMenuPosition();
  // }
  // public toggleAdminMobileSideBar(): void {
  //   this.sidebar.switchAdminMobileSideBarPosition();
  // }
  // logOut() {

  //   this.authService.logout().subscribe((res) => {
  //     this.router.navigate([routes.professionalLogin]);
  //   });
  // }
  checkedValGet: any[] = [];
  ngOnInit(): void {
    this.element = document.documentElement;
 // Fetch Data
 this.allnotifications = allNotification;

 this.messages = messages;
 this.cartData = cartData;
    // Cookies wise Language set
    this.cookieValue = this._cookiesService.get('lang');
    const val = this.listLang.filter((x) => x.lang === this.cookieValue);
    this.countryName = val.map((element) => element.text);
    if (val.length === 0) {
      if (this.flagvalue === undefined) {
        this.valueset = 'assets/images/flags/us.svg';
      }
    } else {
      this.flagvalue = val.map((element) => element.flag);
    }
  }
getProfileImageUrl(profilePic?: string | null): string {
  if (!profilePic) {
    return 'assets/images/users/user-dummy-img.jpg';
  }

  if (profilePic.startsWith('http')) {
    return profilePic;
  }

  return `${environment.apiUrl}/${profilePic}`;
}
  openModal(content: any) {
    // this.submitted = false;
    this.modalService.open(content, { centered: true });
  }
  toggleMobileMenu(event: any) {
    event.preventDefault();
    this.mobileMenuButtonClicked.emit();
  }

  /**
   * Fullscreen method
   */
  fullscreen() {
    document.body.classList.toggle('fullscreen-enable');
    if (!document.fullscreenElement && !this.element.mozFullScreenElement && !this.element.webkitFullscreenElement) {
      if (this.element.requestFullscreen) {
        this.element.requestFullscreen();
      } else if (this.element.mozRequestFullScreen) {
        /* Firefox */
        this.element.mozRequestFullScreen();
      } else if (this.element.webkitRequestFullscreen) {
        /* Chrome, Safari and Opera */
        this.element.webkitRequestFullscreen();
      } else if (this.element.msRequestFullscreen) {
        /* IE/Edge */
        this.element.msRequestFullscreen();
      }
    } else {
      if (this.document.exitFullscreen) {
        this.document.exitFullscreen();
      } else if (this.document.mozCancelFullScreen) {
        /* Firefox */
        this.document.mozCancelFullScreen();
      } else if (this.document.webkitExitFullscreen) {
        /* Chrome, Safari and Opera */
        this.document.webkitExitFullscreen();
      } else if (this.document.msExitFullscreen) {
        /* IE/Edge */
        this.document.msExitFullscreen();
      }
    }
  }

  /**
   * Topbar Light-Dark Mode Change
   */
  changeMode(mode: string) {
    this.mode = mode;
    this.eventService.broadcast('changeMode', mode);
 
    switch (mode) {
      case 'light':
        document.documentElement.setAttribute('data-bs-theme', "light");
        document.body.setAttribute('data-layout-mode', 'light');
        document.body.setAttribute('data-sidebar', 'light');
        break;
      case 'dark':
        document.documentElement.setAttribute('data-bs-theme', "dark");
        document.body.setAttribute('data-layout-mode', 'dark');
        document.body.setAttribute('data-sidebar', 'dark');
        break;
      default:
        document.documentElement.setAttribute('data-bs-theme', "light");
        document.body.setAttribute('data-layout-mode', 'light');
        break;
    }
  }

  setLanguage(text: string, lang: string, flag: string) {
    this.countryName = text;
    this.flagvalue = flag;
    this.cookieValue = lang;
    this.languageService.setLanguage(lang);
  }

  logout() {
    this.auth.logout().subscribe();

  }

  windowScroll() {
    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
      (document.getElementById('back-to-top') as HTMLElement).style.display = 'block';
    } else {
      (document.getElementById('back-to-top') as HTMLElement).style.display = 'none';
    }
  }

  onCheckboxChange(event: any, id: any) {
    this.notifyId = id
    var result;
    if (id == '1') {
      var checkedVal: any[] = [];
      for (var i = 0; i < this.allnotifications.length; i++) {
        if (this.allnotifications[i].state == true) {
          result = this.allnotifications[i].id;
          checkedVal.push(result);
        }
      }
      this.checkedValGet = checkedVal;
    } else {
      var checkedVal: any[] = [];
      for (var i = 0; i < this.messages.length; i++) {
        if (this.messages[i].state == true) {
          result = this.messages[i].id;
          checkedVal.push(result);
        }
      }
      console.log(checkedVal)
      this.checkedValGet = checkedVal;
    }
    checkedVal.length > 0 ? (document.getElementById("notification-actions") as HTMLElement).style.display = 'block' : (document.getElementById("notification-actions") as HTMLElement).style.display = 'none';
  }

  notificationDelete() {
    if (this.notifyId == '1') {
      for (var i = 0; i < this.checkedValGet.length; i++) {
        for (var j = 0; j < this.allnotifications.length; j++) {
          if (this.allnotifications[j].id == this.checkedValGet[i]) {
            this.allnotifications.splice(j, 1)
          }
        }
      }
    } else {
      for (var i = 0; i < this.checkedValGet.length; i++) {
        for (var j = 0; j < this.messages.length; j++) {
          if (this.messages[j].id == this.checkedValGet[i]) {
            this.messages.splice(j, 1)
          }
        }
      }
    }
    this.calculatenotification()
    this.modalService.dismissAll();
  }

  calculatenotification() {
    this.totalNotify = 0;
    this.checkedValGet = []

    this.checkedValGet.length > 0 ? (document.getElementById("notification-actions") as HTMLElement).style.display = 'block' : (document.getElementById("notification-actions") as HTMLElement).style.display = 'none';
    if (this.totalNotify == 0) {
      document.querySelector('.empty-notification-elem')?.classList.remove('d-none')
    }
  }
}
