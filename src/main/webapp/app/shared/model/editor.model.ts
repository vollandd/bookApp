import { type IBook } from '@/shared/model/book.model';

export interface IEditor {
  id?: number;
  editorName?: string | null;
  book?: IBook | null;
}

export class Editor implements IEditor {
  constructor(
    public id?: number,
    public editorName?: string | null,
    public book?: IBook | null,
  ) {}
}
