import { ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { TableColumn, TableColumnFilterList, TableColumnFilterListItem } from '../../model';

@Component({
  selector: 'tp-table-list-filter',
  templateUrl: './tp-table-list-filter.component.html',
  styleUrls: ['./tp-table-list-filter.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TpTableListFilterComponent implements OnInit, OnDestroy {
  private subscription = new Subscription();

  @Input() column!: TableColumn;
  @Output() filterValueChanged = new EventEmitter();

  filter = new FormControl('');
  config!: TableColumnFilterList;
  items: TableColumnFilterListItem[] = [];

  constructor(private cdRef: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.config = this.column?.filter?.config as TableColumnFilterList;

    if (this.config.list instanceof Array) {
      this.items = this.config.list;
    } else {
      this.subscription.add(
        this.config.list.subscribe((items) => {
          this.cdRef.markForCheck();
          this.items = items;
        }),
      );
    }

    this.subscription.add(
      this.filter.valueChanges.subscribe((filterValue) => {
        this.filterValueChanged.emit(filterValue);
      }),
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
