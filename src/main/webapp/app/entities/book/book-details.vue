<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="book">
        <h2 class="jh-entity-heading" data-cy="bookDetailsHeading"><span v-text="t$('bookApp.book.detail.title')"></span> {{ book.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('bookApp.book.bookName')"></span>
          </dt>
          <dd>
            <span>{{ book.bookName }}</span>
          </dd>
          <dt>
            <span v-text="t$('bookApp.book.author')"></span>
          </dt>
          <dd>
            <span v-for="(author, i) in book.authors" :key="author.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'AuthorView', params: { authorId: author.id } }">{{ author.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('bookApp.book.editor')"></span>
          </dt>
          <dd>
            <div v-if="book.editor">
              <router-link :to="{ name: 'EditorView', params: { editorId: book.editor.id } }">{{ book.editor.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="book.id" :to="{ name: 'BookEdit', params: { bookId: book.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./book-details.component.ts"></script>
