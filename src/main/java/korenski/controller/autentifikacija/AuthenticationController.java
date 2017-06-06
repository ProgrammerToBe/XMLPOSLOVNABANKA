/*<<<<<<< HEAD
package korenski.controller.autentifikacija;

import javax.servlet.http.HttpServletRequest;
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

import korenski.model.autorizacija.Role;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.Bank;
import korenski.repository.autorizacija.RoleRepository;
import korenski.repository.autorizacija.UserRepository;
import korenski.repository.institutions.BankRepository;
import korenski.service.autorizacija.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService userService;

	
	
	@Autowired 
	BankRepository bankRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping(
			value = "/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginObject> loginUser(@RequestBody LoginObject loginObject , @Context HttpServletRequest request) throws Exception {
		
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
			
				boolean valid = userService.authenticate(loginObject.getPassword(), user.getPassword(), user.getSalt());
				
				if(!valid){
					loginObject.setId(-1);
					return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
				}else{
					request.getSession().setAttribute("user", user);
					
					if(!user.isChangedFirstPassword()){
						loginObject.setId(-5);
						loginObject.setUrl("http://localhost:8080/authentification/change.html");
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


	@RequestMapping(
			value = "/loginDummy",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(1));
		Role role = roleRepository.findOne(new Long(1));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	

	@RequestMapping(
			value = "/loginDummy2",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser2(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(2));
		Role role = roleRepository.findOne(new Long(2));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/loginDummy3",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser3(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(2));
		Role role = roleRepository.findOne(new Long(2));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/loginDummy4",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser4(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(2));
		Role role = roleRepository.findOne(new Long(3));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
======= */
package korenski.controller.autentifikacija;

import javax.servlet.http.HttpServletRequest;
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
import korenski.model.autorizacija.Role;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.Bank;
import korenski.repository.autorizacija.RoleRepository;
import korenski.repository.autorizacija.UserRepository;
import korenski.repository.institutions.BankRepository;
import korenski.service.autorizacija.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService userService;
	
	
	@Autowired 
	BankRepository bankRepository;
	@Autowired
	RoleRepository roleRepository;

//	@RequestMapping(
//			value = "/special/loginSubject",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<LoginObject> loginUser(@RequestBody LoginObject loginObject , @Context HttpServletRequest request) throws Exception {
//		
//		User userFromSession  = (User) request.getSession().getAttribute("user");
//		
//		if(userFromSession != null){
//			loginObject.setId(-2);
//			return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//		}
//		
//		User user = null;
//		try {
//			user = repository.findByUsername(loginObject.getUsername());
//			
//			boolean expired = userService.checkPasswordExpiration(user);
//			
//			if(expired){
//				loginObject.setId(-4);
//				return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//			}
//			
//			if(user != null){
//			
//				boolean valid = userService.authenticate(loginObject.getPassword(), user.getPassword(), user.getSalt());
//				
//				if(!valid){
//					loginObject.setId(-1);
//					return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//				}else{
//					request.getSession().setAttribute("user", user);
//					
//					if(!user.isChangedFirstPassword()){
//						loginObject.setId(-5);
//						String url = "";
//						String scheme = request.getScheme();
//						String host = request.getServerName();
//						int port = request.getServerPort();
//						url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/authentification/change.html");
//						
//						loginObject.setUrl(url);
//						return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//					}else{
//						if(user.getRole().getName().equals("ADMINISTRATOR_BANK")){
//							
//							String url = "";
//							String scheme = request.getScheme();
//							String host = request.getServerName();
//							int port = request.getServerPort();
//							url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/adminResources/AdminPage.html");
//							loginObject.setId(-5);
//							loginObject.setUrl(url);
//							return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//						}else if(user.getRole().getName().equals("COUNTER_OFFICER") ||
//								 user.getRole().getName().equals("MANAGER")){
//							
//							
//							String url = "";
//							String scheme = request.getScheme();
//							String host = request.getServerName();
//							int port = request.getServerPort();
//							url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/inicijalizacija/InitPage.html");
//							loginObject.setId(-5);
//							loginObject.setUrl(url);
//							return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//						}
//					}
//					
//					return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//				}
//			}else{
//				loginObject.setId(-1);
//				return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			loginObject.setId(-3);
//			return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//		}
//	
//		//return new ResponseEntity<LoginObject>(loginObject, HttpStatus.OK);
//	}


	@RequestMapping(
			value = "/loginDummy",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(1));
		Role role = roleRepository.findOne(new Long(1));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	/*
	@RequestMapping(
			value = "/loginDummy2",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser2(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(2));
		Role role = roleRepository.findOne(new Long(2));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	*/
	
	@RequestMapping(
			value = "/loginDummy2",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser2(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(1));
		Role role = roleRepository.findOne(new Long(1));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	
	@RequestMapping(
			value = "/loginDummy3",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser3(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(2));
		Role role = roleRepository.findOne(new Long(2));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/loginDummy4",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> loginDummyUser4(@Context HttpServletRequest request) throws Exception {
		
		Bank bank = bankRepository.findOne(new Long(2));
		Role role = roleRepository.findOne(new Long(3));
		
		
		User user = new User();
		user.setBank(bank);
		user.setRole(role);
		
		request.getSession().setAttribute("user", user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}

