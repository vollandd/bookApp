import { type IBook } from '@/shared/model/book.model';

export interface IType {
  id?: number;
  nameType?: string | null;
  books?: IBook[] | null;
}

export class Type implements IType {
  constructor(
    public id?: number,
    public nameType?: string | null,
    public books?: IBook[] | null,
  ) {}
}
