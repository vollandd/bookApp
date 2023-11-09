import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import BookService from './book.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AuthorService from '@/entities/author/author.service';
import { type IAuthor } from '@/shared/model/author.model';
import EditorService from '@/entities/editor/editor.service';
import { type IEditor } from '@/shared/model/editor.model';
import { type IBook, Book } from '@/shared/model/book.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BookUpdate',
  setup() {
    const bookService = inject('bookService', () => new BookService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const book: Ref<IBook> = ref(new Book());

    const authorService = inject('authorService', () => new AuthorService());

    const authors: Ref<IAuthor[]> = ref([]);

    const editorService = inject('editorService', () => new EditorService());

    const editors: Ref<IEditor[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveBook = async bookId => {
      try {
        const res = await bookService().find(bookId);
        book.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.bookId) {
      retrieveBook(route.params.bookId);
    }

    const initRelationships = () => {
      authorService()
        .retrieve()
        .then(res => {
          authors.value = res.data;
        });
      editorService()
        .retrieve()
        .then(res => {
          editors.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      bookName: {},
      authors: {},
      types: {},
      editor: {},
    };
    const v$ = useVuelidate(validationRules, book as any);
    v$.value.$validate();

    return {
      bookService,
      alertService,
      book,
      previousState,
      isSaving,
      currentLanguage,
      authors,
      editors,
      v$,
      t$,
    };
  },
  created(): void {
    this.book.authors = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.book.id) {
        this.bookService()
          .update(this.book)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('bookApp.book.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.bookService()
          .create(this.book)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('bookApp.book.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option): any {
      if (selectedVals) {
        return selectedVals.find(value => option.id === value.id) ?? option;
      }
      return option;
    },
  },
});
