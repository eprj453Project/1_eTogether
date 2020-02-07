<template>
<div v-if="isPwdRight">
  <v-form ref="form" v-model="valid" lazy-validation>
      <v-container fluid style="width:700px; padding-bottom:80px">
        <h2 class="text-center mt-5">회원 정보</h2>
        <v-text-field
          v-model="userDetail.name"
          label="nickname"
          counter="20,"
          required
          readonly
        ></v-text-field>
        <v-text-field 
          v-model="userDetail.email" 
          label="E-mail" 
          required 
          readonly="False"
        ></v-text-field>
        <v-text-field
          label="Phone"
          v-model="userDetail.phone"
          readonly
        ></v-text-field>
        <v-btn
          color="success"
          class="mr-4"
          @click="goToModify"
          style="float:right"
        >수정하기</v-btn>
      </v-container>
    </v-form>
  </div>
  <div v-else>
    <h1>비밀번호를 확인해주세요. 잘못된 접근입니다.</h1>
  </div>
</template>

<script>
export default {
  name: "UserInfo",
  data() {
    return {}
  },

  props: {
    isPwdRight : {
      Type: Boolean,
      required: true
    },
    userDetail : {
      Type: Object,
      required: true
    },
  },

  methods : {
    goToModify() {
      this.$router.push('/userinfomodify')
    },
  }
}
</script>

<style scoped>

</style>