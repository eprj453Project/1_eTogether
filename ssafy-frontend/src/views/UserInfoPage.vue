<template>
  <div>
    <ImgBanner>
      <div
        class="text-center text-white"
        style="line-height:1.2em;font-size:2.5em;"
        slot="text"
        v-resize-text
      >User Info</div>
    </ImgBanner>

    <v-form ref="form" v-model="valid" lazy-validation>
      <v-container fluid style="width:700px; padding-bottom:80px">
        <h2 class="text-center mt-5">회원 정보</h2>

        <v-text-field v-model="user.name" label="name" readonly></v-text-field>

        <v-text-field v-model="user.email" label="E-mail" readonly></v-text-field>

        <v-text-field v-model="user.phone" label="Phone" readonly></v-text-field>

        <v-btn color="error" class="mr-4" style="float:left" @click="withdraw">탈퇴하기</v-btn>
        <template>
          
            <v-dialog v-model="dialog" persistent max-width="500px">
              <template v-slot:activator="{ on }">
                <v-btn color="primary" dark v-on="on">비밀번호 변경</v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">User Profile</span>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          v-model="pwd"
                          :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, rules.min_8]"
                          :type="show1 ? 'text' : 'password'"
                          name="input-10-1"
                          label="Password"
                          hint="At least 8 characters"
                          counter="16"
                          @click:append="show1 = !show1"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>

                <div style="margin:auto; text-align:center; width:200px; padding-bottom:80px; ">
                  <v-btn
                    color="blue darken-1"
                    style="float:left;"
                    outlined
                    @click="dialog = false"
                  >취소</v-btn>
                  <v-btn color="blue darken-1" style="float:right;" outlined @click="savepwd">저장</v-btn>
                </div>
              </v-card>
            </v-dialog>
        
        </template>
        <v-btn color="success" class="mr-4" to="/userinfomodify" style="float:right">수정하기</v-btn>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import ImgBanner from "../components/ImgBanner";
import ResizeText from "vue-resize-text";
import http from "../http-common";
export default {
  name: "UserInfoPage",
  components: {
    ImgBanner
  },
  directives: {
    ResizeText
  },
  data: () => ({
    show1: false,
    valid: true,
    isLogin: false,
    user: {
      name: "",
      email: "",
      phone: ""
    },
    pwd: "",
    dialog: false,
    rules: {
      required: value => !!value || "Required.",
      min_8: v => v.length >= 8 || "Min 8 characters",
      min_4: v => v.length >= 4 || "Min 8 characters",
      is_num: v => !isNaN(v) || "Please input number",
      emailMatch: () => "The email and password you entered don't match"
    },
    nameRules: [
      v => !!v || "Name is required",
      v => (v && v.length <= 10) || "Name must be less than 10 characters"
    ],
    emailRules: [
      v => !!v || "E-mail is required",
      v => /.+@.+\..+/.test(v) || "E-mail must be valid"
    ],
    phoneNumbers: ["010", "011"]
  }),
  methods: {
    validate() {
      if (this.$refs.form.validate()) {
        this.snackbar = true;
      }
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
    getUserInfo(UserEmail) {
      let myemail = localStorage.getItem("email");
      console.log(this.$store.getters.isLoggedIn);
      http
        .post(`/myselfDetail/${myemail}`, this.$store.getters.requestHeader)
        .then(res => {
          console.log(res);

          this.user.email = res.data.email;
          this.user.name = res.data.name;
          this.user.phone = res.data.phone;
          this.isLogin = this.$store.getters.isLoggedIn;
        })
        .catch(err => {
          consloe.log(err);
        });
      // this.user.name = localStorage.getItem('user')
      // this.user.email = localStorage.getItem('email')
      // this.user.phone = localStorage.getItem('phone')
    },
    savepwd() {
      http
        .post("/updatePwd", {
          email: this.user.email,
          pwd: this.pwd
        })

        .then(response => {
          window.console.log(response);
          if (response.data.state == "succ") {
            alert("비밀번호가 수정되었습니다!");
            this.dialog = false;
            this.$router.push("/userinfo");
          } else {
            alert("오류가 발생했습니다. 다시 시도해 주세요.");
          }
        });
    },
    withdraw() {
      if (confirm("탈퇴하시겠습니까?") == true) {         
        http
          .post("/deleteMyself", {
            email: this.user.email
          })
          //.then(response => (this.user = response.data ))
          .then(response => {
            window.console.log(response);
            if (response.data.state == "succ") {
             
              alert("탈퇴 하였습니다."); 
              this.logout();
              this.$router.push("/");  
              location.reload();          
                            
            } else {
              alert("오류가 발생했습니다. 다시 시도해 주세요.");
              this.$router.push("/userinfo");
            }
          })
          .catch(() => {
            this.errored = true;
            //  alert('error!!');
          })
          .finally(() => (this.loading = false));
      } else {
        return false;
      }
    },
    getUserName() {
      console.log("emit!");
      console.log("getUserName");
      console.log(localStorage.getItem("user"));
      this.username = localStorage.getItem("user");
      this.isLoggedIn = this.$store.getters.isLoggedIn;
    },
    logout() {
      http
        .post(
          "/logout",
          {
            email: localStorage.getItem("email")
          },
          this.$store.getters.requestHeader
        )
        .then(res => {
          console.log(res);
          if (res.data.state === "succ" && res.data.count == 1) {
            localStorage.clear();
            this.getUserName();
            this.$router.push("/");
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },

  mounted() {
    this.getUserInfo();
  }
};
</script>