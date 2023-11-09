/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Editor from './editor.vue';
import EditorService from './editor.service';
import AlertService from '@/shared/alert/alert.service';

type EditorComponentType = InstanceType<typeof Editor>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Editor Management Component', () => {
    let editorServiceStub: SinonStubbedInstance<EditorService>;
    let mountOptions: MountingOptions<EditorComponentType>['global'];

    beforeEach(() => {
      editorServiceStub = sinon.createStubInstance<EditorService>(EditorService);
      editorServiceStub.retrieve.resolves({ headers: {} });

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
          editorService: () => editorServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        editorServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Editor, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(editorServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.editors[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: EditorComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Editor, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        editorServiceStub.retrieve.reset();
        editorServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        editorServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeEditor();
        await comp.$nextTick(); // clear components

        // THEN
        expect(editorServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(editorServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
