package SpringBoot_Maven_VueJS_Example.hansab.Models;

public class Car {

	private int id;
	private String make;
	private String model;
	private String numberplate;
	
	public Car(){}
	
	public Car(int id, String make, String model, String numberPlate) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.numberplate = numberPlate;
	}
	
	// Getters / Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getNumberPlate() {
		return numberplate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberplate = numberPlate;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", make=" + make + ", model=" + model + ", numberPlate=" + numberplate + "]";
	}
}
