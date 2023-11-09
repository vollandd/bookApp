import { defineComponent, provide } from 'vue';

import BookService from './book/book.service';
import EditorService from './editor/editor.service';
import AuthorService from './author/author.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('bookService', () => new BookService());
    provide('editorService', () => new EditorService());
    provide('authorService', () => new AuthorService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
