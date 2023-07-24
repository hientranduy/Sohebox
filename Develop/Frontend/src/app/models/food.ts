import { FoodType } from '@app/models/foodType';

export class Food {
  id: number;
  name: string;
  imageName: string;
  description: string;
  locationNote: string;
  type: FoodType;
  category: FoodType;
  isFastFood: Boolean;
  recipe: Blob;
  urlReference: string;

  // Sub fields
  imageExtention: string;
  imageFile: string;
  recipeString: Blob;
}
