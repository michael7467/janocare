import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: 'img[appImgFallback]',
  standalone: true,
})
export class ImgFallbackDirective {
  constructor(private element: ElementRef) {}

  @Input('appImgFallback') fallback: string;

  @HostListener('error')
  displayFallbackImg() {
    this.element.nativeElement.src = this.fallback || 'assets/images/users/user-dummy-img.jpg';
  }
}
