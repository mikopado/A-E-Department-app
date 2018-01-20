
import java.time.LocalDate;

// Person class
public class Person {

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String nationality;	
	private LocalDate dob;
	private String phoneNumber;
	
	public Person(String fname, String lname, String address, String city, String nationality, LocalDate dob, String phone) {
		firstName = fname;
		lastName = lname;
		this.address = address;
		this.city = city;
		this.nationality = nationality;		
		this.dob = dob;
		phoneNumber = phone;
	
	}
	public Person() {
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
