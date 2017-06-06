package korenski.controller.autorizacija;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.controller.autentifikacija.pomocneKlase.PasswordChanging;
import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.Role;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.Bank;
import korenski.model.klijenti.Employee;
import korenski.model.klijenti.PravnoLice;
import korenski.repository.autorizacija.RoleRepository;
import korenski.repository.autorizacija.UserRepository;
import korenski.repository.institutions.BankRepository;
import korenski.repository.klijenti.EmployeeRepository;
import korenski.repository.klijenti.PravnoLiceRepository;
import korenski.service.autorizacija.UserService;
import korenski.singletons.ValidatorSingleton;

@Controller
public class UserController {


	@Autowired
	UserRepository repository;
	@Autowired
	UserService userService;
	@Autowired
	BankRepository bankRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PravnoLiceRepository pravnoRepository;
	@Autowired
	RoleRepository roleRepository;
	
//	@CustomAnnotation(value = "INSERT_USER")
//	@RequestMapping(
//			value = "/newUser",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<User> newUser(@RequestBody User user , @Context HttpServletRequest request) throws Exception {
//		
//
//		if(!userService.checkInput(user)){
//			return new ResponseEntity<User>(new User(new Long(-1), null, null, null, null, null, null, null), HttpStatus.OK);
//		}
//		
//		String pass = userService.generatePassword();
//		
//		System.out.println("============================================Generated pass " + pass +" size : "+pass.length());
//		
//		User sessionUser = (User) request.getSession().getAttribute("user");
//		Bank bank = bankRepository.findOne(sessionUser.getBank().getId());
//		
//		User rle;
//		try {
//			
//			userService.sendPassToUser(pass, user);
//			user = userService.handleThePassword(user, pass);
//			
//			
//			user.setBank(bank);
//			Employee employee = employeeRepository.findOne(user.getSubject().getId());
//			user.setSubject(employee);
//			Date current = new Date();
//			
//			user.setCreationTime(new java.sql.Date(current.getTime()));
//			
//			User validity = validityCheck(user);
//			if(validity != null){
//				return new ResponseEntity<User>(validity, HttpStatus.OK);
//			}
//			rle = repository.save(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//			rle = new User(new Long(-1), "Greska pri upisu u bazu!", null, null, null, null, null, null);
//		}
//	
//		return new ResponseEntity<User>(rle, HttpStatus.OK);
//	}
//	
//	
//	@CustomAnnotation(value = "INSERT_USER")
//	@RequestMapping(
//			value = "/newLegalUser",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<User> newLegalUser(@RequestBody User user , @Context HttpServletRequest request) throws Exception {
//		
//
//		if(!userService.checkInput(user)){
//			return new ResponseEntity<User>(new User(new Long(-1), null, null, null, null, null, null, null), HttpStatus.OK);
//		}
//		
//		String pass = userService.generatePassword();
//		
//		System.out.println("============================================Generated pass " + pass +" size : "+pass.length());
//		
//		User sessionUser = (User) request.getSession().getAttribute("user");
//		Bank bank = bankRepository.findOne(sessionUser.getBank().getId());
//		
//		User rle;
//		try {
//			
//			userService.sendPassToUser(pass, user);
//			user = userService.handleThePassword(user, pass);
//			
//			
//			user.setBank(bank);
//			PravnoLice pravno = pravnoRepository.findOne(user.getSubject().getId());
//			user.setSubject(pravno);
//			Date current = new Date();
//			
//			user.setCreationTime(new java.sql.Date(current.getTime()));
//			
//			User validity = validityCheck(user);
//			if(validity != null){
//				return new ResponseEntity<User>(validity, HttpStatus.OK);
//			}
//			rle = repository.save(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//			rle = new User(new Long(-1), "Greska pri upisu u bazu!", null, null, null, null, null, null);
//		}
//	
//		return new ResponseEntity<User>(rle, HttpStatus.OK);
//	}
//	
//	
//	@RequestMapping(
//			value = "/special/newUser",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<User> newUser2(@RequestBody User user , @Context HttpServletRequest request) throws Exception {
//		
//
//		if(!userService.checkInput(user)){
//			return new ResponseEntity<User>(new User(new Long(-1), null, null, null, null, null, null, null), HttpStatus.OK);
//		}
//		
//		String pass = userService.generatePassword();
//		
//		System.out.println("============================================Generated pass " + pass +" size : "+pass.length());
//		
//		//User sessionUser = (User) request.getSession().getAttribute("user");
//		Bank bank = bankRepository.findOne(user.getBank().getId());
//		
//		User rle;
//		try {
//			
//			//userService.sendPassToUser(pass, user);
//			user = userService.handleThePassword(user, pass);
//			
//			
//			user.setBank(bank);
//			Employee employee = employeeRepository.findOne(user.getSubject().getId());
//			user.setSubject(employee);
//			Date current = new Date();
//			
//			user.setCreationTime(new java.sql.Date(current.getTime()));
//			
//			User validity = validityCheck(user);
//			if(validity != null){
//				return new ResponseEntity<User>(validity, HttpStatus.OK);
//			}
//			rle = repository.save(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//			rle = new User(new Long(-1), "Greska pri upisu u bazu!", null, null, null, null, null, null);
//		}
//	
//		return new ResponseEntity<User>(rle, HttpStatus.OK);
//	}
	
	
	@CustomAnnotation(value = "INSERT_USER")
	@RequestMapping(
			value = "/newUser",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> newUser(@RequestBody User user , @Context HttpServletRequest request) throws Exception {
		

		
		
		
		User sessionUser = (User) request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(sessionUser.getBank().getId());
		
		User rle;
		try {
			user.setBank(bank);
			Employee employee = employeeRepository.findOne(user.getSubject().getId());
			user.setSubject(employee);
			Date current = new Date();
			user.setPassword("password");
			user.setCreationTime(new java.sql.Date(current.getTime()));
			
			User validity = validityCheck(user);
			if(validity != null){
				return new ResponseEntity<User>(validity, HttpStatus.OK);
			}
			
			rle = repository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			rle = new User("Greska pri upisu u bazu!", null, null, null, null, null, null);
		}
	
		return new ResponseEntity<User>(rle, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "DELETE_USER")
	@RequestMapping(
			value = "/deleteUser/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<User> deleteUser(@PathVariable("id") Long id , @Context HttpServletRequest request) throws Exception {

		User User = repository.findOne(id);
		
		try {
			repository.delete(User);
		} catch (Exception e) {
			return new ResponseEntity<User>(new User(null, null, null, null, null, null, null), HttpStatus.OK);
		}
	
		return new ResponseEntity<User>(new User(), HttpStatus.OK);
	}

	
	@CustomAnnotation(value = "UPDATE_USER")
	@RequestMapping(
			value = "/updateUser",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@RequestBody User user , @Context HttpServletRequest request) throws Exception {
		
		User sessionUser = (User) request.getSession().getAttribute("user");
		
		User validity = validityCheck(user);
		if(validity != null){
			return new ResponseEntity<User>(validity, HttpStatus.OK);
		}
		
		User userToModify = null;
		
		try {
			userToModify = repository.findOne(user.getId());
		} catch (Exception e) {
			return new ResponseEntity<User>(new User(null, null, null, null, null, null, null), HttpStatus.OK);
		}
		
		Bank bank = bankRepository.findOne(sessionUser.getBank().getId());
		
		userToModify.setUsername(user.getUsername());
		userToModify.setPassword(user.getPassword());
		userToModify.setEmail(user.getEmail());
		userToModify.setRole(user.getRole());
		userToModify.setBank(bank);
		
		try {
			userToModify = repository.save(userToModify);
		} catch (Exception e) {
			return new ResponseEntity<User>(new User("Greska pri upisu u bazu!", null, null, null, null, null, null), HttpStatus.OK);
		}

		return new ResponseEntity<User>(userToModify, HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FIND_ALL_USER")
	@RequestMapping(
			value = "/allUsers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<User>> allUsers(@Context HttpServletRequest request) throws Exception {

		User user = (User)request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(user.getBank().getId());
		Role role = roleRepository.findByName("LEGAL");
		
		return new ResponseEntity<Collection<User>>( repository.findAllButLegal(bank.getId(), role.getId()), HttpStatus.OK);
	}
	
	@CustomAnnotation(value = "FIND_ALL_USER")
	@RequestMapping(
			value = "/allLegalUsers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<User>> allLegalUsers(@Context HttpServletRequest request) throws Exception {

		
		User user = (User)request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(user.getBank().getId());
		Role role = roleRepository.findByName("LEGAL");
		
		return new ResponseEntity<Collection<User>>( repository.findByBankAndRole(bank, role), HttpStatus.OK);
	}
	
	
//	@CustomAnnotation(value = "PASSWORD_CHANGE_USER")
//	@RequestMapping(
//			value = "/passwordChange",
//			method = RequestMethod.POST,
//			consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<User> passwordChange(@RequestBody PasswordChanging data , @Context HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		User userfromsession = (User) request.getSession().getAttribute("user");
//		
//		User user = null;
//		
//		if(userfromsession == null){
//			String url = "";
//			String scheme = request.getScheme();
//			String host = request.getServerName();
//			int port = request.getServerPort();
//			url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/authentification/login.html");
//			user = new User();
//			user.setId(new Long(-5));
//			user.setUsername(url);
//			return  new ResponseEntity<User>(user, HttpStatus.OK);
//		}
//		
//		if(!userfromsession.getUsername().equals(data.getUsername())){
//			//posalji poruku da se ne podudaraju ulogovani korisnik i uneti podaci
//			user = new User();
//			user.setId(new Long(-2));
//			new ResponseEntity<User>(user, HttpStatus.OK);
//		}
//
//		
//		
//		try {
//			user = repository.findByUsername(data.getUsername());
//			boolean valid = userService.authenticate(data.getPassword(), user.getPassword(), user.getSalt());
//			
//			if(!valid){
//				user.setId(new Long(-1));
//				return new ResponseEntity<User>(user, HttpStatus.OK);
//			}else{
//				
//				
//				user = userService.handleThePassword(user, data.getNewPassword());
//				
//				if(!user.isChangedFirstPassword()){
//					user.setChangedFirstPassword(true);
//				}
//				user = repository.save(user);
//				
//				
//				
//				String url = "";
//				String scheme = request.getScheme();
//				String host = request.getServerName();
//				int port = request.getServerPort();
//				url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/authentification/login.html");
//				user = new User();
//				user.setId(new Long(-5));
//				user.setUsername(url);
//				
//				request.getSession().setAttribute("user", null);
//				
//				request.getSession().invalidate();
//				return new ResponseEntity<User>(user, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<User>(user, HttpStatus.OK);
//		}
//		
//		//return new ResponseEntity<String>("Ulogovan!", HttpStatus.OK);
//		
//		
//	}
/*	
	@RequestMapping(
			value = "/logoff",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> logoff(@Context HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = (User) request.getSession().getAttribute("user");
		
		request.getSession().setAttribute("user", null);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
		
	}
	
	*/
	
	@CustomAnnotation(value = "PASSWORD_CHANGE_USER")
	@RequestMapping(
			value = "/passwordChange",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> passwordChange(@RequestBody PasswordChanging data , @Context HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User userfromsession = (User) request.getSession().getAttribute("user");
		User user = null;
		
		if(userfromsession == null){
			//redirektuj na login.html
			String url = "";
			String scheme = request.getScheme();
			String host = request.getServerName();
			int port = request.getServerPort();
			url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/authentification/login.html");
			user = new User();
			user.setId(new Long(-5));
			user.setUsername(url);
			return  new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		if(!userfromsession.getUsername().equals(data.getUsername())){
			//posalji poruku da se ne podudaraju ulogovani korisnik i uneti podaci
			user = new User();
			user.setId(new Long(-2));
			new ResponseEntity<User>(user, HttpStatus.OK);
		}

		
		
		try {
			user = repository.findByUsername(data.getUsername());
			
			if(!userfromsession.getPassword().equals(user.getPassword())){
				user.setId(new Long(-1));
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}else{
				
				user.setChangedFirstPassword(true);
				user.setPassword(data.getNewPassword());
				user = repository.save(user);
			
				request.getSession().invalidate();
				
				String url = "";
				String scheme = request.getScheme();
				String host = request.getServerName();
				int port = request.getServerPort();
				url = url.concat(scheme).concat("://").concat(host).concat(":"+Integer.toString(port)).concat("/authentification/login.html");
				user = new User();
				user.setId(new Long(-5));
				user.setUsername(url);
				
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		//return new ResponseEntity<String>("Ulogovan!", HttpStatus.OK);
		
		
	}
	
	
	public User validityCheck(User user){
		Set<ConstraintViolation<User>> violations = ValidatorSingleton.getInstance().getValidator().validate(user);
		
		if(!violations.isEmpty()){
			Iterator iter = violations.iterator();

			ConstraintViolation<User> first = (ConstraintViolation<User>) iter.next();
			User u = new User(new Long(-1), first.getMessage());
			return u;
		}else{
			return null;
		}
	}
}
