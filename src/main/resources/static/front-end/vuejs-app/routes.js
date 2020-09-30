export default [
	{'regEx': /^\/$/, 'view': 'Home'},
	{'regEx': /^\/users$/, 'view': 'Users'},
	{'regEx': /^\/users\/\d+$/, 'view': 'UserDetails'},
	{'regEx': /^\/users\/\d+\/cars$/, 'view': 'UserCars'},
	{'regEx': /^\/cars$/, 'view': 'Cars'},
	{'regEx': /^\/cars\/\d+$/, 'view': 'CarDetails'}
]
