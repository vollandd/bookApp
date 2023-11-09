import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EditorService from './editor.service';
import { type IEditor } from '@/shared/model/editor.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EditorDetails',
  setup() {
    const editorService = inject('editorService', () => new EditorService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const editor: Ref<IEditor> = ref({});

    const retrieveEditor = async editorId => {
      try {
        const res = await editorService().find(editorId);
        editor.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.editorId) {
      retrieveEditor(route.params.editorId);
    }

    return {
      alertService,
      editor,

      previousState,
      t$: useI18n().t,
    };
  },
});
