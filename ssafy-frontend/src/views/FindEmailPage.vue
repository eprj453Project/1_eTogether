<template>
  <div>
    <ImgBanner>
      <!-- <div
        class="text-center text-white"
        style="line-height:1.2em;font-size:2.0em;"
        slot="text"
        v-resize-text
      >비밀번호 찾기</div> -->
    </ImgBanner>

    <v-form ref="form" v-model="valid" lazy-validation>
      <v-container fluid style="width:500px; padding-top:80px; padding-bottom:80px">
        <h2 style="text-align:center;">E-mail 찾기</h2>
        <br>        
        <v-text-field label="Name" v-model="name" type="text" required :rules="[v => !!v || '이름을 입력해주세요!']"></v-text-field>
        <v-text-field label="Phone number" v-model="phone"  autocomplete="tel" :rules="[rules.required, rules.is_num]"></v-text-field>
        <div style="text-align:center; margin:auto; padding-top:20px">
        <v-btn
          :disabled="!valid"
          color="success"
          outlined
          class="mr-4"
          @click="validate"          
        >이메일 찾기</v-btn>
        </div>
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
  name: "FindPwdPage",
  components: {
    ImgBanner,
    UserInfo
  },
  directives: {
    ResizeText
  },

  computed: {
    requestHeader: function() {
      return this.$store.getters.requestHeader;
    }
  },

  data() {
    return {
      valid: false,
      emailCheck: true,

      name: "",
      phone:"",

      rules: {
        required: value => !!value || "Required.",
        min_8: v => v.length >= 8 || "Min 8 characters",
        min_4: v => v.length >= 4 || "Min 8 characters",
        is_num: v => !isNaN(v) || "Please input number",
        emailMatch: () => "The email and password you entered don't match"
      },
      emailRules: [
        v => !!v || "가입한 E-mail을 입력해주세요!",
        v => /.+@.+\..+/.test(v) || "E-mail must be valid"
      ]
    };
  },
  methods: {
    validate() {
      if (this.$refs.form.validate()) {
        http
          .post("/findEmail", {           
            name: this.name,
            phone: this.phone
          })
          .then(res => {
            console.log(res);
             if (res.data.email != null) {
            alert("회원님이 가입하신 E-mail는 " + res.data.email + " 입니다." );
            this.$router.push("/");
          }else{
            alert("정보를 찾을 수 없습니다. 다시 입력해주세요." );
          } 
          })
          .catch(err => {
            // alert('비밀번호 오류입니다.')
            console.log(err);
          });
        this.emailCheck = false;
      }
    }
  }
};
</script>
<style scoped>
</style>