import { type IBook } from '@/shared/model/book.model';

export interface IAuthor {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  book?: IBook | null;
}

export class Author implements IAuthor {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public book?: IBook | null,
  ) {}
}
