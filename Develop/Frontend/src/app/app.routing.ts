import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './pages/account';
import { AdministrationComponent } from './pages/administration';
import { CryptoComponent } from './pages/crypto/crypto.component';
import { EnglishComponent } from './pages/english';
import { EnglishReportComponent } from './pages/english-report';
import { FinanceComponent } from './pages/finance/finance.component';
import { FoodComponent } from './pages/food';
import { FoodDetailComponent } from './pages/food/food-detail/food-detail.component';
import { HomeComponent } from './pages/home';
import { MediaComponent, YoutubePlayerChannelComponent, YoutubePlayerComponent } from './pages/media';
import { LoginComponent } from './user/login';
import { RegisterComponent } from './user/register';
import { AuthGuard } from './user/_guards';

const appRoutes: Routes = [
    {
        path: '',
        component: HomeComponent,
        data: {
            title: 'S.H Box',
            ogUrl: 'https://sohebox.com',
            description: 'Create an account or log into S.H Box. Keep and save all your common accounts, learn english with image, experience with food collection',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'login',
        component: LoginComponent,
        data: {
            title: 'S.H Box - Login',
            ogUrl: 'https://sohebox.com/login',
            description: 'Onetime login for using all belonging functions',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'register',
        component: RegisterComponent,
        data: {
            title: 'S.H Box - Sign Up',
            ogUrl: 'https://sohebox.com/register',
            description: 'Sign up for S.H Box and select function. Create an account to start getting your accounts, learning english, get food information. It is easy to register',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'adminDev',
        component: AdministrationComponent,
        canActivate: [AuthGuard],
        data: {
            title: 'S.H Box - Administration',
            ogUrl: 'https://sohebox.com/adminDev',
            description: 'For admin only, control all settings and contents',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'account',
        component: AccountComponent,
        canActivate: [AuthGuard],
        data: {
            title: 'S.H Box - Account keeping & saving',
            ogUrl: 'https://sohebox.com/account',
            description: 'Keeping Your Account Secure, let start saving your uncommon accounts first like wifi, passcode, private box, public accounts ...',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'english',
        component: EnglishComponent,
        canActivate: [AuthGuard],
        data: {
            title: 'S.H Box - English Vocabulary Learning',
            ogUrl: 'https://sohebox.com/english',
            description: 'Learn english by image, improve prononciation with void US/UK',
            keywords: 'S.H Box, english'
        }
    },
    {
        path: 'englishReport/:id',
        component: EnglishReportComponent,
        canActivate: [AuthGuard],
        data: {
            title: 'S.H Box - English Reporting',
            ogUrl: 'https://sohebox.com/englishReport',
            description: 'Report your english learning process',
            keywords: 'S.H Box, english'
        }
    },
    {
        path: 'food',
        component: FoodComponent,
        data: {
            title: 'S.H Box - Food Collection',
            ogUrl: 'https://sohebox.com/food',
            description: 'Find your favorist food/drink. Check its recepe and atttibute. You can also find the saler location',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'foodDetail/:id',
        component: FoodDetailComponent
    },
    {
        path: 'media',
        component: MediaComponent,
        data: {
            title: 'S.H Box - Media',
            ogUrl: 'https://sohebox.com/media',
            description: 'Incomming function',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'media/youtubeplayer/:videoId',
        component: YoutubePlayerComponent,
        data: {
            title: 'S.H Box - Youtube Player',
            ogUrl: 'https://sohebox.com/media/youtubeplayer',
            description: 'Youtube player',
            keywords: 'S.H Box, player'
        }
    },
    {
        path: 'media/youtubeplayerchannel/:channelId',
        component: YoutubePlayerChannelComponent,
        data: {
            title: 'S.H Box - Youtube Channel Player',
            ogUrl: 'https://sohebox.com/media/youtubeplayerchannel',
            description: 'Youtube channe player',
            keywords: 'S.H Box, channe player'
        }
    },
    {
        path: 'finance',
        component: FinanceComponent,
        data: {
            title: 'S.H Box - Finance Indicators',
            ogUrl: 'https://sohebox.com/finance',
            description: 'Gold, Old, Stock, Crypto charts and indicators',
            keywords: 'S.H Box'
        }
    },
    {
        path: 'crypto',
        component: CryptoComponent,
        canActivate: [AuthGuard],
        data: {
            title: 'S.H Box - Crypto monitor',
            ogUrl: 'https://sohebox.com/crypto',
            description: 'Crypto porfolio',
            keywords: 'S.H Box'
        }
    },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes, { relativeLinkResolution: 'legacy' });
