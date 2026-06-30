import { Observable } from 'rxjs';
import { FILTER_CONTROL_TYPE, FILTER_DEFAULT_CONTROL_TYPE, SORT_ORDER } from './tp-table.types';

export interface TablePaging {
  itemsPerPageOptions: number[];
  itemsPerPage: number;
  maxSize: number;
  showPaging: boolean;
}

export interface TableDataSource {
  (request: DatasourceParameters): Observable<DatasourceResult>;
}

export interface TableColumn {
  name?: string;
  title?: string;
  width?: number | string;
  sort?: boolean;
  sortOrder?: SORT_ORDER;
  filter?: TableColumnFilter;
  filterValue?: unknown;
  template?: string;
}

export interface TableColumnFilter {
  controlType: FILTER_CONTROL_TYPE;
  config?: TableColumnFilterDefault | TableColumnFilterList;
}

export interface TableColumnFilterDefault {
  placeholder?: string;
  type?: FILTER_DEFAULT_CONTROL_TYPE;
  max?: number | Date;
  min?: number | Date;
  step?: number;
}

export interface TableColumnFilterList {
  nullText?: string;
  list: Observable<TableColumnFilterListItem[]> | TableColumnFilterListItem[];
}

export interface TableColumnFilterListItem {
  value: string | number | symbol;
  text: string;
}

// export interface DatasourceParameters_ {
//   start: number;
//   length: number;
//   orders: DatasourceOrder[];
//   filters: DatasourceFilter[];
//   fullTextFilter: string;
// }
export interface DatasourceParameters {
  page?: number; // requested page index (starts at 1)
  take?: number; // number of rows on the page

  orders?: DatasourceOrder[];
  filters?: DatasourceFilter[];
  fullTextFilter?: string;
}

export interface DatasourceFilter {
  name: string;
  value: unknown;
}

export interface DatasourceOrder {
  name: string;
  dir: SORT_ORDER;
}

// export interface DatasourceResult_<T = unknown> {
//   recordsTotal: number;
//   // recordsFiltered: number;
//   data: T[];
// }
export interface DatasourceMeta {
  // page?: number; // requested page index (starts at 0)
  // take?: number; // number of rows on the page
  //
  itemCount: number; // total number of rows in the table
  // pageCount?: number; // total number of pages in the table
  hasPreviousPage?: boolean; // is there a previous page
  hasNextPage?: boolean; // is there a next page
}
export interface DatasourceResult<T = unknown> {
  // Status
  success?: boolean;
  message?: string;
  statusCode?: number;

  // Page Meta
  meta: DatasourceMeta;

  // Data
  data: T[];
}

export interface LanguageMap {
  [key: string]: string | LanguageMap;
}

export interface LanguagesMap {
  [culture: string]: LanguageMap;
}

export interface PageChangedEvent {
  itemsPerPage: number;
  page: number;
}

export interface Page {
  text: string;
  number: number;
  active: boolean;
}
