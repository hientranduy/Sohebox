import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@app/material-module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgIdleKeepaliveModule } from '@ng-idle/keepalive';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';
import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { AdministrationComponent } from './pages/administration/administration.component';
import { CryptoTokenConfigComponent } from './pages/administration/crypto-token-config/crypto-token-config.component';

import { EnglishUserReportComponent } from './pages/administration/english-user-report/english-user-report.component';

import { CryptoComponent } from './pages/crypto/crypto.component';
import { PortfolioComponent } from './pages/crypto/portfolio/portfolio.component';

import { ShowLearnedWordComponent } from './dialogs/english/show-learned-word.component';

import { TimeoutProgressDialogComponent } from './commons/timeout/timeout-progress-dialog.component';
import { JwtInterceptor } from './commons/jwt.interceptor';
import { ErrorInterceptor } from './commons/error.interceptor';
import { AutoFocusDirective } from './commons/auto-focus.directive';
import { AlertComponent } from './commons/alert/alert.component';
import { UserSlideBarComponent } from './pages/user/user-slide-bar/user-slide-bar.component';
import { LoginComponent } from './pages/user/login.component';
import { RegisterComponent } from './pages/user/register.component';
import { AccountComponent } from './pages/account/account.component';
import { SettingTypeComponent } from './pages/administration/setting-type/setting-type.component';
import { EnglishConfigComponent } from './pages/administration/english-config/english-config.component';
import { EnglishTypeComponent } from './pages/administration/english-type/english-type.component';
import { EnglishComponent } from './pages/english/english.component';
import { HomeComponent } from './pages/home/home.component';
import { EnglishReportComponent } from './pages/english-report/english-report.component';
import { AddAccountDialogComponent } from './dialogs/account/add-account-dialog.component';
import { EditAccountDialogComponent } from './dialogs/account/edit-account-dialog.component';
import { DeleteAccountDialogComponent } from './dialogs/account/delete-account-dialog.component';
import { ShowPasswordDialogComponent } from './dialogs/account/show-password-dialog.component';
import { AddCryptoTokenConfigDialogComponent } from './dialogs/crypto-token-config/add-crypto-token-config-dialog.component';
import { EditCryptoTokenConfigDialogComponent } from './dialogs/crypto-token-config/edit-crypto-token-config-dialog.component';
import { EditEnglishTypeDialogComponent } from './dialogs/english-type/edit-english-type-dialog.component';
import { AddTypeDialogComponent } from './dialogs/setting-type/add-type-dialog.component';
import { EditTypeDialogComponent } from './dialogs/setting-type/edit-type-dialog.component';
import { AddCryptoFortfolioDialogComponent } from './dialogs/crypto-portfolio/add-crypto-portfolio-dialog.component';
import { DeleteCryptoPortfolioDialogComponent } from './dialogs/crypto-portfolio/delete-crypto-portfolio-dialog.component';
import { EditCryptoPortfolioDialogComponent } from './dialogs/crypto-portfolio/edit-crypto-portfolio-dialog.component';
import { DownloadVoiceComponent } from './dialogs/english/download-voice.component';
import { AddWordDialogComponent } from './dialogs/english/add-word-dialog.component';
import { EditWordDialogComponent } from './dialogs/english/edit-word-dialog.component';
import { WordInfoDialogComponent } from './dialogs/english/word-info-dialog.component';
import { HelpGuestDialogComponent } from './dialogs/user/help-guest-dialog.component';
import { ChangePasswordDialogComponent } from './dialogs/user/change-password-dialog.component';
import { ChangePrivateKeyDialogComponent } from './dialogs/user/change-private-key-dialog.component';
import { DeleteConfirmationDialogComponent } from './dialogs/user/delete-confirmation-dialog.component';
import { UpdateEnglishLevelDialogComponent } from './dialogs/user/update-english-level-dialog.component';
import { UpdateInforDialogComponent } from './dialogs/user/update-infor-dialog.component';

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    routing,
    NgbModule,
    MaterialModule,
    NgIdleKeepaliveModule.forRoot(),
    NgxDatatableModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-full-width',
    }),
    NgxSpinnerModule,
    FlexLayoutModule,
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    AlertComponent,
    AutoFocusDirective,
    TimeoutProgressDialogComponent,
    UserSlideBarComponent,
    HelpGuestDialogComponent,

    //////////
    // PAGE //
    //////////
    // Admin
    AdministrationComponent,
    SettingTypeComponent,

    ////////////
    // DIALOG //
    ////////////
    // Type
    AddTypeDialogComponent,
    EditTypeDialogComponent,

    // User
    LoginComponent,
    RegisterComponent,

    // Dialog
    DeleteConfirmationDialogComponent,
    ChangePasswordDialogComponent,
    UpdateInforDialogComponent,
    ChangePrivateKeyDialogComponent,

    // Account
    AccountComponent,
    // Dialog
    AddAccountDialogComponent,
    EditAccountDialogComponent,
    DeleteAccountDialogComponent,
    ShowPasswordDialogComponent,

    // English
    EnglishComponent,
    EnglishConfigComponent,
    EnglishTypeComponent,
    ShowLearnedWordComponent,
    DownloadVoiceComponent,
    EnglishUserReportComponent,
    EnglishReportComponent,
    EnglishUserReportComponent,

    // Dialog
    EditEnglishTypeDialogComponent,
    UpdateEnglishLevelDialogComponent,
    AddWordDialogComponent,
    EditWordDialogComponent,
    WordInfoDialogComponent,

    // Crypto
    CryptoComponent,

    CryptoTokenConfigComponent,
    AddCryptoTokenConfigDialogComponent,
    EditCryptoTokenConfigDialogComponent,

    PortfolioComponent,
    AddCryptoFortfolioDialogComponent,
    EditCryptoPortfolioDialogComponent,
    DeleteCryptoPortfolioDialogComponent,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
