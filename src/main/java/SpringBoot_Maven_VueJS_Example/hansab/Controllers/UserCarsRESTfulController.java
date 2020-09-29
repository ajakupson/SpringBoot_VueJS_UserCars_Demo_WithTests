package SpringBoot_Maven_VueJS_Example.hansab.Controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import SpringBoot_Maven_VueJS_Example.hansab.Models.Car;
import SpringBoot_Maven_VueJS_Example.hansab.Models.User;
import SpringBoot_Maven_VueJS_Example.hansab.Utils.xConstants;


@RestController
public class UserCarsRESTfulController {

	public static String fileToReadFrom = xConstants.FILE_WITH_USER_CARS_DATA;
	
	@GetMapping("/api/users")
	public String GetUsers(HttpServletRequest request) throws IOException {
		
		String searchKeyWord = request.getParameter("searchKeyWord");
		String response = "{}";
		
		if(searchKeyWord == null || searchKeyWord.isEmpty()) {
			response = getUsersDataAsJsonString();
		} else {
			ArrayList<User> users = this.getUsersDataAsArray(true);
			users = this.searchInUsersByKeyword(searchKeyWord, users);
			Gson gson = new Gson();
			response = gson.toJson(users);
		}
		
		return response;
	}
	
	public String GetUsersJson() throws IOException {
		
		return this.getUsersDataAsJsonString();
	}
	
