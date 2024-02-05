package sg.nus.iss.adproject.service;

import org.springframework.web.reactive.function.client.WebClient;

public class PredictDiseaseApi {

	WebClient webClient=WebClient.create();
	String apiUrl="";
	String response=webClient.get()
					.uri(apiUrl)
					.retrieve()
					.bodyToMono(String.class)
					.block();
	
	//WebClient webClient

}
