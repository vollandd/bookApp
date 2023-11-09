import { type IAuthor } from '@/shared/model/author.model';
import { type IEditor } from '@/shared/model/editor.model';

export interface IBook {
  id?: number;
  bookName?: string | null;
  authors?: IAuthor[] | null;
  editor?: IEditor | null;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public bookName?: string | null,
    public authors?: IAuthor[] | null,
    public editor?: IEditor | null,
  ) {}
}
