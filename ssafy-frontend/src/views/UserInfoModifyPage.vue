<template>
  <div>
    <ImgBanner>
      <!-- <div
        class="text-center text-white"
        style="line-height:1.2em;font-size:2.5em;"
        slot="text"
        v-resize-text
      ></div>-->
    </ImgBanner>

    <v-form ref="form" v-model="valid" lazy-validation>
      <v-container fluid style="width:700px; padding-bottom:80px">
        <h2 class="text-center mt-5">내 정보 수정</h2>
        <br />
        <v-text-field
          v-model="computedUser.name"
          :rules="nameRules"
          label="Name"
          counter="20,"
          required
          autocomplete="name"
        ></v-text-field>

        <!-- <div style="padding-bottom:30px">
          <v-text-field v-model="computedUser.email" :rules="emailRules" label="E-mail" required></v-text-field>
          <v-btn @click="emailcheck" small color="blue" style="float:right; color:white">중복 확인</v-btn>
        </div> -->

        <v-text-field
          v-model="computedUser.phone"
          label="Phone number"
          :rules="[rules.required, rules.is_num, rules.min_11]"
          required
          autocomplete="tel"
        ></v-text-field>

        <div class="btn" style="float:right">
          <!-- <v-btn color="primary" class="mr-4"  @click="pwdmodi">비밀번호 수정</v-btn> -->
          <v-btn color="error" class="mr-4" @click="reset">Reset</v-btn>
          <v-btn :disabled="!valid" color="success" class="mr-4" @click="validate">수정!</v-btn>
        </div>
      </v-container>
    </v-form>
  </div>
</template>



<script>
import ImgBanner from "../components/ImgBanner";
import ResizeText from "vue-resize-text";
import http from "../http-common";
export default {
  name: "SignUpPage",
  components: {
    ImgBanner
  },
  directives: {
    ResizeText
  },
  data: () => ({
    user: {
      name: "",
      email: "",
      phone: ""
    },
    dialog: false,

    show1: false,
    valid: true,

    rules: {
      required: value => !!value || "Required.",
      min_8: v => v.length >= 8 || "Min 8 characters",
      min_4: v => v.length >= 4 || "Min 4 characters",
      min_11: v => v.length == 11 || "휴대폰번호 형식에 맞게 입력해주세요!",
      is_num: v => !isNaN(v) || "Please input number",
      emailMatch: () => "The email and password you entered don't match"
    },
    nameRules: [
      v => !!v || "Name is required",
      v => (v && v.length <= 20) || "Name must be less than 20 characters"
    ],
    emailRules: [
      v => !!v || "E-mail is required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ]
  }),
  methods: {
    getUserDetail() {
      console.log("getUserDetail active");
      let myEmail = localStorage.getItem("email");
      http
        .post(`/myselfDetail/${myEmail}`)
        .then(res => {
          // console.log(res)
          this.user.email = res.data.email;
          this.user.name = res.data.name;
          this.user.phone = res.data.phone;
        })
        .catch(err => {
          console.log(err);
        });
    },
    validate() {
      if (this.$refs.form.validate()) {
        // let formData = new formData()
        // formData.append('email', this.email)
        // formData.append('pwd', this.pwd)
        // formData.append('phone', this.phone)
        http
          .post("/updateMyself", this.user, this.$store.getters.requestHeader)
          .then(res => {
            console.log(res);
            if (
              res.data.state == "succ" &&
              this.$store.getters.isLoggedIn == true
            ) {
              alert("수정 성공");
            } else {
              alert("수정 오류입니다.");
            }
            this.$router.push("/userinfo");
          })
          .catch(err => {
            console.log(err);
          });
        this.snackbar = true;
      }
    },
    emailcheck() {
      http
        .post("/emailCheck", {
          email: this.credentials.email
        })
        .then(respone => {
          console.log(respone);
          if (respone.data.state == "succ") {
            alert("중복된 E-mail입니다. 다른 E-mail을 입력해주세요.");
            this.credentials.email = "";
          } else {
            alert("사용 가능합니다.");
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    reset() {
      this.$refs.form.reset();
    }
  },
  mounted() {
    this.getUserDetail();
  },

  computed: {
    computedUser: function() {
      return this.user;
    },

    requestHeader: function() {}
  }
};
</script>
<style scoped>
</style>