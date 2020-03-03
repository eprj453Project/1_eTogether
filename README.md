## [#1. Web/Mobile_웹기술 PJT : 웹 프론트엔드 / 백엔드 개발]

### [공통-Sub PJT II] 개발 시스템 API 활용 및 관리자 기능 구현

#### 1. 소개

###### < 단체 장보기 서비스  "e-투계더" >

https://i02b109.p.ssafy.io/

e-투계더는 emart 상품을 기반으로 워크샵, MT, 여행 등 단체로 장을 봐야 하는 상황에 카테고리 별로 필요한 상품을 보여주고 예산 별 카테고리 비율을 추천해주는 서비스입니다. 선택한 상품의 합계를 계산해주고, 장보기 리스트를 저장하고 pdf로 다운로드할 수 있습니다. 서비스 이용 후기를 등록하여 다른 사용자들과 장보기 내역을 공유하고 인원과 예산이 비슷한 사용자의 리뷰를 검색해 볼 수 있습니다. e-투계더 서비스로 합리적인 장보기를 해보세요.



#### 2. 개발 환경

- 기술 스택
  - Front-end: Vue.js, Vue-cli, Vuetify, Vuex, Node.js
  - Back-end: Spring boot, MyBatis, Restful API
  - DB: MySQL
  - IDE: Visual Studio Code, Spring Tool Suite
  - Server : AWS(ubuntu) (Back-end: Apache Tomcat / Front-end: Nginx / SSH: certbot)
  - 협업 툴: GitLab, Jira, slack
- 기술 특이점
  - 라이브러리: JWT, HtmlEmail, jsoup, Multipart, html2canvas, jspdf
  - API: 카카오톡 링크 api, 카카오맵 api



#### 3. 시스템 아키텍쳐

![시스템아키텍쳐](\typora-user-images\시스템아키텍쳐.png)

#### 4. ERD

![ERD](\typora-user-images\ERD.PNG)

#### 5. Restful API 

###### 	User API

- `POST /api/emailCheck` : 이메일 중복체크
- `POST /api/logout` : 로그아웃
- `POST /api/pwdCheck` : 내 정보 확인 시 비밀번호 체크
- `POST /api/myselfDetail/{email}` : 현재 로그인 되어있는 사용자의 상세정보 보기
- `POST /api/findEmail` : 이메일 찾기
- `POST /api/findPwd` : 비밀번호 찾기 및 임시 비밀번호 발급
- `POST /api/updateMyself` : 현재 로그인된 사용자의 정보 수정
- `POST /api/updatePwd` : 현재 로그인된 사용자의 비밀번호 수정
- `POST /api/deleteMyself` : 탈퇴
- `GET /api/user` : 전체 회원 정보 리스트 반환
- `GET /api/fingUserByEmail/{email}` : 이메일로 회원 검색
- `POST /api/auth/signin` : 권한을 가지고 로그인
- `POST /api/auth/signup` : 권한을 가지고 회원가입



###### 	Product API

- `GET /api/product/{category}` : 카테고리 별 상품 보기
- `GET /api/product/{category}/{keyword}` : 카테고리 별 상품 검색



###### Budget API

- `POST /api/budget` : 내 예산안 저장하기
- `GET /api/budget/{user_email}` : 내 예산안 목록 보기
- `GET /api/budget/detail/{budget_num}` : 내 예산안 상세보기
- `POST /api/budget/{budget_num}/{suitability}` : 내 예산안 가져오기
- `DELETE /api/budget/{budget_num}` : 내 예산안 지우기



###### Review API

- `POST /api/review` : review 작성하기
- `GET /api/review` : 전체 review 목록 보기
- `GET /api/review/{review_num} ` : review 상세보기
- `POST /api/review/{review_num} ` : review 수정하기
- `DELETE /api/review/{review_num} ` : review 삭제하기
- `POST /api/review/like_count` : review 좋아요 수 update
- `DELETE /api/review/like_count/{review_num}/{user_email}` : review 좋아요 취소
- `GET /api/review/{personnel}/{budget}` : 인원과 예산에 맞는 review 검색



###### Reply API

- `GET /api/reply/{review_num}` : review 전체 댓글 보여주기
- `POST /api/reply` : 댓글 작성하기
- `POST /api/reply/update` : 댓글 수정하기
- `DELETE /api/reply/{review_num}` : 댓글 삭제하기



###### Recommand API

- `GET /api/recommend` : 내 예산에 맞게 카테고리 별 비율 추천



------



## FrontEnd

- 프레임워크 : JavaScript 기반 Vue.js

- 비동기 원리 : 

  - Vue.js의 기본개념인 상위, 하위 컴포넌트간의 이벤트 이동으로 비동기 구현

    1.  상위 컴포넌트에서 발생한 이벤트 결과는 props로 하위 컴포넌트에 전달 후 하위 컴포넌트에 반영
    2.  하위 컴포넌트에서 발생한 이벤트는 emit으로 상위 컴포넌트에 전달 후 다시 1번 과정을 통해 하위 컴포넌트에 반영

    3.  부득이하게 상, 하위 관계가 아닌 컴포넌트 간 통신이 필요한 경우 EventBus를 통해 이벤트 전달.

  - Vuex 저장소를 통한 상태관리

    1. Vuex의 state로 로그인 상태 및 전역 컴포넌트에서 써야 할 데이터들을 관리

    2. Mutations, actions를 이용한 전역 컴포넌트 상태 비동기로 변경 및 유지

       

