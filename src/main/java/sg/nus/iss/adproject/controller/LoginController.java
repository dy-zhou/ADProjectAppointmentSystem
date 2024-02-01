package sg.nus.iss.adproject.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.iss.adproject.interfacemethods.StaffService;
import sg.nus.iss.adproject.model.Staff;
import sg.nus.iss.adproject.service.StaffServiceImpl;


@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private StaffService staffService;
	
	@Autowired
	public void setService(StaffServiceImpl staffService) {
		this.staffService=staffService;
	}
	@GetMapping("")
	public String login(Model model) 
	{
		Staff staff = new Staff();
		model.addAttribute("staff", staff);
		return "login";
	}
	
	@PostMapping("")
	public String login(@Valid @ModelAttribute("staff") Staff staff,BindingResult bindingresult,Model model,@RequestParam("keyword") String keyword, HttpSession sessionobj) {
		if(bindingresult.hasErrors()) {
			return "login";
		}
		Staff rlstaff = staffService.Authentication(staff.getName(), staff.getPassword());
		if(rlstaff != null) {
			sessionobj.setAttribute("staffid", rlstaff.getId());
			sessionobj.setAttribute("username", rlstaff.getName());
			sessionobj.setAttribute("staffDesignation", rlstaff.getDesignation());
			String designation=(String)sessionobj.getAttribute("staffDesignation");
			
			if(designation.compareToIgnoreCase("nurse")==0) {
				return "homePage_Nurse";
			}
			else if(designation.compareToIgnoreCase("doctor")==0) {
				return "homePage_Doctor";
			}
			else{
				return "homePage_Manager";
			}
		}
		 else
				return "error";
		
	}	
		
}
