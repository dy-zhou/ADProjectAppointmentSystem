package sg.nus.iss.adproject.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Staff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String password;
	private String designation;

	@OneToMany(mappedBy = "staff")
	private List<Appointment> appointments;

	@OneToMany(mappedBy = "staff")
	private List<Schedule> schedules;

	@OneToOne(mappedBy="staff")
	private Room room;

	@ManyToOne
	private Department department;

	public Staff() {
	}

	public Staff(String name, String password, String designation) {
		this.name = name;
		this.password = password;
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}
