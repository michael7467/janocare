import { DatasourceParameters } from '../model';

const page = 0;
const take = 10;
const filters = [] as any[];
const orders = [] as any[];
const fullTextFilter = '';

export const defaultRequestParams: DatasourceParameters = {
  page,
  take,
  filters,
  orders,
  fullTextFilter,
};
