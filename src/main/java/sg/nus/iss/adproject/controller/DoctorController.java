package sg.nus.iss.adproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.adproject.interfacemethods.AppointmentService;
import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.Feedback;
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

	public void setService(FeedbackServiceImpl feedbackService, AppointmentServiceImpl appointmentService,
			StaffServiceImpl staffService) {
		this.appointmentService = appointmentService;
		this.feedbackService = feedbackService;
		this.staffService = staffService;
	}

	@GetMapping("/Docctor/{id}")
	public String showDashboard(@PathVariable("id") int id, Model model) {
		List<Appointment> appointmentList = appointmentService.findAppointmentByStaffId(id);
		//List<Feedback> feedbackList = feedbackService.findFeedbacksByStaffId(id);
		//change to show first 15 feedback
		List<Feedback> feedbackList = feedbackService.findTop15Feedbacks(id);
		model.addAttribute("feedbackList", feedbackList);
		model.addAttribute("appointmentList", appointmentList);
		return "homePage_Doctor";
	}

	// after login ,get this doctor's feedback
	@GetMapping("/doctorFeedbacks")
	public String showDoctorFeedbacks(Model model, HttpSession session) {
		int staffId = (int) session.getAttribute("staffId");

		String staffName = staffService.getStaffNameById(staffId);

		List<Feedback> doctorFeedbackList = feedbackService.findFeedbacksByStaffId(staffId);

		model.addAttribute("doctorFeedbackList", doctorFeedbackList);
		model.addAttribute("staffName", staffName);

		return "doctorFeedbackList";
	}

	@GetMapping("feedbackDetails/{id}")
	public String showFeedbackDetails(@PathVariable("id") int feedbackId, Model model, HttpSession session) {

		int staffId = (int) session.getAttribute("staffId");

		Feedback feedbackDetails = feedbackService.getFeedbackDetail(feedbackId);
//make sure doctor can only see himself's feedback
		if (feedbackDetails != null && feedbackDetails.getAppointments() != null
				&& feedbackDetails.getAppointments().getStaffs() != null) {

			int feedbackStaffId = feedbackDetails.getAppointments().getStaffs().getId();

			if (feedbackStaffId == staffId) {
				model.addAttribute("feedbackDetails", feedbackDetails);
				return "feedbackDetail";
			}
		}

		return "redirect:/Doctor/doctorFeedbacks";
	}
}
