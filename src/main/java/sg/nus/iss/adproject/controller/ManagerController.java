package sg.nus.iss.adproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.nus.iss.adproject.interfacemethods.FeedbackService;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.model.Appointment;
import sg.nus.iss.adproject.model.Feedback;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.service.FeedbackServiceImpl;
import sg.nus.iss.adproject.service.StaffServiceImpl;

@Controller
@RequestMapping("Manager")
public class ManagerController {
	// view feedback

	@Autowired
	private FeedbackService feedbackService;
	private StaffService staffService;

	public void setFeedbackService(FeedbackServiceImpl feedbackService, StaffServiceImpl staffService) {
		this.feedbackService = feedbackService;
		this.staffService = staffService;
	}

	@GetMapping
	public String showDashboard(Model model) {
		List<Feedback> feedbackList = feedbackService.findAllFeedbacks();
		model.addAttribute("feedbackList", feedbackList);
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
		return "managerFeedbackList";
	}

	@GetMapping("/doctorFeedbacks/{id}")
	public String showDoctorFeedbacks(@PathVariable("id") int doctorId, Model model) {
		List<Feedback> doctorFeedbackList = feedbackService.findFeedbacksByStaffId(doctorId);
		Staff staff=staffService.findStaffById(doctorId);
		String staffName=staff.getName();
		model.addAttribute("doctorFeedbackList", doctorFeedbackList);
		//show which doctor's feedback
		model.addAttribute("staffName", staffName);
		
		return "managerDoctorFeedbackList";
	}

	@GetMapping("feedbackDetails/{id}")
	public String showFeedbackDetails(@PathVariable("id") int feedbackId, Model model) {
		Feedback feedbackDetails = feedbackService.getFeedbackDetail(feedbackId);
		model.addAttribute("feedbackDetails", feedbackDetails);
		return "managerSeeDoctorFeedbackDetails";
	}

}
