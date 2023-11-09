/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EditorDetails from './editor-details.vue';
import EditorService from './editor.service';
import AlertService from '@/shared/alert/alert.service';

type EditorDetailsComponentType = InstanceType<typeof EditorDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const editorSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Editor Management Detail Component', () => {
    let editorServiceStub: SinonStubbedInstance<EditorService>;
    let mountOptions: MountingOptions<EditorDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      editorServiceStub = sinon.createStubInstance<EditorService>(EditorService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          editorService: () => editorServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        editorServiceStub.find.resolves(editorSample);
        route = {
          params: {
            editorId: '' + 123,
          },
        };
        const wrapper = shallowMount(EditorDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.editor).toMatchObject(editorSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        editorServiceStub.find.resolves(editorSample);
        const wrapper = shallowMount(EditorDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
