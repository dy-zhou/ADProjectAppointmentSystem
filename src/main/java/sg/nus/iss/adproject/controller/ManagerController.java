package sg.nus.iss.adproject.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sg.nus.iss.adproject.interfacemethods.DepartmentService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.interfacemethods.keyWordsApiService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.Department;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.service.DepartmentServiceImpl;
import sg.nus.iss.adproject.service.FeedbackServiceImpl;
import sg.nus.iss.adproject.service.StaffServiceImpl;

@Controller
@RequestMapping("Manager")
public class ManagerController {
	// view feedback

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private StaffService staffService;

	@Autowired
	private keyWordsApiService keywordsApi;

	@Autowired
	private DepartmentService departmentService;

	public void setFeedbackService(FeedbackServiceImpl feedbackService, StaffServiceImpl staffService,
			DepartmentServiceImpl departmentService) {
		this.feedbackService = feedbackService;
		this.staffService = staffService;
		this.departmentService = departmentService;
	}

	@GetMapping("")
	public String showDashboard(Model model) {
		List<Feedback> feedbackList = feedbackService.findAllFeedbacks();
		model.addAttribute("feedbackList", feedbackList);

		// add this to get all department
		List<Department> departments = departmentService.findAllDepartments();
		model.addAttribute("departments", departments);

		// add this for doctor list
		List<Staff> doctors = staffService.findAllDoctors();
		model.addAttribute("doctors", doctors);
		
		//this to show average score
		Map<Integer, Double> doctorAverageScores = new HashMap<>();
		for (Staff doctor : doctors) {
		    List<Feedback> doctorFeedbackList = feedbackService.findFeedbacksByStaffId(doctor.getId());
		    double averageScore = calculateAverageFeedbackScore(doctorFeedbackList);
		    doctorAverageScores.put(doctor.getId(), averageScore);
		}
		model.addAttribute("doctorAverageScores", doctorAverageScores);
		

		return "homePage_Manager";
	}
//	
//	@GetMapping("/feedback")
//	public String viewFeedbackDetail() {
//		
//		return "feedbackDetail";
//	}

	// will show feedback list and related doctor
	@GetMapping("/allFeedbacks")
	public String showAllFeedbacks(Model model) {
		List<Feedback> feedbackList = feedbackService.findAllFeedbacksAndDoctorName();
		model.addAttribute("feedbackList", feedbackList);
		return "allFeedbackList";
	}

	@PostMapping("/deleteFeedback")
	public String deleteFeedback(@RequestParam("feedbackId") int feedbackId) {
		feedbackService.deleteFeedbackById(feedbackId);
		// Redirect to the page where feedbacks are displayed
		return "redirect:/Manager/allFeedbacks";
	}

//	@GetMapping("/doctorFeedbacks/{id}")
//	public String showDoctorFeedbacks(@PathVariable("id") int staffId, Model model) {
	@GetMapping("/doctorFeedbacks")
	public String showDoctorFeedbacks(@RequestParam("id") int staffId, Model model) {
		List<Feedback> doctorFeedbackList = feedbackService.findFeedbacksByStaffId(staffId);

		Staff staff = staffService.findStaffById(staffId);
		if (staff == null || !staff.getDesignation().equals("doctor")) {
			model.addAttribute("errorMessage", "Please enter a valid Doctor ID.");
			return "errorPage";
		}
		String staffName = staff.getName();
		Department department = staff.getDepartment();

		// add this
		String allFeedbackComments = feedbackService.getAllFeedbackDescriptionsByStaffId(staffId);
		double averageScore = calculateAverageFeedbackScore(doctorFeedbackList);

		model.addAttribute("doctorFeedbackList", doctorFeedbackList);
		// show which doctor's feedback
		model.addAttribute("staffName", staffName);

		// add this
		model.addAttribute("allFeedbackComments", allFeedbackComments);
		model.addAttribute("department", department);
		model.addAttribute("averageScore", averageScore);

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

	//// Method to Parse api JSON response to get prediction in string format
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

	public double calculateAverageFeedbackScore(List<Feedback> feedbacks) {
		OptionalDouble average = feedbacks.stream().mapToDouble(Feedback::getScore).average();
		double avg = average.isPresent() ? average.getAsDouble() : 0.0;
		DecimalFormat df = new DecimalFormat("#.#");
		return Double.parseDouble(df.format(avg));
	}

//	@GetMapping("feedbackDetails/{id}")
//	public String showFeedbackDetails(@PathVariable("id") int feedbackId, Model model) {
//		Feedback feedbackDetails = feedbackService.getFeedbackDetail(feedbackId);
//		model.addAttribute("feedbackDetails", feedbackDetails);
//		return "feedbackDetail";
//
//	}

}