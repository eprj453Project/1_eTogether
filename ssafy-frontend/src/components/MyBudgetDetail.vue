<template>
  <div style>
    <div id="downloadpdf">
      <table width="100%" style="font-size:15px" class="table-white">
        <tr style="text-align:center;">
          <th>제목</th>
          <td v-html="computedBudgetInfo.budget_title" style="text-align:center"></td>
          <th>인원</th>
          <td style="text-align:center">{{computedBudgetInfo.personnel}} 명</td>
          <th>예산</th>
          <td style="text-align:center">{{computedBudgetInfo.budget}} 원</td>
          <th>날짜</th>
          <td style="text-align:center">{{dateParsing(computedBudgetInfo.budget_date)}}</td>

          <th>적/부</th>
          <td style="text-align:center">
            <!-- 적 / 부 -->
            <div>
              <i
                :class="likeClass"
                style="color:blue; margin-right: 5px;"
                @click="changeLikeStatus(1)"
              ></i>
              <!-- <i class="fas fa-thumbs-up"></i> -->
              <i :class="dislikeClass" style="color:red" @click="changeLikeStatus(2)"></i>
              <!-- <i class="fas fa-thumbs-down"></i> -->
            </div>
          </td>
        </tr>
      </table>

      <table style="width:100%; margin:auto; text-align:center">
        <col width="55%" />
        <col width="15%" />
        <col width="30%" />
        <thead>
          <tr>
            <th style="background-color: #fffeaa; color:#3d2300">항목</th>
            <th style="background-color: #fffeaa; color:#3d2300">수량</th>
            <th style="background-color: #fffeaa; color:#3d2300">단위 가격</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="i in computedBudgetList.length" :key="i">
            <td v-html="computedBudgetList[i-1].pro_name"></td>
            <td v-html="computedBudgetList[i-1].quantity"></td>
            <td class="pro_price">{{computedBudgetList[i-1].price}} 원</td>
          </tr>
          <tr>
            <th colspan="2">합계</th>
            <td>{{this.total}} 원</td>
          </tr>
          <tr>
            <th colspan="2">잔액</th>
            <td>{{computedBudgetInfo.budget-this.total}} 원</td>
          </tr>
        </tbody>
      </table>
    </div>
    <br />
    <div data-html2canvas-ignore="true" style="text-align:center">
      <v-btn outlined color="success" class="mr-4" @click="makePDF()">pdf로 저장</v-btn>
      <v-btn outlined color="error" @click.stop="dialog=true">후기 남기기</v-btn>
    </div>

    <!-- 이미지, 내용 등록 modal -->
    <v-dialog data-html2canvas-ignore="true" v-model="dialog" max-width="600">
      <v-card>
        <v-card-title class="headline">후기 등록</v-card-title>
        <p style="padding-left:30px; color:gray">후기를 등록하시면 회원님의 예산 상세 내역이 함께 게시됩니다.!</p>
        <v-col>
          <div class="container">
            <div class="large-12 medium-12 small-12 cell">
              <label>
                <i class="material-icons"  style="vertical-align: top">insert_photo</i>
                <input type="file" id="files" ref="files" v-on:change="handleFilesUploads()">                
              </label>
              <v-textarea
                v-model="content"
                background-color="amber lighten-4"
                color="orange orange-darken-4"
                outlined
                shaped
                placeholder="후기를 작성해주세요!"
              ></v-textarea>
            </div>
          </div>
        </v-col>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="writeReview(true)">저장</v-btn>
          <v-btn color="green darken-1" text @click="writeReview(false)">취소</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import http from "../http-common";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";
