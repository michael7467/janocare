export enum Order {
  ASC = 'ASC',
  DESC = 'DESC',
}

export interface IPageOption {
  order?: Order;
  page: number;
  take: number;
  q?: string;
}

export interface IResultMeta {
  page: number;
  take: number;
  itemCount: number;
  pageCount: number;
  hasPreviousPage: boolean;
  hasNextPage: boolean;
}

export class ISuccess<T> {
  success: boolean;
  message: string;
  statusCode: number;
  data: T;
}
