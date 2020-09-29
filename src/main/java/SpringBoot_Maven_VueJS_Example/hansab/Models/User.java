package SpringBoot_Maven_VueJS_Example.hansab.Models;

import java.util.ArrayList;

public class User {

	private int id;
	private String name;
	private ArrayList<Car> cars;
	
	public User(){}
	
	public User(int id, String fullName, ArrayList<Car> cars) {
		super();
		this.id = id;
		this.name = fullName;
		this.cars = cars;
	}
	
	public User(int id, String fullName) {
		super();
		this.id = id;
		this.name = fullName;
	}
	
	// Getters / setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFullName() {
		return name;
	}
	public void setFullName(String fullName) {
		this.name = fullName;
	}
	
	public ArrayList<Car> getCars() {
		return cars;
	}
	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + name + ", cars=" + cars + "]";
	}	
}
