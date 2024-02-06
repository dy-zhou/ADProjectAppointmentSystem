package sg.nus.iss.adproject.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class predictDiseaseApiService {
private final String apiURL = "https://predictdisease.azurewebsites.net/predict";
	
	public ResponseEntity<String> sendSymptomsForPrediction(List<String> selectedSymptoms) {
		RestTemplate restTemplate = new RestTemplate();
		
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<List<String>> requestEntity = new HttpEntity<>(selectedSymptoms, httpHeaders);
		//restTemplate.postForObject(apiURL, selectedSymptoms, String.class);
		
//		return restTemplate.postForEntity(apiURL, selectedSymptoms,String.class);
		return restTemplate.postForEntity(apiURL, requestEntity,String.class);
	}
}
