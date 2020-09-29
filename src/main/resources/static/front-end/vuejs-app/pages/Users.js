import MainLayout from '../layouts/Main'
import VLink from '../components/VLink'
import axios from 'axios'

export default {
	template: `<main-layout>
				<p><v-link href="/">Home</v-link></p>
				<p><v-link href="/cars">Cars</v-link></p>
				<h1>Users</h1>
				<div class="container">
					<div class="search-container left-padd-100">
						<input type="text" class="text-input long" v-model="searchKeyWord" placeholder="Search by name, company, model or plate number"/>
						<button class="button "v-on:click="search()">Search</button>
					</div>
					<div class="sort-by-container">
						<div class="select">
			                <label for="sort-by">Sort by:</label>
			                <select id="sort-by" name="sort-by" v-model="sortBy">
			                    <option value="id">ID</option>
			                    <option value="name">Name</option>
			                </select>
							<div class="select-arrow"></div>
						</div>
						<div class="select">
			                <label for="sort-order">Order:</label>
			                <select id="sort-order" name="sort-order" v-model="sortOrder">
			                    <option value="ASC" selected>ASC</option>
			                    <option value="DESC">DESC</option>
			                </select>
							<div class="select-arrow"></div>
						</div>
	                </div>
					<div class="user-container" v-for="(user, userIndx) in usersData">
						<p class="user"><b>Fullname:</b><a :href="'/users/' + user.id">{{ user.name }}</a>(<b>ID:</b> {{ user.id }})</p>
						<p class="left-padd-100"><b>Cars: </b></p>
						<div class="car-container" v-for="(car, carIndx) in user.cars">
							<p class="left-padd-100 car"><b>Company: </b>{{ car.make }} (<b>ID:</b>{{ car.id }})<p>
							<p class="left-padd-120">Model: {{ car.model }}<p>
							<p class="left-padd-120">Plate number: {{ car.numberplate }}</p>
						</div>
					</div>
				</div>
			   </main-layout>`,
	components: {
		'v-link': VLink,
		'main-layout': MainLayout
    },
	mounted: function(){
		console.log("Users mounted");
		this.ajaxGet();
	},
	data: function() {
		return {
			usersData: [],
			sortBy: "id",
            sortOrder: "ASC",
			searchKeyWord: ""
		}
	},
	methods: {
    	sortUsers: function() {
        	var that = this;
            function dynamicSort(property, sortOrder) {
                var sortOrder = 1;
                if (that.sortOrder == "DESC") {
                    sortOrder = -1;
                }
                return function(a, b) {
                    var result = (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
                    return result * sortOrder;
                }
            }

            this.usersData.sort(dynamicSort(this.sortBy));
        },
		ajaxGet: function() {
			var that = this;
			axios
				.get('/api/users', {
  					params: {
    					searchKeyWord: that.searchKeyWord
  					}
				})
		    	.then(response => {
		    		//console.log("response.data", response.data);
					that.usersData = response.data;
                	that.sortUsers();
				})
		    	.catch(error => console.log(error));
		},
		search() {
			this.ajaxGet();
		}
	},
	watch: {
        sortBy: function (newVal, oldVal) {
            this.sortUsers();
        },
        sortOrder: function (newVal, oldVal) {
            this.sortUsers();
        }
    },
}