import { Pipe, PipeTransform } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Pipe({
  name: 'removePort',
  standalone: true,
})
export class RemovePortPipe implements PipeTransform {
  constructor(private location: Location) {}
  transform(value: string): string {
    let windowOrigin = window.location.origin + value;

    if (!value) return value;
    try {
      let url = new URL(window.location.origin + '/' + value);
      return url.protocol + '//' + url.hostname + url.pathname + url.search + url.hash;
    } catch (error) {
      console.error('Invalid URL:', value);
      return value;
    }
  }
}