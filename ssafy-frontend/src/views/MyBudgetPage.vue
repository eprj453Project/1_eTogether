<template>
  <div>
    <!-- <input type="text" name="title" v-model="title" />
    <input type="text" name="body" v-model="body" />
    <v-btn @click="postMyBudgets(title, body)">add</v-btn>-->
    <v-img :src="getImgUrl('budgetlist.jpg')" aspect-ratio="5.5">
      <v-layout align-center justify-center row fill-height>
        <v-flex text-xs-center>
          <span class="text-shadow display-2 font-weight-light">
            <slot name="text" />
            <div
        class="text-center text-white"
        style="line-height:1.2em; font-size:2.5em;"
        slot="text"
        v-resize-text
      >My Budget</div>
          </span>
        </v-flex>
      </v-layout>       
    </v-img>
   
    <div class="main">
      <v-card style="width:80%;" class="mx-auto my-5 flat">
        <MyBudgetList 
          :allBudgets="allBudgets"
          :budgetPerPage="budgetPerPage"
          :pages="pages"></MyBudgetList>
      </v-card>

      <div class="text-center">
        {{pages}}
        <v-pagination 
          v-model="pages" 
          :length="pagingLength" 
          total-visible="9" 
          ></v-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import FirebaseService from "@/services/FirebaseService";
import ImgBanner from "../components/ImgBanner";
import MyBudgetList from "../components/MyBudgetList";
import ResizeText from "vue-resize-text";
import http from "../http-common";
export default {
  name: "MyListPage",

  components: {
    ImgBanner,
    MyBudgetList
  },
  directives: {
    ResizeText
  },
  data: () => ({
    title: "",
    body: "",
    pages: 1,
    allBudgets: [],
    pagingList: [],
    pagingLength: 0,
    allLength: 0,
    budgetPerPage: 5,
    items: [
      {
        icon: "folder",
        iconClass: "grey lighten-1 white--text",
        title: "Photos",
        subtitle: "Jan 9, 2014"
      },
      {
        icon: "folder",
        iconClass: "grey lighten-1 white--text",
        title: "Recipes",
        subtitle: "Jan 17, 2014"
      },
      {
        icon: "folder",
        iconClass: "grey lighten-1 white--text",
        title: "Work",
        subtitle: "Jan 28, 2014"
      }
    ]
  }),
  methods: {
    getImgUrl(img) {
      return require("../assets/" + img);
    },
    // postMyBudgets(title, body) {
    //   title = this.title;
    //   body = this.body;
    //   FirebaseService.postMyBudgets(title, body);
    //   this.getMyBudgets();
    // },
    // async getMyBudgets() {
    //   console.log("active");
    //   this.pagingList = await FirebaseService.getMyBudgets();
    //   this.pagingLength = parseInt(this.pagingList / 5) + 1;
    //   return this.pagingList;
    // },
    getMyBudgets() {
      let myEmail = localStorage.getItem('email')
      http
        .get(`/budget/${myEmail}`, {
          user_email : myEmail
        }, this.$store.getters.RequestHeader)
        .then(res => {
          console.log(res)
          return res.data

        })
        .catch(err => {
          console.log(err)
        })
    }


  },
  computed: {
    mountedBudget() {
      this.getMyBudgets();
    }
  },
  mounted() {
    this.allBudgets = this.getMyBudgets()
    // this.allPages = await FirebaseService.getMyBudgets();
    // this.allLength = this.allPages.length;
    // console.log("allLength : " + this.allPages.length);
    // this.pagingList = await FirebaseService.getMyBudgetPaging(
    //   (this.pages - 1) * 5,
    //   (this.pages - 1) * 5 + 5,
    //   this.allLength
    // );
    // console.log(this.pagingList);

    // if (this.allLength % this.budgetPerPage === 0) {
    //   this.pagingLength = parseInt(this.allLength / 5);
    // } else {
    //   this.pagingLength = parseInt(this.allLength / 5 + 1);
    // }
    // console.log("pagingLength : " + this.pagingLength);
    //this.getMyBudgets()
  }
};
</script>

<style scoped>
.main {
  padding-top: 80px;
  padding-bottom: 80px;
}
</style>