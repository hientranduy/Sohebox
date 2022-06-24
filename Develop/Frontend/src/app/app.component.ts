import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap/modal/modal.module';
import { EventTargetInterruptSource, Idle } from '@ng-idle/core';
import { Keepalive } from '@ng-idle/keepalive';
import { UserDialogService } from './user/_dialogs';
import { User } from './user/_models';
import { AuthenticationService } from './user/_service';
import { TimeoutProgressDialogComponent } from './_common/timeout-progress-dialog';

@Component({
    selector: 'app-sohebox',
    templateUrl: 'app.component.html',
    styleUrls: ['app.component.css']
})
export class AppComponent {
    currentUser: User;
    isShowMainToolbar: Boolean;

    @ViewChild('sidenav') public sidenav: MatSidenav;

    private progressBarPopup: NgbModalRef;
    private timeoutCount: number;

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private element: ElementRef,
        private idle: Idle,
        private keepalive: Keepalive,
        private ngbModal: NgbModal,
        private userDialogService: UserDialogService
    ) {
        // Get logged user
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);

        // Check show main toolbar
        router.events.subscribe((val) => {
            const currentURL = this.router.url;
            if (currentURL.includes('/media/youtubeplayer/') ||
                currentURL.includes('/foodDetail/') ||
                currentURL.includes('/media/youtubeplayerchannel/')
            ) {
                this.isShowMainToolbar = false;
            } else {
                this.isShowMainToolbar = true;
            }
        });

        // Set idle timeout
        this.setTimeout(idle, keepalive);
    }

    private setTimeout(idle: Idle, keepalive: Keepalive) {
        // Set idle timeout: 60 minutes.
        idle.setIdle(3600);

        // Set timeout period: 1 minutes.
        this.timeoutCount = 60;
        idle.setTimeout(this.timeoutCount);

        // Set the interrupts type like Keydown, scroll, mouse wheel, mouse down, and etc
        idle.setInterrupts([
            new EventTargetInterruptSource(this.element.nativeElement,
                'keydown DOMMouseScroll mousewheel mousedown touchstart touchmove scroll')
        ]);

        // Start idle
        idle.onIdleStart.subscribe(() => {
            this.openProgressForm(1);
        });

        // Timeout warning
        idle.onTimeoutWarning.subscribe((countdown: any) => {
            this.progressBarPopup.componentInstance.count = (Math.floor((countdown - 1) / 60) + 1);
            this.progressBarPopup.componentInstance.progressCount = this.reverseNumber(countdown);
            this.progressBarPopup.componentInstance.progressCountMax = this.timeoutCount;
            this.progressBarPopup.componentInstance.countMinutes = (Math.floor(countdown / 60));
            this.progressBarPopup.componentInstance.countSeconds = countdown % 60;
        });

        // Timeout
        idle.onTimeout.subscribe(() => {
            this.closeProgressForm();
            this.authenticationService.logout();
            this.logout();
            this.router.navigate(['/']);
        });

        // End idle
        idle.onIdleEnd.subscribe(() => {
            // If want to add any action: prompt message ?
        });

        // Set the ping interval to 15 seconds
        keepalive.interval(15);

        /**
         *  // Keepalive can ping request to an HTTP location to keep server session alive
         * keepalive.request('<String URL>' or HTTP Request);
         * // Keepalive ping response can be read using below option
         * keepalive.onPing.subscribe(response => {
         * // Redirect user to logout screen stating session is timeout out if if response.status != 200
         * });
         */
        this.reset();
    }

    /**
     * Open timeout progress dialog
     */
    private openProgressForm(count: number) {
        this.progressBarPopup = this.ngbModal.open(TimeoutProgressDialogComponent, {
            backdrop: 'static',
            keyboard: false
        });
        this.progressBarPopup.componentInstance.count = count;
        this.progressBarPopup.result.then((result: any) => {
            if (result !== '' && 'logout' === result) {
                this.logout();
            } else {
                this.reset();
            }
        });
    }

    private logout() {
        // Reset timeout
        this.idle.stop();
        this.idle.onIdleStart.unsubscribe();
        this.idle.onTimeoutWarning.unsubscribe();
        this.idle.onIdleEnd.unsubscribe();
        this.idle.onIdleEnd.unsubscribe();
    }

    private reset() {
        this.idle.watch();
    }

    /**
     * Close timeout progress dialog
     */
    private closeProgressForm() {
        this.progressBarPopup.close();
    }

    private reverseNumber(countdown: number) {
        return (this.timeoutCount - (countdown - 1));
    }

    /**
     * Side navigation
     */
    public onToggleSidenav() {
        this.sidenav.toggle();
    }

    public openHelpGuest() {
        this.userDialogService.openHelpGuest();
    }
}
