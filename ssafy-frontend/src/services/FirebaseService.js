import firebase from 'firebase/app'
import 'firebase/firestore'
import 'firebase/auth'

const POSTS = 'posts'
const PORTFOLIOS = 'portfolios'


// Setup Firebase
// Do change to your own firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyDsZyLT45vBk_XE32xRyM8MM9dA6HKOCsQ",
  authDomain: "solar-virtue-258705.firebaseapp.com",
  databaseURL: "https://solar-virtue-258705.firebaseio.com",
  projectId: "solar-virtue-258705",
  storageBucket: "solar-virtue-258705.appspot.com",
  messagingSenderId: "153925964602",
  appId: "1:153925964602:web:76986ed1f3a73852bd7603",
  measurementId: "G-T6D7YR0J51"
};
firebase.initializeApp(firebaseConfig)

const firestore = firebase.firestore()

export default {
	getPosts() {
		const postsCollection = firestore.collection(POSTS)
		return postsCollection
				.orderBy('created_at', 'desc')
				.get()
				.then((docSnapshots) => {
					return docSnapshots.docs.map((doc) => {
						let data = doc.data()
						data.created_at = new Date(data.created_at.toDate())
						return data
					})
				})
	},
	postPost(title, body) {
		return firestore.collection(POSTS).add({
			title,
			body,
			created_at: firebase.firestore.FieldValue.serverTimestamp()
		})
	},
	getPortfolios() {
		const postsCollection = firestore.collection(PORTFOLIOS)
		return postsCollection
				.orderBy('created_at', 'desc')
				.get()
				.then((docSnapshots) => {					
					return docSnapshots.docs.map((doc) => {
						let data = doc.data()
						data.created_at = new Date(data.created_at.toDate())
						return data
					})
				})
	},
	postPortfolio(title, body, img) {
		return firestore.collection(PORTFOLIOS).add({
			title,
			body,
			img,
			created_at: firebase.firestore.FieldValue.serverTimestamp()
		})
	},
	loginWithGoogle() {
		console.log('google active')
		let provider = new firebase.auth.GoogleAuthProvider()
		return firebase.auth().signInWithPopup(provider).then(function(result) {
			let accessToken = result.credential.accessToken
			let user = result.user
			return result
		}).catch(function(error) {
			console.error('[Google Login Error]', error)
		})
	},
	loginWithFackbook() {
		console.log('facebook active')
		var provider = new firebase.auth.FacebookAuthProvider();
		return firebase.auth().signInWithPopup(provider).then(function(result) {
			console.log(result)
			let accessToken = result.credential.accessToken
			let user = result.user
			//return result
		}).catch(function(error) {
			console.log('[Facebook Login Error]', error)
			// let errorCode = error.code;
			// let errorMessage = error.message;
			// let email = error.email;
			// let credential = error.credential;
		});
	}
}