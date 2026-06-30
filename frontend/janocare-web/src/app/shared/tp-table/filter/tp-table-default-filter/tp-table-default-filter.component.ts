import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription, debounceTime, distinctUntilChanged } from 'rxjs';
import { TableColumn, TableColumnFilterDefault } from '../../model';

@Component({
  selector: 'tp-table-default-filter',
  templateUrl: './tp-table-default-filter.component.html',
  styleUrls: ['./tp-table-default-filter.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TpTableDefaultFilterComponent implements OnInit, OnDestroy {
  private subscription = new Subscription();

  @Input() column!: TableColumn;
  @Output() filterValueChanged = new EventEmitter();

  filter = new FormControl('');
  config!: TableColumnFilterDefault;

  ngOnInit(): void {
    this.config = this.column?.filter?.config as TableColumnFilterDefault;

    this.subscription.add(
      this.filter.valueChanges.pipe(debounceTime(300), distinctUntilChanged()).subscribe((filterValue) => {
        this.filterValueChanged.emit(filterValue);
      }),
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
