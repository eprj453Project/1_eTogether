<template>
  <div>
    <LoginModal @checkLogIn="getUserName"></LoginModal>
    <v-navigation-drawer v-model="sidebar" fixed temporary>
      <v-list>
        <h5 class="ml-5">hello</h5>
        <v-list-item v-if="setUserName" @click="pwdCheck">
          <!-- <v-list-item-action></v-list-item-action> -->
          {{setUserName}}
        </v-list-item>
        <v-list-item v-for="item in menuItems" :key="item.title" :to="item.path">
          <v-list-item-action></v-list-item-action>
          <v-list-item-content>{{ item.title }}</v-list-item-content>
        </v-list-item>
        <v-list-item v-if="setUserName" @click="$modal.show('login-modal')">
          <v-list-item-action></v-list-item-action>
          <v-list-item-content class="text-center">Logout</v-list-item-content>
        </v-list-item>
        <v-list-item v-else @click="$modal.show('login-modal')">
          <v-list-item-action></v-list-item-action>
          <v-list-item-content class="text-center">Login</v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <span class="hidden-sm-and-up">
        <v-app-bar-nav-icon @click="sidebar = !sidebar">
          <v-icon>menu</v-icon>
        </v-app-bar-nav-icon>
      </span>
      <v-toolbar-title>
        <router-link to="/" tag="span" style="cursor: pointer">
          <v-img :src="getImgUrl('etogether.png')" width="100px" />
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      
      <v-toolbar-items class="hidden-xs-only">
        <div class="menu" style="height:20px">       
       <v-btn text @click="clickproduct" style="height:64px" >Mart</v-btn>             
        <v-btn text v-if="username"  @click="clickmybudget" style="height:64px">My Budget</v-btn>
       
        
        <v-btn text @click="clickreview" style="height:64px">Review</v-btn>
        
        </div>
        <div  v-if="username"  class="text-center" style="padding-top:20px; padding-left:10px">
          <v-menu offset-y open-on-hover>
            <template v-slot:activator="{ on }">
              <span text v-on="on" style="padding-bottom:17px; font-weight:bold; color:darkblue" >  <i v-on="on" class="material-icons">account_circle</i> {{setUserName}}</span>님, 환영합니다.
            </template>
            <v-list dense shaped>
              <v-list-item>
                <v-list-item-title @click="pwdCheck" style="font-weight:bold; color:dimgrey">내 정보 보기</v-list-item-title>
              </v-list-item>
              <v-list-item>
                <v-list-item-title @click="logout" style="font-weight:bold; color:dimgrey">LOGOUT</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
        <!-- <v-btn v-if="username" text @click="logout">Logout</v-btn> -->
        <v-btn v-else text @click="$modal.show('login-modal')">Login</v-btn>
        <!-- <v-btn v-if="!username" text to="/findpwd">비밀번호 찾기</v-btn> -->
        
      </v-toolbar-items>
    </v-app-bar>
  </div>
</template>

<script>
import LoginModal from "../components/LoginModal";
import http from "../http-common";
export default {
  name: "Header",
  components: {
    LoginModal
  },
  methods: {
    getImgUrl(img) {
      return require("../assets/" + img);
    },
    pwdCheck() {
      if (this.username) {
        this.$router.push("/pwdcheck");
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
      let conf = confirm("로그아웃 하시겠습니까?");
      if (conf == true) {
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
    clickproduct(){
      this.$router.push("/product");
    },
    clickmybudget(){
      this.$router.push("/mybudget");
    },
    clickreview(){
      this.$router.push("/review");
    }
  },

  data() {
    return {
      appTitle: "Blog",
      sidebar: false,
      menuItems: [        
        { title: "Mart", path: "/product" },
        { title: "My Budget", path: "/mybudget" },
        { title: "Review", path: "/review" },
        // { title: "UserInfo", path: "/userinfo" }
        // { title: "비밀번호 찾기", path:"/findpwd"}
      ],
      username: ""
    };
  },

  computed: {
    setUserName() {
      console.log("setUserName");
      // this.username = this.$store.state.user
      console.log(this.username);
      return this.username;
    },
    checkLoggedIn() {
      return this.isLoggedIn;
    }
  },
  mounted() {
    this.getUserName();
    //this.checkLoggedIn()
  }
};
</script>
<style>


</style>
