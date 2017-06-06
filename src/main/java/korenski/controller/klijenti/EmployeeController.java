package korenski.controller.klijenti;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import korenski.intercepting.CustomAnnotation;
import korenski.model.autorizacija.User;
import korenski.model.infrastruktura.Bank;
import korenski.model.klijenti.Employee;
import korenski.repository.institutions.BankRepository;
import korenski.repository.klijenti.EmployeeRepository;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;
	@Autowired 
	BankRepository bankRepository;
	
	@CustomAnnotation(value = "FIND_ALL_EMPLOYEE")
	@RequestMapping(
			value = "/sviZaposleni",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Employee>> sviZaposleni(@Context HttpServletRequest request) throws Exception {
		User u = (User)request.getSession().getAttribute("user");
		Bank bank = bankRepository.findOne(u.getBank().getId());
		
		return new ResponseEntity<Collection<Employee>>( repository.findByBank(bank), HttpStatus.OK);
	}
	
	
}
