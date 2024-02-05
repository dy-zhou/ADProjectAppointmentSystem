package sg.nus.iss.adproject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Symptoms {
private Map<String, List<String>> symptoms;
	
	public Symptoms() {
		symptoms = new HashMap<>();
		
		//Skin, Dermatological 
		symptoms.put("Skin", Arrays.asList("itching", "skin rash", "nodal skin eruptions","dischromic  patches",
				"constipation", "pain during bowel movements", "pain in anal region", "bloody stool", "irritation in anus", 
				"skin rash", "pus filled pimples", "blackheads", "scurring", "joint pain", "skin peeling", "silver like dusting",
				"small dents in nails", "inflammatory nails", "high fever", "blister", "red sore around nose","yellow crust ooze"));
		
		//Stomach, Gastrointestinal
		symptoms.put("Stomach", Arrays.asList("stomach pain", "acidity", "ulcers on tongue", "vomiting", "cough", "chest pain",
				"itching", "yellowish skin", "nausea", "loss of appetite", "abdominal pain", "yellowing of eyes", "vomiting", 
				"passage of gases", "internal itching", "sunken eyes", "dehydration", "diarrhoea"));
		
		//Infectious Disease
		symptoms.put("Infectious Disease", Arrays.asList("muscle wasting", "patches in throat", "high fever", "extra marital contacts", 
				"chills", "vomiting", "sweating", "headache", "nausea", "muscle pain", "itching", "skin rash", "fatigue", "lethargy", 
				"loss of appetite", "mild fever", "swelled lymph nodes", "malaise", "red spots over body", "skin rash", "chills", 
				"joint pain", "pain behind the eyes", "back pain", "constipation", "abdominal pain", "diarrhoea", "toxic look (typhos)",
				 "belly pain"));
		
		//Head, Neurological
		symptoms.put("Head", Arrays.asList("acidity", "indigestion", "headache", "blurred and distorted vision", "excessive hunger", 
				"stiff neck", "depression", "irritability", "visual disturbances", "back pain", "weakness in limbs", "neck pain", 
				"dizziness", "loss of balance", "vomiting", "weakness of one body side", "altered sensorium", "nausea", "spinning movements",
				 "unsteadiness"));
		
		//Lungs, Respiratory
		symptoms.put("Lungs", Arrays.asList("fatigue", "cough", "high fever", "breathlessness", "family history", "mucoid sputum", "chills", 
				"vomiting", "fatigue", "weight loss", "sweating", "loss of appetite", "mild fever", "yellowing of eyes", "swelled lymph nodes", 
				"malaise", "phlegm", "chest pain", "blood in sputum", "continuous sneezing", "chills", "headache", "throat irritation", 
				"redness of eyes", "sinus pressure", "runny nose", "congestion", "loss of smell", "muscle pain", "fast heart rate",
				 "rusty sputum"));
		
		//Bone Inflammation, Rheumatological Disease
		symptoms.put("Bone Inflmmation", Arrays.asList( "joint pain", "neck pain", "knee pain", "hip joint pain", "swelling joints", "painful walking",
				"muscle weakness", "stiff neck", "movement stiffness"));
		
		//Urinal Disease, Urological Disease
		symptoms.put("Urinal Disease", Arrays.asList("burning micturition", "bladder discomfort", "foul smell of urine", "continuous feel of urine"));
		
		//Heart, Blood Vessels/ Vascular Disorders
		symptoms.put("Heart, Blood Vessels", Arrays.asList("headache", "chest pain", "dizziness", "loss of balance", "lack of concentration", "vomiting", 
				"breathlessness", "sweating", "fatigue", "cramps", "bruising", "obesity", "swollen legs", "swollen blood vessels", "prominent veins on calf"));
		
	}
	
	//Get Symptoms Group
	public List<String> getSymptomsGroup(){
		return new ArrayList<>(symptoms.keySet());
	}
	//Get Symptoms from Symtoms Group
	public List<String> getSymptoms(String symptomsChoice){
		return symptoms.get(symptomsChoice);
	}
}
