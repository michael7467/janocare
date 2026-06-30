import { CommonModule, NgClass, NgFor, NgIf, TitleCasePipe } from '@angular/common';
import { Component, ElementRef, EventEmitter, inject, OnInit, Output, ViewChild } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

import 'simplebar';


import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { NgbDropdownModule, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { MenuItem } from '../../shared/models/sidebar-model';
import { routes } from '../../shared/routes/routes';
import { SidebarService } from '../../shared/sidebar/sidebar.service';
import { CommonService } from '../../shared/common/common.service';
import { DataService } from '../../shared/data/data.service';
import { AuthService } from '../../core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  standalone: true,
  imports: [
    NgbNavModule,
    NgbDropdownModule,
    TranslateModule,
    NgFor,
    CommonModule,
    NgIf,
    RouterLinkActive,
    RouterLink,
    NgClass,
    TitleCasePipe
  ],
})
export class SidebarComponent implements OnInit {
  menu: any;
  toggle: any = true;
  menuItems: MenuItem[] = [];
  @ViewChild('sideMenu') sideMenu!: ElementRef;
  @Output() mobileMenuButtonClicked = new EventEmitter();
  public routes = routes;
// private authService = inject(AuthService);
  constructor(
    private sidebar: SidebarService,
    public translate: TranslateService,
    private common: CommonService,
    private data: DataService,
    public authService:AuthService
  ) {
    translate.setDefaultLang('en');
  }

  ngOnInit(): void {
    this.menuItems = this.data.patientNewSidebar;
  }
getItems(role: string | null): MenuItem[] {

  const sidebarMap: Record<string, MenuItem[]> = {

    PROFESSIONAL: this.data.professionalSidebar,

    // ADMIN: this.data.adminSidebar,

    PATIENT: this.data.patientNewSidebar,

    // PATIENT_USER: this.data.patientNewSidebar,

  };

  return sidebarMap[role || ''] || this.data.patientNewSidebar;
}
  ngAfterViewInit() {
    this.initActiveMenu();
  }
    // When the user clicks on the button, scroll to the top of the document
    topFunction() {
      document.body.scrollTop = 0;
      document.documentElement.scrollTop = 0;
    }
    removeActivation(items: any) {
      items.forEach((item: any) => {
        if (item.classList.contains('menu-link')) {
          if (!item.classList.contains('active')) {
            item.setAttribute('aria-expanded', false);
          }
          item.nextElementSibling ? item.nextElementSibling.classList.remove('show') : null;
        }
        if (item.classList.contains('nav-link')) {
          if (item.nextElementSibling) {
            item.nextElementSibling.classList.remove('show');
          }
          item.setAttribute('aria-expanded', false);
        }
        item.classList.remove('active');
      });
    }
  
    toggleSubItem(event: any) {
      let isCurrentMenuId = event.target.closest('a.nav-link');
      let isMenu = isCurrentMenuId.nextElementSibling as any;
      let dropDowns = Array.from(document.querySelectorAll('.sub-menu'));
      dropDowns.forEach((node: any) => {
        node.classList.remove('show');
      });
  
      let subDropDowns = Array.from(document.querySelectorAll('.menu-dropdown .nav-link'));
      subDropDowns.forEach((submenu: any) => {
        submenu.setAttribute('aria-expanded', 'false');
      });
  
      if (event.target && event.target.nextElementSibling) {
        isCurrentMenuId.setAttribute('aria-expanded', 'true');
        event.target.nextElementSibling.classList.toggle('show');
      }
    }
  
    toggleExtraSubItem(event: any) {
      let isCurrentMenuId = event.target.closest('a.nav-link');
      let isMenu = isCurrentMenuId.nextElementSibling as any;
      let dropDowns = Array.from(document.querySelectorAll('.extra-sub-menu'));
      dropDowns.forEach((node: any) => {
        node.classList.remove('show');
      });
  
      let subDropDowns = Array.from(document.querySelectorAll('.menu-dropdown .nav-link'));
      subDropDowns.forEach((submenu: any) => {
        submenu.setAttribute('aria-expanded', 'false');
      });
  
      if (event.target && event.target.nextElementSibling) {
        isCurrentMenuId.setAttribute('aria-expanded', 'true');
        event.target.nextElementSibling.classList.toggle('show');
      }
    }
  
