/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeDetails from './type-details.vue';
import TypeService from './type.service';
import AlertService from '@/shared/alert/alert.service';

type TypeDetailsComponentType = InstanceType<typeof TypeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Type Management Detail Component', () => {
    let typeServiceStub: SinonStubbedInstance<TypeService>;
    let mountOptions: MountingOptions<TypeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      typeServiceStub = sinon.createStubInstance<TypeService>(TypeService);

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
          typeService: () => typeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeServiceStub.find.resolves(typeSample);
        route = {
          params: {
            typeId: '' + 123,
          },
        };
        const wrapper = shallowMount(TypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.type).toMatchObject(typeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeServiceStub.find.resolves(typeSample);
        const wrapper = shallowMount(TypeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
