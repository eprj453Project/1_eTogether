<template>
  <v-layout mt-5 wrap>
    <v-flex v-for="product in computedPagingProducts" xs6 sm4 lg3 :key="product.pro_name">
      <Product class="ma-2" :pro_name="product.pro_name" :price="product.price" :img="product.img" :product="product"></Product>
    </v-flex>
  </v-layout>
</template>
<script>
import http from "../http-common";
import Product from "@/components/Product";

export default {
  name: "ProductsList",
  data() {
    return {
      pagingProductList: []
    };
  },
  props: {
    product: { type: Object },
    productPerPage: {
      type: Number
    },
    products: {
      type: Array
    },
    pages: {
      type: Number,
      required: true
    },
    category: {
      type: String,
      required: true
    }
  },
  data() {
    return {};
  },
  components: {
    Product
  },
  methods: {
    all() {
      //product = this.$attrs.product;
    }
  },
  computed: {
    computedPagingProducts() {
      console.log("productlist computed");
      this.pagingProductList = [];

      let tempList = [];
      if (this.category !== "전체") {
        tempList = this.products.filter(product => {
          // console.log("productlist computed")
          return product.main_category === this.category;
        });
      } else {
        tempList = this.products;
      }

      let start = (this.pages - 1) * this.productPerPage;
      let end = (this.pages - 1) * this.productPerPage + this.productPerPage;

      if (end > tempList.length) {
        end = tempList.length;
      }
      for (let i = start; i < end; i++) {
        this.pagingProductList.push(tempList[i]);
      }
      //console.log("pagingProudct : " + this.pagingProductList)
      return this.pagingProductList;
    }
  }
};
</script>
<style>
.mw-700 {
  max-width: 700px;
  margin: auto;
}
</style>
