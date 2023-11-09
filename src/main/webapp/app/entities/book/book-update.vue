<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bookApp.book.home.createOrEditLabel"
          data-cy="BookCreateUpdateHeading"
          v-text="t$('bookApp.book.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="book.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="book.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('bookApp.book.bookName')" for="book-bookName"></label>
            <input
              type="text"
              class="form-control"
              name="bookName"
              id="book-bookName"
              data-cy="bookName"
              :class="{ valid: !v$.bookName.$invalid, invalid: v$.bookName.$invalid }"
              v-model="v$.bookName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('bookApp.book.name')" for="book-name"></label>
            <select class="form-control" id="book-name" data-cy="name" name="name" v-model="book.name">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="book.name && authorOption.id === book.name.id ? book.name : authorOption"
                v-for="authorOption in authors"
                :key="authorOption.id"
              >
                {{ authorOption.id }}
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
<script lang="ts" src="./book-update.component.ts"></script>
