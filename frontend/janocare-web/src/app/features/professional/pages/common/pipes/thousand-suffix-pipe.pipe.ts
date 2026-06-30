import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'thousandSuffixPipe',
  standalone: true,
})
export class ThousandSuffixPipePipe implements PipeTransform {
  transform(value: number): string {
    if (value >= 1000) {
      return (value / 1000).toFixed(1) + 'K';
    }
    return value.toString();
  }
}
