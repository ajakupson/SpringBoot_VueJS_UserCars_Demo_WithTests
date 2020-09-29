export default [
	{'regEx': /^\/$/, 'view': 'Home'},
	{'regEx': /^\/users$/, 'view': 'Users'},
	{'regEx': /^\/users\/[1-9]+$/, 'view': 'UserDetails'},
	{'regEx': /^\/users\/[1-9]+\/cars$/, 'view': 'UserCars'},
	{'regEx': /^\/cars$/, 'view': 'Cars'},
	{'regEx': /^\/cars\/[1-9]+$/, 'view': 'CarDetails'}
]
