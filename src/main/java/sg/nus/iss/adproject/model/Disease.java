package sg.nus.iss.adproject.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


@Entity
public class Disease {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@ManyToMany
	@JoinTable(name = "Disease_Symptom",
	joinColumns = @JoinColumn(name = "disease_id"),
	inverseJoinColumns = @JoinColumn(name = "symptom_id"))
	private List<Symptom> symptoms;
	
	public Disease() {
		
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Symptom> getSymptoms() {
		return symptoms;
	}


	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}


}
