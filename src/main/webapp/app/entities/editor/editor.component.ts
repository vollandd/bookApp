import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EditorService from './editor.service';
import { type IEditor } from '@/shared/model/editor.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Editor',
  setup() {
    const { t: t$ } = useI18n();
    const editorService = inject('editorService', () => new EditorService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const editors: Ref<IEditor[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEditors = async () => {
      isFetching.value = true;
      try {
        const res = await editorService().retrieve();
        editors.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEditors();
    };

    onMounted(async () => {
      await retrieveEditors();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEditor) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEditor = async () => {
      try {
        await editorService().delete(removeId.value);
        const message = t$('bookApp.editor.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEditors();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      editors,
      handleSyncList,
      isFetching,
      retrieveEditors,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEditor,
      t$,
    };
  },
});
