<template>
<div>
 <v-img :src="getImgUrl('grilling-2491123_1920.jpg')" aspect-ratio="5.5"></v-img>
  <v-container class="main">
   
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-text-field type="number" v-model="personnel" :step="10" min="10" label="인원" required :rules="[v => !!v || '인원을 입력해 주세요']"></v-text-field>
      <v-text-field type="number" v-model="budget" :step="10000" min="10000" label="예산" required :rules="[v => !!v || '예산을 입력해 주세요']"></v-text-field>

      <!-- <v-select
        v-model="select"
        :items="items"
        :rules="[v => !!v || 'Item is required']"
        label="마트"
        required
      ></v-select> -->
      
      <div class="btn">       
        <v-btn color="error" class="mr-4" @click="reset">Reset</v-btn>
        <v-btn :disabled="!valid" color="#ffd900" class="mr-4" @click="validate">장보기</v-btn>
      </div>
    </v-form>
  </v-container>
  </div>
</template>
<script>
import ImgBanner from "../components/ImgBanner";
export default {
  components: {
    ImgBanner,
    
  },
  data: () => ({
    valid: true,
    personnel: "",
    budget: "",
    
    // items: ["Item 1", "Item 2", "Item 3", "Item 4"]    
  }),

  methods: {
    validate() {
      if (this.$refs.form.validate()) {         
        this.snackbar = true;
        this.$router.push("/product"); 
        this.$store.state.personnel=this.personnel;
        this.$store.state.budget=this.budget;
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    getImgUrl(img) {
      return require("../assets/" + img);
    }   
  }  
};

</script>
<style scoped>
.main {
  padding-top: 120px;
  padding-bottom: 120px;
  width: 450px;
}
.btn {
  width: 430px;
  margin: auto;
}
</style>
