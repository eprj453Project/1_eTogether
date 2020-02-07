<template>
  <modal name="budget-modal" transition="pop-out" height="650">
    <div style="width:100%">
    <b-list-group-item style="text-align:center; background-color:lightgrey">
      <strong>장보기 내역</strong>
    </b-list-group-item>

    <b-list-group-item class="product_table" style="height:350px">
      <table style="width:100%; margin:auto">
        <col width="45%" />
        <col width="15%" />
        <col width="25%" />
        <col width="15%" />
        <thead>
          <tr style="font-size:10pt; text-align:center">
            <th>항목</th>
            <th>수량</th>
            <th >가격</th>
            <th >삭제</th>
          </tr>
        </thead>
        <tbody >          
           <tr v-for="i in computedBudgetList.length" :key="i"  style="font-size:10pt;">
            <td v-html="computedBudgetList[i-1].pro_name" class="product"></td>
            <!-- <td v-html="list[i-1].quantity" class="quantity" style="text-align:center"></td> -->
            <td class="quantity" style="text-align:center"><input
              type="number"   
              v-model="computedBudgetList[i-1].quantity"
              @input="newquantity(i-1)"
              style="text-align:right; width:50px"
              autofocus
              min="1"
            /></td>
            
            <td class="pro_price" style="text-align:center;">{{computedBudgetList[i-1].price}} 원</td>
            <td style="text-align:center;">
                <v-btn text icon color="red" @click="del_pro(i-1)">
                  <v-icon>close</v-icon>
                </v-btn>             
            </td>
          </tr>
          
        </tbody>
      </table>
     
      <br />
    </b-list-group-item>


    <b-list-group-item>
      <table style="width:100%; margin:auto">
        <tr>
          <td width="20%">
            <strong>인원</strong>
          </td>
          <td width="80%" style="text-align:right">
            <input
              type="number"
              :step="10"
              min="10"
              id="personnel"
              v-model="personnel"
              @input="triggerEvent"
              style="text-align:right"
              autofocus
            />명
          </td>
        </tr>
      </table>
    </b-list-group-item>
    <b-list-group-item>
      <table style="width:100%; margin:auto">
        <tr>
          <td width="20%">
            <strong>예산</strong>
          </td>
          <td width="80%" style="text-align:right">
            <input
              type="number"
              :step="10000"
              min="10000"
              id="budget"
              v-model="budget"
              @input="triggerEvent"
              style="text-align:right"
              autofocus
            />원
          </td>
        </tr>
      </table>
    </b-list-group-item>


    <b-list-group-item>
      <table style="width:100%; margin:auto">
        <tr>
          <td width="20%">
            <strong>합계</strong>
          </td>
          <td width="80%" style="text-align:right; font-weight:bold; color:darkblue">{{this.total}} 원</td>
        </tr>
      </table>
    </b-list-group-item>
    <b-list-group-item>
      <table style="width:100%; margin:auto">
        <tr>
          <td width="20%">
            <strong>잔액</strong>
          </td>
          <td width="80%" style="text-align:right; font-weight:bold; color:darkred">{{this.budget-this.total}} 원</td>
        </tr>
      </table>
    </b-list-group-item>
    <v-btn block @click.stop="dialog=true">저장하기</v-btn>

    <!-- 제목 지정 modal -->
    <v-dialog v-model="dialog" max-width="290">
      <v-card>
        <v-card-title class="headline">리스트 저장</v-card-title>

        <v-col>
          <v-text-field autocomplete="nope" label="제목을 입력해주세요"></v-text-field>
        </v-col>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="budgetSave(true)">저장</v-btn>
          <v-btn color="green darken-1" text @click="budgetSave(false)">취소</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
  </modal>
</template>

<script>
import EventBus from "../event-bus.js";
export default {
  data(){    
    return{
    personnel: "",
    budget: "",
    quantity: 0,
    price: 0,
    dialog: false,
    total: 0,
    
    list: []
    }
  },
  
 created() {
    EventBus.$on("addCart", product=>{
      this.list.push(product);
      this.total_sum(product.price*=product.quantity);
    });
  },
  watch:{
    list:{
      handler:function(newVal){
        console.log(newVal);
        console.log("dddddddddd");
        console.log(this.list);
        let sum = 0 ;
        for(let i = 0 ; i<this.list.length; i++){
            sum += newVal[i].quantity*newVal[i].pro_price;
        }
        this.total = sum;
         this.budgetalert();
      // this.total+= this.list[i].price;
    },
    deep :true,
    immediate:true
   }
  },
  methods: {
    cookie(){
      this.$cookie.set('test',this.personnel, 1);
    },
    triggerEvent() {
      this.$store.state.personnel = this.personnel;
      this.$store.state.budget = this.budget;
    },    
    stringNumberToInt(stringNumber) {
      //console.log(parseInt(stringNumber.replace(/,/g, "")));
      return parseInt(stringNumber.replace(/,/g, ""));
    },
   budgetSave(bool) {
      if (bool === true) {
        if (this.budget_title == "") {
          alert("제목을 입력해주세요.");
          return;
        }
        if (this.personnel == "") {
          alert("인원을 입력해주세요.");
        }
        if (this.budget == "") {
          alert("예산을 입력해주세요.");
        }
        httpCommon
          .post("/budget", {
            budget_title: this.budget_title,
            personnel: this.personnel,
            budget: this.budget,
            list: this.list
          })
          .then(response => {
            this.result = response.data;
          })
          .catch(ex => {
            console.warn("ERROR! :", ex);
          });
        router.push("/mylist");
      } else {        
        this.dialog=false;
      }
    },
    budgetalert() {
      if (this.total > this.budget) {
        alert("예산을 초과하였습니다!");
      }
    },
    total_sum(val) {
      this.total += val;
      this.budgetalert();
      return this.total;
    },
    del_pro(i){
      var index = this.list.indexOf(i)
      this.total -= this.list[i].price;
      this.list.splice(i,1);  
      this.budgetalert();  
    },
    newquantity(i){
      if(isNaN(this.list[i].quantity)){
        this.list[i].quantity =1;
      }
      this.list[i].price=this.list[i].quantity*this.list[i].pro_price;
      // console.log(this.list[i].price);
       this.budgetalert();
    }
  },
  mounted() {
    this.personnel = this.$store.state.personnel;
    this.budget = this.$store.state.budget;
    if(this.budget=="" || this.personnel==""){
        alert("인원과 예산을 입력해주세요!");
      };
      
  },
  computed: {
    computedBudgetList() {
      return this.$store.state.budgetlist
    }
  }
};
</script>
<style>
.product_table {
  overflow-y: scroll;
  overflow-x: hidden;
  width: 100%;
}

.product {  
  padding-top:8px;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 130px;
  color: darkblue;
  font-weight: bold;
}
</style>