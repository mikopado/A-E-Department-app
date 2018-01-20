
import java.time.LocalDateTime;

// Transaction class to store patient transactions
public class Transaction {
	
	private Integer id;
	private String description;
	private LocalDateTime date;
	private Integer patientID;
	
	public Transaction() {
		
	}
	
	public Transaction(String description, LocalDateTime date, int patientId) {
		
		this.description = description;
		this.date = date;
		patientID = patientId;
		
	}
	public Transaction(int id, String description, LocalDateTime date, int patientId) {
		this(description, date, patientId);
		
		this.id = id;
		
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPatientID() {
		return patientID;
	}
	public void setPatient(Integer patient) {
		this.patientID = patient;
	}
}
