import { ChangeDetectionStrategy, ChangeDetectorRef, Component, ContentChildren, Input, OnDestroy, OnInit, QueryList, TemplateRef } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription, debounceTime, distinctUntilChanged } from 'rxjs';
import { Languages } from '../config';
import { TpTableCellTemplateDirective } from '../directives';
import { LanguageMap, PageChangedEvent, TableColumn, TableDataSource, TableOptions, TablePaging } from '../model';
import { TpTableService } from '../services/tp-table.service';

@Component({
  selector: 'tp-standard-table',
  templateUrl: './tp-standard-table.component.html',
  styleUrls: ['./tp-standard-table.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [TpTableService],
})
export class TpStandardTableComponent implements OnDestroy, OnInit {
  //-------------------------------
  // Private properties
  //-------------------------------
  private subscription = new Subscription();
  private _paging: TablePaging = {
    itemsPerPage: 10,
    itemsPerPageOptions: [10, 25, 50],
    maxSize: 5,
    showPaging: true,
  };

  //-------------------------------
  // Public properties
  //-------------------------------
  fullTextFilter = new FormControl('');
  language!: LanguageMap;
  showFilterRow = false;
  processing = false;
  rows = [];
  currentPage = 1;
  itemsPerPage = 10;
  recordsTotal = 0;

  //-------------------------------
  // Input properties
  //-------------------------------
  @Input() options: TableOptions = { language: 'en', showSearch: true, showFilter: true };
  @Input() set paging(value: TablePaging) {
    this._paging = value;
    this.itemsPerPage = value.itemsPerPage;
    this.service.setPaging(value.itemsPerPage);
  }
  @Input() set datasource(value: TableDataSource | unknown[]) {
    this.service.setDataSource(value);
  }
  @Input() set columns(value: TableColumn[]) {
    this.showFilterRow = value.some((c) => !!c.filter);
    this.service.setColumns(value);
  }

  get paging(): TablePaging {
    return this._paging;
  }
  get cols(): TableColumn[] {
    return this.service.columns;
  }
  get colsCount(): number {
    return this.service.columns.length;
  }
  get page() {
    return this.itemsPerPage;
  }
  set page(page: number) {
    this.itemsPerPage;
  }
  get pageSize() {
    return this.recordsTotal;
  }
  @ContentChildren(TpTableCellTemplateDirective)
  cellTemplates!: QueryList<TpTableCellTemplateDirective>;

  public constructor(private service: TpTableService, private cdRef: ChangeDetectorRef) {}

  ngOnInit() {
    this.language = (typeof this.options?.language === 'string' ? Languages[this.options?.language] : this.options?.language) as LanguageMap;
    if (!this.language) {
      this.language = Languages['en'];
    }

    this.subscription.add(
      this.fullTextFilter.valueChanges.pipe(debounceTime(300), distinctUntilChanged()).subscribe((filterValue) => {
        this.currentPage = 1;
        this.service.request({
          fullTextFilter: filterValue ?? undefined,
          page: 0,
          // start: 0,
        });
      }),
    );

    this.subscription.add(
      this.service.result$.subscribe((result) => {
        this.cdRef.markForCheck();
        this.rows = result.data as any;
        this.recordsTotal = result?.meta?.itemCount;
      }),
    );

    this.subscription.add(
      this.service.processing$.subscribe((result) => {
        this.cdRef.markForCheck();
        this.processing = result;
      }),
    );

    this.service.request({});
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  onPageChanged(event: PageChangedEvent) {
    this.currentPage = event.page;
    this.itemsPerPage = event.itemsPerPage;
    this.service.request({
      // start: (event.page - 1) * event.itemsPerPage,
      page: (event.page - 1) * event.itemsPerPage,
      // length: event.itemsPerPage,
      take: event.itemsPerPage,
    });
  }

  
  onToggleSort(col: TableColumn, shiftKey: boolean) {
    this.service.toggleSort(col, (shiftKey && this.options.orderMulti) as boolean);
  }

  onChangeFilter(col: TableColumn, filterValue: unknown) {
    this.service.changeFilter(col, filterValue);
  }

  getData(row: unknown, propertyName: string): string {
    if (propertyName) {
      return propertyName.split('.').reduce((prev: any, curr: string) => prev[curr], row) as string;
    }
    return '';
  }
  getContext(el: any) {
    return el as HTMLElement;
  }
  getCellTemplate(col: TableColumn, standardTemplate: TemplateRef<HTMLElement>): TemplateRef<HTMLElement> {
    if (!!col.template || !!col.name) {
      const templates = this.cellTemplates.filter((p) => p.tpTableCellTemplate === (col.template ? col.template : col.name));
      if (templates.length > 0) {
        return templates.map((p) => p.templateRef)[0];
      }
    }
    return standardTemplate;
  }

  get PaginationResult() {
    const x = this.language ? this.language['info'] : '';
    return this.service.interpolateLocalization(x as string, {
      start: (this.currentPage - 1) * this.itemsPerPage + 1,
      end: (this.currentPage - 1) * this.itemsPerPage + this.rows.length,
      // total: this.recordsTotal,
      total: this.recordsTotal,
    });
  }

  refresh() {
    this.service.request({});
  }
}
