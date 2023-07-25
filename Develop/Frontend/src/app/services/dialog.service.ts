import { Injectable } from '@angular/core';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import { AddAccountDialogComponent } from '@app/dialogs/account/add-account-dialog.component';
import { DeleteAccountDialogComponent } from '@app/dialogs/account/delete-account-dialog.component';
import { EditAccountDialogComponent } from '@app/dialogs/account/edit-account-dialog.component';
import { ViewAccountDialogComponent } from '@app/dialogs/account/view-account-dialog.component';
import { ShowPasswordDialogComponent } from '@app/dialogs/account/show-password-dialog.component';
import { Account } from '@app/models/account';
import { AddConfigDialogComponent } from '@app/dialogs/config/add-config-dialog.component';
import { EditConfigDialogComponent } from '@app/dialogs/config/edit-config-dialog.component';
import { ViewConfigDialogComponent } from '@app/dialogs/config/view-config-dialog.component';
import { Config } from '@app/models/config';
import { AddCryptoFortfolioDialogComponent } from '@app/dialogs/crypto-portfolio/add-crypto-portfolio-dialog.component';
import { DeleteCryptoPortfolioDialogComponent } from '@app/dialogs/crypto-portfolio/delete-crypto-portfolio-dialog.component';
import { EditCryptoPortfolioDialogComponent } from '@app/dialogs/crypto-portfolio/edit-crypto-portfolio-dialog.component';
import { ViewCryptoPortfolioDialogComponent } from '@app/dialogs/crypto-portfolio/view-crypto-portfolio-dialog.component';
import { CryptoPortfolio } from '@app/models/cryptoPortfolio';
import { AddCryptoTokenConfigDialogComponent } from '@app/dialogs/crypto-token-config/add-crypto-token-config-dialog.component';
import { EditCryptoTokenConfigDialogComponent } from '@app/dialogs/crypto-token-config/edit-crypto-token-config-dialog.component';
import { CryptoTokenConfig } from '@app/models/cryptoTokenConfig';
import { AddWordDialogComponent } from '@app/dialogs/english/add-word-dialog.component';
import { DownloadVoiceComponent } from '@app/dialogs/english/download-voice.component';
import { EditWordDialogComponent } from '@app/dialogs/english/edit-word-dialog.component';
import { ShowLearnedWordComponent } from '@app/dialogs/english/show-learned-word.component';
import { WordInfoDialogComponent } from '@app/dialogs/english/word-info-dialog.component';
import { English } from '@app/models/english';
import { EditEnglishTypeDialogComponent } from '@app/dialogs/english-type/edit-english-type-dialog.component';
import { ViewEnglishTypeDialogComponent } from '@app/dialogs/english-type/view-english-type-dialog.component';
import { EnglishType } from '@app/models/englishType';
import { Food } from '@app/models/food';
import { AddFoodDialogComponent } from '@app/dialogs/food/add-food-dialog.component';
import { EditFoodDialogComponent } from '@app/dialogs/food/edit-food-dialog.component';
import { EditFoodTypeDialogComponent } from '@app/dialogs/food-type/edit-food-type-dialog.component';
import { ViewFoodTypeDialogComponent } from '@app/dialogs/food-type/view-food-type-dialog.component';
import { FoodType } from '@app/models/foodType';
import { YoutubeVideo } from '@app/models/youtubeVideo';
import { AddYoutubeVideoDialogComponent } from '@app/dialogs/media/add-youtube-video-dialog.component';
import { DeleteYoutubeVideoDialogComponent } from '@app/dialogs/media/delete-youtube-video-dialog.component';
import { Type } from '@app/models/type';
import { AddTypeDialogComponent } from '@app/dialogs/type/add-type-dialog.component';
import { EditTypeDialogComponent } from '@app/dialogs/type/edit-type-dialog.component';
import { ViewTypeDialogComponent } from '@app/dialogs/type/view-type-dialog.component';
import { ChangePasswordDialogComponent } from '@app/dialogs/user/change-password-dialog.component';
import { ChangePrivateKeyDialogComponent } from '@app/dialogs/user/change-private-key-dialog.component';
import { DeleteConfirmationDialogComponent } from '@app/dialogs/user/delete-confirmation-dialog.component';
import { HelpGuestDialogComponent } from '@app/dialogs/user/help-guest-dialog.component';
import { UpdateEnglishLevelDialogComponent } from '@app/dialogs/user/update-english-level-dialog.component';
import { UpdateInforDialogComponent } from '@app/dialogs/user/update-infor-dialog.component';
import { AddYoutubeChannelDialogComponent } from '@app/dialogs/youtube-channel/add-youtube-channel-dialog.component';
import { EditYoutubeChannelDialogComponent } from '@app/dialogs/youtube-channel/edit-youtube-channel-dialog.component';
import { YoutubeChannel } from '@app/models/youtubeChannel';
import { EditMediaTypeDialogComponent } from '@app/dialogs/media-type/edit-media-type.component';
import { ViewMediaTypeDialogComponent } from '@app/dialogs/media-type/view-media-type.component';

