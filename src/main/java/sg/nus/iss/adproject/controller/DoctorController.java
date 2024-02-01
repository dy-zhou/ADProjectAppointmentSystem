package sg.nus.iss.adproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.service.AppointmentServiceImpl;
import sg.nus.iss.adproject.service.FeedbackServiceImpl;

@Controller
@RequestMapping("/Doctor")
public class DoctorController {
	//view appointment
	//view feedback
	//view/edit patient detail
	
	@Autowired
	private FeedbackService feedbackService;
	private AppointmentService appointmentService;
	
	public void setService(FeedbackServiceImpl feedbackService, AppointmentServiceImpl appointmentService) {
		this.appointmentService=appointmentService;
		this.feedbackService=feedbackService;
	}
	
	@GetMapping("/Docctor/{id}")
	public String showDashboard(@PathVariable("id")int id, Model model) {
		List<Appointment>appointmentList=appointmentService.findAppointmentByStaffId(id);
		List<Feedback>feedbackList=feedbackService.findFeedbacksByStaffId(id);
		model.addAttribute("feedbackList",feedbackList);
		model.addAttribute("appointmentList", appointmentList);
		return "homePage_Doctor";
	}

}
