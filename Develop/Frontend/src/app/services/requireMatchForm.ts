import { AbstractControl } from '@angular/forms';

export function RequireMatchForm(control: AbstractControl) {
  const selection: any = control.value;
  if (control.value !== '' && typeof selection === 'string') {
    return { incorrect: true };
  }
  return null;
}
