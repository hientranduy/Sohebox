import { EnglishType } from './englishType';

export class English {
  id: number;
  keyWord: string;
  wordLevel: EnglishType;
  category: EnglishType;
  learnDay: EnglishType;
  imageName: string;
  imageExtention: string;
  imageFile: string;
  explanationEn: string;
  explanationVn: string;
  voiceUkFile: string;
  voiceUsFile: string;
  recordTimes: number;
  vusGrade: EnglishType;
}
