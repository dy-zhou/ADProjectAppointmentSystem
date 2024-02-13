package sg.nus.iss.adproject.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String address;
	
	private String sex;
	
	private String allergy;
	
	private String medical_condition;
	
	private String additional_info;
	
	//mapping
	@ManyToMany
	@JoinTable(name = "Patient_User",
	joinColumns = @JoinColumn(name = "patient_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<Customer>customers;
	
	@OneToMany(mappedBy = "patient")
	private List<Appointment> appointments;
	
	
	public Patient() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAllergy() {
		return allergy;
	}
	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}
	public String getMedical_condition() {
		return medical_condition;
	}
	public void setMedical_condition(String medical_condition) {
		this.medical_condition = medical_condition;
	}
	public String getAdditional_info() {
		return additional_info;
	}
	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setUsers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	
	
}
