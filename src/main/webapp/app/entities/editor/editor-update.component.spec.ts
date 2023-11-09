/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EditorUpdate from './editor-update.vue';
import EditorService from './editor.service';
import AlertService from '@/shared/alert/alert.service';

type EditorUpdateComponentType = InstanceType<typeof EditorUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const editorSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EditorUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Editor Management Update Component', () => {
    let comp: EditorUpdateComponentType;
    let editorServiceStub: SinonStubbedInstance<EditorService>;

    beforeEach(() => {
      route = {};
      editorServiceStub = sinon.createStubInstance<EditorService>(EditorService);
      editorServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          editorService: () => editorServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EditorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.editor = editorSample;
        editorServiceStub.update.resolves(editorSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(editorServiceStub.update.calledWith(editorSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        editorServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EditorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.editor = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(editorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        editorServiceStub.find.resolves(editorSample);
        editorServiceStub.retrieve.resolves([editorSample]);

        // WHEN
        route = {
          params: {
            editorId: '' + editorSample.id,
          },
        };
        const wrapper = shallowMount(EditorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.editor).toMatchObject(editorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        editorServiceStub.find.resolves(editorSample);
        const wrapper = shallowMount(EditorUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
