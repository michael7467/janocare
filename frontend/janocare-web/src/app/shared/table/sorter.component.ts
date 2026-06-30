import { Component, EventEmitter, Input, OnInit, Optional, Output } from '@angular/core';
import { ListComponent, SortEvent } from './list.component';
import { Order } from './order.model';

@Component({
  selector: 'app-sorter',
  template: `
  <a style="cursor: pointer; display: flex; justify-content: space-between; align-items: center;" (click)="sort()">
  <ng-content></ng-content>
  
  <span>
    <i *ngIf="!isSortedByMeAsc && !isSortedByMeDesc" class="bx bxs-sort-alt" aria-hidden="true"></i>
    <i *ngIf="isSortedByMeAsc" class="bx bx-caret-up" aria-hidden="true"></i>
    <i *ngIf="isSortedByMeDesc" class="bx bx-caret-down" aria-hidden="true"></i>
  </span>
</a>
  `,
})
export class SorterComponent implements OnInit {
  @Input() sortBy: string;
  @Output() sorted = new EventEmitter<any>();
  isSortedByMeAsc = false;
  isSortedByMeDesc = false;
   constructor(public table: ListComponent) {}

  public ngOnInit(): void {
    if (this.table) {
      this.table.onSortChange.subscribe((event: SortEvent) => {
        this.isSortedByMeAsc = event.column === this.sortBy && event.direction === Order.ASC;
        this.isSortedByMeDesc = event.column === this.sortBy && event.direction === Order.DESC;
      });
    }
  }

  sort(): void {
    if (this.isSortedByMeAsc) {
      this.table.setSort(this.sortBy, Order.DESC);
      this.sorted.emit({ sortColumn: this.sortBy, sortDirection: Order.DESC });
    } else {
      this.table.setSort(this.sortBy, Order.ASC);
      this.sorted.emit({ sortColumn: this.sortBy, sortDirection: Order.ASC });
    }
  }
}
