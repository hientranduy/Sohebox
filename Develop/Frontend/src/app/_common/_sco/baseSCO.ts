import { Sorter } from './core_sco';

export class BaseSCO {
  deleteFlag: boolean;
  maxRecordPerPage: number;
  pageToGet: number;
  reportFlag: boolean;
  searchOr: boolean;
  sorters: Array<Sorter>;

  constructor() {
    this.maxRecordPerPage = 99999;
  }
}
