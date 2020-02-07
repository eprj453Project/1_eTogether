<template>
  <div>
    <ImgBanner>
      <div
        class="text-center text-white"
        style="line-height:1.2em;font-size:2.5em;"
        slot="text"
        v-resize-text
      >Mart</div>
    </ImgBanner>

    <v-row class="main" style="padding-top:80px">
      <v-col lg="8">
        <!-- search box -->
        <v-text-field
          width="100px"
          flat
          hide-details
          label="Search"
          prepend-inner-icon="search"
          solo-inverted
          shaped
          v-model="keyword"
          v-on:keyup.enter="getProductList(keyword)"
        ></v-text-field>

        <b-card no-body style>
          <v-tabs dark background-color="darken-3" show-arrows>
            <v-tabs-slider color="teal lighten-3"></v-tabs-slider>
            <v-tab
              v-for="tab in tabs"
              :key="tab.title"
              @click="clickTab(tab.title)"
              v-model="category"
            >{{tab.title}}</v-tab>
          </v-tabs>

          <!-- page navigation-->
          <br />
          <div class="text-center">
            <v-pagination v-model="pages" :length="pagingLength" total-visible="12"></v-pagination>
          </div>

          <ProductList
            :products="products"
            :pages="pages"
            :category="category"
            :productPerPage="productPerPage"
          ></ProductList>
          <!-- 카테고리 탭 -->
          <!-- <b-tabs small card :tabs="tabs">
              <b-tab v-for="tab in tabs" :key="tab.title" :title="tab.title" @click="clickTab">
              </b-tab>
              <ProductList 
                :products="products" 
                :pages="pages" 
                :category="category"
                :productPerPage="productPerPage"></ProductList>
              
          </b-tabs>-->

          <!-- modal 플로팅 버튼-->
          <v-btn
            fixed
            dark
            fab
            bottom
            right
            color="#ffd900"
            @click="modalAppear"
            class="hidden-lg-only"
          >
            <i class="material-icons">shopping_cart</i>
          </v-btn>
        </b-card>
      </v-col>
      <!-- v-b-modal.modal-1 -->
      <!-- 장보기 내역 -->
      <v-col>
        <v-flex d-none d-lg-flex>
          <BudgetList id="budgetList">
          </BudgetList>
        </v-flex>
      </v-col>
      
      
    </v-row>
    <!-- modal 창 -->
    <v-row justify="center">
      <v-dialog v-model="budgetDialog" scrollable max-width="300px">
        <BudgetList></BudgetList>
      </v-dialog> 
    </v-row>
    

    <!-- <BudgetModal id="budgetModal">

    </BudgetModal> -->
  </div>
</template>
<script>
import ImgBanner from "../components/ImgBanner";
import http from "../http-common";
import BudgetList from "../components/BudgetList";
import ProductList from "../components/ProductList";
import ResizeText from "vue-resize-text";
import BudgetModal from "../components/BudgetModal";

export default {
  components: {
    ImgBanner,
    BudgetList,
    ProductList,
    BudgetModal,
  },
  directives: {
    ResizeText
  },

  data() {
    return {
      keyword: "",
      products: [],
      tabs: [
        { title: "전체" },
        { title: "정육/계란류" },
        { title: "수산물/해산물" },
        { title: "채소" },
        { title: "쌀/잡곡" },
        { title: "라면" },
        { title: "즉석식품" },
        { title: "생수/음료" },
        { title: "과일" },
        { title: "스낵" },
        { title: "견과/건해산물" }
      ],
      pagingProduct: [],
      pages: 1,
      productPerPage: 12,
      pagingLength: 10,
      category: "전체",
      allLegnth: 0,
      pagingLength: 0,
      budgetDialog: false,
      budgetList: [],
    };
  },
  mounted() {
    this.getProductList(this.keyword);
  },
  computed: {
    mountedProduct() {
      this.getProductList(this.keyword);
    }
  },

  methods: {
    clickTab: function(title) {
      console.log(title);
      this.category = title;
      if (this.category !== "전체") {
        this.pagingProduct = this.products.filter(product => {
          return product.main_category === this.category;
        });
      } else {
        this.pagingProduct = this.products;
      }

      this.allLength = this.pagingProduct.length;
      console.log("allLength : " + this.allLength);
      if (this.allLength % this.productPerPage === 0) {
        this.pagingLength = parseInt(this.allLength / this.productPerPage);
      } else {
        this.pagingLength = parseInt(this.allLength / this.productPerPage) + 1;
      }
      this.pages = 1;
    },

    // selectTab: function(title) {
    //   console.log(title)
    //   if (title !== "전체") {
    //     // console.log("not all")
    //     this.pagingProduct = this.products.filter(product => {
    //       console.log("not all")
    //       return product.main_category == title
    //     })
    //   }
    //   else {

    //   }
    // },
    getImgUrl(img) {
      return require("../assets/" + img);
    },
    // search(keyword){
    //   console.log("/////검색어: "+keyword+" "+this.keyword);
    //   this.keyword=keyword;

    // },
    getProductList(keyword) {
      // this.$emit('search');
      this.keyword = keyword;
      console.log("키워드는" + this.keyword);
      if (this.keyword != "" && this.keyword.length > 0) {
        this.search();
      } else {
        this.all();
      }
    },
    all() {
      http
        .get("/product")
        .then(response => {
          this.products = response.data;
          console.log(this.products);
          if (this.products.length % this.productPerPage === 0) {
            this.pagingLength = parseInt(
              this.products.length / this.productPerPage
            );
          } else {
            this.pagingLength =
              parseInt(this.products.length / this.productPerPage) + 1;
          }
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    search() {
      http
        .get("/product/" + this.keyword)
        .then(response => {
          this.products = response.data;
          if (this.products.length % this.productPerPage === 0) {
            this.pagingLength = parseInt(
              this.products.length / this.productPerPage
            );
          } else {
            this.pagingLength =
              parseInt(this.products.length / this.productPerPage) + 1;
          }
          return this.products;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    modalAppear() {
      this.budgetDialog = true
      // const budgetList = document.querySelector('#budgetList')
      // const cloneBudgetList = budgetList.cloneNode(true)
      // const budgetModal = document.querySelector('#budgetModal')
      // console.log(budgetModal)
      // budgetModal.innerHTML = ''
      // while (budgetModal.firstChild) {
      //   budgetModal.removeChild(budgetModal.firstChild);
      // }

      // budgetModal.appendChild(cloneBudgetList)
      // console.log(budgetList)
      // console.log(budgetModal)
      // this.$modal.show('budget-modal')
      
    }
  }
};
</script>

<style scoped>
.main {
  padding-bottom: 80px;
  margin: auto;
  width: 80%;
}
</style>
