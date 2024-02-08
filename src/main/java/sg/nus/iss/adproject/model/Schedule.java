package sg.nus.iss.adproject.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate date;
	private LocalTime time_start;
	private LocalTime time_end;
	private int patient_slot;

	@ManyToOne
	private Staff staff;

	public Schedule() {
	}

	public Schedule(LocalDate date, LocalTime time_start, LocalTime time_end, int patient_slot) {
		this.date = date;
		this.time_start = time_start;
		this.time_end = time_end;
		this.patient_slot = patient_slot;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime_start() {
		return time_start;
	}

	public void setTime_start(LocalTime time_start) {
		this.time_start = time_start;
	}

	public LocalTime getTime_end() {
		return time_end;
	}

	public void setTime_end(LocalTime time_end) {
		this.time_end = time_end;
	}

	public int getPatient_slot() {
		return patient_slot;
	}

	public void setPatient_slot(int patient_slot) {
		this.patient_slot = patient_slot;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
}