import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { User } from '@app/_common/_models';
import { AuthenticationService } from '../../_common/_services/';

@Component({
  templateUrl: 'help-guest-dialog.component.html',
})
export class HelpGuestDialogComponent implements OnInit {
  @Input() helpTitle: string;
  @Input() helpContent: string;
  @Input() btnCancelText: string;

  currentURL: string;
  currentUser: User;

  constructor(
    private activeModal: NgbActiveModal,
    private toastr: ToastrService,
    private router: Router,
    private authenticationService: AuthenticationService,
  ) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );

    // Get current URL
    this.currentURL = this.router.url;
  }

  ngOnInit() {
    this.setHelpContent();
  }

  public decline() {
    this.activeModal.close(false);
  }

  public setHelpContent() {
    switch (this.currentURL) {
      case '/account':
        this.helpTitle = 'Account - Safety storage';
        this.helpContent =
          '- We recommend to just save common accounts here\n- Your password is encrypted and showed limited chars in screen';
        break;

      case '/english':
        this.helpTitle = 'English - Speaking dictionary';
        this.helpContent =
          "- Guessing the english word by its image, funny game\n- Don't forget to update your english level for new word appearance\n- It is easy to review and tracking your learn history, your E-Dashboard\n- There are almost 1000 words and stil be added";
        break;

      case '/food':
        this.helpTitle = 'Food - Hungry notebook';
        this.helpContent =
          '- Find your favourite dish for breafast, lunch and dinner\n- You are welcome to contribute :)';
        break;

      case '/media':
        this.helpTitle = 'Media - Ignore adversisement';
        this.helpContent =
          '- Could login to add your favarist youtube video in dashboard\n- We select some english channels for kid, listing 20 newest movies each channel\n- Could watch movie by input your movie URL from any host';
        break;

      case '/finance':
        this.helpTitle = 'Finance - Realtime indicators - charts';
        this.helpContent =
          '- International and Vietnam gold price\n- Oil & Gas price\n- VCB VND rate\n- Stock index';
        break;

      default:
        if (this.currentUser) {
          this.helpTitle = 'Contact us for giving feedback';
          this.helpContent =
            '- Gmail: hientranduy@gmail.com\n- Phone: 0908240910';
        } else {
          this.helpTitle = 'You should login to use below functions';
          this.helpContent =
            '- Account storage\n- English and E-Dashboard\n- Personal video play list\n\nFor trial: let use account visitor/visitor';
        }
        break;
    }
  }
}
