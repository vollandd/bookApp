import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TypeService from './type.service';
import { type IType } from '@/shared/model/type.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TypeDetails',
  setup() {
    const typeService = inject('typeService', () => new TypeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const type: Ref<IType> = ref({});

    const retrieveType = async typeId => {
      try {
        const res = await typeService().find(typeId);
        type.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.typeId) {
      retrieveType(route.params.typeId);
    }

    return {
      alertService,
      type,

      previousState,
      t$: useI18n().t,
    };
  },
});
