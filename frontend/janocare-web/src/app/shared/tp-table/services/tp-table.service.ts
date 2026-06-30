import { Injectable } from '@angular/core';
import { Subject, switchMap, tap } from 'rxjs';
import { defaultRequestParams } from '../config';
import { DatasourceFilter, DatasourceOrder, DatasourceParameters, TableColumn, TableDataSource } from '../model';
import { getNextSortOrder } from '../util';
import { TpTableLocalDataSource } from './tp-table-local-datasource';

@Injectable()
export class TpTableService {
  private localizationInterpolationMatcher = /{{\s?([^{}\s]*)\s?}}/g;
  private lastRequestParams = defaultRequestParams;

  private dataSource!: TableDataSource;
  private requestSubject$ = new Subject<DatasourceParameters>();
  private processingSubject$ = new Subject<boolean>();

  columns: TableColumn[] = [];
  sortStack: TableColumn[] = [];

  result$ = this.requestSubject$.pipe(
    tap(() => this.processingSubject$.next(true)),
    switchMap((request) => this.dataSource(request)),
    tap(() => this.processingSubject$.next(false)),
  );

  processing$ = this.processingSubject$.asObservable();

  setDataSource(datasource: TableDataSource | unknown[]) {
    this.dataSource = datasource instanceof Array ? new TpTableLocalDataSource(datasource).toDataSource() : datasource;
  }

  setPaging(take: number) {
    this.lastRequestParams = {
      ...this.lastRequestParams,
      // length: take,
      take,
    };
  }

  setColumns(columns: TableColumn[]): void {
    this.columns = columns;
    this.sortStack = [...this.columns.filter((c) => !!c.sortOrder)];
    const orders = this.buildDatasourceOrders();
    const filters = this.buildDatasourceFilter();
    this.lastRequestParams = { ...this.lastRequestParams, orders, filters };
  }

  request(parameters: Partial<DatasourceParameters>) {
    this.lastRequestParams = { ...this.lastRequestParams, ...parameters };
    this.requestSubject$.next(this.lastRequestParams);
  }

  toggleSort(column: TableColumn, orderMulti: boolean): void {
    column.sortOrder = getNextSortOrder(column.sortOrder);

    if (orderMulti) {
      const curIndex: number = this.sortStack.indexOf(column);
      if (curIndex === -1) {
        this.sortStack.push(column);
      } else if (!column.sortOrder) {
        this.sortStack.splice(curIndex, 1);
      }
    } else {
      this.sortStack = column.sortOrder ? [column] : [];
      this.columns.forEach((c) => {
        if (c !== column) c.sortOrder = null;
      });
    }

    const orders = this.buildDatasourceOrders();
    this.request({ orders });
  }
  private buildDatasourceOrders(): DatasourceOrder[] {
    return this.sortStack.map((column) => ({ dir: column.sortOrder, name: column.name } as DatasourceOrder));
  }

  changeFilter(column: TableColumn, filterValue: unknown) {
    column.filterValue = filterValue;
    const filters = this.buildDatasourceFilter();
    this.request({ filters });
  }

  private buildDatasourceFilter(): DatasourceFilter[] {
    return this.columns.filter((c) => !!c.filterValue).map((column) => ({ name: column.name, value: column.filterValue } as DatasourceFilter));
  }

  interpolateLocalization(str: string, params: { [x: string]: any; start?: number; end?: number; total?: number }) {
    return str.replace(this.localizationInterpolationMatcher, (substring: string, b: string) => {
      const r = params[b];
      return r ? r : substring;
    });
  }
}
