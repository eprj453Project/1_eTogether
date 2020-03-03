<template>
  <v-flex  xs12 md8 lg8 class="main">
    
    <div class="card mb-3" style="height:100%;">
      <div class="row no-gutters">
        <div class="col-md-5">
          <v-img :src="'http://13.209.9.53'+review_img" class="card-img" alt></v-img>
        </div>
        <div class="col-md-7">
          <div class="card-body mt-4">
            <h4 class="card-title text-center">{{budgetInfo.budget_title}}</h4>
            <h6 class="text-center" style="color:dimgrey">by. {{review.name}}</h6>
            <div class="text-center">
              <p class="card-text" style="padding:0px">
                <small class="text-muted">{{dateParsing(review.review_date)}}</small>
              </p>

              <v-card-text class="text-center" style="color:dimgrey; padding:3px">
                <i class="material-icons"  style="vertical-align:top">people_outline</i> <span style="font-size:12pt">
                 {{budgetInfo.personnel}}명 </span>
                <i class="material-icons"  style="vertical-align:top">money</i> <span style="font-size:12pt">
                {{budgetInfo.budget}}원 </span>
              </v-card-text>

              <p
                v-if="budgetInfo.suitability === 1"
                class="text-center bg-primary rounded d-inline-block"
                style="color:white"
              >사용자가 적합하다고 판단한 예산입니다.</p>
              <p
                v-else-if="budgetInfo.suitability == 2"
                class="text-center bg-warning rounded d-inline-block"
                style="color:white"
              >사용자가 부적합하다고 판단한 예산입니다.</p>
              <p
                v-else
                class="text-center bg-secondary rounded d-inline-block"
                style="color:white"
              >사용자가 아직 적합도를 정하지 않았습니다.</p>
              <br>
              <i class="fas fa-heart text-danger mr-1 mb-3"></i>
              {{like_users.length}}
              <!--이미 좋아요 누른 경우-->
              <span
                v-if="likeStatus"
                class="likeBtn badge badge-pill badge-danger"
                style="cursor:pointer"
                @click="like(review)"
              >좋아요 취소</span>
              <span
                v-else
                class="likeBtn badge badge-pill badge-primary"
                style="cursor:pointer"
                @click="like(review)"
              >좋아요</span>

              <!-- 카톡링크전송: 작성자랑 현재 로그인한 사람이랑 같아야만 보인다 -->
              <span v-if="checkUser()==true">
                <a
                  href="javascript:;"
                  @click="kakaotalklink"
                  id="kakao-link-btn"
                  style="width:30px; height:auto"
                >
                  <!-- 버튼이 생기는 부분, id는 맘대로 쓰시되 아래 js 코드도 동일하게 적용해주셔야 합니다. -->
                  <img
                    src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png"
                    style="width:30px; height:auto"
                  />
                  <!-- 톡 이미지 부분이고, 전 kakaolink_btn_small.png로 불러왔습니다.   -->
                </a>
              </span>
            </div>
          </div>
        </div>
        <v-textarea
          solo
          name="input-7-4"
          no-resize
          readonly
          :value="review.review_content"
          style="padding:5%;"
        ></v-textarea>
        <!-- <p class="card-text" style="padding-top:15px">{{review.review_content}}</p> -->
        <div class="col-md-12">
          <table style="width:100%; margin:auto; text-align:center">
            <col width="55%" />
            <col width="15%" />
            <col width="30%" />
            <thead>
              <tr>
                <th style="background-color: #fffeaa; color:#3d2300">항목</th>
                <th style="background-color: #fffeaa; color:#3d2300">수량</th>
                <th style="background-color: #fffeaa; color:#3d2300">가격</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="product in budgetList" :key="product.pro_name">
                <td v-html="product.pro_name"></td>

                <td v-html="product.quantity"></td>

                <td class="pro_price">{{product.price}} 원</td>
              </tr>

              <tr>
                <th colspan="2">합계</th>
                <td>{{total_sum()}} 원</td>
              </tr>
              <tr>
                <th colspan="2">잔액</th>
                <td>{{budgetInfo.budget-total_sum()}} 원</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  
    <div style="text-align:right">
      <v-btn outlined color="success" class="mr-4" @click="gotoList">목록</v-btn>

      <!-- 작성자랑 지금 로그인한 사용자가 같아야만 이 버튼이 보임 -->
      <span v-if="checkUser()==true">
        <v-btn outlined color="error" class="mr-4" @click="deleteReview(review.review_num)">삭제</v-btn>
      </span>
    </div>
  </v-flex >
</template>

