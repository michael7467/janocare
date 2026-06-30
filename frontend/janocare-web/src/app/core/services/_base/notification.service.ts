import { Injectable, NgZone, TemplateRef, inject } from '@angular/core';

export interface IToastOpt {
  header: string;
  classname: string;
  autohide: boolean;
  animation: boolean;
  delay: number;
}
@Injectable({ providedIn: 'root' })
export class NotificationService {
  private zone = inject(NgZone);
  toasts: any[] = [];

  show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options, classname: 'bg-primary text-light' });
  }
  success(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options, classname: 'bg-success text-light' });
  }
  info(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options, classname: 'bg-info text-light' });
  }
  warn(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options, classname: 'bg-danger text-light' });
  }
  error(textOrTpl: string | TemplateRef<any>, options: any = {}) {
    this.toasts.push({ textOrTpl, ...options, classname: 'bg-danger text-light' });
  }
  showError(message: string): void {
    // Had an issue with the snackbar being ran outside of angular's zone.
    this.zone.run(() => {
      this.error(message);
    });
  }

  remove(toast: any) {
    this.toasts = this.toasts.filter((t) => t !== toast);
  }
}
