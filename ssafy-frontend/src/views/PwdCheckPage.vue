<template>
  <div>
    <ImgBanner>
      <div
        class="text-center text-white"
        style="line-height:1.2em;font-size:2.5em;"
        slot="text"
        v-resize-text
      >Pwd Check</div>
    </ImgBanner>
    
    <v-form ref="form" v-if="pwdCheck" v-model="valid" lazy-validation>
      <v-container  fluid style="width:700px; padding-top:80px; padding-bottom:120px">
        <h6 style="color:darkred">회원 정보를 확인하려면 비밀번호 확인이 필요합니다!</h6>
      <v-text-field
          v-model="user.pwd"
          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
          type="password"
        ></v-text-field>
      <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate" style="float:right">비밀번호 확인</v-btn>
      </v-container>
    </v-form>

    <!-- <UserInfo v-if="isPwdRight" :isPwdRight="isPwdRight" :userDetail="userDetail"></UserInfo> -->
  </div>
</template>

<script>
import ImgBanner from "../components/ImgBanner";
import http from "../http-common";
import ResizeText from "vue-resize-text";
import UserInfo from "../components/UserInfo";
export default {
  name: "PwdCheckPage",
  components: {
    ImgBanner,
    UserInfo
  },
directives: {
    ResizeText
  },
  computed: {
    requestHeader: function() {
      return this.$store.getters.requestHeader
    }
  },

  data() {
    return {
      pwdCheck : true,
      isPwdRight: false,
      user: {
        email: localStorage.getItem('email'),
        pwd: "",
        //Authorization : 'Bearer '+this.$store.state.accessToken
      },

      show1: false,
      valid: true,
      rules: {
        required: value => !!value || "Required.",
        min_8: v => v.length >= 8 || "Min 8 characters",
        min_4: v => v.length >= 4 || "Min 8 characters",
        is_num: v => !isNaN(v) || "Please input number",
        emailMatch: () => "The email and password you entered don't match"
      },
    }
  },
  methods: {
    
    validate() {
      if (this.$refs.form.validate()) {
      
        http
          .post('/pwdCheck',{
            email: this.user.email,
            pwd : this.user.pwd,
            
          }, this.$store.getters.requestHeader)
          .then(res => {
            console.log(res)
            console.log(this.$store.getters.isLoggedIn)
            if (res.data.state == 'succ') {
              this.$router.push('/userinfo')
            } else {
              alert('비밀번호 오류입니다.')
              this.$router.push('/pwdcheck')
            }
          })
          .catch(err => {
            alert('비밀번호 에러입니다.')
            console.log(err)
            this.$router.push('/pwdcheck')
          })
          this.pwdCheck = false
      }
    }
  },

  
}

</script>
<style scoped>

</style>