@Injectable({ providedIn: 'root' })
export class DialogService {
  constructor(
    config: NgbModalConfig,
    private modalService: NgbModal,
  ) {
    config.backdrop = 'static';
    config.keyboard = false;
  }
  //////////
  // TYPE //
  //////////
  public addType(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public editType(
    title: string,
    message: string,
    type: Type,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.type = type;
    return modalRef.result;
  }

  public viewType(
    title: string,
    message: string,
    type: Type,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.type = type;
    return modalRef.result;
  }

  ///////////
  // CONFIG//
  ///////////
  public addConfig(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddConfigDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public editConfig(
    title: string,
    message: string,
    item: Config,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditConfigDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  public viewConfig(
    title: string,
    message: string,
    item: Config,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewConfigDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  //////////
  // USER //
  //////////
  public deleteUser(
    title: string,
    message: string,
    information: string,
    dialogSize: 'sm' | 'lg' = 'sm',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteConfirmationDialogComponent, {
      size: 'lg',
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public changePassword(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ChangePasswordDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Change';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public updateUser(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(UpdateInforDialogComponent, {
      size: 'lg',
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Update';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public updateEnglishLevel(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(UpdateEnglishLevelDialogComponent, {
      size: 'lg',
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Update';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public openHelpGuest(dialogSize: 'sm' | 'lg' = 'sm'): Promise<boolean> {
    const modalRef = this.modalService.open(HelpGuestDialogComponent, {
      size: 'lg',
    });
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public changePrivateKey(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'sm',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ChangePrivateKeyDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Change';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  ////////////
  // ACCOUNT//
  ////////////
  public addAccount(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public deleteAccount(
    title: string,
    message: string,
    account: Account,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }

  public editAccount(
    title: string,
    message: string,
    account: Account,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }

  public viewAccount(
    title: string,
    message: string,
    account: Account,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewAccountDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }

  public showPassword(
    account: Account,
    dialogSize: 'sm' | 'sm' = 'sm',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ShowPasswordDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.account = account;
    return modalRef.result;
  }

  //////////////////////
  // CRYPTO PORTFOLIO //
  //////////////////////
  public addCryptoPortfolio(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddCryptoFortfolioDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public deleteCryptoPortfolio(
    title: string,
    message: string,
    cryptoPortfolio: CryptoPortfolio,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(
      DeleteCryptoPortfolioDialogComponent,
      { size: dialogSize },
    );
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoPortfolio = cryptoPortfolio;
    return modalRef.result;
  }

  public editCryptoPortfolio(
    title: string,
    message: string,
    cryptoPortfolio: CryptoPortfolio,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(
      EditCryptoPortfolioDialogComponent,
      { size: dialogSize },
    );
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoPortfolio = cryptoPortfolio;
    return modalRef.result;
  }

  public viewCryptoPortfolio(
    title: string,
    message: string,
    cryptoPortfolio: CryptoPortfolio,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(
      ViewCryptoPortfolioDialogComponent,
      { size: dialogSize },
    );
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoPortfolio = cryptoPortfolio;
    return modalRef.result;
  }

  /////////////////////////
  // CRYPTO TOKEN CONFIG //
  /////////////////////////
  public addCryptoTokenConfig(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(
      AddCryptoTokenConfigDialogComponent,
      {
        size: dialogSize,
      },
    );
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public editCryptoTokenConfig(
    title: string,
    message: string,
    cryptoTokenConfig: CryptoTokenConfig,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(
      EditCryptoTokenConfigDialogComponent,
      {
        size: dialogSize,
      },
    );
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.cryptoTokenConfig = cryptoTokenConfig;
    return modalRef.result;
  }

  /////////////
  // ENGLISH //
  /////////////
  public addWord(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddWordDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public editWord(
    title: string,
    message: string,
    english: English,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditWordDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.english = english;
    return modalRef.result;
  }

  public viewWordInfo(
    english: English,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(WordInfoDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.english = english;
    return modalRef.result;
  }

  public downloadVoice(
    title: string,
    message: string,
    english: English,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DownloadVoiceComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Download';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.english = english;
    return modalRef.result;
  }

  public showLearnedWord(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ShowLearnedWordComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  //////////////////
  // ENGLISH TYPE //
  //////////////////
  public editEnglishType(
    title: string,
    message: string,
    item: EnglishType,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditEnglishTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  public viewEnglishType(
    title: string,
    message: string,
    item: EnglishType,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewEnglishTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  //////////
  // FOOD //
  //////////
  public addFood(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddFoodDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public editFood(
    title: string,
    message: string,
    food: Food,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditFoodDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.food = food;
    return modalRef.result;
  }

  ///////////////
  // FOOD TYPE //
  ///////////////
  public editFoodType(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditFoodTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  public viewFoodType(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewFoodTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }
  ///////////////////
  // YOUTUBE VIDEO //
  ///////////////////
  public addPrivateVideo(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddYoutubeVideoDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public deletePrivateVideo(
    title: string,
    message: string,
    video: YoutubeVideo,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(DeleteYoutubeVideoDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Delete';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.video = video;
    return modalRef.result;
  }

  /////////////////////
  // YOUTUBE CHANNEL //
  /////////////////////
  public addYoutubeChannel(
    title: string,
    message: string,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(AddYoutubeChannelDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Add';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    return modalRef.result;
  }

  public editYoutubeChannel(
    title: string,
    message: string,
    youtubeChannel: YoutubeChannel,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditYoutubeChannelDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.youtubeChannel = youtubeChannel;
    return modalRef.result;
  }

  ////////////////
  // MEDIA TYPE //
  ////////////////
  public editMediaType(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(EditMediaTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = 'Edit';
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }

  public viewMediaType(
    title: string,
    message: string,
    item: FoodType,
    dialogSize: 'sm' | 'lg' = 'lg',
  ): Promise<boolean> {
    const modalRef = this.modalService.open(ViewMediaTypeDialogComponent, {
      size: dialogSize,
    });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnCancelText = 'Cancel';
    modalRef.componentInstance.item = item;
    return modalRef.result;
  }
}
