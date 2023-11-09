import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Book = () => import('@/entities/book/book.vue');
const BookUpdate = () => import('@/entities/book/book-update.vue');
const BookDetails = () => import('@/entities/book/book-details.vue');

const Editor = () => import('@/entities/editor/editor.vue');
const EditorUpdate = () => import('@/entities/editor/editor-update.vue');
const EditorDetails = () => import('@/entities/editor/editor-details.vue');

const Author = () => import('@/entities/author/author.vue');
const AuthorUpdate = () => import('@/entities/author/author-update.vue');
const AuthorDetails = () => import('@/entities/author/author-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'book',
      name: 'Book',
      component: Book,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/new',
      name: 'BookCreate',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/:bookId/edit',
      name: 'BookEdit',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'book/:bookId/view',
      name: 'BookView',
      component: BookDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'editor',
      name: 'Editor',
      component: Editor,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'editor/new',
      name: 'EditorCreate',
      component: EditorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'editor/:editorId/edit',
      name: 'EditorEdit',
      component: EditorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'editor/:editorId/view',
      name: 'EditorView',
      component: EditorDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'author',
      name: 'Author',
      component: Author,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'author/new',
      name: 'AuthorCreate',
      component: AuthorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'author/:authorId/edit',
      name: 'AuthorEdit',
      component: AuthorUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'author/:authorId/view',
      name: 'AuthorView',
      component: AuthorDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
