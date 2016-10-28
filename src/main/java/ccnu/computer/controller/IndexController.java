package ccnu.computer.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import ccnu.computer.model.User;
import ccnu.computer.model.UserInfo;
import ccnu.computer.service.UserServiceImpl;
@Controller
@SessionAttributes("loginUser")
public class IndexController {
	@Resource
	private UserServiceImpl userService;
	
	public UserServiceImpl getUserService() {
		return userService;
	}
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
	@RequestMapping("/login")
	public String login(){	
		//return "redirect:/text/texts";
		return "index";
	}
	
/*	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,Model model){
		System.out.println(username+password);
		UserInfo u = userService.selectUserByname(username);
		if(u.getPassword().equals(password)){
			model.addAttribute("loginUser",u);
			return "redirect:/text/texts";
		}else{
			return "error";
		}
	}*/
	@RequestMapping("/logout")
	public String logout(Model model,HttpSession session){
		model.asMap().remove("loginUser");
		session.invalidate();
		return "redirect:/login";
	}
}
