<template>
  <v-container mb-12>
    <div style="margin:auto; padding-top:50px; width:80%">
      <h2 class="headline mb-3">
        About
        <strong>Site</strong>
        <span class="likeBtn badge badge-pill badge-warning" style="font-size:small; color:white; cursor:pointer; float:right; text-align:right" @click="scrolltoqna()">문의사항</span>
      </h2>
      <br>
      <h5>e-투계더는 <strong style="color:darkorange">'단체 장보기 서비스'</strong>를 제공합니다!</h5>
      <br>
      <p>e-투계더는 emart 상품을 기반으로 워크샵, MT, 여행 등 단체로 장을 봐야 하는 상황에 카테고리 별로 필요한 상품을 보여주고 예산 별 카테고리 비율을 추천해주는 서비스입니다. <br>
      선택한 상품의 합계를 계산해주고, 장보기 리스트를 저장하고 pdf로 다운로드할 수 있습니다. <br>
      이용 후기를 등록하여 다른 사용자들과 장보기 내역을 공유하고 인원과 예산이 비슷한 사용자의 리뷰를 검색해 볼 수 있습니다. <br>
      <br><strong style="color:darkblue">e-투계더와 함께 합리적인 장보기를 해보세요!</strong></p>
    </div>
    <v-layout style="margin:auto; padding-top:80px; width:80%">
      <v-flex>
        <h2 class="headline mb-1">
          <strong>1. REVIEW 페이지</strong>
        </h2>
        <v-flex>
          <v-img :src="getImgUrl('1reviewpage.png')" aspect-ratio="1.7" />
        </v-flex>

        <h2 class="headline mb-1">
          <strong>2. MART 페이지</strong>
        </h2>
        <v-flex>
          <v-img :src="getImgUrl('2martpage.png')" aspect-ratio="1.7" />
        </v-flex>

        <h2 class="headline mb-1">
          <strong>3. MY BUDGET 페이지</strong>
        </h2>
        <v-flex>
          <v-img :src="getImgUrl('3mybudgetpage.png')" aspect-ratio="1.7" />
        </v-flex>

        <h2 class="headline mb-1">
          <strong>4. REVIEW 페이지</strong>
        </h2>
        <v-flex>
          <v-img :src="getImgUrl('4reviewdetail.png')" aspect-ratio="1.7" />
        </v-flex>

        <h2 class="headline mb-1">
          <strong>5. MAP 페이지</strong>
        </h2>
        <v-flex>
          <v-img :src="getImgUrl('5mappage.png')" aspect-ratio="1.7" />
        </v-flex>
      </v-flex>
    </v-layout>

    <div style="margin:auto; width:80%" id="qna">
      
        <h2 class="headline mb-3">
          About
          <strong>Developers</strong>
        </h2>
        <br />
        <p>사이트 오류나 불편 사항은 아래의 담당자 e-mail로 문의해 주시길 바랍니다.</p>
        <br />

        <v-expansion-panels popout>
          <v-expansion-panel hide-actions>
            <v-expansion-panel-header>
              <v-row align="center" class="spacer" no-gutters>
                <v-col cols="4" sm="2" md="1">
                  <v-avatar size="36px">
                    <img alt="Avatar" :src="getImgUrl('안지연.jpg')" />
                  </v-avatar>
                </v-col>

                <v-col class="hidden-xs-only" sm="5" md="3">
                  <v-chip :color="`orange  lighten-4`" class="ml-0 mr-2 black--text" label small>팀장</v-chip>
                  <strong>안지연</strong>
                </v-col>

                <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                  <strong>Front-end</strong>
                </v-col>

                <v-col class="grey--text text-truncate">jennyahn94@naver.com</v-col>
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <v-divider></v-divider>
              <v-card-text>
                <v-row>
                  <v-col cols="4">Front-end (HTML, CSS)</v-col>
                  <v-col>이용 가이드 및 팀 소개 페이지, 회원가입 & 로그인, MY BUDGET 페이지 관련</v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">API 활용</v-col>
                  <v-col>MAP 페이지 지도 기능, REVIEW 페이지 카카오톡 공유 기능</v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">AWS 배포</v-col>
                  <v-col>서버 관련, 이미지 업로드</v-col>
                </v-row>
              </v-card-text>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel hide-actions>
            <v-expansion-panel-header>
              <v-row align="center" class="spacer" no-gutters>
                <v-col cols="4" sm="2" md="1">
                  <v-avatar size="36px">
                    <img alt="Avatar" :src="getImgUrl('김수빈.jpg')" />
                  </v-avatar>
                </v-col>

                <v-col class="hidden-xs-only" sm="5" md="3">
                  <v-chip :color="`yellow lighten-3`" class="ml-0 mr-2 black--text" label small>팀원</v-chip>
                  <strong>김수빈</strong>
                </v-col>

                <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                  <strong>Back-end</strong>
                </v-col>

                <v-col class="grey--text text-truncate">soobin1080@naver.com</v-col>
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <v-divider></v-divider>
              <v-card-text>
                <v-row>
                  <v-col cols="4">Back-end</v-col>
                  <v-col>서버 및 pdf파일 저장 기능</v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">DataBase 구축</v-col>
                  <v-col>개인 정보 및 리스트 & 게시물 관련</v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">데이터 수집 (크롤링)</v-col>
                  <v-col>MART 페이지 상품 데이터 관련</v-col>
                </v-row>
              </v-card-text>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel hide-actions>
            <v-expansion-panel-header>
              <v-row align="center" class="spacer" no-gutters>
                <v-col cols="4" sm="2" md="1">
                  <v-avatar size="36px">
                    <img alt="Avatar" :src="getImgUrl('박상원.jpg')" />
                  </v-avatar>
                </v-col>

                <v-col class="hidden-xs-only" sm="5" md="3">
                  <v-chip :color="`yellow lighten-3`" class="ml-0 mr-2 black--text" label small>팀원</v-chip>
                  <strong>박상원</strong>
                </v-col>

                <v-col class="text-no-wrap hidden-xs-only" cols="5" sm="3">
                  <strong>Front-end</strong>
                </v-col>

                <v-col class="grey--text text-truncate">zetcar54@gmail.com</v-col>
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <v-divider></v-divider>
              <v-card-text>
                <v-row>
                  <v-col cols="4">Front-end (script, Axios, Vuex)</v-col>
                  <v-col>
                    MART 페이지 장보기 내역 데이터 전송 기능, REVIEW 페이지 무한 스크롤 기능
                    <br />전체 사이트 비동기 처리 관련
                  </v-col>
                </v-row>
                <v-row>
                  <v-col cols="4">Git Master</v-col>
                  <v-col>소스 코드 관련</v-col>
                </v-row>
              </v-card-text>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
     
    </div>
  </v-container>
</template>
<script>
export default {
  name: "IntroPage",
  components: {},
  methods: {
    getImgUrl(img) {
      return require("../assets/" + img);
    },
    scrolltoqna(){
      document.getElementById('qna').scrollIntoView();

    }
  }
};
</script>
