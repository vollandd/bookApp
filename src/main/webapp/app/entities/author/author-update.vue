<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="bookApp.author.home.createOrEditLabel"
          data-cy="AuthorCreateUpdateHeading"
          v-text="t$('bookApp.author.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="author.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="author.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('bookApp.author.firstName')" for="author-firstName"></label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="author-firstName"
              data-cy="firstName"
              :class="{ valid: !v$.firstName.$invalid, invalid: v$.firstName.$invalid }"
              v-model="v$.firstName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('bookApp.author.lastName')" for="author-lastName"></label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="author-lastName"
              data-cy="lastName"
              :class="{ valid: !v$.lastName.$invalid, invalid: v$.lastName.$invalid }"
              v-model="v$.lastName.$model"
            />
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
<script lang="ts" src="./author-update.component.ts"></script>
