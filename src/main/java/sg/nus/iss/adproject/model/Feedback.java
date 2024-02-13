package sg.nus.iss.adproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String Description;
	private double score;

	@ManyToOne
	private Customer customer;

	@OneToOne
	private Appointment appointment;

	public Feedback() {
	}

	public Feedback(String Description, String feedback_type) {
		this.Description = Description;
		
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}


	

	public Appointment getAppointment() {
		return appointment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public double getScore() {
		return score;
	}


}