package sg.nus.iss.adproject.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
import sg.nus.iss.adproject.interfacemethods.DepartmentService;
import sg.nus.iss.adproject.interfacemethods.DiseaseService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.PatientService;
import sg.nus.iss.adproject.interfacemethods.ScheduleService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.Department;
import sg.nus.iss.adproject.model.Disease;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.model.Patient;
import sg.nus.iss.adproject.model.Schedule;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.model.SympGroup;
import sg.nus.iss.adproject.model.Symptom;
import sg.nus.iss.adproject.model.Symptoms;
import sg.nus.iss.adproject.model.SymptomsSelected;
import sg.nus.iss.adproject.service.AppointmentServiceImpl;
import sg.nus.iss.adproject.service.DepartmentServiceImpl;
import sg.nus.iss.adproject.service.FeedbackServiceImpl;
import sg.nus.iss.adproject.service.PatientServiceImpl;
import sg.nus.iss.adproject.service.predictDiseaseApiService;

@Controller
@RequestMapping("Nurse")
public class NurseController {

	@Autowired
	private PatientService patientService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DiseaseService diseaseService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private ScheduleService scheduleService;

	//For Disease prediction
	@Autowired
	private Symptoms symptoms;
	
	@Autowired
	private predictDiseaseApiService predictApi;
	
	
	public void setPatientService(PatientServiceImpl patientService,
								  AppointmentServiceImpl appointmentService,
								  FeedbackServiceImpl feedbackService) {
		this.patientService=patientService;
		this.appointmentService=appointmentService;
		this.feedbackService=feedbackService;

	}
//make/view/ appointment

	@GetMapping
	public String showDashboard(Model model) {
		List<Appointment> appointmentList = appointmentService.findAllAppointments();
		List<Feedback> feedbackList = feedbackService.findAllFeedbacks();
		model.addAttribute("feedbackList", feedbackList);
		model.addAttribute("appointmentList", appointmentList);
		return "homePage_Nurse";
	}
	//Show Patient
	@GetMapping("/patientList")
	public String patientList(Model model) {
		List<Patient> patients = patientService.getAllPatients();
		model.addAttribute("patients", patients);
		return "patientList";
	}
	//Add new Patient
	@GetMapping("/newPatient")
	public String showAddPatientForm(Model model) {
		Patient patient = new Patient();
		model.addAttribute("patient", patient);
		return "addNewPatient";
	}
	//Get Patient List
	@PostMapping("/newPatient")
	public String submitPatient(@ModelAttribute("patient") Patient patient) {

		patientService.addPatient(patient);
		return "redirect:/patientList";
	}
	//Get Patient
	@GetMapping("patient/{id}")
	public String patientDetail(@PathVariable("id") int id, 
			Model model, HttpSession session) {

		Patient patient = patientService.getPatientById(id);
		if (patient != null) {
			model.addAttribute("patient", patient);
			session.setAttribute("patient", patient);
			session.setAttribute("patientId", id);
			System.out.println("Patient ID: " + patient.getName());
			return "patientDetail";
		} else
			return "addNewPatient";
	}
	
	//Disease Prediction
	@GetMapping("patient/disease/{appointmentId}")
	public String selectDisease(Model model,
			@PathVariable("appointmentId")int appointmentId,
			HttpSession session) {
		session.setAttribute("appointmentId", appointmentId);

		//Add Symptom Groups
		model.addAttribute("symptomGroup", symptoms.getSymptomsGroup());
		//Initialize selectedSymptomGroup for binding 
		model.addAttribute("selectedSymptomGroup", new SympGroup());
		
		return "symptoms";
	}
	//Get Symptoms from Symptoms group
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
	
