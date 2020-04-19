package cl.geraud.demoApi.rs.model;

public class PersonDb {

	private int rutInt;
	private char rutDv;
	private String name;
	private String lastName;
	private int age;
	private String course;
	
	public int getRutInt() {
		return rutInt;
	}
	public void setRutInt(int rutInt) {
		this.rutInt = rutInt;
	}
	public char getRutDv() {
		return rutDv;
	}
	public void setRutDv(char rutDv) {
		this.rutDv = rutDv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
}
