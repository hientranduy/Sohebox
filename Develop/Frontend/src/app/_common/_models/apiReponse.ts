import { PageResultVO } from "./pageResultVO";

export class ApiReponse<Data> {
  message: string;
  errors: Array<string>;
  data: PageResultVO<Data>;
}