	//Submit selected symptoms for disease prediction
	@PostMapping("submitSelectedSymptoms")
	public String submitSymptoms( @ModelAttribute SymptomsSelected selectedSymptoms,
			Model model,
			HttpSession session) {
		//Add Patient, appointmentId to session
		Patient patient = (Patient) session.getAttribute("patient");
		int appointmentId = (Integer)session.getAttribute("appointmentId");
		
		//Add patientId for goback button
		model.addAttribute("patientId", (Integer)session.getAttribute("patientId"));
		
		//Get list of symptoms and convert to string
		List<String> symptomsList = selectedSymptoms.getSymptoms();
		String symptomString = symptomsList.stream()
	            .collect(Collectors.joining(", "));
		
		//Add symptoms string to model
		model.addAttribute("patientSymptoms", symptomString);
		System.out.println("Patient ID: " + patient.getName());
		System.out.println("Appointment ID: " + appointmentId);
		
		//Call external api with selected symptoms
		ResponseEntity<String> responseEntity = predictApi.sendSymptomsForPrediction(selectedSymptoms.getSymptoms());
		
		//Extract information from the response
		String apiResponse = responseEntity.getBody();
		
		//Parse JSON response
		String prediction = extractPredictionFromApiResponse(apiResponse);
		HttpStatusCode statusCode = responseEntity.getStatusCode();
		
		//Update Patient medical condition
		String medicalCondition = "Disease: " + prediction + "; Symptoms: " + symptomString; 
		patient.setMedical_condition(medicalCondition);
		patientService.updatePatient((Integer)session.getAttribute("patientId"), patient);
		
		//Bind api response
		model.addAttribute("prediction", prediction);
		model.addAttribute("statusCode", statusCode);
		
		//Set appointment department
		//Get disease id from prediction
		Department diseseDepartment = diseaseService.findDepartmentByDiseaseName(prediction).getDepartment();
		
		//Get appointment by appointment id
		Appointment patientAppointment = appointmentService.getAppointmentDetail(appointmentId);
		
		//Set appointment department id 
		patientAppointment.setDepartment(diseseDepartment);
		appointmentService.updateAppointment(patientAppointment);
		
		//Bind patient appointment
		model.addAttribute("patientAppointment", patientAppointment);
		
		/*
		 * Select available doctors for appointment
		 */
		
		//Get doctor list from department id
		List<Staff> departmentStaff = staffService.findStaffByDepartmentId(diseseDepartment.getId());
		System.out.println("Department staff: " + departmentStaff);
		
		//List to hold staffAvailable
		List<Staff> staffAvailable = new ArrayList<>();
		
		//Get schedule from doctor list
		List<Schedule> staffSchedule = new ArrayList<>();
		
		//Check if staff have schedule, if no schedule add to available doctors
		for(Staff staff:departmentStaff) {
			List<Schedule> schedule = scheduleService.findSchedulesByStaff(staff.getId());//TODO:Not finding the staff
			if(schedule.isEmpty()) {
				System.out.println("Schedule in loop: " + scheduleService.findSchedulesByStaff(staff.getId()));
				staffAvailable.add(staff);
			}else {
				staffSchedule.addAll(schedule);//TODO: add staff who have no schedule
			}
		}
		System.out.println("Available staff after check schedules: " + staffAvailable);
		//For doctors with schedules
		//Check if schedule clash with appointment
		//if staffSchdeule isEmpty, staff is free, can add staff
		if(staffSchedule.isEmpty()) {
			model.addAttribute("departmentStaff", departmentStaff);
		}
		else {
			//Get appointmentDate and appointmentTime for checking
			//AppointmentEndTime is 30mins after appointmentStartTime
			LocalDate appointmentDate = patientAppointment.getDate();
			LocalTime appointmentStartTime = patientAppointment.getTime();
			LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
			
			
			//Check date then time
			//If appointmentDate same as appointmentDate then check if time clash
			for(Schedule schedule : staffSchedule) {
				if(schedule.getDate() == appointmentDate) {
					//Get scheduleStartTime and scheduleStartTime for checking
					LocalTime scheduleStartTime = schedule.getTime_start();
					LocalTime scheduleEndTime = schedule.getTime_end();
					//If appointEndTime is before scheduleStartTime or
					//appointmentStartTime is after scheduleEndTime, no clash
					//then add staff to staffAvailable
					if(appointmentEndTime.isBefore(scheduleStartTime) 
							|| appointmentStartTime.isAfter(scheduleEndTime)) {
						staffAvailable.add(schedule.getStaff());
					}
				}else {
					staffAvailable.add(schedule.getStaff());
				};
				
			}
			
			model.addAttribute("departmentStaff", staffAvailable);
		}
		System.out.println("Staff Schedule: " + staffSchedule);
		return "predictedDisease";
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
	
	//Assign Doctor
	@PostMapping("selectDoctors")
	private String assignDoctor(Model model,
			@ModelAttribute Appointment patientAppointment,
			HttpSession session){
		int patientId = (Integer)session.getAttribute("patientId");
		
		//Get staff from 
		Staff selctedStaff = patientAppointment.getStaff();
		patientAppointment.setStaff(selctedStaff);
		appointmentService.updateAppointment(patientAppointment);
		//TODO:Alert window when updateAppointment is successful
		return "redirect:patient/" + patientId;// TODO:to redirect back to patient appointments detail
		
	}
	

	
	

}
