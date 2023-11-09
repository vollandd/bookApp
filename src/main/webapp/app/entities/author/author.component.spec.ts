/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Author from './author.vue';
import AuthorService from './author.service';
import AlertService from '@/shared/alert/alert.service';

type AuthorComponentType = InstanceType<typeof Author>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Author Management Component', () => {
    let authorServiceStub: SinonStubbedInstance<AuthorService>;
    let mountOptions: MountingOptions<AuthorComponentType>['global'];

    beforeEach(() => {
      authorServiceStub = sinon.createStubInstance<AuthorService>(AuthorService);
      authorServiceStub.retrieve.resolves({ headers: {} });

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
          authorService: () => authorServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        authorServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Author, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(authorServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.authors[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: AuthorComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Author, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        authorServiceStub.retrieve.reset();
        authorServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        authorServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAuthor();
        await comp.$nextTick(); // clear components

        // THEN
        expect(authorServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(authorServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
