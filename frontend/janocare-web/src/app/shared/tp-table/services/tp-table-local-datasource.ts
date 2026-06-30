import { of } from 'rxjs';
import { DatasourceParameters, TableDataSource } from '../model';

export function getItemValues(item: unknown): string {
  return Object.entries(item as object)
    .map((i) => i[1])
    .reduce((str, val) => {
      return `${str}${typeof val === 'object' ? getItemValues(val) : val + ';'}`;
    }, '');
}

function defaultTextFilter(value: unknown, search: unknown): boolean {
  return (value as string)
    ?.toString()
    ?.toLowerCase()
    ?.includes(search?.toString()?.toLowerCase() as string);
}

function getData(item: unknown, propName: string): string {
  if (propName) {
    return (propName as string)?.split('.')?.reduce((prev: unknown, curr: string) => prev[curr], item as object);
  }
  return '';
}

export class TpTableLocalDataSource {
  private data: unknown[] = [];

  constructor(data: unknown[]) {
    this.data = data;
  }

  toDataSource(): TableDataSource {
    return (request: DatasourceParameters) => {
      let filteredData = this.data;
      const page = request.page / request.take + 1;

      filteredData = filteredData.filter((item) => {
        return defaultTextFilter(getItemValues(item), request.fullTextFilter);
      });

      request.filters
        .filter((c) => !!c.value)
        .forEach((column) => {
          filteredData = filteredData.filter((item) => {
            const value = getData(item, column.name);
            return defaultTextFilter(value, column.value);
          });
        });

      [...request.orders].reverse().forEach((column) => {
        const dir = column.dir === 'asc' ? 1 : -1;

        const compare = (direction: number, a: unknown, b: unknown) => {
          if (a < b) {
            return -1 * direction;
          }
          if (a > b) {
            return direction;
          }
          return 0;
        };

        filteredData = filteredData.sort((a, b) => {
          return compare(dir, getData(a, column.name), getData(b, column.name));
        });
      });
      const meta = { itemCount: this.data.length };
      return of({
        meta,
        recordsTotal: this.data.length,
        recordsFiltered: filteredData.length,
        // data: filteredData.slice(request.length * (page - 1), request.length * page),
        data: filteredData.slice(request.take * (page - 1), request.take * page),
      });
    };
  }
}
