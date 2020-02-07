import Vue from 'vue'
import Router from 'vue-router'
import MainPage from './views/MainPage.vue'
import PostPage from './views/PostPage.vue'
import PortfolioPage from './views/PortfolioPage.vue'
import SignUpPage from './views/SignUpPage.vue'
import MartPage from './views/MartPage.vue'
import TeamPage from './views/TeamPage.vue'
import UserInfoPage from './views/UserInfoPage.vue'
import PwdCheckPage from './views/PwdCheckPage.vue'
import MyBudgetPage from './views/MyBudgetPage.vue'
import ReviewPage from './views/ReviewPage.vue'
import UserInfoModifyPage from './views/UserInfoModifyPage.vue'
import FindPwdPage from './views/FindPwdPage.vue'
import FindEmailPage from './views/FindEmailPage.vue'


Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
		{
			path: '/',
			name: 'home',
			component: MainPage
		},
		{
			path: '/post',
			name: 'post',
			component: PostPage
		},
		{
			path: '/portfolio',
			name: 'portfolio',
			component: PortfolioPage
		},
		{

			path: '/product',
			name: 'product',
			component: MartPage
		},				
		{
			path: '/team',
			name: 'team',
			component: TeamPage
		},	
		{
			path: '/signup',
			name: 'signup',
			component: SignUpPage
		},
		{
			path: '/userinfo',
			name: 'userinfo',
			component: UserInfoPage

		},
		{
			path: '/mybudget',
			name: 'mybudget',
			component: MyBudgetPage
		},
		{
			path: '/review',
			name: 'review',
			component: ReviewPage
		},
		{
			path: '/pwdcheck',
			name: 'pwdcheck',
			component: PwdCheckPage
		},
		{
			path: '/userinfomodify',
			name: 'userinfomodify',
			component: UserInfoModifyPage
		},
		{
			path: '/findemail',
			name :'findemail',
			component: FindEmailPage
		},
		{
			path: '/findpwd',
			name :'findpwd',
			component: FindPwdPage
		},

  ]
})
