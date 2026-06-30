import { LanguagesMap } from './tp-table-paging.interface';

export interface TableOptions {
  language?: string | LanguagesMap;
  orderMulti?: boolean;
  className?: string | string[];
  showSearch?: boolean;
  showFilter?: boolean;
}
