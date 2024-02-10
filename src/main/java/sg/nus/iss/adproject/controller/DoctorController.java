package sg.nus.iss.adproject.controller;


import java.text.DecimalFormat;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.PatientService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.interfacemethods.keyWordsApiService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.AppointmentStatusEnum;
import sg.nus.iss.adproject.model.Department;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.model.Patient;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.service.AppointmentServiceImpl;
import sg.nus.iss.adproject.service.FeedbackServiceImpl;
import sg.nus.iss.adproject.service.PatientServiceImpl;
import sg.nus.iss.adproject.service.StaffServiceImpl;

@Controller
@RequestMapping("/Doctor")
public class DoctorController {
	// view appointment
	// view feedback
	// view/edit patient detail

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private PatientService patientService;

	@Autowired
	private keyWordsApiService keywordsApi;

	public void setService(FeedbackServiceImpl feedbackService, AppointmentServiceImpl appointmentService,
			StaffServiceImpl staffService,PatientServiceImpl patientService) {
		this.appointmentService = appointmentService;
		this.feedbackService = feedbackService;
		this.staffService = staffService;
		this.patientService=patientService;
	}

	@GetMapping("")
	public String showDashboard( Model model,HttpSession sessionObj) {
		Staff doctor=(Staff) sessionObj.getAttribute("staffObj");
		List<Appointment>appointments=doctor.getAppointments();
		int staffId=doctor.getId();
		
		List<Appointment>filteredAppointments=new ArrayList<>();
		AppointmentStatusEnum status=AppointmentStatusEnum.Proceeding;
		
		LocalDate currentDate=LocalDate.now();
		LocalTime currentTime=LocalTime.now();
		LocalTime morningShift=LocalTime.of(12, 0, 0);
		LocalTime nightShift=LocalTime.of(17, 30, 0);
		for(Appointment appointment:appointments) {
			if(currentTime.isBefore(morningShift)) 
			{
				if((appointment.getStatus().compareTo(status)==0)&&
					(appointment.getDate().equals(currentDate))&&
					(appointment.getTime().isBefore(morningShift))) 
				{
				filteredAppointments.add(appointment);
				
				}
			}
			else if(currentTime.isAfter(morningShift)&&currentTime.isBefore(nightShift)){
						if((appointment.getStatus().compareTo(status)==0)&&
							(appointment.getDate().equals(currentDate))&&
							(appointment.getTime().isAfter(morningShift))&&
							(appointment.getTime().isBefore(nightShift))) {
							
							filteredAppointments.add(appointment);
				
				}
			}
			

		}
		Collections.sort(filteredAppointments, Comparator.comparing(a ->a.getQueue_number()));
		
		List<Feedback>feedbackListReverse=feedbackService.reverseFeedbacks(staffId);
		List<Feedback>feedbackList6=new ArrayList<>();
		for(Feedback feedback:feedbackListReverse)
			
			if(feedbackList6.size()<=6) {
				if(feedback.getAppointment().getStatus().equals(AppointmentStatusEnum.Completed)){
				feedbackList6.add(feedback);}
			}
		
		model.addAttribute("feedbackList", feedbackList6);
		model.addAttribute("appointmentList", filteredAppointments);
		return "homePage_Doctor";
	}

	// after login ,get this doctor's feedback
	@GetMapping("/FeedbackDetails")
	public String showDoctorFeedbacks(Model model, HttpSession sessionObj) {

		Staff doctor = (Staff) sessionObj.getAttribute("staffObj");

		int staffId = doctor.getId();
		String staffName = doctor.getName();
		Department department = doctor.getDepartment();

		List<Feedback> doctorFeedbackList = feedbackService.findFeedbacksByStaffId(staffId);
		//add this
		double averageScore = calculateAverageFeedbackScore(doctorFeedbackList);

		// add this for average score
		String allFeedbackComments = feedbackService.getAllFeedbackDescriptionsByStaffId(staffId);

		model.addAttribute("staffId",staffId);
		model.addAttribute("staffName", staffName);
		model.addAttribute("doctorFeedbackList", doctorFeedbackList);
		model.addAttribute("department", department);
		// add this
		model.addAttribute("allFeedbackComments", allFeedbackComments);
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


	
	@GetMapping("/AppointmentDetails")
	public String showAppointmentDetails(Model model) {
		List<Appointment>appointmentList=appointmentService.findAllAppointments();
		model.addAttribute("AppointmentList",appointmentList);
		return "viewAppointmentDetail";
	}
	
	@PostMapping("/AppointmentDetails")
	public String showAppointmentDetails(Model model,@RequestParam(name="datepicker")String date) {
		LocalDate localDate = LocalDate.parse(date);
		List<Appointment>appointmentListWithDate=appointmentService.findAppointmentByDate(localDate);
		model.addAttribute("AppointmentList", appointmentListWithDate);
		model.addAttribute("date",localDate);
		return "viewAppointmentDetail";
	}
	
	@GetMapping("/EditAppointmentDetails/{id}")
	public String editPatiendDetails(Model model,@PathVariable("id")int id) {
		Appointment appointment=appointmentService.findAppointmentById(id);
		int departmentId=appointment.getStaff().getDepartment().getId();
		int patientId=appointment.getPatient().getId();
		List<Appointment>appointmentListByPatient=appointmentService.findAppointmentByPatientId(patientId);
		List<Appointment>appointmentListFilteredByDepart=new ArrayList<>();
		for(Appointment filteredAppointment:appointmentListByPatient) {
			if(filteredAppointment.getStaff().getDepartment().getId()==(departmentId))
				appointmentListFilteredByDepart.add(filteredAppointment);
		}
		model.addAttribute("Appointment",appointment);
		model.addAttribute("medicalRecords",appointmentListFilteredByDepart);
		return "editAppointmentDetail";
	}
	@PostMapping("/EditAppointmentDetails/{id}")
	public String editPatientDetails(@ModelAttribute("Appointment")Appointment appointment,Model model,@PathVariable("id")int id) {
		//appointment.setId(id);
		appointmentService.updateAppointmentDetails(id,appointment.getMedical_condition());
		return "redirect:/Doctor/EditAppointmentDetails/"+id;
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
	// add this for average score
    public double calculateAverageFeedbackScore(List<Feedback> feedbacks) {
        OptionalDouble average = feedbacks.stream().mapToDouble(Feedback::getScore).average();
        double avg = average.isPresent() ? average.getAsDouble() : 0.0;
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(avg));
    }

}




