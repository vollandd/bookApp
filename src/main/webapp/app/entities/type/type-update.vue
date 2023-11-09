<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bookApp.type.home.createOrEditLabel"
          data-cy="TypeCreateUpdateHeading"
          v-text="t$('bookApp.type.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="type.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="type.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('bookApp.type.nameType')" for="type-nameType"></label>
            <input
              type="text"
              class="form-control"
              name="nameType"
              id="type-nameType"
              data-cy="nameType"
              :class="{ valid: !v$.nameType.$invalid, invalid: v$.nameType.$invalid }"
              v-model="v$.nameType.$model"
            />
          </div>
          <div class="form-group">
            <label v-text="t$('bookApp.type.book')" for="type-book"></label>
            <select
              class="form-control"
              id="type-books"
              data-cy="book"
              multiple
              name="book"
              v-if="type.books !== undefined"
              v-model="type.books"
            >
              <option v-bind:value="getSelected(type.books, bookOption)" v-for="bookOption in books" :key="bookOption.id">
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
<script lang="ts" src="./type-update.component.ts"></script>
