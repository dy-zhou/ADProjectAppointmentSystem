package sg.nus.iss.adproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.interfacemethods.keyWordsApiService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.AppointmentStatusEnum;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.service.AppointmentServiceImpl;
import sg.nus.iss.adproject.service.FeedbackServiceImpl;
import sg.nus.iss.adproject.service.StaffServiceImpl;

@Controller
@RequestMapping("/Doctor")
public class DoctorController {
	// view appointment
	// view feedback
	// view/edit patient detail

	@Autowired
	private FeedbackService feedbackService;
	private AppointmentService appointmentService;
	private StaffService staffService;

	@Autowired
	private keyWordsApiService keywordsApi;

	public void setService(FeedbackServiceImpl feedbackService, AppointmentServiceImpl appointmentService,
			StaffServiceImpl staffService) {
		this.appointmentService = appointmentService;
		this.feedbackService = feedbackService;
		this.staffService = staffService;
	}

	@GetMapping("")
	public String showDashboard(Model model, HttpSession sessionObj) {
		Staff doctor = (Staff) sessionObj.getAttribute("staffObj");
		List<Appointment> appointments = doctor.getAppointments();
		List<Feedback> feedbacks = new ArrayList<>();
		List<Appointment> filteredAppointments = new ArrayList<>();
		AppointmentStatusEnum status = AppointmentStatusEnum.Proceeding;
		for (Appointment appointment : appointments) {
			if (feedbacks.size() <= 6)
				feedbacks.add(appointment.getFeedback());

			if (appointment.getStatus().compareTo(status) == 0)
				filteredAppointments.add(appointment);

		}
		model.addAttribute("feedbackList", feedbacks);
		model.addAttribute("appointmentList", filteredAppointments);
		return "homePage_Doctor";
	}

	// after login ,get this doctor's feedback
	@GetMapping("/FeedbackDetails")
	public String showDoctorFeedbacks(Model model, HttpSession sessionObj) {

		Staff doctor = (Staff) sessionObj.getAttribute("staffObj");

		int staffId = doctor.getId();
		String staffName = doctor.getName();

		List<Feedback> doctorFeedbackList = feedbackService.findFeedbacksByStaffId(staffId);

		// add this
		String allFeedbackComments = feedbackService.getAllFeedbackDescriptionsByStaffId(staffId);

		model.addAttribute("staffName", staffName);
		model.addAttribute("doctorFeedbackList", doctorFeedbackList);
		// add this
		model.addAttribute("allFeedbackComments", allFeedbackComments);

		// this is link the api
		StringBuilder jsonString = new StringBuilder();
		jsonString.append("{");
		jsonString.append("\"text\": \"").append(allFeedbackComments).append("\"");
		jsonString.append("}");
		String jsonStringSend = jsonString.toString();
		System.out.println(jsonStringSend);
		System.out.println(jsonString);
		System.out.println(allFeedbackComments);

		ResponseEntity<String> responseEntity = keywordsApi.sendComments(jsonStringSend);
		// Extract information from response
		String apiResponse = responseEntity.getBody();
		// Parse JSON response
		List<String> keyWords = extractKeywordsFromApiResponse(apiResponse);
		// Bind api response
		model.addAttribute("keywords", keyWords);

		return "doctorFeedbackList";
	}

	// Method to Parse api JSON response to get prediction in string format
	private List<String> extractKeywordsFromApiResponse(String apiResponse) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(apiResponse);
			JsonNode keywordsNode = jsonNode.get("Top 10 Keywords");
			List<String> keywordsList = new ArrayList<>();
			for (JsonNode keyword : keywordsNode) {
				String keywords = keyword.asText();
				keywordsList.add(keywords);
			}
			return keywordsList;
		} catch (Exception e) {
			List<String> errorList = new ArrayList<>();
			errorList.add("Error parsing API response");
			return errorList;
		}
	}

}