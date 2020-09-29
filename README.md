# SpringBoot_VueJS_UserCars_Demo_WithTests
Simple SpringBoot demo with JUnit tests

Demo on [Heroku](https://springboot-user-cars-demo.herokuapp.com/)

```
src/main/
├── java/
|   └── SpringBoot_Maven_VueJS_Example.hasab/ -- server side
|       ├── SpringBootMavenVueJSExampleApplication.java
|       ├── Config/
│       |   └── WebConfiguration.java
│       ├── Controllers/
│       |   ├── EntryPointController.java
│       |   └── UserCarsRESTfulController.java
│       ├── Models/
│       |   ├── User.java
|       |   ├── Car.java
|       └── Utils/
|           └── xConstants.java
|
├── resources/ -- everything related to client side
|    ├── static/front-end
|    |   ├── vuejs-app/
|    |   |   ├── app.js -- VueJS app
|    |   |   ├── build/
|    |   |   |   └── main.bundle.js -- compiled JavaScript used in index.html
|    |   |   ├── components/
|    |   |   |   └── VLink.js
|    |   |   ├── layouts/
|    |   |   |   └── Main.js
|    |   |   └── pages/
|    |   |       ├── Home.js
|    |   |       ├── Users.js
|    |   |       ├── UserDetails.js
|    |   |       ├── UserCars.js
|    |   |       ├── Cars.js
|    |   |       ├── CarDetails.js
|    |   |       └── NotFound.js
|    |   |
|    |   └── assets/ -- third party libs, custom styles, images and JS utils
|    |       ├── css/
|    |       │   └── main.css
|    |       ├── img/
|    |       │   └── ...
|    |       └── js/
|    |            └── ...
|    |
|    ├── templates/
|    |   └── index.html
|    |
|    └── application.properties
|    
src/test/
├── java/ 
|   └── SpringBoot_Maven_VueJS_Example.hasab/ -- tests
|       └── UserCarsTest.java
├── pom.xml
├── package.json
├── webpack.config.js
├── UserCarsData.json
.
.
.
. . . .
```
