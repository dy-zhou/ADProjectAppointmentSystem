package sg.nus.iss.adproject.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate date;
	private LocalTime time;
	private String queue_number;
	// private String medical_condition;
	private AppointmentStatusEnum status;

	@OneToOne(mappedBy = "appointment")
	private Feedback feedbacks;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Staff staff;
	
	@ManyToOne
	private Department department;

	public Appointment() {
	}

	public Appointment(LocalDate date, LocalTime time, String queue_number, AppointmentStatusEnum status) {
		this.date = date;
		this.time = time;
		this.queue_number = queue_number;
		// this.medical_condition = medical_condition;
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getQueue_number() {
		return queue_number;
	}

	public void setQueue_number(String queue_number) {
		this.queue_number = queue_number;
	}
// connected with patient
//	public String getMedical_condition() {
//		return medical_condition;
//	}
//
//	public void setMedical_condition(String medical_condition) {
//		this.medical_condition = medical_condition;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AppointmentStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatusEnum status) {
		this.status = status;
	}

	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Patient getPatient() {
		return patient;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department departmentId) {
		this.department = departmentId;
	}

}
