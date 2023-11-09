<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bookApp.editor.home.createOrEditLabel"
          data-cy="EditorCreateUpdateHeading"
          v-text="t$('bookApp.editor.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="editor.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="editor.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('bookApp.editor.editorName')" for="editor-editorName"></label>
            <input
              type="text"
              class="form-control"
              name="editorName"
              id="editor-editorName"
              data-cy="editorName"
              :class="{ valid: !v$.editorName.$invalid, invalid: v$.editorName.$invalid }"
              v-model="v$.editorName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('bookApp.editor.book')" for="editor-book"></label>
            <select class="form-control" id="editor-book" data-cy="book" name="book" v-model="editor.book">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="editor.book && bookOption.id === editor.book.id ? editor.book : bookOption"
                v-for="bookOption in books"
                :key="bookOption.id"
              >
                {{ bookOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./editor-update.component.ts"></script>
