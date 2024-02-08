package sg.nus.iss.adproject.interfacemethods;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class keyWordsApiService {
	private final String apiURL = "https://keywordsserver.azurewebsites.net/keywords";
	public ResponseEntity<String> sendComments(String comments){
		RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(comments, httpHeaders);
		return restTemplate.postForEntity(apiURL, requestEntity, String.class);
	}
}