import { Directive } from '@angular/core';
import { ReplaySubject } from 'rxjs';
import { Order } from './order.model';

export interface SortEvent {
  column: string
  direction: Order
}
@Directive()
export abstract class ListComponent {
  onSortChange: ReplaySubject<SortEvent> = new ReplaySubject<SortEvent>(1)
  abstract setSort(sortBy: string, sortOrder: Order): void
}

