<template>
  <v-card height="260px">
    <v-img :src="img" height="140px"></v-img>
    <v-card-title>
      <div>
        <v-tooltip top>
          <template v-slot:activator="{ on }">
            <div class="lightgrey--text" v-on="on">{{pro_name}}</div>
          </template>
          <span>{{pro_name}}</span>
        </v-tooltip>
        <div class="grey--text" style="float:right">{{price}}원</div>
        <br />
        <div class="black--text" style="font-size:10pt; text-align:right">
          수량:
          <input
            type="number"
            placeholder="1"
            min="1"
            size="small"
            style="text-align:center; font-size:10pt; width:40px"
            inline
            controls
            v-model="quantity"
            :quantity="quantity"
          />

          <v-btn small right color="#ffd900" style="float:right" @click="addcart">+</v-btn>
        </div>
      </div>
    </v-card-title>
  </v-card>
</template>
<script>
import EventBus from "../event-bus.js";
export default {
  name: "Product",
  props: {
    pro_id: { type: String },
    pro_name: { type: String },
    price: { type: String },
    img: { type: String },
    main_category: { type: String },
    product: { type: Object }
  },
  data: () => ({
    quantity: 1
  }),
  methods: {
    stringNumberToInt(stringNumber) {
      //console.log(parseInt(stringNumber.replace(/,/g, "")));
      return parseInt(stringNumber.replace(/,/g, ""));
    },
    addcart() {
      console.log(this.product);

      // console.log(
      //   "/////////////" + this.pro_id + "             " + this.main_category
      // );
      // console.log(this.product)
      let product = {
        pro_id : this.product.pro_id,
        pro_name: this.pro_name,
        price: this.stringNumberToInt(this.price),
        quantity: this.quantity,
        pro_price:this.stringNumberToInt(this.price)
      };
      this.$store.dispatch('addCartAsync', product)
      // EventBus.$emit("addCart", product);
    }
  }
};
</script>
<style scoped>
.lightgrey--text {
  font-size: 12pt;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 140px;
}

.grey--text {
  font-size: 11pt;
  /* 한 줄 자르기 */
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>

