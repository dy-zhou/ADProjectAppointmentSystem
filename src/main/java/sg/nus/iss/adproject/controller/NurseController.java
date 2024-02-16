package sg.nus.iss.adproject.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.interfacemethods.DepartmentService;
import sg.nus.iss.adproject.interfacemethods.DiseaseService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.PatientService;
import sg.nus.iss.adproject.interfacemethods.ScheduleService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.AppointmentStatusEnum;
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

	@GetMapping("")
	public String showDashboard(Model model) {
		List<Staff> doctorList = staffService.findAllDoctors();
		List<Appointment> appointmentList =appointmentService.findAppointmentStatus(AppointmentStatusEnum.Proceeding);
		model.addAttribute("appointmentList", appointmentList);
		model.addAttribute("doctorList", doctorList);
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
	public String submitPatient(@ModelAttribute("patient") Patient patient,Model model) {

		patientService.addPatient(patient);
		return "redirect:/Nurse/patientList";
	}
	

	@GetMapping("editPatient/{id}")
	public String editPatient(@PathVariable("id")int id,
			Model model) {
		Patient updatePatient = patientService.getPatientById(id);
		model.addAttribute("updatePatient", updatePatient);
		
		return "editPatient";
		
	}
	
	@PostMapping("updatePatient/{id}")
	public String updatePatient(Model model, 
			@ModelAttribute("updatePatient") Patient updatePatient,
			@PathVariable("id")int id) {
		int patientId = id;
		
		patientService.updatePatient(id, updatePatient);
		
		System.out.println("Patient ID: " + patientId);
		return "redirect:/Nurse/patient/" + patientId;
		
	}
	
	@PostMapping("deletePatient/{id}")
	public String deletePatient(Model model, 
			@ModelAttribute("updatePatient") Patient updatePatient,
			@PathVariable("id")int id) {
		
		int patientId = id;
		model.addAttribute("id", patientId);
		Patient patient = updatePatient;
		patient.setName(null);
		return "redirect:/Nurse/patient/" + patientId;
		
	}
	
	
	

	@GetMapping("/viewAppointmentDetails/{id}")
	public String viewAppointmentDetails(@PathVariable("id") int id,Model model) {
		List<Appointment>appointmentList=appointmentService.findAppointmentByStaffId(id);
		model.addAttribute("AppointmentList", appointmentList);
		return "viewAppointmentDetail";
	}

	//Get Patient
	@GetMapping("patient/{id}")
	public String patientDetail(@PathVariable("id") int id, 
			Model model, HttpSession session) {
		Patient patient = patientService.getPatientById(id);
		Appointment appointment = new Appointment();
		session.setAttribute("patientId", id);
		
		if (patient != null) {
			model.addAttribute("patient", patient);
			model.addAttribute("patientId", id);
			model.addAttribute("appointment", appointment);
			model.addAttribute("successMessage", "");
//			session.setAttribute("patient", patient);
//			session.setAttribute("patientId", id);
			return "patientDetail";
		} else
			return "addNewPatient";
	}
	
	//Update Appointment Status
	@PostMapping("patient/updateStatus/{appointmentId}")
	public String updateAppointmentStatus(@PathVariable("appointmentId") int appointmentId,
			Model model, @ModelAttribute("appointment") Appointment appointment,
			HttpSession session) {
		Appointment appointmentStaus = appointment;
		AppointmentStatusEnum status = appointmentStaus.getStatus();
		
		Appointment appointmentUpdate = appointmentService.findAppointmentById(appointmentId);
		appointmentUpdate.setStatus(status);
		appointmentService.updateAppointment(appointmentUpdate);
		
		int pId = (Integer)session.getAttribute("patientId");
		return "redirect:/Nurse/patient/" + pId;
	}
	
	
	//Disease Prediction
//	@GetMapping("patient/disease/{appointmentId}")
	@GetMapping("patient/disease/{patientId}")
	public String selectDisease(Model model,
			@PathVariable("patientId") int patientId,
//			@PathVariable("appointmentId")int appointmentId,
			HttpSession session) {
//		session.setAttribute("appointmentId", appointmentId);
		session.setAttribute("patientId", patientId);

		//Add Symptom Groups
		model.addAttribute("symptomGroup", symptoms.getSymptomsGroup());
		//Initialize selectedSymptomGroup for binding 
		model.addAttribute("selectedSymptomGroup", new SympGroup());
		
		return "symptoms";
	}
	//Get Symptoms from Symptoms group
	@PostMapping("selectSymptoms")
	public String fetchSymptoms(@ModelAttribute SympGroup selectedSymptomGroup,
			 Model model, HttpSession session) {
		
		//Get patiendId from session
		int idPatient = (Integer)session.getAttribute("patientId");
		model.addAttribute("patientId",idPatient);
		
		//Get patient from patientId
		Patient patient = patientService.getPatientById(idPatient);
		session.setAttribute("patient", patient);
		
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
	@PostMapping("makeAppointment")
	public String submitSymptoms( @ModelAttribute SymptomsSelected selectedSymptoms,
			Model model,
			HttpSession session) {
		//Add Patient, //appointmentId to session
		Patient patient = (Patient) session.getAttribute("patient");
//		int appointmentId = (Integer)session.getAttribute("appointmentId");
		
		//Add patientId for goback button
		model.addAttribute("patientId", (Integer)session.getAttribute("patientId"));
		
		//Get list of symptoms and convert to string
		List<String> symptomsList = selectedSymptoms.getSymptoms();
		String symptomString = symptomsList.stream()
	            .collect(Collectors.joining(", "));
		session.setAttribute("symptomString", symptomString);
		
		//Add symptoms string to model
		model.addAttribute("patientSymptoms", symptomString);
		//Call external api with selected symptoms
		ResponseEntity<String> responseEntity = predictApi.sendSymptomsForPrediction(selectedSymptoms.getSymptoms());
		
		//Extract information from the response
		String apiResponse = responseEntity.getBody();
		
		//Parse JSON response
		String prediction = extractPredictionFromApiResponse(apiResponse);
		HttpStatusCode statusCode = responseEntity.getStatusCode();
		
		//Add Patient medical condition
		String medicalCondition = "Disease: " + prediction + "; Symptoms: " + symptomString;
		model.addAttribute("medicalCondition", medicalCondition);
		session.setAttribute("medicalCondition", medicalCondition);
		System.out.println("Medical Condition: " + medicalCondition);
		
		//Bind api response
		model.addAttribute("prediction", prediction);
		session.setAttribute("prediction", prediction);
		
		//Set appointment department
		//Get disease id from prediction
		Department diseseDepartment = diseaseService.findDepartmentByDiseaseName(prediction).getDepartment();
		session.setAttribute("department", diseseDepartment);
		//Make new appointment
		Appointment newAppointment = new Appointment();
//		newAppointment.setDepartment(diseseDepartment);
		model.addAttribute("newAppointment", newAppointment);
		
		//Get doctor list from department id
//		List<Staff> availableStaff = staffService.findStaffByDepartmentId(diseseDepartment.getId());
		List<Staff> availableStaff = staffService.findStaffByDepartmentAndDesignation(diseseDepartment.getId(), "doctor");
		System.out.println("Available Staff length:" + availableStaff.size());
		
		model.addAttribute("departmentStaff", availableStaff);
		return "makeAppointment";
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
	
	@PostMapping("selectDoctors")
	private String assignDoctor(Model model,
			@ModelAttribute Appointment newAppointment,
			HttpSession session,
			HttpServletResponse response){
		//Get patient Id
		int patientId = (Integer)session.getAttribute("patientId");
		
		//Get patient medical condition
		String medCondition = (String)session.getAttribute("medicalCondition");
		
		//Get department
		Department department = (Department)session.getAttribute("department");
		
		//Get patient Appointment
		Appointment patientAppointmentUpdate = newAppointment;
		Patient patient = (Patient) session.getAttribute("patient");
		
		//Get staff from 
		Staff selectedStaff = newAppointment.getStaff();
		int staffId = selectedStaff.getId();
		System.out.println("Selected Staff Id: " + staffId);
		
		//Get selected time
		LocalTime selectedTime = newAppointment.getTime();
		System.out.println("Selected Time: " + selectedTime);
		
		//Get selected date
		LocalDate selectedDate = newAppointment.getDate();
		System.out.println("SelectedDate: " + selectedDate);
		
		//Check doctor availability 
		//Check Slot number by staff and by morning or afternoon
		LocalTime morning = LocalTime.of(8, 0);
		LocalTime afternoon = LocalTime.of(14, 0);
		System.out.println("Morning Time: " + morning);
		System.out.println("Afternoon Time: " + afternoon);
		
		Integer morningSlots = 0;
		Integer afternoonSlots = 0;
		
		//Check if date available, if not available check slots
		List<Schedule> selectedStaffSchedule = scheduleService.findSchedulesByStaff(staffId);
		
		boolean dateAvailable = false;
		
		for(Schedule sched : selectedStaffSchedule) {
			LocalDate scheduleDate = sched.getDate();
			if(!scheduleDate.equals(selectedDate)) {
				dateAvailable = true;
			}else if(scheduleDate.equals(selectedDate)) {
				dateAvailable = false;
			}
		}
		
		if(dateAvailable) {
			System.out.println("Date Available: " + dateAvailable);
			//addAppointment			
			patientAppointmentUpdate.setDate(selectedDate);
			patientAppointmentUpdate.setTime(selectedTime);
			patientAppointmentUpdate.setQueue_number(afternoonSlots);
			patientAppointmentUpdate.setStatus(AppointmentStatusEnum.Proceeding);
			patientAppointmentUpdate.setStaff(selectedStaff);
			patientAppointmentUpdate.setPatient(patient);
//			patientAppointmentUpdate.setDepartment(department);
			patientAppointmentUpdate.setQueue_number(1 + staffId);
			//Save appointment
			appointmentService.createAppointment(patientAppointmentUpdate);
			
			//Update patient medical condition
			patient.setMedical_condition(medCondition);
			patientService.updatePatient((Integer)session.getAttribute("patientId"), patient);
			
			//update schedule
			Schedule newSchedule = new Schedule();
			newSchedule.setDate(selectedDate);
			newSchedule.setTime_start(patientAppointmentUpdate.getTime());
			newSchedule.setTime_end(patientAppointmentUpdate.getTime().plusMinutes(210));
			newSchedule.setStaff(selectedStaff);
			newSchedule.setPatient_slot(1);
			scheduleService.createShedule(newSchedule);
			model.addAttribute("successMessage", "Appointment Succesful!"); 
			
		}else if(!dateAvailable) {
			boolean staffAvailable = false;
			System.out.println("selectedDate: " + selectedDate);
			
			if(selectedTime.equals(morning)) {
				morningSlots = scheduleService.findMaxPatientSlotByTimeStart(morning, staffId, selectedDate);
				if (morningSlots==null) {
					morningSlots =0;
				}
				if(morningSlots < 20) {
					staffAvailable = true;
				}
				
			} else if(selectedTime.equals(afternoon)) {
				afternoonSlots = scheduleService.findMaxPatientSlotByTimeStart(afternoon, staffId, selectedDate);
				if (afternoonSlots==null) {
					afternoonSlots =0;
				}
				if(afternoonSlots < 20) {
					staffAvailable = true;
				}
			}
			
			System.out.println("staffAvailable? " + staffAvailable);
			System.out.println("morningSlots: " + morningSlots);
			System.out.println("afternoonSlots: " + afternoonSlots);
			if(staffAvailable) {
				//Get slot max
				int slot = 0;
				if(selectedTime.equals(morning)) {
					slot = morningSlots + 1;
				}else if(selectedTime.equals(afternoon)) {
					slot = afternoonSlots + 1;
				}
				//Add appointment
				patientAppointmentUpdate.setDate(selectedDate);
				patientAppointmentUpdate.setTime(selectedTime);
				patientAppointmentUpdate.setQueue_number(afternoonSlots);
				patientAppointmentUpdate.setStatus(AppointmentStatusEnum.Proceeding);
				patientAppointmentUpdate.setStaff(selectedStaff);
				patientAppointmentUpdate.setPatient(patient);
//				patientAppointmentUpdate.setDepartment(department);
				patientAppointmentUpdate.setQueue_number(slot + staffId);
				//Save appointment
				appointmentService.createAppointment(patientAppointmentUpdate);
				
				//Update patient medical condition
				patient.setMedical_condition(medCondition);
				patientService.updatePatient((Integer)session.getAttribute("patientId"), patient);
				
				//update schedule
				Schedule newSchedule = new Schedule();
				newSchedule.setDate(selectedDate);
				newSchedule.setTime_start(patientAppointmentUpdate.getTime());
				newSchedule.setTime_end(patientAppointmentUpdate.getTime().plusMinutes(210));
				newSchedule.setStaff(selectedStaff);
				newSchedule.setPatient_slot(slot);
				scheduleService.createShedule(newSchedule);
				
				//Send Server response
				response.setStatus(HttpServletResponse.SC_CREATED);
				model.addAttribute("successMessage", "Appointment Succesful!"); 
			}else if(!staffAvailable) {
				//TODO:redirect back to makeAppointment
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				List<Staff> availableStaff = staffService.findStaffByDepartmentId(department.getId());
				model.addAttribute("departmentStaff",availableStaff);
				model.addAttribute("newAppointment", new Appointment());
				model.addAttribute("prediction", session.getAttribute("prediction"));
				model.addAttribute("patientSymptoms", session.getAttribute("symptomString"));
				System.out.println("DepartmentStaff:" + availableStaff);
				model.addAttribute("alertMessage", "No staff available! Please choose another time slot or staff"); 
				return "makeAppointment";
			}
		}
		
		return "redirect:patient/" + patientId;
		
		
	}
	


	
	

}
