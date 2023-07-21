import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ApiReponse, EnglishType } from '@app/_common/_models';
import {
  EnglishLearnRecordSCO,
  EnglishSCO,
  EnglishTypeSCO,
  EnglishUserGradeSCO,
} from '@app/_common/_sco';
import {
  SearchDate,
  SearchNumber,
  SearchText,
  Sorter,
} from '@app/_common/_sco/core_sco';
import {
  RequireMatchForm,
  SpinnerService,
  UtilsService,
} from '@app/_common/_services';
import { AlertService } from '@app/_common/alert/alert.service';
import { AppSettings } from '@app/app.settings';
import { User } from '@app/_common/_models';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { EnglishDialogService } from './_dialogs';
import { English, EnglishLearnRecord, EnglishUserGrade } from './_model';
import { EnglishService, EnglishTypeService } from './_services';
import { AuthenticationService } from '@app/_common/_services/';

@Component({
  selector: 'app-english',
  templateUrl: './english.component.html',
  styleUrls: ['./english.component.css'],
})
export class EnglishComponent implements OnInit {
  @ViewChild('searchButtonEnglish') public searchButton: any;

  // Current user
  currentUser: User;
  englishUserGrade: EnglishUserGrade;

  /**
   * Construction
   */
  constructor(
    private route: ActivatedRoute,
    private englishDialogService: EnglishDialogService,
    private authenticationService: AuthenticationService,
    private englishService: EnglishService,
    private englishTypeService: EnglishTypeService,
    private alertService: AlertService,
    private toastr: ToastrService,
    private spinner: SpinnerService,
    private utilsService: UtilsService,
  ) {
    // Get logged user
    this.authenticationService.currentUser.subscribe(
      (x) => (this.currentUser = x),
    );

    // Get learned record
    this.getLearnedListByToday();
  }

  //////////////////////
  // Field : key word //
  //////////////////////
  keyWordValue: string;
  keyWordValueSave: string;
  wordFormControl = new FormControl('', []);

  ////////////////////
  // Field Category //
  ////////////////////
  categoryValue: EnglishType;
  categoryValueSave: EnglishType;
  categoryControl = new FormControl('', [RequireMatchForm]);
  filteredCategories: Observable<EnglishType[]>;

  /////////////////
  // Field Grade //
  /////////////////
  gradeValue: EnglishType;
  gradeValueSave: EnglishType;
  gradeControl = new FormControl('', [RequireMatchForm]);
  filteredGrades: Observable<EnglishType[]>;

  ///////////////////////
  // General variables //
  ///////////////////////
  // First search
  firstSearch: Boolean = true;

  // Search list
  searchListWord: English[];

  // Item to display
  displayWord: English;
  displayImageUrl: string;
  displayUkVoiceUrl: string;
  displayUsVoiceUrl: string;

  // Boolean
  isAddLearn: Boolean;
  isClickImage: Boolean;
  isFullScreen: Boolean = false;
  isAllowFullScreen: Boolean = false;
  totalUsVoice: any;
  totalUkVoice: any;
  getWordDate: Date;
  playVoiceDate: Date;
  secondToPass: number = 20;
  isSearchByCondition: Boolean = false;
  inputWordSave: string;

  // Show Excellence Start icon
  isDisplayStart1: Boolean = false;
  isDisplayStart2: Boolean = false;
  isDisplayStart3: Boolean = false;
  isDisplayStart4: Boolean = false;
  isDisplayStart5: Boolean = false;

  englishElemSave: HTMLElement;

  // Show total learned
  learnedRecords: EnglishLearnRecord[];
  numberOfLearned: any = 0;

  /**
   * Control display value of category
   */
  public displayCategory(category: EnglishType) {
    if (category) {
      return category.typeName;
    }
    return null;
  }

  /**
   * Control display value of grade
   */
  public displayGrade(grade: EnglishType) {
    if (grade) {
      return grade.typeName;
    }
    return null;
  }

  /**
   * Initialize
   */
  ngOnInit() {
    // Get data master
    this.getCalegoryList();
    this.getGradeList();
    this.getCurrentUserlevelAndFirstWord();

    // Add event listener for fullscreen
    document.addEventListener('fullscreenchange', (event) => {
      if (document.fullscreenElement) {
        this.isFullScreen = true;
      } else {
        this.isFullScreen = false;
      }
    });

    // Set allow fullscreen
    if (this.utilsService.IsSafari() === true) {
      this.isAllowFullScreen = false;
    } else {
      this.isAllowFullScreen = true;
    }
  }

