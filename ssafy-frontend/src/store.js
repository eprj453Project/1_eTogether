import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    accessToken: localStorage.getItem('accessToken'),
    user: localStorage.getItem('user'),
    email: localStorage.getItem('email'),
    personnel: '', // 인원
    budget: '', // 예산 
    tokenType: '',
    budgetlist : [],
    total: 0,

    
  

  },
  getters : {
    isLoggedIn: function(state) {
      return state.accessToken ? true : false
    },
    requestHeader : function(state) {
      return {
      headers: {
          'Authorization': 'Bearer ' + state.accessToken
        }
      }
    },
    userId: function(state) {
      return state.user ? state.user : ""
    }
  },
  mutations : {
    setToken: function(state, token){
      state.accessToken = token
    },
    deleteProduct: function(state, idx) {
      var i = state.budgetlist.indexOf(idx)
      // state.total -= state.budgetlist[i].price;
      state.budgetlist.splice(i,1);  
      this.budgetalert();
    },
    addCart: function(state, product) {
      const idx = state.budgetlist.findIndex(function (budget) {
        return budget.pro_name === product.pro_name
      })
      console.log(idx)
      if (idx === -1) {
          state.budgetlist.push(product)

      }
      else {
        state.budgetlist[idx].quantity += 1

      }
      state.total += (product.price*=product.quantity);
      console.log(state.budgetlist)
    },
    cartClear: function(state) {
      state.budgetlist = []
    }

  },

  actions : {
 
  login: function(options, token) {
    options.commit('setToken', token)
  },

  logout: function(options) {
    options.commit('setToken')
  },
  
  addCartAsync: function(options, product) {
    console.log('addCartAsync')
    options.commit('addCart', product)
  },

  deleteProductAsync: function(options, idx) {
    options.commit('deleteProduct', idx)
  }

}
})