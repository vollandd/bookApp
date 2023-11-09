import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import TypeService from './type.service';
import { type IType } from '@/shared/model/type.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Type',
  setup() {
    const { t: t$ } = useI18n();
    const typeService = inject('typeService', () => new TypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const types: Ref<IType[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveTypes = async () => {
      isFetching.value = true;
      try {
        const res = await typeService().retrieve();
        types.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveTypes();
    };

    onMounted(async () => {
      await retrieveTypes();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IType) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeType = async () => {
      try {
        await typeService().delete(removeId.value);
        const message = t$('bookApp.type.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveTypes();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      types,
      handleSyncList,
      isFetching,
      retrieveTypes,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeType,
      t$,
    };
  },
});
