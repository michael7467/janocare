import { NgClass } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import { NavigationStart, Router, Event as RouterEvent, RouterOutlet } from '@angular/router';
import { CommonService } from '../professional-shared/common/common.service';
import { Url } from '../professional-shared/models/models';
import { routes } from '../../../shared/routes/routes';

@Component({
  selector: 'app-admin',
  templateUrl: './pages.component.html',
  encapsulation: ViewEncapsulation.None,

})
export class PagesComponent {
  public routes = routes;
  public isUserLoggedIn = false;
  public sidebarActive = false;
  public headerActive = false;

  constructor(private common: CommonService, private router: Router) {
    router.events.subscribe((event: RouterEvent) => {
      if (event instanceof NavigationStart) {
        this.getRoutes(event);
      }
    });
    this.getRoutes(this.router);

  }

  public getRoutes(events: Url) {
    const splitVal = events.url.split('/');
    this.common.base.next(splitVal[1]);
    this.common.page.next(splitVal[2]);
    this.common.last.next(splitVal[3]);
    if (
      events.url.split('/')[2] === 'admin-login' ||
      events.url.split('/')[2] === 'admin-forgot-password' ||
      events.url.split('/')[2] === 'lock-screen' ||
      events.url.split('/')[2] === 'admin-register' ||
      events.url.split('/')[2] === 'errors'
    ) {
      this.sidebarActive = false;
      this.headerActive = false;
    } else {
      this.sidebarActive = true;
      this.headerActive = true;
    }
  }
}