export default {
  name: "MyBudgetDetail",
  props: {
    budgetInfo: {
      type: Object
    },
    budgetList: {
      type: Array
    }
  },

  data() {
    return {
      detail: {},
      total: 0,
      budgetlength: 0,
      dialog: false,
      content: "",
      propTitle: "mypdf",
      likeClass: "far fa-thumbs-up",
      dislikeClass: "far fa-thumbs-down",
      files: ""
    };
  },

  methods: {
    handleFilesUploads() {
      this.files = this.$refs.files.files;
    },

    dateParsing(beforeParsing) {
      const t = beforeParsing.indexOf("T");
      const afterParsing = beforeParsing.substring(0, t);
      // console.log(afterParsing)
      const realdate =
        afterParsing.substring(0, 4) +
        "년 " +
        afterParsing.substring(5, 7) +
        "월 " +
        (Number(afterParsing.substring(8, 11)) + 1) +
        "일";
      console.log("realdate: " + realdate);
      return realdate;
    },
    total_sum() {
      //  console.log("budgetList 길이!:"+ this.budgetlength);
      for (
        let index = 0;
        index < this.computedBudgetDetail.budgetlist.length;
        index++
      ) {
        this.total += this.computedBudgetDetail.budgetlist[index].price;
      }
      return this.total;
    },
    writeReview(bool) {
      if (bool === true) {
        if (this.content == "") {
          alert("내용을 입력해주세요.");
          return;
        }
        if(this.files == ""){
          alert("이미지를 등록해주세요.");
          return;
        }

        let formData = new FormData();

        for (var i = 0; i < this.files.length; i++) {
          let file = this.files[i];
          formData.append("files", file);
        }
        formData.append("budget_num", this.budgetInfo.budget_num);
        // alert("budget_num: " + this.budgetInfo.budget_num);
        formData.append("review_content", this.content);
        // alert("review_content: " + this.content);

        http
          .post("/review", formData)
          .then(response => {
            // console.log("SUCCESS!!");
            this.$router.push({name:'review'});
            // console.log(response);
            // this.result = response.;
          })
          .catch(ex => {
            // console.log("FAILURE!!");
          });
      } else {
        this.dialog = false;
      }
    },
    checklikeStatus() {
      let status = this.budgetInfo.suitability;
      // const like = document.querySelector('.fa-thumbs-up')
      // console.log(status)
      // console.log(like.className)
      // const dislike = document.querySelector('.fa-thumbs-down')
      if (status === 0) {
        this.likeClass = "far fa-thumbs-up";
        this.dislikeClass = "far fa-thumbs-down";
      } else if (status === 1) {
        this.likeClass = "fas fa-thumbs-up";
        this.dislikeClass = "far fa-thumbs-down";
      } else {
        this.likeClass = "far fa-thumbs-up";
        this.dislikeClass = "fas fa-thumbs-down";
      }
      
    },
    changeLikeStatus(num) {
      console.log("changeLikeStatus");
      console.log("num : " + num);
      console.log("budget suit : " + this.budgetInfo.suitability);
      if (this.computedBudgetInfo.suitability === num || num == 0) {

        return;
      } else {
        http.post(
          `/budget/${this.budgetInfo.budget_num}/${num}`,
          {
            budget_num: this.budgetInfo.budget_num,
            suitability: num
          },
          this.$store.getters.requestHeader
        );
        this.$emit("renewBudgetList");
        this.$emit("showdetail", this.budgetInfo.budget_num);
      }
    }
    ,
   makePDF() {     
     
         window.html2canvas = html2canvas //Vue.js 특성상 window 객체에 직접 할당해야한다.
         let pdf = new jsPDF('p', 'mm', 'a4')
         let canvas = pdf.canvas
         const pageWidth = 210//캔버스 너비 mm
         const pageHeight = 297 //캔버스 높이 mm
      canvas.width =pdf.width
      
      let ele = document.querySelector('#downloadpdf')
         let width = ele.offsetWidth // 셀렉트한 요소의 px 너비
         let height = ele.offsetHeight // 셀렉트한 요소의 px 높이
      let imgHeight = pageWidth * height/width // 이미지 높이값 px to mm 변환
         if(!ele){
            // console.warn(selector + ' is not exist.')
            return false
         }
         html2canvas(document.querySelector('body')).then(function(canvas) {
        let position = -120
        var imgData = canvas.toDataURL("image/png"); //캔버스를 이미지로 변환
        pdf.addImage(imgData, "PNG",0,position, pageWidth, imgHeight+1, undefined, 'FAST'); //이미지를 기반으로 pdf생성

        //Paging 처리
               let heightLeft = imgHeight //페이징 처리를 위해 남은 페이지 높이 세팅.
          heightLeft -= pageHeight
               while (heightLeft >= 0) {
            position = -120 + heightLeft - imgHeight
                  pdf.addPage();
                  pdf.addImage(imgData, 'png', 0, position+3, pageWidth, imgHeight)
            heightLeft -= pageHeight
               }



        pdf.save("MyBudget.pdf"); //pdf저장
      });

		}
  },
  computed: {
    convertDate() {
      console.log("convertDate");
      let convertDate = new Date(this.date);
      console.log(convertDate);
      return this.date;
    },
    computedBudgetInfo: function() {
      console.log("computedBudgetInfo");
      console.log(this.budgetInfo);
      console.log(this.budgetInfo.suitability);
      this.checklikeStatus();
      return this.budgetInfo;
    },
    computedBudgetList: function() {
      console.log("computedBudgetList");
      // console.log(this.bungetList)
      this.total = this.budgetList.reduce(
        (total, budget) => (total += budget.price),
        0
      );
      console.log(this.total);
      return this.budgetList;
    }
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-top: 1px solid dimgray;
  border-collapse: collapse;
}
th,
td {
  border-bottom: 1px solid dimgray;
  padding: 5px;
}
th {
  color: dimgray;
  background-color: lightgray;
}
img {
  width: 30%;
  margin: auto;
  display: block;
  margin-bottom: 10px;
}
</style>