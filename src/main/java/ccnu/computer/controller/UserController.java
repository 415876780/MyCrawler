package ccnu.computer.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ccnu.computer.common.Constant;
import ccnu.computer.common.LoginInfo;
import ccnu.computer.model.UserInfo;
import ccnu.computer.service.UserService;

@Controller  
@RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
public class UserController {  
	
    @Resource  
    private UserService userService;  
      
    
    @RequestMapping("/userlogin")
	public String login() {
		return "login";
	}
    
	@RequestMapping("/dologin")
	@ResponseBody
	public String dologin(HttpServletRequest request,HttpServletResponse response) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String isRememberUser = request.getParameter("isRememberUser");

		if (userName == null || password == null) {
			return "请求参数丢失，请重试！";
		}

		/**
		 * 验证用户名和密码是否正确，如不正确，直接返回字符串“用户名或密码不正确” 如果正确则，设置用户信息、角色信息，权限信息到session中.
		 */
		UserInfo loginVO = userService.selectUserByname(userName);
		
		if (loginVO != null) {
			/*
			if (loginVO.getRoleId() == null) {
				return "用户角色信息缺失，请联系系统管理员!";
			}*/
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setIp(request.getRemoteAddr());
			loginInfo.setLoginName(userName);
			loginInfo.setLoginId(loginVO.getId());
			request.getSession().setAttribute(Constant.Session_LoginInfo, loginInfo);
			//super.httpSession.setAttribute(Constant.Session_LoginInfo, loginInfo);			
			if ("true".equals(isRememberUser)) {
				Cookie nameCookie = new Cookie("username", userName);
				// 设置Cookie的有效期为1天
				nameCookie.setMaxAge(60 * 60 * 24 * 1);
				response.addCookie(nameCookie);
			}

			return "";
		} else {
			return "用户名或密码不正确";
		}

	}
    
    @RequestMapping("/login")    
    public String getIndex(Model model){      
        //ModelAndView mav = new ModelAndView("index");   
        UserInfo userinfo = userService.selectUserById(2);  
        
        /*List<UserInfo> userinfo = userService.selectAll();
        UserInfo user = new UserInfo();
        user.setUsername("jackk");
        user.setPassword("adf");*/
        model.addAttribute("user", userinfo);
       // userService.insert(user);
        //mav.addObject("user", user);   
        return "login";    
    }    
}  
