 package sg.nus.iss.adproject.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
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
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private StaffService staffService;

	public void setService(FeedbackServiceImpl feedbackService, AppointmentServiceImpl appointmentService,
			StaffServiceImpl staffService) {
		this.appointmentService = appointmentService;
		this.feedbackService = feedbackService;
		this.staffService = staffService;
	}

	@GetMapping("")
	public String showDashboard( Model model,HttpSession sessionObj) {
		Staff doctor=(Staff) sessionObj.getAttribute("staffObj");
		List<Appointment>appointments=doctor.getAppointments();
		int staffId=doctor.getId();
		
		List<Appointment>filteredAppointments=new ArrayList<>();
		AppointmentStatusEnum status=AppointmentStatusEnum.Proceeding;
		
		LocalDate currentDate=LocalDate.now();
		
		for(Appointment appointment:appointments) { 
			if((appointment.getStatus().compareTo(status)==0)&&(appointment.getDate().equals(currentDate)))
				filteredAppointments.add(appointment);
			
		}
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

	
}
