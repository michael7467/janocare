import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'checkDate',
  standalone: true
})
export class CheckDatePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
