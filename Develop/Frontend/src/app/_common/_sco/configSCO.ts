import { BaseSCO } from './baseSCO';
import { SearchText } from './core_sco';

export class ConfigSCO extends BaseSCO {
  configKey: SearchText;
  configValue: SearchText;
  description: SearchText;
}