  /**
   * Get category list
   */
  public getCalegoryList() {
    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'ENGLISH_CATEGORY';
    const englishTypeSCO = new EnglishTypeSCO();
    englishTypeSCO.typeClass = typeClass;

    // Get list category
    this.englishTypeService.search(englishTypeSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishType> = responseAPi;
        if (typeResponse.data != null) {
          const categories: EnglishType[] = typeResponse.data.elements;
          this.filteredCategories = this.categoryControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              categories.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toString().toLowerCase()),
              ),
            ),
          );
        }
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  /**
   * Get grade list
   */
  public getGradeList() {
    // Prepare search condition
    const typeClass = new SearchText();
    typeClass.eq = 'ENGLISH_VUS_GRADE';
    const sorters: Array<Sorter> = [];
    sorters.push(new Sorter('id', 'ASC'));

    const englishTypeSCO = new EnglishTypeSCO();
    englishTypeSCO.typeClass = typeClass;
    englishTypeSCO.deleteFlag = false;
    englishTypeSCO.sorters = sorters;

    // Get list grade
    this.englishTypeService.search(englishTypeSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishType> = responseAPi;
        if (typeResponse.data != null) {
          const grades: EnglishType[] = typeResponse.data.elements;
          this.filteredGrades = this.gradeControl.valueChanges.pipe(
            startWith(''),
            map((value) =>
              grades.filter((valueFilter) =>
                valueFilter.typeCode
                  .toLowerCase()
                  .includes(value.toString().toLowerCase()),
              ),
            ),
          );
        }
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  /**
   * Get learned record list by today
   */
  public getLearnedListByToday() {
    // Prepare search condition
    const updatedDateSearch = new SearchDate();
    updatedDateSearch.eq = new Date();
    const userIdSearch = new SearchNumber();
    userIdSearch.eq = this.currentUser.id;
    const sco = new EnglishLearnRecordSCO();
    sco.updatedDate = updatedDateSearch;
    sco.userId = userIdSearch;

    // Search
    this.englishService.searchLearnRecord(sco).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishLearnRecord> = responseAPi;
        if (typeResponse.data != null) {
          this.learnedRecords = typeResponse.data.elements;

          // Calculate total times of learn
          if (this.learnedRecords != null) {
            this.learnedRecords.forEach((element) => {
              this.numberOfLearned =
                this.numberOfLearned + element.learnedToday;
            });
          } else {
            this.numberOfLearned = 0;
          }
        }
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  /**
   * Get english level of current user
   */
  public getCurrentUserlevelAndFirstWord() {
    // Prepare search condition
    const userId = new SearchNumber();
    userId.eq = this.currentUser.id;

    const englishUserGradeSCO = new EnglishUserGradeSCO();
    englishUserGradeSCO.userId = userId;

    // Show loading
    this.spinner.show();

    // Search
    this.englishService.searchEnglishLevel(englishUserGradeSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<EnglishUserGrade> = responseAPi;
        const englishLevel: EnglishUserGrade[] = typeResponse.data.elements;
        if (typeResponse.data.elements != null) {
          this.englishUserGrade = englishLevel[0];
          this.gradeValue = this.englishUserGrade.vusGrade;
        }

        // Hide loading
        this.spinner.hide();

        this.generateWord();
      },
      (error) => {
        // Hide loading
        this.spinner.hide();

        this.alertService.error(error);
      },
    );
  }

  /**
   * Set display image & voice
   */
  public setDisplayWordElements(english: English) {
    this.displayWord = english;
    this.displayImageUrl = AppSettings.ENGLISH_IMAGE_PATH + english.imageName;

    if (english.voiceUkFile != null) {
      this.displayUkVoiceUrl =
        AppSettings.ENGLISH_SOUND_PATH + english.voiceUkFile;
    } else {
      this.displayUkVoiceUrl = null;
    }

    if (english.voiceUsFile != null) {
      this.displayUsVoiceUrl =
        AppSettings.ENGLISH_SOUND_PATH + english.voiceUsFile;
    } else {
      this.displayUsVoiceUrl = null;
    }
  }

  /**
   * Search Word from server
   */
  public searchWord() {
    // Prepare search condition
    const englishSCO = new EnglishSCO();

    if (this.isSearchByCondition === false && this.englishUserGrade) {
      // Search by current user level
      const learnDaySearch = new SearchNumber();
      learnDaySearch.le = this.englishUserGrade.learnDay.id;
      englishSCO.learnDayId = learnDaySearch;

      const gradeIdSearch = new SearchNumber();
      gradeIdSearch.le = this.gradeValue.id;
      englishSCO.vusGradeId = gradeIdSearch;
    } else {
      // Search by conditions
      if (this.categoryValue) {
        const categoryIdSearch = new SearchNumber();
        categoryIdSearch.eq = this.categoryValue.id;
        englishSCO.categoryId = categoryIdSearch;
      }
      if (this.keyWordValue) {
        const keywordSearch = new SearchText();
        keywordSearch.like = this.keyWordValue;
        englishSCO.keyWord = keywordSearch;
      }
      if (this.gradeValue) {
        const gradeIdSearch = new SearchNumber();
        gradeIdSearch.le = this.gradeValue.id;
        englishSCO.vusGradeId = gradeIdSearch;
      }
    }

    // Set user id
    const userIdSearch: SearchNumber = new SearchNumber();
    userIdSearch.eq = this.currentUser.id;
    englishSCO.userId = userIdSearch;

    // Set max result to 50 record
    englishSCO.maxRecordPerPage = 50;

    // Show loading
    this.spinner.show();

    // Get list word
    this.englishService.searchLowLearnEnglish(englishSCO).subscribe(
      (data) => {
        // Get data
        const responseAPi: any = data;
        const typeResponse: ApiReponse<English> = responseAPi;
        if (typeResponse.data != null) {
          this.searchListWord = typeResponse.data.elements;

          // Get random index
          const randomIndex = this.getRandomInt(1, this.searchListWord.length);
          const english: English = this.searchListWord[randomIndex - 1];

          // Get word object
          this.setDisplayWordElements(english);

          // Remove selected word from list
          const index = this.searchListWord.findIndex((d) => d === english);
          this.searchListWord.splice(index, 1);

          // Check if have excellent icon
          this.checkShowExcelenceStar(english);
        } else {
          // Toast warning if not found
          this.toastr.warning(
            'No WORD with search condition, please select others',
          );

          // Set display word elements to null
          this.displayWord = null;
          this.displayImageUrl = null;
          this.displayUkVoiceUrl = null;
          this.displayUsVoiceUrl = null;
        }

        // Hide loading
        this.spinner.hide();
      },
      (error) => {
        this.processError(error);
      },
    );
  }

  ////////////////////
  // BOTTON CONTROL //
  ////////////////////

  /**
   * Go to google translate button
   */
  public goToGooleTranslate() {
    if (this.displayWord) {
      window.open(
        AppSettings.GOOGLE_TRANSLATE_WORD_URL + this.displayWord.keyWord,
      );
    } else {
      // Toast warning if not found
      this.toastr.warning('Need to generate a WORD first');
    }
  }

  /**
   * Go to cambridge dictionary button
   */
  public goToCambridgeDictionary() {
    if (this.displayWord) {
      window.open(
        AppSettings.CAMBRIDGE_DICTIONATY_WORD_URL +
          this.displayWord.keyWord.split(' ').join('-'),
      );
    } else {
      // Toast warning if not found
      this.toastr.warning('Need to generate a WORD first');
    }
  }

  /**
   * Generate new word
   */
  public generateWord() {
    if (this.isFormValid()) {
      // Set boolean to false
      this.isAddLearn = false;
      this.isClickImage = false;
      this.totalUsVoice = 0;
      this.totalUkVoice = 0;
      this.getWordDate = new Date();
      this.playVoiceDate = new Date();

      // Process to get new word
      if (
        this.isHaveNewSearch() ||
        this.searchListWord == null ||
        this.searchListWord.length === 0
      ) {
        // Search from server
        this.searchWord();

        // Save search condition
        this.keyWordValueSave = this.keyWordValue;
        this.categoryValueSave = this.categoryValue;
        this.gradeValueSave = this.gradeValue;
      } else {
        // Chose random word to display
        const randomIndex = this.getRandomInt(1, this.searchListWord.length);
        const english: English = this.searchListWord[randomIndex - 1];

        // Set display word elements
        this.setDisplayWordElements(english);

        // Remove selected word from list
        const index = this.searchListWord.findIndex((d) => d === english);
        this.searchListWord.splice(index, 1);

        // Check if have excellent icon
        this.checkShowExcelenceStar(english);
      }
    }
  }

  /**
   * Click full screen
   */
  public clickFullScreen() {
    this.isFullScreen = true;
    const elem = document.getElementById('englishFunction') as HTMLElement & {
      // const elem = document.documentElement as HTMLElement & {
      mozRequestFullScreen(): Promise<void>;
      webkitRequestFullscreen(): Promise<void>;
      msRequestFullscreen(): Promise<void>;
    };

    if (elem.requestFullscreen) {
      elem.requestFullscreen();
    } else if (elem.mozRequestFullScreen) {
      /* Firefox */
      elem.mozRequestFullScreen();
    } else if (elem.webkitRequestFullscreen) {
      /* Chrome, Safari and Opera */
      elem.webkitRequestFullscreen();
    } else if (elem.msRequestFullscreen) {
      /* IE/Edge */
      elem.msRequestFullscreen();
    }
  }

  /**
   * Click exit full screen
   */
  public clickExitFullScreen() {
    this.isFullScreen = false;
    const elem = document as Document & {
      mozCancelFullScreen(): Promise<void>;
      webkitExitFullscreen(): Promise<void>;
      msExitFullscreen(): Promise<void>;
    };
    if (elem.exitFullscreen) {
      elem.exitFullscreen();
    } else if (elem.mozCancelFullScreen) {
      /* Firefox */
      elem.mozCancelFullScreen();
    } else if (elem.webkitExitFullscreen) {
      /* Chrome, Safari and Opera */
      elem.webkitExitFullscreen();
    } else if (elem.msExitFullscreen) {
      /* IE/Edge */
      elem.msExitFullscreen();
    }
  }

  /**
   * Play UK Voice botton
   */
  public playUkVoice() {
    const durationVoiceTime =
      (new Date().getTime() - this.playVoiceDate.getTime()) / 1000;
    if (durationVoiceTime < 1) {
      // Toast warning if not found
      this.toastr.warning('Click too fast - Nhấn nhanh quá');
    } else {
      this.playVoiceDate = new Date();
      // Count
      this.totalUkVoice = this.totalUkVoice + 1;

      // Call voice
      if (this.displayWord) {
        const audio = new Audio();
        audio.src = this.displayUkVoiceUrl;
        audio.load();
        audio.play();

        // Check & add to learn
        if (this.isValidAddLearnWord() === true) {
          this.addLearn(this.currentUser, this.displayWord);
        }
      } else {
        // Toast warning if not found
        this.toastr.warning('For UK Voice: Need to generate a WORD first');
      }
    }

    // Focus on search button
    this.forcusObjet(this.searchButton);
  }

  /**
   * Play US Voice botton
   */
  public playUsVoice() {
    const durationVoiceTime =
      (new Date().getTime() - this.playVoiceDate.getTime()) / 1000;
    if (durationVoiceTime < 1) {
      // Toast warning if not found
      this.toastr.warning('Click too fast - Nhấn nhanh quá');
    } else {
      this.playVoiceDate = new Date();
      // Count
      this.totalUsVoice = this.totalUsVoice + 1;

      // Call voice
      if (this.displayWord) {
        const audio = new Audio();
        audio.src = this.displayUsVoiceUrl;
        audio.load();
        audio.play();

        // Check & add to learn
        if (this.isValidAddLearnWord() === true) {
          this.addLearn(this.currentUser, this.displayWord);
        }
      } else {
        // Toast warning if not found
        this.toastr.warning('For US Voice: Need to generate a WORD first');
      }
    }

    // Focus on search button
    this.forcusObjet(this.searchButton);
  }

  /**
   * Add Learn
   */
  public addLearn(user: User, english: English) {
    // Call API to add learn
    if (user != null && english != null) {
      // Prepare vo
      const englishLearnRecord: EnglishLearnRecord = new EnglishLearnRecord();
      englishLearnRecord.english = english;

      // Call API count learn
      this.englishService.addLearnRecord(englishLearnRecord).subscribe(
        (data) => {
          // Success
          // Increase learned times
          this.numberOfLearned = this.numberOfLearned + 1;

          // Check add learn flag to true
          this.isAddLearn = true;
        },
        (error) => {
          // Error
        },
      );
    }
  }

  /**
   * Show dialog list word
   */
  public showDialogLearnedWord() {
    if (this.numberOfLearned > 0) {
      this.englishDialogService
        .showLearnedWord(
          'LEARNED WORDS',
          'You are seeing your list of word that learned by today',
        )
        .then(
          (result) => {
            if (result) {
            }
          },
          (reason) => {
            console.log('showDialogLearnedWord reason:' + reason);
          },
        );
    } else {
      this.toastr.warning(
        'You have not learned any word by today, let try hard',
      );
    }
  }

  /**
   * Click image
   */
  public onClickImage() {
    // Check click image to true
    this.isClickImage = true;

    // Play voice
    this.playUkVoice();
  }

  /**
   * Submit checking text of word
   */
  onSubmitTypeWord(inputWord: string) {
    if (!this.isClickImage && inputWord != this.inputWordSave) {
      // Save latest input
      this.inputWordSave = inputWord;

      if (inputWord.toUpperCase() === this.displayWord.keyWord.toUpperCase()) {
        // Show success message
        this.toastr.success(inputWord + ' is the corrected word');

        // Check click image to true
        this.isClickImage = true;

        // Play voice
        this.playUkVoice();

        // Add learn
        this.addLearn(this.currentUser, this.displayWord);
      } else {
        this.toastr.warning('Not corrected ! Try again');
      }
    }
  }

  ///////////////////
  // GENERAL CHECK //
  ///////////////////
  /**
   * Process error in case have error call API
   */
  public processError(error: any) {
    // Hide loading
    this.spinner.hide();
  }

  /**
   * Focus on object
   */
  public forcusObjet(object: any) {
    object.focus();
  }

  /**
   * Get a random number between range min & max
   */
  public getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  /**
   * Have any new search condition
   */
  public isHaveNewSearch() {
    let result = false;
    if (this.firstSearch === true) {
      result = true;
      this.firstSearch = false;
    }

    if (result === false) {
      if (this.keyWordValue !== this.keyWordValueSave) {
        result = true;
        this.isSearchByCondition = true;
      }
      if (this.categoryValue !== this.categoryValueSave) {
        result = true;
        this.isSearchByCondition = true;
      }
      if (this.gradeValue !== this.gradeValueSave) {
        result = true;
        this.isSearchByCondition = true;
      }
    }
    return result;
  }

  /**
   * Validate to show exellence icon
   */
  public checkShowExcelenceStar(english: English) {
    // Set default to false
    this.isDisplayStart1 = false;
    this.isDisplayStart2 = false;
    this.isDisplayStart3 = false;
    this.isDisplayStart4 = false;
    this.isDisplayStart5 = false;

    // Set display exellence based on number of learned
    if (english != null) {
      const learnTime = english.recordTimes;
      if (learnTime > 0) {
        this.isDisplayStart1 = true;
      }
      if (learnTime > 5) {
        this.isDisplayStart2 = true;
      }
      if (learnTime > 10) {
        this.isDisplayStart3 = true;
      }
      if (learnTime > 20) {
        this.isDisplayStart4 = true;
      }
      if (learnTime > 30) {
        this.isDisplayStart5 = true;
      }
    }
  }

  /**
   * Check if valid to add learn
   */
  public isValidAddLearnWord() {
    let result = false;

    if (this.isAddLearn === false) {
      // Valid if learn voice us >= 3
      if (this.totalUsVoice >= 4) {
        result = true;
      }

      // Valid if learn voice uk >= 3
      if (this.totalUkVoice >= 4) {
        result = true;
      }

      // Valid if learn uk >= 2 and us >= 1
      if (this.totalUsVoice >= 2 && this.totalUkVoice >= 2) {
        result = true;
      }

      // Valid if learn >= 20 seconds
      const learnTime =
        (new Date().getTime() - this.getWordDate.getTime()) / 1000;
      if (learnTime >= this.secondToPass) {
        result = true;
      }
    }

    return result;
  }

  /**
   * Validate form
   */
  public isFormValid() {
    let result = true;
    if (this.categoryControl.status === 'INVALID') {
      result = false;
    }
    if (this.gradeControl.status === 'INVALID') {
      result = false;
    }

    return result;
  }
}
