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
	private String feedback_type;

	@ManyToOne
	private User user;

	@OneToOne
	private Appointment appointment;

	public Feedback() {
	}

	public Feedback(String Description, String feedback_type) {
		this.Description = Description;
		this.feedback_type = feedback_type;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getFeedback_type() {
		return feedback_type;
	}

	public void setFeedback_type(String feedback_type) {
		this.feedback_type = feedback_type;
	}

	public Appointment getAppointment() {
		return appointment;
	}
	
	public int getId()
	{
		return id;
	}

}