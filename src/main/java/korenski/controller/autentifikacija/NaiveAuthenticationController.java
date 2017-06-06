package korenski.controller.autentifikacija;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.controller.autentifikacija.pomocneKlase.LoginObject;
import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.Permission;
import korenski.model.autorizacija.User;
import korenski.repository.autorizacija.RoleRepository;
import korenski.repository.autorizacija.UserRepository;
import korenski.repository.institutions.BankRepository;
import korenski.service.autorizacija.UserService;

@Controller
public class NaiveAuthenticationController {

	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService userService;
	
	
	@Autowired 
	BankRepository bankRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping(
			value = "/special/loginSubject",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginObject> loginUserSubject(@RequestBody LoginObject loginObject , @Context HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User userFromSession  = (User) request.getSession().getAttribute("user");
		
		if(userFromSession != null){
			loginObject.setId(-2);
			return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
		}
		
		User user = null;
		try {
			user = repository.findByUsername(loginObject.getUsername());
			
			boolean expired = userService.checkPasswordExpiration(user);
			
			if(expired){
				loginObject.setId(-4);
				return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
			}
			
			if(user != null){
			
				boolean valid = user.getPassword().equals(loginObject.getPassword());
				
				if(!valid){
					loginObject.setId(-1);
					return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
				}else{
					request.getSession().setAttribute("user", user);
					
					if(!user.isChangedFirstPassword()){
						loginObject.setId(-5);
						
						String url = "";
						String scheme = request.getScheme();
						String host = request.getServerName();
						int port = request.getServerPort();
						url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/authentification/change.html");
						
						loginObject.setUrl(url);
						return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
						//request.getRequestDispatcher("/authentification/change.html").forward(request, response);
					}else{
						if(user.getRole().getName().equals("ADMINISTRATOR_BANK")){
							
							String url = "";
							String scheme = request.getScheme();
							String host = request.getServerName();
							int port = request.getServerPort();
							url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/adminResources/AdminPage.html");
							loginObject.setId(-5);
							loginObject.setUrl(url);
							return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
						}else if(user.getRole().getName().equals("COUNTER_OFFICER") ||
								 user.getRole().getName().equals("MANAGER")){
							
							
							String url = "";
							String scheme = request.getScheme();
							String host = request.getServerName();
							int port = request.getServerPort();
							url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/inicijalizacija/InitPage.html");
							loginObject.setId(-5);
							loginObject.setUrl(url);
							return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
						}
					}
					
					
					return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
				}
			}else{
				loginObject.setId(-1);
				return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
			}
		} catch (Exception e) {
			loginObject.setId(-3);
			return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
		}
	
		//return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
	}


	private String checkPermission(User user){
		
		Collection<Permission> permissions = user.getRole().getPermissions();
		
		for(Permission permission : permissions){
			if(permission.getName().equals("ADMINPAGE")){
				return "/adminResources/AdminPage.html";
			}else if(permission.getName().equals("EMPLOYEEPAGE")){
				return "/inicijalizacija/InitPage.html";
			}else if(permission.getName().equals("CLIENTPAGE")){
				return "/index.html";
			}
		}
		
		return "";
	}
	
	@CustomAnnotation(value = "LOGOFF")
	@RequestMapping(
			value = "/logoff",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> logoff(@Context HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = (User) request.getSession().getAttribute("user");
		
		request.getSession().setAttribute("user", null);
		
		String redirect = checkPermission(user);
		Cookie myCookie =new Cookie("XSRF-TOKEN", null);
		response.addCookie(myCookie);
		
		
		user = new User();
		String url = "";
		String scheme = request.getScheme();
		String host = request.getServerName();
		int port = request.getServerPort();
		url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/authentification/login.html");
		
		
		user.setUsername(url);
		request.getSession().invalidate();
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
		
	}
}
