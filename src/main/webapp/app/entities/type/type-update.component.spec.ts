/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TypeUpdate from './type-update.vue';
import TypeService from './type.service';
import AlertService from '@/shared/alert/alert.service';

type TypeUpdateComponentType = InstanceType<typeof TypeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const typeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TypeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Type Management Update Component', () => {
    let comp: TypeUpdateComponentType;
    let typeServiceStub: SinonStubbedInstance<TypeService>;

    beforeEach(() => {
      route = {};
      typeServiceStub = sinon.createStubInstance<TypeService>(TypeService);
      typeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          typeService: () => typeServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.type = typeSample;
        typeServiceStub.update.resolves(typeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeServiceStub.update.calledWith(typeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        typeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.type = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(typeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        typeServiceStub.find.resolves(typeSample);
        typeServiceStub.retrieve.resolves([typeSample]);

        // WHEN
        route = {
          params: {
            typeId: '' + typeSample.id,
          },
        };
        const wrapper = shallowMount(TypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.type).toMatchObject(typeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        typeServiceStub.find.resolves(typeSample);
        const wrapper = shallowMount(TypeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
