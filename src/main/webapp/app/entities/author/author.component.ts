import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import AuthorService from './author.service';
import { type IAuthor } from '@/shared/model/author.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Author',
  setup() {
    const { t: t$ } = useI18n();
    const authorService = inject('authorService', () => new AuthorService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const authors: Ref<IAuthor[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveAuthors = async () => {
      isFetching.value = true;
      try {
        const res = await authorService().retrieve();
        authors.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveAuthors();
    };

    onMounted(async () => {
      await retrieveAuthors();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IAuthor) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeAuthor = async () => {
      try {
        await authorService().delete(removeId.value);
        const message = t$('bookApp.author.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveAuthors();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      authors,
      handleSyncList,
      isFetching,
      retrieveAuthors,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeAuthor,
      t$,
    };
  },
});
