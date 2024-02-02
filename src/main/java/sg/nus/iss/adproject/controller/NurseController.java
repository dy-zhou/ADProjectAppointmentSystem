package sg.nus.iss.adproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.PatientService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.model.Patient;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.model.Symptom;
import sg.nus.iss.adproject.service.AppointmentServiceImpl;
import sg.nus.iss.adproject.service.FeedbackServiceImpl;
import sg.nus.iss.adproject.service.PatientServiceImpl;

@Controller
@RequestMapping("Nurse")
public class NurseController {
	
	@Autowired
	private PatientService patientService;
	private AppointmentService appointmentService;
	private FeedbackService feedbackService;
	private DiseaseService diseaseService;
	
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
		List<Appointment>appointmentList=appointmentService.findAllAppointments();
		List<Feedback>feedbackList=feedbackService.findAllFeedbacks();
		model.addAttribute("feedbackList",feedbackList);
		model.addAttribute("appointmentList", appointmentList);
		return "homePage_Nurse";
	}
	
	
	
	@GetMapping("/patientList")
	public String patientList() {
		return "patientList";
	}

	
	
	@GetMapping("/newPatient")
	public String showAddPatientForm(Model model) {
		Patient patient=new Patient();
		model.addAttribute("patient",patient);
		return "addNewPatient";
	}
	
	
	
	@PostMapping("/newPatient")
	public String submitPatient(@ModelAttribute("patient")Patient patient) {
		
				patientService.addPatient(patient);
				return "redirect:/patientList";
	}
	
	
	
	@GetMapping("/patient/{id}")
	public String patientDetail(@PathVariable("id") int id, Model model) {
	Patient patient= patientService.getPatientById(id);
	if(patient!=null)
		return "patientDetail";
	else
		return "addNewPatient";
	}
	
	
	
	@GetMapping("/createAppiontment/step1")
	public String checkSymptoms() {
		
		return "checkSymptoms";
	}
	
	
	
	@PostMapping("/createAppiontment/step1")
	public String addSymptoms(@RequestParam("syptomId") int[]symptomIds, HttpSession sessionObj,Model model) {
		sessionObj.setAttribute("symptomsIds", symptomIds);
		return "pickDoctors";
	}
	
	@GetMapping("/createAppointment/step2")
	public String pickDoctorsForm(Model model) {
		List<Staff>doctorListByDepartment=findDeparmentByDisease()
	}
	
	
}