    // Click wise Parent active class add
    toggleParentItem(event: any) {
      let isCurrentMenuId = event.target.closest('a.nav-link');
      let dropDowns = Array.from(document.querySelectorAll('#navbar-nav .show'));
      dropDowns.forEach((node: any) => {
        node.classList.remove('show');
      });
      const ul = document.getElementById('navbar-nav');
      if (ul) {
        const iconItems = Array.from(ul.getElementsByTagName('a'));
        let activeIconItems = iconItems.filter((x: any) => x.classList.contains('active'));
        activeIconItems.forEach((item: any) => {
          item.setAttribute('aria-expanded', 'false');
          item.classList.remove('active');
        });
      }
      isCurrentMenuId.setAttribute('aria-expanded', 'true');
      if (isCurrentMenuId) {
        this.activateParentDropdown(isCurrentMenuId);
      }
    }
  
    toggleItem(event: any) {
      let isCurrentMenuId = event.target.closest('a.nav-link');
      let isMenu = isCurrentMenuId.nextElementSibling as any;
      if (isMenu.classList.contains('show')) {
        isMenu.classList.remove('show');
        isCurrentMenuId.setAttribute('aria-expanded', 'false');
      } else {
        let dropDowns = Array.from(document.querySelectorAll('#navbar-nav .show'));
        dropDowns.forEach((node: any) => {
          node.classList.remove('show');
        });
        isMenu ? isMenu.classList.add('show') : null;
        const ul = document.getElementById('navbar-nav');
        if (ul) {
          const iconItems = Array.from(ul.getElementsByTagName('a'));
          let activeIconItems = iconItems.filter((x: any) => x.classList.contains('active'));
          activeIconItems.forEach((item: any) => {
            item.setAttribute('aria-expanded', 'false');
            item.classList.remove('active');
          });
        }
        isCurrentMenuId.setAttribute('aria-expanded', 'true');
        if (isCurrentMenuId) {
          this.activateParentDropdown(isCurrentMenuId);
        }
      }
    }
  
    activateParentDropdown(item: any) {
      item.classList.add('active');
      let parentCollapseDiv = item.closest('.collapse.menu-dropdown');
  
      if (parentCollapseDiv) {
        // to set aria expand true remaining
        parentCollapseDiv.classList.add('show');
        parentCollapseDiv.parentElement.children[0].classList.add('active');
        parentCollapseDiv.parentElement.children[0].setAttribute('aria-expanded', 'true');
        if (parentCollapseDiv.parentElement.closest('.collapse.menu-dropdown')) {
          parentCollapseDiv.parentElement.closest('.collapse').classList.add('show');
          if (parentCollapseDiv.parentElement.closest('.collapse').previousElementSibling)
            parentCollapseDiv.parentElement.closest('.collapse').previousElementSibling.classList.add('active');
          if (parentCollapseDiv.parentElement.closest('.collapse').previousElementSibling.closest('.collapse')) {
            parentCollapseDiv.parentElement.closest('.collapse').previousElementSibling.closest('.collapse').classList.add('show');
            parentCollapseDiv.parentElement.closest('.collapse').previousElementSibling.closest('.collapse').previousElementSibling.classList.add('active');
          }
        }
        return false;
      }
      return false;
    }
  
    updateActive(event: any) {
      const ul = document.getElementById('navbar-nav');
      if (ul) {
        const items = Array.from(ul.querySelectorAll('a.nav-link'));
        this.removeActivation(items);
      }
      this.activateParentDropdown(event.target);
    }
  
    initActiveMenu() {
      const pathName = window.location.pathname;
      const ul = document.getElementById('navbar-nav');
      if (ul) {
        const items = Array.from(ul.querySelectorAll('a.nav-link'));
        let activeItems = items.filter((x: any) => x.classList.contains('active'));
        this.removeActivation(activeItems);
  
        let matchingMenuItem = items.find((x: any) => {
          return x.pathname === pathName;
        });
        if (matchingMenuItem) {
          this.activateParentDropdown(matchingMenuItem);
        }
      }
    }
  
    /**
     * Returns true or false if given menu item has child or not
     * @param item menuItem
     */
    hasItems(item: MenuItem) {
      return item.subItems !== undefined ? item.subItems.length > 0 : false;
    }
  
    /**
     * Toggle the menu bar when having mobile screen
     */
    toggleMobileMenu(event: any) {
      var sidebarsize = document.documentElement.getAttribute('data-sidebar-size');
      if (sidebarsize == 'sm-hover-active') {
        document.documentElement.setAttribute('data-sidebar-size', 'sm-hover');
      } else {
        document.documentElement.setAttribute('data-sidebar-size', 'sm-hover-active');
      }
    }
  
    /**
     * SidebarHide modal
     * @param content modal content
     */
    SidebarHide() {
      document.body.classList.remove('vertical-sidebar-enable');
    }
}
