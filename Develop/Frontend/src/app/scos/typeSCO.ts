import { BaseSCO } from './baseSCO';
import { SearchText } from './core_sco/searchText';

export class TypeSCO extends BaseSCO {
  typeClass: SearchText;
  typeCode: SearchText;
  typeName: SearchText;
  description: SearchText;
}
