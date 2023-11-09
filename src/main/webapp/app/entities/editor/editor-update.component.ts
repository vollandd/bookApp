import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EditorService from './editor.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IEditor, Editor } from '@/shared/model/editor.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EditorUpdate',
  setup() {
    const editorService = inject('editorService', () => new EditorService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const editor: Ref<IEditor> = ref(new Editor());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      editorName: {},
      books: {},
    };
    const v$ = useVuelidate(validationRules, editor as any);
    v$.value.$validate();

    return {
      editorService,
      alertService,
      editor,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.editor.id) {
        this.editorService()
          .update(this.editor)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('bookApp.editor.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.editorService()
          .create(this.editor)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('bookApp.editor.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
