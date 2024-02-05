package sg.nus.iss.adproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sg.nus.adproject.apiService.predictDiseaseApiService;
import sg.nus.iss.adproject.model.SympGroup;
import sg.nus.iss.adproject.model.Symptoms;
import sg.nus.iss.adproject.model.SymptomsSelected;



@Controller
@RequestMapping("symptoms")
public class symptomsController {
	@Autowired
	private Symptoms symptoms;
	
	@Autowired
	private predictDiseaseApiService predictApi;
	
	@GetMapping("/disease")
	public String selectDisease(Model model) {
		//Add Symptom Groups
		model.addAttribute("symptomGroup", symptoms.getSymptomsGroup());
		//Initialize selectedSymptomGroup for binding 
		model.addAttribute("selectedSymptomGroup", new SympGroup());
		
		return "symptoms";
	}
	
	@PostMapping("selectSymptoms")
	public String fetchSymptoms(@ModelAttribute SympGroup selectedSymptomGroup,
			 Model model) {
		//Bind selected symptom group to model
		model.addAttribute("selectedSymptomGroup", selectedSymptomGroup);
		//Get related symptoms from symptom group
		String selected = selectedSymptomGroup.getSymptomGroup();
		List<String> relatedSymptoms = symptoms.getSymptoms(selected);
		//Add related symptoms to model
		model.addAttribute("relatedSymptoms", relatedSymptoms);
		//Initialize selectedSymptoms model
		model.addAttribute("selectedSymptoms", new SymptomsSelected());
		return "selectSymptoms";
	}
	
	@PostMapping("submitSelectedSymptoms")
	public String submitSymptoms( @ModelAttribute SymptomsSelected selectedSymptoms,
			Model model) {
		//Add selected symptoms
		model.addAttribute("selectedSymptoms", selectedSymptoms);
		System.out.println("Selected Symptoms: " + selectedSymptoms.getSymptoms());

		//Call external api with selected symptoms
		ResponseEntity<String> responseEntity = predictApi.sendSymptomsForPrediction(selectedSymptoms.getSymptoms());
		
		//Extract information from the response
		String apiResponse = responseEntity.getBody();
		
		//Parse JSON response
		String prediction = extractPredictionFromApiResponse(apiResponse);
		HttpStatusCode statusCode = responseEntity.getStatusCode();
		
		//Bind api response
		model.addAttribute("prediction", prediction);
		model.addAttribute("statusCode", statusCode);
		
		//predictApi.sendSymptomsForPrediction(selectedSymptoms.getSymptoms());
		return "predictedDisease";//TODO: make html for response
	}
	
	//Parse api JSON response to get prediction in string format
		private String extractPredictionFromApiResponse(String apiResponse) {
	        try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(apiResponse);
	            return jsonNode.get("prediction").asText();
	        } catch (Exception e) {
	            return "Error parsing API response";
	        }
	    }
	
	
}