<script>
import EventBus from "../event-bus";
import http from "../http-common";
import Kakao from "../services/KakaotalkService";
export default {
  name: "ReviewDetail",
  components: {},

  data() {
    return {
      // review: Object,
      // budgetInfo: Object,
      // budgetList: Array,
      like_users: Array,
      reviewNum: Number,
      dialogm1: "",
      dialog: false,
      total: 0,
      allReplys: Array,
    };
  },

  props: {
    review: {
      type: Object
    },
    budgetList: {
      type: Array
    },
    budgetInfo: {
      type: Object
    },
    like_users: {
      type: Array
    },
    total: {
      type: Number
    },
    review_img: {
      type: String
    }
  },

  methods: {
    checkUser() {
      if (this.budgetInfo.user_email == sessionStorage.getItem("email")) {
        return true;
      } else {
        return false;
      }
    },
    gotoList() {
      this.$router.push("/review");
    },
    deleteReview(num) {
      console.log(num);
      let conf = confirm("리뷰를 삭제하시겠습니까?");
      if (conf) {
        http
          .delete("/review/" + num)
          .then(response => {
            console.log(response);
            if (response.status == 200) {
              alert("삭제 처리 하였습니다!");
              this.$router.push("/review");
            } else {
              alert("삭제 처리하지 못하였습니다!");
            }
          })
          .catch(err => {
            console.log(err);
            this.errored = true;
          })
          .finally(() => (this.loading = false));
      } else {
        return;
      }
    },
    kakaotalklink() {
      //<![CDATA[
      // // 사용할 앱의 JavaScript 키를 설정해 주세요.
      // Kakao.cleanup();
      Kakao.init("918cb2cf36b91b4f11f0a63002dc2154");
      // // 카카오링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.
      Kakao.Link.createDefaultButton({
        container: "#kakao-link-btn", // 컨테이너는 아까 위에 버튼이 쓰여진 부분 id
        objectType: "feed",
        content: {
          // 여기부터 실제 내용이 들어갑니다.
          title: this.budgetInfo.budget_title, // 본문 제목
          description: this.review.review_content, // 본문 바로 아래 들어가는 영역?
          imageUrl: "http://13.209.9.53" + this.review_img, // 이미지
          link: {
            webUrl:
              "https://i02b109.p.ssafy.io/reviewdetail/" +
              this.$route.params.reviewNum,
              
            mobileWebUrl:
              "https://i02b109.p.ssafy.io/reviewdetail/" +
              this.$route.params.reviewNum,
            androidExecParams:
              "https://i02b109.p.ssafy.io/reviewdetail/" +
              this.$route.params.reviewNum,
            iosExecParams:
              "https://i02b109.p.ssafy.io/reviewdetail/" +
              this.$route.params.reviewNum
          }
        },
        social: {
          /* 공유하면 소셜 정보도 같이 줄 수 있는데, 이 부분은 기반 서비스마다 적용이 쉬울수도 어려울 수도 있을듯 합니다. 전 연구해보고 안되면 제거할 예정 (망할 google  blogger...) */
          likeCount: this.like_users.length
          // commentCount: 45,
          // sharedCount: 845
        },
        buttons: [
          {
            title: "e투계더로 이동",
            link: {
              webUrl:
                "https://i02b109.p.ssafy.io/reviewdetail/" +
                this.$route.params.reviewNum,
              mobileWebUrl:
                "https://i02b109.p.ssafy.io/reviewdetail/" +
                this.$route.params.reviewNum,
              androidExecParams:
                "https://i02b109.p.ssafy.io/reviewdetail/" +
                this.$route.params.reviewNum,
              iosExecParams:
                "https://i02b109.p.ssafy.io/reviewdetail/" +
                this.$route.params.reviewNum
            }
          }
        ]
      });
    },

    total_sum() {
      this.total = this.budgetList.reduce(
        (total, budget) => (total += budget.price),
        0
      );
      return this.total;
      // console.log(this.budgetList);
    },
    dateParsing(beforeParsing) {
      const t = beforeParsing.indexOf("T");
      const afterParsing = beforeParsing.substring(0, t);
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

    like(review) {
      let loginUser = sessionStorage.getItem("email");
      if (loginUser === null || typeof loginUser === undefined) {
        alert("로그인이 필요합니다.");
        return;
      }
      if (this.like_users.includes(loginUser)) {
        console.log("delete");
        console.log(review.review_num);
        console.log(loginUser);
        http
          .delete(
            `/review/like_count/${this.review.review_num}/${loginUser}`,
            this.$store.getters.requestHeader
          )
          .then(res => {
            this.$emit("getReviewDetailbyArg", review.review_num);
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        console.log("like");
        http
          .post(
            `/review/like_count`,
            {
              review_num: review.review_num,
              user_email: loginUser
            },
            this.$store.getters.requestHeader
          )
          .then(res => {
            this.$emit("getReviewDetailbyArg", review.review_num);
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          });
      }
    }
  },
  mounted() {
    console.log(this.review)
    this.total_sum();
    this.getAllReplys();
  },

  computed: {
    convertDate() {
      console.log("convertDate");
      let convertDate = new Date(this.date);
      console.log(convertDate);
      return this.date;
    },

    computedReviewNum() {
      return this.$route.params.reviewNum;
    },

    likeStatus() {
      let loginUser = sessionStorage.getItem("email");
      if (this.like_users.includes(loginUser)) {
        return true;
      } else {
        return false;
      }
    }
  }
};
</script>

<style scoped>
.main {
  padding-top: 80px;
  padding-bottom: 80px;
  margin: auto;
  
}
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