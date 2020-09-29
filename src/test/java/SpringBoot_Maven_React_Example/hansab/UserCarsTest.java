package SpringBoot_Maven_React_Example.hansab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import SpringBoot_Maven_VueJS_Example.hansab.Controllers.UserCarsRESTfulController;
import SpringBoot_Maven_VueJS_Example.hansab.Models.Car;
import SpringBoot_Maven_VueJS_Example.hansab.Models.User;
import SpringBoot_Maven_VueJS_Example.hansab.Utils.xConstants;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

public class UserCarsTest {

	private static MockMvc mockMvc;
	private static UserCarsRESTfulController controller;
	
	
    @BeforeAll // wants static to run
    public static void setup() {
        controller = new UserCarsRESTfulController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@Test
	public void FileWithDataExists() {
		File file = new File(UserCarsRESTfulController.fileToReadFrom);
		assertTrue(file.exists());
	}
	
	@Test
	public void WhenGetUsersDataAsArray_ThenReturnNonEmptyArray() throws FileNotFoundException {
		
		ArrayList<User> found = controller.getUsersDataAsArray(true);
	    assertThat(found).isNotEmpty();
	}
	
	@Test
	public void WhenGetUsersDataAsArray_ThenThrowFileNotFoundException() {
		
		UserCarsRESTfulController.fileToReadFrom = "Qwerty.json";
		try {
			controller.getUsersDataAsArray(true);
		} catch (FileNotFoundException e) {
			UserCarsRESTfulController.fileToReadFrom = xConstants.FILE_WITH_USER_CARS_DATA;
			assertThat(e.getMessage().equals("Qwerty.json (No such file or directory"));
		}
	}
	
	@Test
	public void WhenGetCarsDataAsArray_ThenReturnNonEmptyArray() throws FileNotFoundException {
		
		ArrayList<Car> found = controller.getCarsDataAsArray();
	    assertThat(found).isNotEmpty();
	}
	
	@Test
	public void WhenGetUsersDataAsJsonString_ThenReturnValidJsonSrtring() throws IOException {
		
		String result = controller.getUsersDataAsJsonString();
		
		Gson gson = new Gson();
        Object usersDataObject = gson.fromJson(result, User[].class);
        final JsonArray usersDataJsonArray = gson.toJsonTree(usersDataObject).getAsJsonArray();
        int firstUserId = usersDataJsonArray.get(0).getAsJsonObject().get("id").getAsInt();
        
        assertThat(firstUserId).isEqualTo(1);
	}
	
	@Test
	public void WhenSearchInUsersByKeyword_ThenReturnArrayOfTwo() throws FileNotFoundException {
		
		ArrayList<User> users = controller.getUsersDataAsArray(false);
		ArrayList<User> foundUsers = controller.searchInUsersByKeyword("Teet", users);
		
		assertThat(foundUsers).hasSize(2);
	}
	
	@Test
	public void WhebSearchInCarsByKeyword_ThenReturnArrayOfOne() throws FileNotFoundException {
		ArrayList<Car> cars = controller.getCarsDataAsArray();
		ArrayList<Car> foundCars = controller.searchInCarsByKeyword("Skoda", cars);
		
		assertThat(foundCars).hasSize(1);
	}
	
	@Test
	public void ShouldFetchAllUsers() throws Exception {
		
		ArrayList<User> users = controller.getUsersDataAsArray(false);
		
		mockMvc.perform(get("/api/users"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(users.size())));
	}
	
	@Test
	public void ShouldFetchSecondUserPillePurk() throws Exception {
		
		User secondUser = new User(2, "Pille Purk", new ArrayList<Car>());
		
		mockMvc.perform(get("/api/users/2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(secondUser.getFullName())));
	}
	
	@Test
	public void ShouldFetchEmptyUser() throws Exception {
		
		User emptyUser = new User();
		
		mockMvc.perform(get("/api/users/250"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(emptyUser.getId())));
	}
	
	@Test
	public void ShouldReturn404() throws Exception {
		
		mockMvc.perform(get("/api/employees/1"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void ShouldFetchUserWithCars() throws Exception {
		
		User thirdUser = new User(3, "Mati Kaal");
		ArrayList<Car> userCars = new ArrayList<Car>();
		
		Car car = new Car(5, "Lada", "2101", "445KKK");
		userCars.add(car);
		
		car = new Car(6, "Audi", "A6", "997HHH"); 
		userCars.add(car);
		
		thirdUser.setCars(userCars);
		
		mockMvc.perform(get("/api/users/3/cars"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(thirdUser.getId())))
			.andExpect(jsonPath("$.name", is(thirdUser.getFullName())))
			.andExpect(jsonPath("$.cars.size()", is(thirdUser.getCars().size())))
			.andExpect(jsonPath("$.cars[0].numberplate", is(thirdUser.getCars().get(0).getNumberPlate())))
			.andExpect(jsonPath("$.cars[1].numberplate", is(thirdUser.getCars().get(1).getNumberPlate())));
	}
	
	@Test
	public void ShouldFetchAllCars() throws Exception {
		
		ArrayList<Car> cars = controller.getCarsDataAsArray();
		
		mockMvc.perform(get("/api/cars"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(cars.size())));
	}
	
	@Test
	public void ShouldFetchCarWithId8() throws Exception {
		
		Car car = new Car(8, "Audi", "A6", "876OUI"); 
		
		mockMvc.perform(get("/api/cars/8"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberplate", is(car.getNumberPlate())));
	}
	
	@Test
	public void ShouldFetchEmptyCar() throws Exception {
		
		Car car = new Car(); 
		
		mockMvc.perform(get("/api/cars/800"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(car.getId())));
	}
}
