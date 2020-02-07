<template>
  <modal name="login-modal" transition="pop-out" :width="modalWidth" :height="450">
    <div class="box">
      <div class="box-part" id="bp-left">
        <div class="partition" id="partition-register">
          <div class="partition-title">LOGIN</div>
          <div class="partition-form">
            <!-- <form action="" method="post"> -->
            <!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> -->
            <input
              id="n-email"
              name="email"
              type="text"
              placeholder="Email"
              v-model="credentials.email"
            />
            <input
              id="n-password2"
              type="password"
              name="pwd"
              placeholder="Password"
              v-model="credentials.pwd"
            />
            <div style="margin-top: 42px"></div>

            <div class="button-set">
              <router-link to="/signup" tag="span">
                <button id="goto-signup-btn">Sign Up</button>
              </router-link>
              <button id="register-btn" @click="login">Login</button>
            </div>

            <!-- </form> -->

            <button class="large-btn google-btn" @click="loginWithGoogle">
              connect with
              <span>google</span>
            </button>
            <button class="large-btn facebook-btn" @click="loginWithFackbook">
              connect with
              <span>facebook</span>
            </button>

            <div class="button-set" style="padding-top:20px">
             
                <button id="goto-signup-btn" @click="findemail">이메일 찾기</button>
             
                <button id="goto-signup-btn" style="float:right" @click="findpwd">비밀번호 찾기</button>
              
            </div>
          </div>
        </div>
      </div>
      <!-- <div class="box-part" id="bp-right">
        <div class="box-messages">
        </div>
      </div>-->
    </div>
  </modal>
</template>
<script>
import FirebaseService from "@/services/FirebaseService";
import http from "../http-common";
import axios from "axios";
const MODAL_WIDTH = 328;
export default {
  name: "LoginModal",

  data() {
    return {
      modalWidth: MODAL_WIDTH,
      credentials: {
        email: "",
        pwd: ""
      }
    };
  },

  created() {
    this.modalWidth =
      window.innerWidth < MODAL_WIDTH ? MODAL_WIDTH / 2 : MODAL_WIDTH;
  },
  methods: {
    async loginWithGoogle() {
      const result = await FirebaseService.loginWithGoogle();
      this.$store.state.accessToken = result.credential.accessToken;
      this.$store.state.user = result.user;
      if (result) {
        this.$modal.hide("login-modal");
        //this.$router.push('/')
        console.log(this.$store.state.user);
      }
    },
    async loginWithFackbook() {
      const result = await FirebaseService.loginWithFackbook();
      // console.log(result)
      //this.$store.state.accessToken = result.credential.accessToken
      //this.$store.state.user = result.user
      this.$store.state.accessToken = result.credential.accessToken;
      this.$store.state.user = result.user;
      if (result) {
        this.$modal.hide("login-modal");
        //this.$router.push('/')
        console.log(this.$store.state.user);
      }
    },
    login() {
      console.log("login active");
      http
        .post("auth/signin", this.credentials)
        .then(res => {
          console.log(res);
          console.log(res.data.accessToken);
          if (res.data.username !== "") {
            localStorage.setItem("accessToken", res.data.accessToken);
            localStorage.setItem("user", res.data.username);
            localStorage.setItem("email", res.data.email);
            localStorage.setItem("phone", res.data.phone);
            this.$emit("checkLogIn");
            this.$router.push("/");
          }
          // this.$store.state.accessToken = res.data.accessToken
          // this.$store.state.user = res.data.username
          // this.$store.state.tokenType = 'Bearer'
          else {
            alert("로그인 오류!");
          }
          this.$modal.hide("login-modal");
        })
        .catch(error => {
          alert("회원정보가 올바르지 않습니다!");
          console.log(error);
        });
    },
    findemail(){
      this.$modal.hide("login-modal");
      this.$router.push("/findemail");      
    },
    findpwd(){
      this.$modal.hide("login-modal");
      this.$router.push("/findpwd");
    }
    
  }
};
</script>
<style lang="scss">
$background_color: #404142;
$google_color: #db4437;
$github_color: #dba226;
$facebook_color: #3880ff;

.box {
  background: white;
  overflow: hidden;
  width: 656px;
  height: 450px;
  border-radius: 2px;
  box-sizing: border-box;
  box-shadow: 0 0 40px black;
  color: #8b8c8d;
  font-size: 0;

  .box-part {
    display: inline-block;
    position: relative;
    vertical-align: top;
    box-sizing: border-box;
    height: 100%;
    width: 50%;

    &#bp-right {
      background: url("/static/panorama.jpg") no-repeat top left;
      border-left: 1px solid #eee;
    }
  }

  .box-messages {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
  }

  .box-error-message {
    position: relative;
    overflow: hidden;
    box-sizing: border-box;
    height: 0;
    line-height: 32px;
    padding: 0 12px;
    text-align: center;
    width: 100%;
    font-size: 11px;
    color: white;
    background: #f38181;
  }

  .partition {
    width: 100%;
    height: 100%;

    .partition-title {
      box-sizing: border-box;
      padding: 30px;
      width: 100%;
      text-align: center;
      letter-spacing: 1px;
      font-size: 20px;
      font-weight: 300;
    }

    .partition-form {
      padding: 0 20px;
      box-sizing: border-box;
    }
  }

  input[type="password"],
  input[type="text"] {
    display: block;
    box-sizing: border-box;
    margin-bottom: 4px;
    width: 100%;
    font-size: 12px;
    line-height: 2;
    border: 0;
    border-bottom: 1px solid #dddedf;
    padding: 4px 8px;
    font-family: inherit;
    transition: 0.5s all;
    outline: none;
  }

  button {
    background: white;
    border-radius: 4px;
    box-sizing: border-box;
    padding: 10px;
    letter-spacing: 1px;
    font-family: "Open Sans", sans-serif;
    font-weight: 400;
    min-width: 140px;
    margin-top: 8px;
    color: #8b8c8d;
    cursor: pointer;
    border: 1px solid #dddedf;
    text-transform: uppercase;
    transition: 0.1s all;
    font-size: 10px;
    outline: none;

    &:hover {
      border-color: mix(#dddedf, black, 90%);
      color: mix(#8b8c8d, black, 80%);
    }
  }

  .large-btn {
    width: 100%;
    background: white;

    span {
      font-weight: 600;
    }

    &:hover {
      color: white !important;
    }
  }

  .button-set {
    margin-bottom: 8px;
  }

  #register-btn,
  #signup-btn {
    margin-left: 8px;
  }

  .facebook-btn {
    border-color: $facebook_color;
    color: $facebook_color;

    &:hover {
      border-color: $facebook_color;
      background: $facebook_color;
    }
  }

  .github-btn {
    border-color: $github_color;
    color: $github_color;

    &:hover {
      border-color: $github_color;
      background: $github_color;
    }
  }

  .google-btn {
    border-color: $google_color;
    color: $google_color;

    &:hover {
      border-color: $google_color;
      background: $google_color;
    }
  }

  .autocomplete-fix {
    position: absolute;
    visibility: hidden;
    overflow: hidden;
    opacity: 0;
    width: 0;
    height: 0;
    left: 0;
    top: 0;
  }
}

.pop-out-enter-active,
.pop-out-leave-active {
  transition: all 0.5s;
}

.pop-out-enter,
.pop-out-leave-active {
  opacity: 0;
  transform: translateY(24px);
}
</style>