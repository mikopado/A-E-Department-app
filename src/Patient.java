import java.time.LocalDate;

// Patient class which inherits from person. Adding only id as new property
public class Patient extends Person {	
	
	private Integer id;

	public Patient(String fname, String lname, String address, String city, String nationality, LocalDate dob, String phone) {
		super(fname, lname, address, city, nationality, dob, phone);

	}
	public Patient(int id, String fname, String lname, String address, String city, String nationality, LocalDate dob, String phone) {
		this(fname, lname, address, city, nationality, dob, phone);
		
		this.id = id;
	}	

	public Patient() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
}
