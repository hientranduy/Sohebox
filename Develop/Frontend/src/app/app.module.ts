import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { YouTubePlayerModule } from '@angular/youtube-player';
import { MaterialModule } from '@app/material-module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgIdleKeepaliveModule } from '@ng-idle/keepalive';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgxImageZoomModule } from 'ngx-image-zoom';
import { NgxSpinnerModule } from 'ngx-spinner';
import { ToastrModule } from 'ngx-toastr';
import { AppComponent } from './app.component';
import { routing } from './app.routing';
import { AccountComponent } from './pages/account';
import { AddAccountDialogComponent, DeleteAccountDialogComponent, EditAccountDialogComponent, ShowPasswordDialogComponent, ViewAccountDialogComponent } from './pages/account/_dialogs';
import { EnglishConfigComponent, EnglishTypeComponent, FoodConfigComponent, FoodTypeComponent, MediaTypeComponent, SettingConfigComponent, SettingTypeComponent, YoutubeChannelConfigComponent } from './pages/administration';
import { AdministrationComponent } from './pages/administration/administration.component';
import { AddEnglishTypeDialogComponent, DeleteEnglishTypeDialogComponent, EditEnglishTypeDialogComponent, ViewEnglishTypeDialogComponent } from './pages/administration/english-type/_dialogs';
import { EnglishUserReportComponent } from './pages/administration/english-user-report/english-user-report.component';
import { AddFoodTypeDialogComponent, DeleteFoodTypeDialogComponent, EditFoodTypeDialogComponent, ViewFoodTypeDialogComponent } from './pages/administration/food-type/_dialogs';
import { DeleteMediaTypeDialogComponent, EditMediaTypeDialogComponent, ViewMediaTypeDialogComponent } from './pages/administration/media-type/_dialogs';
import { AddConfigDialogComponent, DeleteConfigDialogComponent, EditConfigDialogComponent, ViewConfigDialogComponent } from './pages/administration/setting-config/_dialogs';
import { AddTypeDialogComponent, DeleteTypeDialogComponent, EditTypeDialogComponent, ViewTypeDialogComponent } from './pages/administration/setting-type/_dialogs';
import { UserControlComponent } from './pages/administration/user-control/user-control.component';
import { AddYoutubeChannelDialogComponent, EditYoutubeChannelDialogComponent } from './pages/administration/youtube-channel-config/_dialogs';
import { EnglishComponent } from './pages/english';
import { EnglishReportComponent } from './pages/english-report';
import { AddWordDialogComponent, DownloadVoiceComponent, EditWordDialogComponent, WordInfoDialogComponent } from './pages/english/_dialogs';
import { ShowLearnedWordComponent } from './pages/english/_dialogs/show-learned-word.component';
import { FinanceComponent, FinanceCurrencyComponent, FinanceGoldComponent, FinanceOilComponent, FinanceStockComponent } from './pages/finance';
import { FoodComponent } from './pages/food';
import { FoodDetailComponent } from './pages/food/food-detail/food-detail.component';
import { FoodItemComponent } from './pages/food/food-item/food-item.component';
import { AddFoodDialogComponent, EditFoodDialogComponent } from './pages/food/_dialogs';
import { HomeComponent } from './pages/home';
import { MoviePlayerComponent, PhimmoiPlayerComponent, YoutubeChannelComponent, YoutubePlayerComponent, YoutubePlayerChannelComponent } from './pages/media';
import { MediaComponent } from './pages/media/media.component';
import { ChangePasswordComponent } from './user/change-password';
import { LoginComponent } from './user/login';
import { RegisterComponent } from './user/register';
import { UserSlideBarComponent } from './user/user-slide-bar';
import { ChangePasswordDialogComponent, DeleteConfirmationDialogComponent, HelpGuestDialogComponent,
         UpdateEnglishLevelDialogComponent, UpdateInforDialogComponent, ChangePrivateKeyDialogComponent } from './user/_dialogs';
import { AlertComponent } from './_common/alert';
import { AutoFocusDirective } from './_common/directive';
import { TimeoutProgressDialogComponent } from './_common/timeout-progress-dialog/timeout-progress-dialog.component';
import { ErrorInterceptor, JwtInterceptor } from './_common/_helpers';
import { AddYoutubeVideoDialogComponent, DeleteYoutubeVideoDialogComponent } from './pages/media/_dialogs';
import { CryptoComponent } from './pages/crypto/crypto.component';
import { PortfolioComponent } from './pages/crypto/portfolio/portfolio.component';
import { CoinComponent } from './pages/crypto/coin/coin.component';

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
        NgxImageZoomModule,
        NgxDatatableModule,
        YouTubePlayerModule,
        ToastrModule.forRoot({
            timeOut: 5000,
            positionClass: 'toast-top-full-width'
        }),
        NgxSpinnerModule,
        FlexLayoutModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        AlertComponent,
        AutoFocusDirective,
        TimeoutProgressDialogComponent,
        UserSlideBarComponent,
        HelpGuestDialogComponent,
        // Admin
        AdministrationComponent,
        SettingConfigComponent,
        SettingTypeComponent,
        // Dialog
        AddTypeDialogComponent,
        EditTypeDialogComponent,
        DeleteTypeDialogComponent,
        ViewTypeDialogComponent,
        AddConfigDialogComponent,
        EditConfigDialogComponent,
        DeleteConfigDialogComponent,
        ViewConfigDialogComponent,
        // User
        LoginComponent,
        RegisterComponent,
        ChangePasswordComponent,
        // Dialog
        DeleteConfirmationDialogComponent,
        ChangePasswordDialogComponent,
        UpdateInforDialogComponent,
        UserControlComponent,
        ChangePrivateKeyDialogComponent,
        // Account
        AccountComponent,
        // Dialog
        AddAccountDialogComponent,
        EditAccountDialogComponent,
        DeleteAccountDialogComponent,
        ViewAccountDialogComponent,
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
        AddEnglishTypeDialogComponent,
        DeleteEnglishTypeDialogComponent,
        EditEnglishTypeDialogComponent,
        ViewEnglishTypeDialogComponent,
        UpdateEnglishLevelDialogComponent,
        AddWordDialogComponent,
        EditWordDialogComponent,
        WordInfoDialogComponent,
        // Food
        FoodComponent,
        FoodItemComponent,
        FoodConfigComponent,
        FoodTypeComponent,
        FoodDetailComponent,
        // Dialog
        AddFoodDialogComponent,
        EditFoodDialogComponent,
        AddFoodTypeDialogComponent,
        DeleteFoodTypeDialogComponent,
        EditFoodTypeDialogComponent,
        ViewFoodTypeDialogComponent,
        // Media
        MediaComponent,
        MediaTypeComponent,
        YoutubeChannelConfigComponent,
        YoutubeChannelComponent,
        YoutubePlayerComponent,
        YoutubePlayerChannelComponent,
        MoviePlayerComponent,
        PhimmoiPlayerComponent,
        // Dialog
        DeleteMediaTypeDialogComponent,
        EditMediaTypeDialogComponent,
        ViewMediaTypeDialogComponent,
        AddYoutubeChannelDialogComponent,
        EditYoutubeChannelDialogComponent,
        AddYoutubeVideoDialogComponent,
        DeleteYoutubeVideoDialogComponent,
        // Finance
        FinanceComponent,
        FinanceGoldComponent,
        FinanceOilComponent,
        FinanceCurrencyComponent,
        FinanceStockComponent,
        CryptoComponent,
        PortfolioComponent,
        CoinComponent
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
