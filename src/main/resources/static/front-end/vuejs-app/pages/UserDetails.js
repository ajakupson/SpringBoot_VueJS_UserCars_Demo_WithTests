import MainLayout from '../layouts/Main'
import VLink from '../components/VLink'
import axios from 'axios'

export default {
	template: `<main-layout>
				<p><v-link href="/">Home</v-link></p>
				<p><v-link href="/users">Users</v-link></p>
				<p><v-link href="/cars">Cars</v-link></p>
				<h1>User details</h1>
				<div class="container">
					<div class="user-container">
						<p class="user"><b>Fullname:</b>{{ user.name }}(<b>ID:</b> {{ user.id }})</p>
						<p class="left-padd-120">... Some additional information about the user ...</p>
						<p><b><a :href="'/users/' + user.id + '/cars'" class="left-padd-40">User cars</a></b></p>
					</div>
				</div>
			   </main-layout>`,
	components: {
		'v-link': VLink,
		'main-layout': MainLayout
    },
	mounted: function(){
		console.log("UserDetails mounted");
		var that = this;
		var currentAppPath = window.location.pathname;
		axios
			.get('/api' + currentAppPath)
		    .then(response => {
		    	//console.log("response.data", response.data);
				that.user = response.data;
			})
		    .catch(error => console.log(error));
	},
	data: function() {
		return {
			user: {id: null, name: null, cars: []},
		}
	},
	methods: {},
	watch: {},
}