import { Injectable } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

@Injectable({ providedIn: 'root' })
export class SpinnerService {

    constructor(private spinner: NgxSpinnerService) {
    }

    // Show loading spinner
    show() {
        this.spinner.show();
    }

    // Hide loading spinner
    hide() {
        this.spinner.hide();
    }
}
