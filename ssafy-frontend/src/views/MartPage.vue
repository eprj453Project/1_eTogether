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
    <v-flex xs12 md9 lg9 class="main">
      <!-- 비율 추천 그래프 -->
      <div class="mt-8">
        <div>
          <p>
            <span
              class="mx-auto py-1 bg-warning rounded"
              style="margin:auto; width:100%; color:white; font-weight:bold"
            >
              <i class="material-icons" style="vertical-align: top">assessment</i> 추천 비율
            </span>
            <span
              style="padding-left:15px; font-size:10pt; font-weight:bold; color:darkblue"
            >나와 비슷한 예산의 카테고리별 평균 비율입니다!</span>
          </p>
          <div
            class="progress text-center mx-auto"
            style="width: 100%;"
            v-on="on"
            v-if="canRecommend"
          >
            <div
              v-for="bar in recommendBar"
              :key="bar.className"
              :class="bar.className"
              :style="{width: (bar.price / (recommendTotal + recommendETCTotal) * 100) +'%'}"
            >
              <!-- {{bar.price}} -->
              <div v-if="bar.price > 0">
                <v-tooltip bottom>
                  <template v-slot:activator="{ on }">
                    <span v-on="on">{{bar.category}}</span>
                  </template>
                  <span>{{bar.category}} : {{numberCut(bar.price / (recommendTotal + recommendETCTotal) * 100)+'%'}}</span>
                </v-tooltip>
              </div>
            </div>

            <div
              v-if="recommendETCTotal > 0"
              class="progress-bar bg-secondary"
              v-bind:style="{width: (recommendETCTotal / (recommendTotal + recommendETCTotal)) * 100+'%'}"
            >
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <span v-on="on">기타</span>
                </template>
                <span v-for="bar in recommendBarETC" :key="bar.className">
                  <span v-if="bar.price > 0">
                    {{bar.category}} : {{numberCut(bar.price / (recommendTotal + recommendETCTotal) * 100)+'%'}}
                    <br />
                  </span>
                </span>
              </v-tooltip>
            </div>
          </div>
          <br />
          <p>
            <span
              class="mx-auto py-1 bg-warning rounded"
              style="margin:auto; width:100%; color:white; font-weight:bold"
            >
              <i class="material-icons" style="vertical-align: top">assessment</i> 현재 비율
            </span>
            <span
              style="padding-left:15px; font-size:10pt; font-weight:bold; color:darkblue"
            >현재 장보기 리스트에 담은 카테고리별 비율입니다!</span>
          </p>
          <div class="progress text-center mx-auto" style="width: 100%; height: 15%;" v-on="on">
            <!-- <div v-for="category in computedBudgetListBar" class="progress-bar">{{category.category}}</div> -->
            <div
              v-for="bar in getMainBar"
              :key="bar.className"
              :class="bar.className"
              :style="{width: (bar.price / (getMainTotal + getETCTotal)) * 100+'%'}"
            >
              <div v-if="bar.price > 0">
                <v-tooltip bottom>
                  <template v-slot:activator="{ on }">
                    <span v-on="on">{{bar.category}}</span>
                  </template>
                  <span>{{numberCut((bar.price / (getMainTotal + getETCTotal)) * 100)+'%'}} / {{bar.price}}원</span>
                </v-tooltip>
              </div>
            </div>
            <div
              v-if="getETCTotal > 0"
              class="progress-bar bg-secondary"
              v-bind:style="{width: (getETCTotal / (getMainTotal + getETCTotal)) * 100+'%'}"
            >
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <span v-on="on">기타</span>
                </template>
                <span v-for="bar in getETCBar" :key="bar.className">
                  <span v-if="bar.price > 0">
                    {{bar.category}} : {{numberCut((bar.price / (getMainTotal + getETCTotal)) * 100)+'%'}}
                    <br />
                  </span>
                </span>
              </v-tooltip>
            </div>
          </div>
        </div>
      </div>

      
      <!-- <v-flex xs12 md9 lg9 class="main"> -->
      <v-row style="padding-top:40px">
        <v-col lg="8" style="padding-top:0px;">
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
            v-on:keyup="getProductListByCaterogy(category, pages, keyword)"
          ></v-text-field>

          <b-card no-body style>
            <v-tabs dark background-color="darken-3" show-arrows>
              <v-tabs-slider color="teal lighten-3"></v-tabs-slider>
              <v-tab
                v-for="tab in tabs"
                :key="tab.title"
                @click="getProductListByCaterogy(tab.title, pages, keyword)"
                v-model="category"
              >{{tab.title}}</v-tab>
            </v-tabs>

              <ProductList :pagingProducts="pagingProducts"></ProductList>

            <!-- page navigation-->
            <br />
            <div class="text-center">
              <v-pagination v-model="pages" :length="pagingLength" total-visible="7"></v-pagination>
            </div>
            <br />
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
        <!-- <v-col> -->
        <v-flex d-none d-lg-flex>
          <BudgetList @changeRecommendBar="recommendBudgetBar"></BudgetList>
        </v-flex>
        <!-- </v-col> -->
      </v-row>
    </v-flex>
    <!-- modal 창 -->
    <v-row justify="center">
      <v-dialog v-model="budgetDialog" scrollable max-width="400px">
        <BudgetList @changeRecommendBar="recommendBudgetBar"></BudgetList>
      </v-dialog>
    </v-row>


   
    <!-- <BudgetModal id="budgetModal">
    </BudgetModal>-->
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
    BudgetModal
  },
  directives: {
    ResizeText
  },
  data() {
    return {
      keyword: "",
      tabs: [
        { title: "전체" },
        { title: "정육/계란류" },
        { title: "생수/음료" },
        { title: "채소" },
        { title: "라면" },
        { title: "수산물/해산물" },
        { title: "즉석식품" },
        { title: "쌀/잡곡" },
        { title: "과일" },
        { title: "스낵" },
        { title: "견과/건해산물" }
      ],
      pagingProducts: [],
      pages: 1,
      productPerPage: 12,
      category: "전체",
      // allLegnth: 0,
      pagingLength: 0,
      budgetDialog: false,
      budgetList: [],
      etcTotal: 0,
      mainTotal: 0,
      recommendBar: [
        {
          category: "정육/계란류",
          price: 0,
          className: "progress-bar bg-danger"
        },
        {
          category: "생수/음료",
          price: 0,
          className: "progress-bar bg-primary"
        },
        {
          category: "채소",
          price: 0,
          className: "progress-bar bg-success"
        },
        {
          category: "라면",
          price: 0,
          className: "progress-bar bg-warning"
        }
      ],
      recommendBarETC: [
        {
          category: "수산물/해산물",
          price: 0,
          className: "progress-bar bg-primary"
        },
        {
          category: "쌀/잡곡",
          price: 0,
          className: "progress-bar bg-secondary"
        },
        {
          category: "즉석식품",
          price: 0,
          className: "progress-bar bg-dark"
        },
        {
          category: "과일",
          price: 0,
          className: "progress-bar bg-success"
        },
        {
          category: "스낵",
          price: 0,
          className: "progress-bar bg-warning"
        },
        {
          category: "견과/건해산물",
          price: 0,
          className: "progress-bar bg-info"
        }
      ],
      recommendTotal: 0,
      recommendETCTotal: 0,
      colorByCategory: [
        { "정육/계란류": "bg-danger" },
        { "생수/음료": "bg-primary" },
        { 채소: "bg-success" },
        { 라면: "bg-warning" },
        { 기타: "bg-secondary" }
      ],
      updateMyBudgets: Object,
    };
  },
  mounted() {
    // console.log(this.$route.params.myBudgets)
    this.getProductListByCaterogy(this.category, 1, this.keyword);
    this.recommendBudgetBar(this.$store.state.budget)
  },
  created() {
    this.updateMyBudgets = this.$route.params.myBudgets
    
  },
  computed: {
    // mountedProduct() {
    //   this.getProductList(this.keyword);
    //   return true;
    // },

    getMainBar() {
      return this.$store.state.budgetListBar;
    },
    getETCBar() {
      return this.$store.state.budgetListBarETC;
    },
    getMainTotal() {
      return this.$store.state.mainTotal;
    },
    getETCTotal() {
      return this.$store.state.etcTotal;
    },
    getTotal() {
      return this.$store.state.mainTotal + this.$store.state.etcTotal;
    },
    canRecommend() {
      if (this.recommendETCTotal > 0 || this.recommendTotal > 0) {
        return true;
      } else {
        //alert("비교예산이 없습니다");
        return false;
      }
    }
  },
  watch: {
    pages: function() {
      this.getProductListByCaterogy(this.category, this.pages, this.keyword);
      window.scrollTo(0,0);
      console.log(window.scrollTop)
    }
  },
  methods: {
    getProductListByCaterogy(cat, num, key) {
      console.log(cat, num, key);
      console.log("this.category : " + this.category + " cat : " + cat);
      let myPage = 0;
      if (this.category !== cat || key !== this.keyword) {
        // 카테고리나 검색어가 갱신되면 페이지도 초기화
        myPage = 1;
      } else {
        myPage = num;
      }
      let requestUrl = "";
      if (key !== "") {
        requestUrl = `/product/${key}`;
      } else {
        requestUrl = `/product/`;
      }
      console.log(requestUrl);
      http
        .get(
          requestUrl,
          {
            params: {
              category: cat,
              page: myPage
            }
          },
          this.$store.getters.requestHeader
        )
        .then(res => {
          console.log(res);
          console.log(res.data.totPage);
          this.pagingProducts = res.data.productlist;
          this.pagingLength = key === "" ? res.data.totPage : res.data.endPage;
          console.log(this.pagingLength);
          this.category = cat;
          this.pages = myPage;
        });
    },
    clickTab: function(title) {
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
    recommendBudgetBar(mybudget) {
      mybudget = Number(mybudget);
      this.recommendTotal = 0;
      this.recommendETCTotal = 0;
      let i = 0;
      while (i < this.recommendBar.length || i < this.recommendBarETC.length) {
        if (i < this.recommendBar.length) {
          this.recommendBar[i].price = 0;
        }
        this.recommendBarETC[i].price = 0;
        i++;
      }
      if (mybudget !== null && mybudget !== undefined && mybudget !== 0) {
        http
          .get("/recommend", {
            params: {
              budget: mybudget
            }
          })
          .then(res => {
            console.log(res)
            const keys = Object.keys(res.data);
            const vals = Object.values(res.data);
            const total = vals.reduce((total, val) => (total + val), 0)
            if (total === 0) {
              alert('범위에 맞는 예산이 없습니다. 다른 예산을 입력해주세요')
              return
            }
            const categoryDict = this.$store.state.recommendDict;
            for (let i = 0; i < keys.length; i++) {
              if (this.$store.state.ETC.includes(categoryDict[keys[i]])) {
                const idx = this.recommendBarETC.findIndex(bar => {
                  return bar.category === categoryDict[keys[i]];
                });
                this.recommendBarETC[idx].price = vals[i];
                this.recommendETCTotal += vals[i];
              } else {
                const idx = this.recommendBar.findIndex(bar => {
                  return bar.category === categoryDict[keys[i]];
                });
                this.recommendBar[idx].price = vals[i];
                this.recommendTotal += vals[i];
              }
            }
          })
          .catch(err => {});
      } else {
        alert("예산을 입력해주세요!");
      }
    },
    getImgUrl(img) {
      return require("../assets/" + img);
    },

    getProductList(keyword) {
      this.keyword = keyword;
      console.log(this.keyword);
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
          this.pagingProduct = response.data;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => (this.loading = false));
    },
    search() {
      http
        .get(`/product/category/${this.keyword}`, {
          keyword: this.keyword
        })
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
      this.budgetDialog = true;
    },
    numberCut(number) {
      return number.toFixed(2);
    },
    showdetail(budgetNum) {
      http
        .get(
          `/budget/detail/${budgetNum}`,
          {
            budget_num: budgetNum
          },
          this.$store.getters.RequestHeader
        )
        .then(res => {
          this.$store.state.budgetlist = res.data.budgetlist;
          this.$store.state.personnel = res.data.budgetinfo.personnel;
          this.$store.state.budget = res.data.budgetinfo.budget;
        });
    }
  }
};
</script>

<style scoped>
.main {
  padding-bottom: 80px;
  margin: auto;
}
.mt-8 {
  background-color: lightyellow;
  margin: auto;
  padding: 12px;
  padding-bottom: 20px;
  border-radius: 20px;
}
</style>
