import MainLayout from '../layouts/Main'
import VLink from '../components/VLink'
import axios from 'axios'

export default {
	template: `<main-layout>
				<p><v-link href="/">Home</v-link></p>
				<p><v-link href="/users">Users</v-link></p>
				<p><v-link href="/cars">Cars</v-link></p>
				<h1>Car details</h1>
				<div class="container">
					<div class="car-container">
						<p class="left-padd-100 car"><b>Company: </b>{{ car.make }} (<b>ID:</b>{{ car.id }})<p>
						<p class="left-padd-120">Model: {{ car.model }}<p>
						<p class="left-padd-120">Plate number: {{ car.numberplate }}</p>
						<p class="left-padd-120">... Some additional information about the car ...</p>
					</div>
				</div>
			   </main-layout>`,
	components: {
		'v-link': VLink,
		'main-layout': MainLayout
    },
	mounted: function(){
		console.log("CarDetails mounted");
		var that = this;
		var currentAppPath = window.location.pathname;
		axios
			.get('/api' + currentAppPath)
		    .then(response => {
		    	//console.log("response.data", response.data);
				that.car = response.data;
			})
		    .catch(error => console.log(error));
	},
	data: function() {
		return {
			car: {id: null, make: null, model: null, numberplate: null },
		}
	},
	methods: {},
	watch: {},
}