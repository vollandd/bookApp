<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="editor">
        <h2 class="jh-entity-heading" data-cy="editorDetailsHeading">
          <span v-text="t$('bookApp.editor.detail.title')"></span> {{ editor.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('bookApp.editor.editorName')"></span>
          </dt>
          <dd>
            <span>{{ editor.editorName }}</span>
          </dd>
          <dt>
            <span v-text="t$('bookApp.editor.book')"></span>
          </dt>
          <dd>
            <div v-if="editor.book">
              <router-link :to="{ name: 'BookView', params: { bookId: editor.book.id } }">{{ editor.book.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="editor.id" :to="{ name: 'EditorEdit', params: { editorId: editor.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./editor-details.component.ts"></script>
