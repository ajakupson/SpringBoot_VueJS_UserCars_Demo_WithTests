import routes from './routes'
import Home from './pages/Home'
import Users from './pages/Users'
import UserDetails from './pages/UserDetails'
import UserCars from './pages/UserCars'
import Cars from './pages/Cars'
import CarDetails from './pages/CarDetails'
import NotFound from './pages/NotFound'

var app = new Vue({
    el: '#app',
    components: {
		'Home': Home,
		'Users': Users,
		'UserDetails': UserDetails,
		'UserCars': UserCars,
		'Cars': Cars,
		'CarDetails': CarDetails,
        'NotFound': NotFound
    },
    created: function () {
      console.log("app launched");
      console.log("this.currentRoute", this.currentRoute);
    },
    data: {
		currentRoute: window.location.pathname
 	},
    computed: {
        ViewComponent() {
			for(var i = 0; i < routes.length; i++) {
				if(this.currentRoute.match(routes[i].regEx)) {
					return routes[i].view;
				}
			}
			return NotFound;
        }
    },
    render(h) { return h(this.ViewComponent) }
});

window.addEventListener('popstate', () => {
    app.currentRoute = window.location.pathname;
});