	@GetMapping("/api/users/{userId}")
	public String GetUserDetails(@PathVariable("userId") int userId) {
		
		String response = "{}";
		
		try {
			ArrayList<User> usersData = this.getUsersDataAsArray(false);
			User ourUser = usersData.stream()
											.filter(user -> userId == user.getId())
											.findFirst()
											.orElse(new User());
			Gson gson = new Gson();
			response = gson.toJson(ourUser);			   					
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@GetMapping("/api/users/{userId}/cars")
	public String GetUserCars(@PathVariable("userId") int userId, HttpServletRequest request) {
		
		String response = "{}";
		String searchKeyWord = request.getParameter("searchKeyWord");
		
		try {
			ArrayList<User> usersData = this.getUsersDataAsArray(true);
			User ourUser = usersData.stream()
									.filter(user -> userId == user.getId())
									.findFirst()
									.orElse(new User());
			
			if(searchKeyWord != null && !searchKeyWord.isEmpty()) {
				List<Car> filteredCars = ourUser.getCars().stream()
										 .filter(car -> 
										 	String.valueOf(car.getId()).toUpperCase().contains(searchKeyWord.toUpperCase()) ||
										 	car.getMake().toUpperCase().contains(searchKeyWord.toUpperCase()) ||
										 	car.getModel().toUpperCase().contains(searchKeyWord.toUpperCase()) ||
										 	car.getNumberPlate().toUpperCase().contains(searchKeyWord.toUpperCase()))
										 .collect(Collectors.toList());
					
				ourUser.setCars((ArrayList<Car>) filteredCars);
			}
			
			Gson gson = new Gson();
			response = gson.toJson(ourUser);			   					
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@GetMapping("/api/cars")
	public String GetCars(HttpServletRequest request) {
		
		String response = "{}";
		String searchKeyWord = request.getParameter("searchKeyWord");
		Gson gson = new Gson();
		
		try {
			ArrayList<Car> cars = getCarsDataAsArray();
			if(searchKeyWord != null && !searchKeyWord.isEmpty()) {
				cars = this.searchInCarsByKeyword(searchKeyWord, cars);
			}
			response = gson.toJson(cars);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@GetMapping("/api/cars/{carId}")
	public String GetCarById(@PathVariable("carId") int carId) {
		
		String response = "{}";
		
		try {
			ArrayList<Car> cars = getCarsDataAsArray();
			Car ourCar = cars.stream()
					.filter(car -> carId == car.getId())
					.findFirst()
					.orElse(new Car());
			
			Gson gson = new Gson();
			response = gson.toJson(ourCar);			   					
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	public ArrayList<User> getUsersDataAsArray(boolean withCars) throws FileNotFoundException {
		
		ArrayList<User> users = new ArrayList<User>();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToReadFrom));

        Gson gson = new Gson();
        Object usersDataObject = gson.fromJson(bufferedReader, User[].class);
        final JsonArray usersDataJsonArray = gson.toJsonTree(usersDataObject).getAsJsonArray();
        for (JsonElement u : usersDataJsonArray) {
            JsonObject userObject = u.getAsJsonObject();
            
			User user = new User(
					userObject.get("id").getAsInt(),
					userObject.get("name").getAsString()
			);
			
			ArrayList<Car> cars = new ArrayList<Car>();
			
			if(withCars) {
				JsonArray carsJsonArray = userObject.get("cars").getAsJsonArray();
				for (JsonElement c : carsJsonArray) {
					JsonObject carObject = c.getAsJsonObject();
					Car car = new Car(
							carObject.get("id").getAsInt(),
							carObject.get("make").getAsString(),
							carObject.get("model").getAsString(),
							carObject.get("numberplate").getAsString()
					);
					cars.add(car);
				}
			}
			user.setCars(cars);
			users.add(user);
        }
        
		return users;
	}
	
	public ArrayList<Car> getCarsDataAsArray() throws FileNotFoundException {
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToReadFrom));
		ArrayList<Car> cars = new ArrayList<Car>();

        Gson gson = new Gson();
        Object usersDataObject = gson.fromJson(bufferedReader, User[].class);
        final JsonArray usersDataJsonArray = gson.toJsonTree(usersDataObject).getAsJsonArray();
        for (JsonElement u : usersDataJsonArray) {
        	JsonObject userObject = u.getAsJsonObject();
			JsonArray carsJsonArray = userObject.get("cars").getAsJsonArray();
			for (JsonElement c : carsJsonArray) {
				JsonObject carObject = c.getAsJsonObject();
				Car car = new Car(
						carObject.get("id").getAsInt(),
						carObject.get("make").getAsString(),
						carObject.get("model").getAsString(),
						carObject.get("numberplate").getAsString()
				);
				cars.add(car);
			}
        }
        
		return cars;
	}
	
	public String getUsersDataAsJsonString() throws IOException {
		Path filePath = Path.of(fileToReadFrom);
		String jsonString = Files.readString(filePath);
		
		return jsonString;
	}
	
	public ArrayList<User> searchInUsersByKeyword(String keyword, ArrayList<User> users) {
		List<User> foundUsers;
		final String sKeyword = keyword.toUpperCase();
		
		/* Only by name
		foundUsers = users.stream()
						  .filter(user -> user.getFullName().toUpperCase().contains(sKeyword))
						  .collect(Collectors.toList());
		*/
		
		foundUsers = users.stream()
				.filter(user -> (user.getFullName().toUpperCase().contains(sKeyword)) ||
						(user.getCars().stream().anyMatch(
							car -> car.getMake().toUpperCase().contains(sKeyword) ||
							car.getModel().toUpperCase().contains(sKeyword) ||
							car.getNumberPlate().toUpperCase().contains(sKeyword)
						))	
				).collect(Collectors.toList());
		
		return (ArrayList<User>) foundUsers;
	}
	
	public ArrayList<Car> searchInCarsByKeyword(String keyword, ArrayList<Car> cars) {
		List<Car> foundCars;
		final String sKeyword = keyword.toUpperCase();
		
		foundCars = cars.stream()
						.filter(car -> car.getMake().toUpperCase().contains(sKeyword) ||
								car.getModel().toUpperCase().contains(sKeyword) ||
								car.getNumberPlate().toUpperCase().contains(sKeyword)
						).collect(Collectors.toList());
		
		return (ArrayList<Car>) foundCars;
	}
}
