/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Type from './type.vue';
import TypeService from './type.service';
import AlertService from '@/shared/alert/alert.service';

type TypeComponentType = InstanceType<typeof Type>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Type Management Component', () => {
    let typeServiceStub: SinonStubbedInstance<TypeService>;
    let mountOptions: MountingOptions<TypeComponentType>['global'];

    beforeEach(() => {
      typeServiceStub = sinon.createStubInstance<TypeService>(TypeService);
      typeServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          typeService: () => typeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        typeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Type, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(typeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.types[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: TypeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Type, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        typeServiceStub.retrieve.reset();
        typeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        typeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeType();
        await comp.$nextTick(); // clear components

        // THEN
        expect(typeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(typeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
