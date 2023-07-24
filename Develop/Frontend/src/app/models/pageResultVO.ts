export class PageResultVO<T> {
  totalPage: number;
  totalElement: number;
  currentPage: number;
  pageSize: number;
  elements: Array<T>;
}
